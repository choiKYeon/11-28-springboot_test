package com.std.sbb.article.controller;

import com.std.sbb.article.entity.Article;
import com.std.sbb.article.form.ArticleForm;
import com.std.sbb.article.service.ArticleService;
import com.std.sbb.user.entity.SiteUser;
import com.std.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/article")
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Article> paging = this.articleService.getList(page);
        model.addAttribute("paging", paging);
        return "article_list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createArticle(ArticleForm articleForm){
        return "article_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createArticle(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal){
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            return "article_form";
        }
        this.articleService.create(articleForm.getSubject(), articleForm.getContent(), siteUser);
        return "redirect:/article/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        return "article_detail";
    }

}
