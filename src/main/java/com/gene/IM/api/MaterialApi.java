package com.gene.IM.api;

import cn.hutool.json.JSONObject;
import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.Material;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.service.MaterialService;
import com.gene.IM.util.TripleExponentialImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/materials")
public class MaterialApi{

    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialMapper materialMapper;
    private TripleExponentialImpl tripleExponential;

    /**
     * 根据物料ID获取物料信息
     *
     * @param materialId 物料ID
     * @return 包含物料信息的通用结果对象
     */
    @NotNeedJWT
    @GetMapping("getid/{materialId}")
    public CommonResult<JSONObject> getMaterialById(@PathVariable Integer materialId) {
        Material material = materialService.getMaterialById(materialId);
        double[] real = materialMapper.getAllConsumes(materialId);
        double alpha = 0.1, beta = 0.45, gamma = 0;
        int period = 12, m = 1;
        boolean debug = false;
        double[] predict = TripleExponentialImpl.forecast(real, alpha, beta, gamma, period, m, debug);
        System.out.println("-----------predict----------------------------------");
        JSONObject json = new JSONObject();
        for (int i = real.length; i < predict.length; i++) {
            json.set("infer",predict[i]);
            System.out.println(predict[i]);
        }
        json.set("material",material);
            return new CommonResult<JSONObject>(json,"查询");
    }

    /**
     * 根据条件获取物料列表
     *
     * @param material 包含查询条件的对象
     * @return 包含物料列表的通用结果对象
     */
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


    /**
     * 根据ID获取推测信息
     *
     * @param id 物料ID
     * @return 包含推测信息列表的通用结果对象
     */
    @NotNeedJWT
    @GetMapping("/getInferById")

    public CommonResult<List<MaterialDTO>> getInferById(@RequestParam("id") int id) {
        List<MaterialDTO> result = materialService.getInferById(id);
        double[] real = materialMapper.getAllConsumes(id);
        double alpha = 0.1, beta = 0.45, gamma = 0;
        int period = 12, m = 1;
        boolean debug = false;
        double[] predict = TripleExponentialImpl.forecast(real, alpha, beta, gamma, period, m, debug);
        System.out.println("-----------predict----------------------------------");
        for (int i = real.length; i < predict.length; i++) {
            result.get(0).setInfer(predict[i]);
            System.out.println(predict[i]);
        }

        //查询结果为空
        return new CommonResult<List<MaterialDTO>>(result);

    }

    /**
     * 添加新的物料
     *
     * @param material 包含要添加的物料信息的对象
     * @return 包含添加操作结果的通用结果对象
     */
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

    /**
     * 更新物料信息
     *
     * @param material 包含要更新的物料信息的对象
     * @return 包含更新后的物料信息的通用结果对象
     */
    @NotNeedJWT
    @PutMapping("update")
    public CommonResult<Material> updateMaterial(@RequestBody Material material) {
        return new CommonResult<Material>(materialService.update(material));
    }

    /**
     * 删除指定ID的物料
     *
     * @param materialId 要删除的物料ID
     * @return 包含删除操作结果的通用结果对象
     */
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
