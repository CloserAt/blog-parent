package com.hj.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hj.blog.service.SysUserService;
import com.hj.blog.service.TokenService;
import com.hj.blog.admin.mapper.SysUserMapper;
import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.admin.vo.ErrorCode;
import com.hj.blog.admin.vo.LoginUserVo;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TokenService tokenService;

    //
    @Override
    public SysUser findAuthorsByAuthorId(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser != null) {
            return sysUser;
        }
        //此处防止查询出来空数据报空指针错误
        sysUser = new SysUser();
        sysUser.setNickname("佚名");
        return sysUser;
    }


    //登录接口实现
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);//判断用户是否相同
        queryWrapper.eq(SysUser::getPassword,password);//判断密码是否相同
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {

        /*
        1.token合法性校验:是否为空/解析是否成功/redis是否存在
        2.失败，返回错误
        3.成功，返回对应结果 LoginUserVo
         */
        //校验token
        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ILLEGAL.getCode(), ErrorCode.TOKEN_ILLEGAL.getMsg());
        }

        //设置属性并返回
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);//判断要注册的account是否存在
        queryWrapper.last("limit 1");//防止无限查询,加快效率
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser newSysUser) {
        //保存用户这里 id会自动生成
        //此处默认生成的id是分布式id，雪花算法 mybatisPlus
        this.sysUserMapper.insert(newSysUser);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();

            sysUser.setNickname("佚名");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }
}
