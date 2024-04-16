package com.gene.IM.api;

import cn.hutool.db.sql.Order;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.service.OrderService;
import com.gene.IM.util.TripleExponentialImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.util.Map;



@RestController
@CrossOrigin
@RequestMapping("/predict")
public class predictApi {


}
