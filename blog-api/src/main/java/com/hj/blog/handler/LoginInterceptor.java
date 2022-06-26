package com.hj.blog.handler;

import com.alibaba.fastjson.JSON;
import com.hj.blog.Service.TokenService;
import com.hj.blog.dao.pojo.SysUser;
import com.hj.blog.utils.UserThreadLocal;
import com.hj.blog.vo.ErrorCode;
import com.hj.blog.vo.Result;
import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登陆逻辑检查
        /*
        1.需要判断 请求接口路径是否为 HandlerMethod (是否访问的controller方法)
        2.判断token是否为空，为空则未登录
        3.如果token不为空，登陆验证loginService checkToken
        4.如果认证成功，放行即可
         */
        //判断请求接口路径
        if (!(handler instanceof HandlerInterceptor)) {
            return true;//handler可能是 RequestResourceHandler springboot程序 访问静态资源 默认去classpath下的static目录查询
        }
        String token = request.getHeader("Authorization");
        //日志打印
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //判断token是否为空
        if (StringUtils.isNullOrEmpty(token)) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        //验证token是否合法
        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        //登陆成功
        //如果希望在controller中直接获取用户信息，如何实现？
        UserThreadLocal.put(sysUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //用完ThreadLocal需要删除其中的用户信息，否则会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}