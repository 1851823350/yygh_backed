package com.atwj.yygh.service;

import com.atwj.yygh.model.user.UserInfo;
import com.atwj.yygh.vo.user.LoginVo;
import com.atwj.yygh.vo.user.UserAuthVo;
import com.atwj.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-09 12:05
 */
public interface UserInfoService extends IService<UserInfo> {
    //用户登陆-》手机号登陆
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据openid查询用户信息
    UserInfo selectWxInfoOpenId(String openid);

    //用户认证
    void userAuth(Long userId, UserAuthVo userAuthVo);

    //分页条件查询用固话列表
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    //鎖定用戶
    void lock(Long userId, Integer status);

    /**
     * 认证审批
     * @param userId
     * @param authStatus 2：通过 -1：不通过
     */
    void approval(Long userId, Integer authStatus);
}
