package com.gene.IM.api;

import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.Material;
import com.gene.IM.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialApi{

    @Autowired
    private MaterialService materialService;

    @NotNeedJWT
    @GetMapping("getid/{materialId}")
    public CommonResult<Material> getMaterialById(@PathVariable Integer materialId) {
        Material material = materialService.getMaterialById(materialId);
            return new CommonResult<Material>(material,"查询");
    }
    @NotNeedJWT
    @PostMapping("/get")

    public CommonResult<List<Material>> getMaterials(@RequestBody SelectMaterial material) {
        List<Material> result = (materialService.find(material));
        if(result.size()>0){
            //查询结果不为空
            return new CommonResult<List<Material>>(materialService.find(material),"查询");
        }
        //查询结果为空
        return new CommonResult<List<Material>>().failed(result, "查询结果为空，");

    }

    @NotNeedJWT
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> addMaterial(@RequestBody Material material) {
        CommonResult<Integer>response =new CommonResult<Integer>();
        int result = materialService.insertMaterial(material);
        if(result>0) {
            return response.success(result, "添加货源");
        }
        else {
            return response.failed(result, "添加货源");
        }
    }
    @NotNeedJWT
    @PutMapping("update")
    public CommonResult<Material> updateMaterial(@RequestBody Material material) {
        return new CommonResult<Material>(materialService.update(material));
    }

    @NotNeedJWT
    @DeleteMapping("delete/{materialId}")
    public CommonResult<Integer> deleteMaterial(@PathVariable Integer materialId) {

        CommonResult<Integer>response =new CommonResult<Integer>();
        int result = materialService.deleteById(materialId);
        if(result>0) {
            return response.success(result, "删除货源");
        }
        else {
            return response.failed(result, "删除货源");
        }
    }
}
