package com.atwj.yygh.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.atwj.yygh.common.utils.JwtHelper;
import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.user.UserInfo;
import com.atwj.yygh.service.UserInfoService;
import com.atwj.yygh.utils.ConstantWxPropertiesUtils;
import com.atwj.yygh.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 微信登陆接口
 * @create 2022-10-10 14:23
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    //1 生成微信扫描二维码
    //返回生成二维码需要参数
    @GetMapping("getLoginParam")
    @ResponseBody
    public Result genQrConnect(HttpSession session) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        //重定向地址，需要进行UrlEncode
        String redirectUri = URLEncoder.encode(ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL, "UTF-8");
        map.put("redirectUri", redirectUri);
        //应用唯一标识，在微信开放平台提交应用审核通过后获得
        map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
        //应用授权作用域
        map.put("scope", "snsapi_login");
        //用于保持请求和回调的状态，授权请求后原样带回给第三方
        map.put("state", System.currentTimeMillis()+"");//System.currentTimeMillis()+""
        return Result.ok(map);
    }

    //微信扫描后回调的方法
    @GetMapping("callback")
    public String callback(String code, String state) {
        //第一步 获取临时票据 code
        System.out.println("code:" + code);

        //第二步 拿着code和微信id和秘钥，请求微信固定地址 ，得到两个值(openid、access_token)
        //2.1使用code和appid以及appscrect拼接请求地址
        //  %s   占位符
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
                code);

        //2.2使用httpclient请求这个地址
        String accesstokenInfo = null;
        try {
            accesstokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accesstokenInfo:" + accesstokenInfo);

            //2.3 从返回字符串获取两个值 openid  和  access_token
            JSONObject jsonObject = JSONObject.parseObject(accesstokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

            /**
             * 第三步 判断数据库是否存在微信的扫描人信息， 根据openid判断
             *       1.存在则不用重复存储用户信息
             *       2.不存在则存储用户
             */
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);
            //数据库不存在微信信息
            if (userInfo == null) {
                //拿着openid  和  access_token请求微信地址，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=%s" + "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultInfo:" + resultInfo);
                JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);

                //解析用户信息
                //用户昵称
                String nickname = resultUserInfoJson.getString("nickname");
                //用户头像
                String headimgurl = resultUserInfoJson.getString("headimgurl");

                //获取扫描人信息添加数据库
                userInfo = new UserInfo();
                userInfo.setNickName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userInfoService.save(userInfo);
            }

            //返回name和token字符串
            Map<String, String> map = new HashMap<>();
            String name = userInfo.getName();
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);

            /**
             * 判断userInfo是否有手机号:
             *      1.如果手机号为空，返回openid，前端从而进行判断openid不为空进而绑定手机号
             *      2.如果手机号不为空，返回openid值是空字符串，前端从而进行判断openid为空则不需要绑定手机号
             */
            if (StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }
            //使用jwt生成token字符串
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);
            //跳转到前端页面
            return "redirect:" + ConstantWxPropertiesUtils.YYGH_BASE_URL + "/weixin/callback?token=" + map.get("token") + "&openid=" + map.get("openid") + "&name=" + URLEncoder.encode(map.get("name"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
