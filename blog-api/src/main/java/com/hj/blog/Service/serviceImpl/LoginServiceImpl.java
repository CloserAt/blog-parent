package com.hj.blog.Service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.hj.blog.Service.LoginService;
import com.hj.blog.Service.SysUserService;
import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.utils.JWTUtils;
import com.hj.blog.admin.vo.ErrorCode;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.LoginParams;
import com.hj.blog.admin.vo.params.RegisterParams;
import com.mysql.cj.util.StringUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional//事务注解
public class LoginServiceImpl implements LoginService {
    private static final String salt = "hj!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {
        /*
        1.检查参数是否合法
        2.根据用户名和密码去User表中查询用户是否存在
        3.不存在，返回失败
        4.存在，使用jwt生成token返回给前端
        5.token放入redis中，redis存储  token:user信息 设置过期时间（登录认证时先认证token字符串是否合法，再去redis认证是否存在）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //如何用户或者密码为空直接返回
        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);//密码使用md5+盐的方式来加密，注意此处需要导入md5依赖
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());//生成token
        redisTemplate.opsForValue().set("TOKEN" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    //用户退出登陆
    @Override
    public Result logOut(String token) {
        redisTemplate.delete("TOKEN" + token);
        return Result.success(null);
    }


    //注册功能接口
    @Override
    public Result register(RegisterParams registerParams) {
        /*
        1.判断参数是否合法
        2.判断账户是否存在，存在返回账户已经被注册
        2.若不存在，注册用户
        4.生成token
        5.传入redis，并返回
        6.注意加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚
         */
        String account = registerParams.getAccount();
        String password = registerParams.getPassword();
        String nickname = registerParams.getNickname();
        //第一步-判断是否合法
        if (StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        //第二步-判断是否存在
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_EXIST.getMsg());
        }

        //第三步-开始注册
        SysUser newSysUser = new SysUser();
        newSysUser.setAccount(account);
        newSysUser.setPassword(DigestUtils.md5Hex(password + salt));//注册时此处也要加盐
        newSysUser.setNickname(nickname);
        newSysUser.setCreateDate(System.currentTimeMillis());
        newSysUser.setLastLogin(System.currentTimeMillis());
        newSysUser.setAdmin(1);// 1 为 true
        newSysUser.setDeleted(0);//0 为 false
        newSysUser.setEmail("");
        newSysUser.setSalt("");
        newSysUser.setStatus("");
        this.sysUserService.save(newSysUser);

        //第四步-生成token
        String token = JWTUtils.createToken(newSysUser.getId());

        //第五步-传入redis
        redisTemplate.opsForValue().set("TOKEN" + token, JSON.toJSONString(newSysUser),1,TimeUnit.DAYS);

        //第六步-在接口上增加事务注解@Transactional

        return Result.success(token);
    }
}
