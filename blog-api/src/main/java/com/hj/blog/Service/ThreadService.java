package com.hj.blog.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hj.blog.dao.mapper.ArticleMapper;
import com.hj.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    //此操作放在线程池执行，不能影响原有的主线程
    @Async("taskExecutor")//此注解即可使用线程池，括号内为注入容器的线程池的名称
    //此外，此注解虽然有默认的线程池实现，但是任务队列存在oom的风险
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Integer viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());//

        //此处仔设置一个：为了在多线程的环境下的线程安全
        queryWrapper.eq(Article::getViewCounts,viewCounts);//乐观锁：之前查询了一次，在修改的时候再查一次，如果没有被修改过再执行+1,
        //避免并发问题
        // update article set view_count = 100 where view_count=99 and id = 10;

        articleMapper.update(articleUpdate,queryWrapper);
//        try {
//            Thread.sleep(5000);//睡眠5s钟
//            System.out.println("更新完成......");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
