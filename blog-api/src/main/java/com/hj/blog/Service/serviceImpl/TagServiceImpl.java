package com.hj.blog.Service.serviceImpl;

import com.hj.blog.Service.TagService;
import com.hj.blog.dao.mapper.TagMapper;
import com.hj.blog.dao.pojo.Tag;
import com.hj.blog.vo.Result;
import com.hj.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisPlus无法进行多表查询，因此此处自行写
        //根据文章id找到标签
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tagVoList.add(copy(tags.get(i)));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    //最热标签接口实现
    @Override
    public Result hots(int i) {
        /*
        实现逻辑：
            标签所拥有的文章数量最多即为最热标签
            查询 根据tag_id 分组计数，从大到小排列取前默认（limit）个
         */
        List<Long> tagIds = tagMapper.findHotTagIds(i);//返回默认数量i下最热的几个标签
        if (tagIds == null) {
            //如果查出的标签id为空，则返回一个空的list集合
            return Result.success(Collections.emptyList());
        }
        //业务需要的是tagId和tagName的Tag对象
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }
}
