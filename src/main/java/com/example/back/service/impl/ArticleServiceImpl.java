package com.example.back.service.impl;

import com.example.back.mapper.ArticleMapper;
import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import com.example.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ARTICLE_CACHE_KEY_PREFIX = "article:";
    private static final String ARTICLE_LIST_CACHE_KEY_PREFIX = "article:list:";
    private static final long CACHE_EXPIRE_TIME = 30; // 缓存过期时间（分钟）
    
    // 预热热门数据，提高高并发性能
    @PostConstruct
    public void preloadHotData() {
        CompletableFuture.runAsync(() -> {
            try {
                // 异步加载热门分类的文章列表到缓存
                for (String type : Arrays.asList("Romance", "Science Fiction", "Suspense", "Thriller")) {
                    list(type);
                    Thread.sleep(500); // 避免同时加载太多数据
                }
                
                // 预热热门文章搜索结果
                searchByTitleOrContent("");
            } catch (Exception e) {
                // 异常处理，预热失败不影响系统正常运行
            }
        });
    }

    @Override
    @Cacheable(value = "articleList", key = "#type")
    public List<Article> list(String type) {
        return articleMapper.list(type);
    }

    @Override
    @Cacheable(value = "articleList", key = "'exclude_' + #type + '_' + #id")
    public List<Article> listExcludeAuthor(String type, Integer id) {
        return articleMapper.listExcludeAuthor(type, id);
    }

    @Override
    @CacheEvict(value = "article", key = "#articleId")
    public void addLike(Integer articleId) {
        articleMapper.addLike(articleId);
    }

    @Override
    @CacheEvict(value = "article", key = "#articleId")
    public void subLike(Integer articleID) {
        articleMapper.subLike(articleID);
    }

    @Override
    @CacheEvict(value = "article", key = "#articleId")
    public void addStar(Integer articleID) {
        articleMapper.addStar(articleID);
    }

    @Override
    @CacheEvict(value = "article", key = "#articleId")
    public void subStar(Integer articleID) {
        articleMapper.subStar(articleID);
    }

    @Override
    @Cacheable(value = "articleList", key = "'ids_' + #articleIds")
    public List<Article> getArticlesByIds(List<Integer> articleIds) {
        return articleMapper.selectArticlesByIds(articleIds);
    }

    @Override
    @Cacheable(value = "articleSearch", key = "#keyword")
    public List<Article> searchByTitleOrContent(String keyword) {
        return articleMapper.searchByTitleOrContent(keyword);
    }

    @Override
    @Cacheable(value = "articleSearch", key = "'exclude_' + #keyword + '_' + #id")
    public List<Article> searchByTitleOrContentExcludeAuthor(String keyword, Integer id) {
        return articleMapper.searchByTitleOrContentExcludeAuthor(keyword, id);
    }

    @Override
    @Cacheable(value = "articleList", key = "'author_' + #authorId")
    public List<Article> getArticlesByAuthorId(Integer authorId) {
        return articleMapper.findArticlesByAuthorId(authorId);
    }

    @Override
    @CacheEvict(value = {"articleList", "article"}, allEntries = true)
    public void save(ArticleRequest article) {
        articleMapper.insert(article);
    }

    @Override
    @Cacheable(value = "articleList", key = "'unreviewed'")
    public List<Article> getUnreviewedArticles() {
        return articleMapper.getUnreviewedArticles();
    }

    @Override
    @Cacheable(value = "articleList", key = "'banned'")
    public List<Article> getBannedArticles() {
        return articleMapper.getBannedArticles();
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public void setReviewedAndBanned(Integer articleId) {
        articleMapper.setReviewedAndBanned(articleId);
    }

    @Override
    @Cacheable(value = "article", key = "#id")
    public Object getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public void setReviewed(Integer articleId) {
        articleMapper.setReviewed(articleId);
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public void unbanArticle(Integer articleId) {
        articleMapper.unbanArticle(articleId);
    }

    @Override
    @CacheEvict(value = {"article", "articleList"}, allEntries = true)
    public void deleteArticle(Integer article_id) {
        articleMapper.deleteArticle(article_id);
    }

    @Override
    @Cacheable(value = "articleSearch", key = "'filter_' + #keyword + '_' + #excludeAuthorId")
    public List<Article> searchByTitleOrContentWithFilter(String keyword, Integer excludeAuthorId) {
        return articleMapper.searchByTitleOrContentWithFilter(keyword, excludeAuthorId);
    }
}