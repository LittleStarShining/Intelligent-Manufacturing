package com.gene.IM.service;

import java.util.Map;

public interface LoginService {

    /***
     * 用户登录
     * @param userID 用户名
     * @param pwd 密码
     * @return
     * 登录结果
     * {
     *      code:
     *      desc:
     *      data:{
     *          token:
     *      }
     * }
     */
    public Map<String,Object> login(String userID, String pwd);

}


