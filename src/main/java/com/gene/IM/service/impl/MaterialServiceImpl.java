package com.gene.IM.service.impl;

import com.gene.IM.entity.Material;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public Material getMaterialById(long materialId) {
        return materialMapper.getMaterialById(materialId);
    }

    @Override
    public List<Material> find(Material material) {
        return materialMapper.find(material);
    }

    @Override
    public int insertMaterial(Material material) {
        return materialMapper.insertMaterial(material);
    }

    @Override
    public int update(Material material) {
        return materialMapper.update(material);
    }

    @Override
    public int deleteById(long materialId) {
        return materialMapper.deleteById(materialId);
    }
}
