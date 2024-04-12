package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.util.TripleExponentialImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



@RestController
@CrossOrigin
@RequestMapping("/predict")
public class predictApi {

    private TripleExponentialImpl tripleExponential;

    @NotNeedJWT
    @GetMapping("/test")
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


}
