package com.gene.IM.service;

import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.entity.Material;

import java.util.List;

public interface MaterialService {

    Material getMaterialById(Integer materialId);

    List<Material> find(SelectMaterial material);

    int insertMaterial(Material material);

//    int update(Material material);
    Material update(Material material);

    int deleteById(Integer materialId);

    List<MaterialDTO> getInferById(Integer id);
}
