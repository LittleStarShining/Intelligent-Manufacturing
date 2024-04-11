package com.gene.IM.api;

import cn.hutool.json.JSONObject;
import com.gene.IM.DTO.OrderIndex;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @NotNeedJWT
    @GetMapping("/showWaitingTask")
    public Map<String,Object> showWaitingList(){
        return orderService.showWaitingList();
    }

    @NotNeedJWT
    @GetMapping("/showDoingTask")
    public Map<String,Object> showDoingList(){
        return orderService.showDoingList();
    }

    @NotNeedJWT
    @GetMapping("/showDetail")
    public Map<String,Object> showOrderDetail(@RequestParam("orderID") int id){
        return orderService.showOrderDetail(id);
    }

    @NotNeedJWT
    @GetMapping("/sortSelect")
    public Map<String,Object> sort(@RequestParam("situation") int situation,@RequestParam("sort") String sort){
        return orderService.orderBySituation(situation,sort);
    }

    @NotNeedJWT
    @PostMapping("/vagueSelect")
    public Map<String,Object> vagueSelect(@RequestBody Map<String,Object> body){
        String source = (String) body.get("source");
        String key = (String) body.get("key");
        return orderService.vagueSelect(source,key);
    }

    @NotNeedJWT
    @GetMapping("/showDoneTask")
    public Map<String,Object> showDoneList(){
        return orderService.showDoneList();
    }
    @NotNeedJWT
    @GetMapping("/greedyAssign")
    public CommonResult<JSONObject> greedyAssign(){
        JSONObject json = new JSONObject();
        int line = 1;
        for(List<OrderInfo> line_order:orderService.greedyAssign()){
            for(OrderInfo order: line_order){
                json.append("line"+line,order);
            }
        line++;
        }
            return new CommonResult<JSONObject>(json);
    }









}
