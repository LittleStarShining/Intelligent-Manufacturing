package com.gene.IM.service;

import java.util.Map;

import com.gene.IM.entity.Material;

import java.util.List;

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

    Material findById(long materialId);

    List<Material> find();

    int insert(Material material);

    int update(Material material);

    int deleteById(long materialId);


