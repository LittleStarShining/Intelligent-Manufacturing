package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class HistoryApi {

    @Autowired
    private OrderService orderService;

    @NotNeedJWT
    @GetMapping("/report")
    public Map<String,Object> showHistoryReport(){
        return orderService.showHistoryReport();
    }
}
