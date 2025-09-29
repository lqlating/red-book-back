package com.example.back.controller;

import com.example.back.pojo.Result;
import com.example.back.pojo.Title;
import com.example.back.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/title")
public class TitleController {

    @Autowired
    private TitleService titleService;

    // 查询所有标题
    @GetMapping("/all")
    public Result getAllTitles() {
        List<Title> titles = titleService.findAll();
        return titles.isEmpty() ? Result.error("No data found") : Result.success(titles);
    }

    // 根据标题查询
    @GetMapping("/search")
    public Result getTitleByName(@RequestParam String title) {
        Title result = titleService.findByTitle(title);
        return result != null ? Result.success(result) : Result.error("No matching title found");
    }

    // 插入新标题
    @PostMapping("/insert")
    public Result insertTitle(@RequestBody Title title) {
        int result = titleService.insertTitle(title.getTitle(), title.getValue());
        return result > 0 ? Result.success() : Result.error("Failed to insert title");
    }

    // 删除标题
    @DeleteMapping("/delete")
    public Result deleteTitle(@RequestParam Integer id) {
        int result = titleService.deleteTitle(id);
        return result > 0 ? Result.success() : Result.error("Failed to delete title");
    }
}
