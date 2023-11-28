package com.std.sbb.article.service;

import com.std.sbb.DataNotFoundException;
import com.std.sbb.article.entity.Article;
import com.std.sbb.article.repository.ArticleRepository;
import com.std.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        if (oa.isPresent()) {
            return oa.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content, SiteUser user){
        Article article = new Article();
        article.setSubject(subject);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        article.setSiteUser(user);
        this.articleRepository.save(article);
    }
    public Page<Article> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }
}
