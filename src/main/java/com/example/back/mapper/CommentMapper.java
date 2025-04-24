package com.example.back.mapper;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 根据喜欢的数量获取评论
    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "AND is_banned = 0 " +
            "ORDER BY like_count DESC")
    List<Comment> getCommentBylikeCount(Integer article_id);

    // 根据发布时间获取评论
    @Select("SELECT * FROM comment " +
            "WHERE article_id = #{article_id} " +
            "AND parent_id IS NULL " +
            "AND is_banned = 0 " +
            "ORDER BY publish_time DESC")
    List<Comment> getCommentByTime(Integer article_id);

    // 获取子评论数量
    @Select("SELECT COUNT(*) FROM comment WHERE parent_id = #{comment_id} AND is_banned = 0")
    int getReplyCountByCommentId(Integer comment_id);

    // 根据根评论一键获取子评论
    @Select("""
            WITH RECURSIVE CommentHierarchy AS (
                SELECT * FROM comment WHERE parent_id = #{parentId} AND is_banned = 0
                UNION ALL
                SELECT c.* FROM comment c
                INNER JOIN CommentHierarchy ch ON c.parent_id = ch.comment_id
                WHERE c.is_banned = 0
            )
            SELECT * FROM CommentHierarchy ORDER BY publish_time ASC;
            """)
    List<Comment> getCommentsByParentId(Integer parent_id);

    // 根据根评论id一键获得子评论数量
    @Select("""
            WITH RECURSIVE CommentHierarchy AS (
                SELECT * FROM comment WHERE parent_id = #{parentId} AND is_banned = 0
                UNION ALL
                SELECT c.* FROM comment c
                INNER JOIN CommentHierarchy ch ON c.parent_id = ch.comment_id
                WHERE c.is_banned = 0
            )
            SELECT COUNT(*) FROM CommentHierarchy;
            """)
    int getCommentCountByParentId(Integer parent_id);

    // 根据评论id查找用户id
    @Select("SELECT user_id FROM comment WHERE comment_id = #{commentId} AND is_banned = 0")
    int findUserIdByCommentId(Integer commentId);

    /**
     * 根据评论ID查找用户信息
     * @param commentId 评论ID
     * @return 用户信息列表
     */
    List<User> findUserByCommentId(Integer commentId);

    // 发评论
    @Insert("INSERT INTO comment (content, article_id, user_id, like_count, publish_time, star_count, parent_id, is_banned) VALUES (#{content}, #{article_id}, #{user_id}, #{like_count}, #{publish_time}, #{star_count}, #{parent_id}, 0)")
    int addComment(Comment comment);

    // 查询回复自己的评论
    @Select("SELECT * FROM comment " +
            "WHERE parent_id IN (" +
            "    SELECT comment_id " +
            "    FROM comment " +
            "    WHERE user_id = #{userId} AND is_banned = 0" +
            ") " +
            "AND user_id <> #{userId} " +
            "AND is_banned = 0 " +
            "ORDER BY publish_time DESC")
    List<Comment> getCommentsByUserId(Integer userId);

    // 根据 comment_id 查找评论
    @Select("SELECT * FROM comment WHERE comment_id = #{commentId} AND is_banned = 0")
    List<Comment> findCommentByCommentId(Integer commentId);

    // 新增方法：获取所有 is_banned 为 1 的评论数据
    @Select("SELECT * FROM comment WHERE is_banned = 1")
    List<Comment> getBannedComments();
    
    // 新增方法：将评论设为禁止状态
    @Update("UPDATE comment SET is_banned = 1 WHERE comment_id = #{commentId}")
    int banCommentById(@Param("commentId") Integer commentId);
    
    // 新增方法：解除评论的禁止状态
    @Update("UPDATE comment SET is_banned = 0 WHERE comment_id = #{commentId}")
    int unbanCommentById(@Param("commentId") Integer commentId);

    Object getCommentById(Integer id);
}