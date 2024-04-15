package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/schedule")
public class ScheduleApi {
    @Autowired
    private OrderService o;
    @NotNeedJWT
    @PostMapping("/getScheduleList")
    public Map<String,Object> addNewOrder(){
        return null;
    }




}
