package com.example.back.service;

import com.example.back.pojo.LikeStar;

import java.util.List;

public interface LikeStarService {
    int addLikeOrStar(Integer user_id, Integer target_id, String content_type, String operation_type);
    int deleteLikeOrStar(Integer user_id, Integer target_id, String content_type, String operation_type);
    List<LikeStar> searchList(Integer user_id, String content_type, String operation_type);

    List<LikeStar> searchCount(Integer user_id, Integer comment_id, Integer article_id, String operation_type);
    // 新增方法：根据 target_id 查询数据条数
    int countByTargetId(Integer targetId);
}
