package com.gene.IM.service;

import com.gene.IM.entity.Material;

import java.util.List;

public interface MaterialService {

    Material getMaterialById(long materialId);

    List<Material> find(Material material);

    int insertMaterial(Material material);

    int update(Material material);

    int deleteById(long materialId);
}
