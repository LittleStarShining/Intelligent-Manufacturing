package com.gene.IM.mapper;

import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.entity.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.sound.sampled.Line;
import java.util.List;

@Mapper
public interface MaterialMapper {

    Material getMaterialById(Integer materialId);

    List<Material> find(SelectMaterial material);

    int insertMaterial(Material material);

    int update(Material material);
    int decreaseMaterial(@Param("materialId") Integer materialId, @Param("num") Double num);
    List<MaterialDTO> getLineOrderMaterial(Integer lineID);
    int deleteById(Integer materialId);

    int updateMaterialNeed();

    List<MaterialDTO> getInferById(Integer id);

    // 看看原料是否足够
    public double getMaterialNeed(Integer materialId);

    public double[] getAllConsumes(Integer materialId);

    public void setIsRemind(Integer materialId);
}