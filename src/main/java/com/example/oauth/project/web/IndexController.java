package com.example.oauth.project.web;

import com.example.oauth.project.config.auth.LoginUser;
import com.example.oauth.project.config.auth.dto.SessionUser;
import com.example.oauth.project.domain.posts.Posts;
import com.example.oauth.project.service.posts.PostsService;
import com.example.oauth.project.web.dto.PostsResponseDto;
import com.example.oauth.project.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.OnClose;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
    @ResponseBody
    @PostMapping(value = "/posts/save",produces = "application/json; charset=UTF-8")
    public Map<String, Object> postsSave(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto, BindingResult bindingResult)throws Exception{
        Map<String, Object> createResult = new HashMap<>();

        if(bindingResult.hasErrors()){
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            for(FieldError error : allErrors){
                createResult.put(error.getField(),error.getDefaultMessage());
            }
        } else {
            postsService.save(postsSaveRequestDto);
            createResult.put("saveResult", 200);
        }
        return createResult;
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}