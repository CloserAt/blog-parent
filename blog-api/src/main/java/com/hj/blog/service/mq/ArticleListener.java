//package com.hj.blog.service.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.hj.blog.service.ArticleService;
//import com.hj.blog.admin.vo.ArticleMessage;
//import com.hj.blog.admin.vo.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.Set;
//
///**
// * @author B站：码神之路
// */
//@Slf4j
//@Component
//@RocketMQMessageListener(topic = "blog-update-article",consumerGroup = "blog-update-article-group")
////RocketMQListener<ArticleMessage>此处泛型就是我们发送的消息
//public class ArticleListener implements RocketMQListener<ArticleMessage> {
//
//    @Autowired
//    private ArticleService articleService;
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Override
//    public void onMessage(ArticleMessage message) {
//        log.info("收到的消息:{}",message);
//        //做什么了，更新缓存
//        //1. 更新查看文章详情的缓存
//        Long articleId = message.getArticleId();
//        String params = DigestUtils.md5Hex(articleId.toString());
//        String redisKey = "view_article::ArticleController::findArticleById::"+params;
//        Result articleResult = articleService.findArticleById(articleId);
//        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(articleResult), Duration.ofMillis(5 * 60 * 1000));
//        log.info("更新了缓存:{}",redisKey);
//
//        //2. 文章列表的缓存 我们不知道参数，不知道用户当前在那个分页,解决办法就是：
//        // 1.直接删除缓存，让它重新缓存就行
//        // 2.文章列表接口不加缓存注解
//        //也可以自行搜寻定向解决文章列表缓存的方式
//        Set<String> keys = redisTemplate.keys("listArticle*");
//        keys.forEach(s -> {
//            redisTemplate.delete(s);
//            log.info("删除了文章列表的缓存:{}",s);
//        });
//    }
//}
