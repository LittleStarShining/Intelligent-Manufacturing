package com.gene.IM.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    /**
     * 获取令牌
     * @param   userID  token需要存储的信息
     * @return  token字符串
     */
    public String getToken(String userID) {
        String token="";
        token= JWT.create().withAudience(userID) // 将用户名存储到token中

                .sign(Algorithm.HMAC256("123")); // 以123 作为 token 的密钥
        return token;
    }
}
