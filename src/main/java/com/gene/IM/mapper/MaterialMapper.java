package com.gene.IM.mapper;

import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.entity.Material;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialMapper {

    Material getMaterialById(Integer materialId);

    List<Material> find(SelectMaterial material);

    int insertMaterial(Material material);

    int update(Material material);

    int deleteById(Integer materialId);
}