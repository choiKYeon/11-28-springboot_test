package com.std.sbb.article.service;

import com.std.sbb.article.entity.Article;
import com.std.sbb.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList(){
        List<Article> articleList = this.articleRepository.findAll();
        return articleList;
    }
    public Article getArticle(Integer id){
        Optional<Article> oa = this.articleRepository.findById(id);
        return oa.get();
    }
    public void create(String subject, String content){
        Article article = new Article();
        article.setSubject(subject);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }
}
