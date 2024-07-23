package com.example.back.mapper;

import com.example.back.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "ORDER BY like_count DESC")
    List<Comment> getCommentBylikeCount(Integer article_id);

    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "ORDER BY publish_time DESC")
    List<Comment> getCommentByTime(Integer articleId);


    @Select("SELECT COUNT(*) FROM comment WHERE parent_id = #{comment_id}")
    int getReplyCountByCommentId(Integer comment_id);

    @Select("""
            WITH RECURSIVE CommentHierarchy AS (
                SELECT * FROM comment WHERE parent_id = #{parentId}
                UNION ALL
                SELECT c.* FROM comment c
                INNER JOIN CommentHierarchy ch ON c.parent_id = ch.comment_id
            )
            SELECT * FROM CommentHierarchy ORDER BY publish_time DESC;
            """)
    List<Comment> getCommentsByParentId(Integer parentId);

    @Select("""
            WITH RECURSIVE CommentHierarchy AS (
                SELECT * FROM comment WHERE parent_id = #{parentId}
                UNION ALL
                SELECT c.* FROM comment c
                INNER JOIN CommentHierarchy ch ON c.parent_id = ch.comment_id
            )
            SELECT COUNT(*) FROM CommentHierarchy;
            """)
    int getCommentCountByParentId(Integer parentId);

    @Select("SELECT user_id FROM comment WHERE comment_id = #{commentId}")
    int findUserIdByCommentId(Integer commentId);
}
