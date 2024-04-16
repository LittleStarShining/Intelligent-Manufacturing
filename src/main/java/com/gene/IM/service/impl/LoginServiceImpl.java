package com.gene.IM.service.impl;

import com.gene.IM.DTO.UserLogin;
import com.gene.IM.entity.User;
import com.gene.IM.mapper.UserInfoMapper;
import com.gene.IM.mapper.UserLoginMapper;
import com.gene.IM.service.LoginService;
import com.gene.IM.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TokenService tokenService;

    /**
     *
     * @param userID 用户名
     * @param pwd 密码
     * @return
     */
    @Override
    public Map<String,Object> login(String userID, String pwd) {
        Map<String,Object> res = new HashMap<>();
        try {
            UserLogin u = userLoginMapper.getUserLoginInfo_by_UserID(userID);
            if(u.getPwd().equals(pwd)){
                res.put("code",1);
                res.put("desc","登录成功");
                Map<String,Object> data = new HashMap<>();
                User userInfo = userInfoMapper.getUserInfo(u.getId());
                data.put("userInfo",userInfo);
                data.put("token",tokenService.getToken(userID));
                res.put("data",data);
            }else{
                throw new Exception("密码错误");
            }
        }catch (Exception e){
            res.put("code",0);
            res.put("desc","登录失败");
        }
        return res;
    }
}
