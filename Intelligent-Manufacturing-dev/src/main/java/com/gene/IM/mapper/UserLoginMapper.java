package com.gene.IM.mapper;

import com.gene.IM.DTO.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserLoginMapper {
    public UserLogin getUserLoginInfo_by_UserID(@Param("userID") String userID);

}
