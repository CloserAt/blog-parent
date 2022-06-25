package com.hj.blog.Service.serviceImpl;

import com.hj.blog.Service.TagService;
import com.hj.blog.dao.mapper.TagMapper;
import com.hj.blog.dao.pojo.Tag;
import com.hj.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
