package com.gene.IM.api;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private LoginService loginService;

    @NotNeedJWT
    @PostMapping("/login")
    public Map<String,Object> doLogin(@RequestBody Map<String,Object> body){
        String userID = (String)body.get("userID");
        String pwd = (String)body.get("pwd");
        return loginService.login(userID,pwd);
    }

}
