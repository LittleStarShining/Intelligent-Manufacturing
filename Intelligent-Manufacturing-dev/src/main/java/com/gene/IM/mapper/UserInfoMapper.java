package com.gene.IM.mapper;

import com.gene.IM.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    public User getUserInfo(@Param("id") int id);
}
