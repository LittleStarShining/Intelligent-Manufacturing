package com.gene.IM.mapper;

import com.gene.IM.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    /**
     * 获取用户信息
     * @param id 用户ID（账号）
     * @return 用户基本信息
     * {
     *      "name":姓名,
     *      "userID":账号,
     *      "tel":电话,
     *      "factory":工厂,
     *      "headImage":头像
     * }
     */
    public User getUserInfo(@Param("id") int id);
}
