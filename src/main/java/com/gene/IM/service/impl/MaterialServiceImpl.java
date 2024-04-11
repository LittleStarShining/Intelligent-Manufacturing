package com.gene.IM.service.impl;

import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.entity.Material;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("MaterialService")
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public Material getMaterialById(Integer materialId) {
        return materialMapper.getMaterialById(materialId);
    }

    @Override
    public List<Material> find(SelectMaterial material) {
        return materialMapper.find(material);
    }

    @Override
    public int insertMaterial(Material material) {
        try{
            return materialMapper.insertMaterial(material);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

//    @Override
//    public int update(Material material) {
//        return materialMapper.update(material);
//    }
@Override
public Material update(Material material) {
    if(materialMapper.update(material)>0){
        return getMaterialById(material.getMaterialId());
    }
    else return null;
}

    @Override
    public int deleteById(Integer materialId) {
        return materialMapper.deleteById(materialId);
    }

    @Override
    public List<MaterialDTO> getInferById(Integer id) {

        return materialMapper.getInferById(id);
    }
}
