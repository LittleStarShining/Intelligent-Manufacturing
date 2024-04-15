package com.gene.IM.api;

import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.DTO.SelectMaterial;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.Material;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.service.MaterialService;
import com.gene.IM.util.TripleExponentialImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    @GetMapping("/getPredict")
    public void test(){
        double[] real = { 362, 385, 432, 341, 382, 409, 498, 387, 473, 513, 582, 600,362, 385, 432, 341, 382, 409, 498, 387, 473, 513, 582, 600,100};
        double alpha = 0.1, beta = 0.45, gamma = 0;
        int period = 12, m = 1;
        boolean debug = false;
        double[] predict = TripleExponentialImpl.forecast(real, alpha, beta, gamma, period, m, debug);
        System.out.println("-----------predict----------------------------------");
        for(int i = real.length; i < predict.length; i++){
            System.out.println(predict[i]);
        }

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

    @NotNeedJWT
    @Scheduled(fixedRate = 60000)
    public void checkMaterial(){
        materialService.checkMaterial();
    }
}
