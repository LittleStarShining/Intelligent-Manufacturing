package com.gene.IM.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InformationMapper {
    public void addInformation(String information);
}
