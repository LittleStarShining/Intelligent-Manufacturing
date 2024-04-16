package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.service.DataScreenService;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/dataScreen")
public class DataScreenApi {
    @Autowired
    private DataScreenService dataScreenService;



    @NotNeedJWT
    @GetMapping("/orderList")
    public Map<String,Object> getOrderList(){
        return dataScreenService.allOrderList();
    }

    @NotNeedJWT
    @GetMapping("/production")
    public Map<String,Object> getProduction(){
        return dataScreenService.production();
    }

    @NotNeedJWT
    @GetMapping("/lineDetail")
    public Map<String,Object> getLineDetail(){
        return dataScreenService.lineProduction();
    }





}
