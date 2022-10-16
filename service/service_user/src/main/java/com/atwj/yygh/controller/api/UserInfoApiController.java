package com.atwj.yygh.controller.api;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.service.UserInfoService;
import com.atwj.yygh.utils.AuthContextHolder;
import com.atwj.yygh.vo.user.LoginVo;
import com.atwj.yygh.vo.user.UserAuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 用户管理
 * @create 2022-10-09 12:04
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String, Object> userLoginInfo = userInfoService.loginUser(loginVo);
        return Result.ok(userLoginInfo);
    }

    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }
}
