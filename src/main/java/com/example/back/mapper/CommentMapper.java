package com.example.back.mapper;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    //根据喜欢的数量获取评论
    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "ORDER BY like_count DESC")
    List<Comment> getCommentBylikeCount(Integer article_id);

    //根据发布时间获取评论
    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "ORDER BY publish_time DESC")
    List<Comment> getCommentByTime(Integer articleId);


    //获取子评论数量
    @Select("SELECT COUNT(*) FROM comment WHERE parent_id = #{comment_id}")
    int getReplyCountByCommentId(Integer comment_id);

    //根据根评论一键获取子评论
    @Select("""
            WITH RECURSIVE CommentHierarchy AS (
                SELECT * FROM comment WHERE parent_id = #{parentId}
                UNION ALL
                SELECT c.* FROM comment c
                INNER JOIN CommentHierarchy ch ON c.parent_id = ch.comment_id
            )
            SELECT * FROM CommentHierarchy ORDER BY publish_time ASC;
            """)
    List<Comment> getCommentsByParentId(Integer parentId);


    //根据根评论id一键获得子评论数量
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

    //根据评论id查找用户id
    @Select("SELECT user_id FROM comment WHERE comment_id = #{commentId}")
    int findUserIdByCommentId(Integer commentId);

    //发评论
    @Insert("INSERT INTO comment (content, article_id, user_id, like_count, publish_time, star_count, parent_id) VALUES (#{content}, #{article_id}, #{user_id}, #{like_count}, #{publish_time}, #{star_count}, #{parent_id})")
    int insertComment(Comment comment);

    //根据userid查找评论
    @Select("SELECT * FROM comment WHERE parent_id IN (SELECT comment_id FROM comment WHERE user_id = #{userId})")
    List<Comment> findCommentsByUserId(@Param("userId") Integer userId);


    // 根据 comment_id 查找评论
    @Select("SELECT * FROM comment WHERE comment_id = #{commentId}")
    List<Comment> findCommentByCommentId(@Param("commentId") Integer commentId);
}
