package com.example.back.service.impl;

import com.example.back.mapper.LikeStarMapper;
import com.example.back.pojo.LikeStar;
import com.example.back.service.LikeStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeStarServiceImpl implements LikeStarService {

    @Autowired
    private LikeStarMapper likeStarMapper;

    @Override
    public int addLikeOrStar(Integer user_id, Integer target_id, String content_type, String operation_type) {
        return likeStarMapper.insertOperation(user_id, target_id, content_type, operation_type);
    }

    @Override
    public int deleteLikeOrStar(Integer user_id, Integer target_id, String content_type, String operation_type) {
        return likeStarMapper.deleteOperation(user_id, target_id, content_type, operation_type);
    }

    @Override
    public List<LikeStar> searchList(Integer user_id, String content_type, String operation_type) {

        System.out.println("传入的参数:");
        System.out.println("user_id: " + user_id);
        System.out.println("content_type: " + content_type);
        System.out.println("operation_type: " + operation_type);

        return likeStarMapper.searchList(user_id, content_type, operation_type);
    }

    @Override
    public List<LikeStar> searchCount(Integer user_id, Integer comment_id, Integer article_id, String operation_type) {
        return likeStarMapper.searchCount(user_id, comment_id, article_id, operation_type);
    }
}
