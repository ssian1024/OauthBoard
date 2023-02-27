package com.example.oauth.project.web;

import com.example.oauth.project.config.auth.dto.SessionUser;
import com.example.oauth.project.domain.posts.Posts;
import com.example.oauth.project.service.posts.PostsService;
import com.example.oauth.project.web.dto.PostsResponseDto;
import com.example.oauth.project.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<Posts> list = postsService.findAllDesc(pageable);

        model.addAttribute("posts", list);
        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index"; // 머스태치 스타터가 앞의 경로 src/main/resources/templates로, 뒤의 확장자 .mustache 로 자동 지정
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @PostMapping("/posts/save")
    @ResponseBody
    /**
     * JSON 형태로 받을 것 이기에 @RequestBody 를 적용시킨다.
     * @Valid 를 @RequestBody 어노테이션 옆에 작성을 해 이를 통해 들어오는 객체를 검증하며,
     * 'PostSaveRequestDto' 에서 유효성 검증에 대한 세부적인 사항을 정의시킨다.
     */
    public Map<String,Object> postsSave(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto,
                                        //BindingResult 는 Controller 에서 검증을 원하는 객체를 넘겨주었을 시
                                        //발생한 에러 객체를 저장해 준다.
                                        BindingResult bindingResult) throws Exception{
        Map<String,Object> createResult = new HashMap<>();

        //PostSaveRequestDto 에 대한 유효성 검사 메시지가 있는 경우
        if(bindingResult.hasErrors()){
            //FieldError 가 에러가 발생한 필드와 출력할 메시지를 저장해준다.
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            for(FieldError error : allErrors){
                createResult.put(error.getField(), error.getDefaultMessage());
            }
        } else {
            //Dto에서 설정해 준 이외의 성공 상황일 경우 성공(200) 을 내보낸다.
            //saveResult 에 200을 넘겨준 후 createResult 를 반환한다.
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