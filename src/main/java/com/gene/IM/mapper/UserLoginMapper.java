package com.gene.IM.mapper;

import com.gene.IM.DTO.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserLoginMapper {
    /**
     * 通过用户ID获取用户登录信息
     * @param userID 用户ID（账号）
     * @return
     *     UserLogin{
     *          int id;
     *          String name;
     *          String userID;
     *          String pwd;
     *          String token;
     *     }
     */
    public UserLogin getUserLoginInfo_by_UserID(@Param("userID") String userID);

}
