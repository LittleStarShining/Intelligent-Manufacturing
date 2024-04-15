package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.service.IndexService;
import com.gene.IM.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/index")
public class IndexApi {

    @Autowired
    private IndexService indexService;

    @NotNeedJWT
    @GetMapping("/showIndexInfo")
    public Map<String,Object> show(){

        return indexService.getIndexInfo();
    }

}
