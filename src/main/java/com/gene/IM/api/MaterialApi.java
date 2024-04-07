package com.gene.IM.api;

import com.gene.IM.entity.Material;
import com.gene.IM.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialApi{

    private MaterialService materialService;

    @GetMapping("/{materialId}")
    public Material getMaterialById(@PathVariable long materialId) {
        return materialService.getMaterialById(materialId);
    }

    @GetMapping("/{materialId}")
    public List<Material> getMaterials(Material material) {
        return materialService.find(material);
    }

    @PostMapping
    public void addMaterial(@RequestBody Material material) {
        materialService.insertMaterial(material);
    }

    @PutMapping("/{materialId}")
    public void updateMaterial(@PathVariable long materialId, @RequestBody Material material) {
        material.setMaterialId(materialId);
        materialService.update(material);
    }

    @DeleteMapping("/{materialId}")
    public void deleteMaterial(@PathVariable long materialId) {
        materialService.deleteById(materialId);
    }
}
