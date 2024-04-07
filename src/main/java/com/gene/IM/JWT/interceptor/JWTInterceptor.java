package com.gene.IM.JWT.interceptor;

import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.JWT.annotation.NeedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取令牌
        // 2.检验调用接口是否需要验证
        // 3.假定需要，就要去验证令牌
        // 一、从请求中获取token，token是放在头部key叫做token
        String token = request.getHeader("JWTToken");  // 从http请求头中取出token
        // 二、不是调用方法的时候直接放行
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 三、通过反射获取当前接口（方法）
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查是否有NotNeedJwt注解，如果有，就跳过验证
        if(method.isAnnotationPresent(NotNeedJWT.class)) {
            NotNeedJWT notNeedJwtObj = method.getAnnotation(NotNeedJWT.class);
            if (notNeedJwtObj.required()) {
                return true;
            }
        }
        // 检查是否有NeedJwt注解，如果有，则进行验证操作
        if(method.isAnnotationPresent(NeedJWT.class)) {
            NeedJWT needJwtObj = method.getAnnotation(NeedJWT.class);
            if(needJwtObj.required()){
                // 执行认证
                // 无token
                if (token == null) {
                    throw new RuntimeException("无token，请重新登陆");
                }

                // 有token，获取token信息(此处简单化了，最好拿着token去数据库中再次匹配，验证真假)
                String username;
                try {
                    username = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("token不正确");
                }

                // 构建解码器
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("111")).build();
                try {
                    // 验证token
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("验证失败，解密有误");
                }
                return true;
            }
        }
        return false;
    }
}
