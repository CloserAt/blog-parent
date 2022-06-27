package com.hj.blog.Service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.hj.blog.Service.TokenService;
import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.utils.JWTUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //校验token
    @Override
    public SysUser checkToken(String token) {
        if (StringUtil.isNullOrEmpty(token)) {
            return null;
        }
        //解析校验
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        //redis中校验
        String userJson = redisTemplate.opsForValue().get("TOKEN" + token);
        if (StringUtil.isNullOrEmpty(userJson)) {
            return null;
        }

        //到此校验过程全部通过
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
