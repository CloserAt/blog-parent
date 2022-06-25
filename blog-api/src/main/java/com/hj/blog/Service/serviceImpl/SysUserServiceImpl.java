package com.hj.blog.Service.serviceImpl;

import com.hj.blog.Service.SysUserService;
import com.hj.blog.dao.mapper.SysUserMapper;
import com.hj.blog.dao.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

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
}
