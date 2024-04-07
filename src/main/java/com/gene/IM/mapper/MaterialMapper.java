package com.gene.IM.mapper;

import com.gene.IM.entity.Material;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialMapper {

    Material getMaterialById(long materialId);

    List<Material> find(Material material);

    int insertMaterial(Material material);

    int update(Material material);

    int deleteById(long materialId);
}