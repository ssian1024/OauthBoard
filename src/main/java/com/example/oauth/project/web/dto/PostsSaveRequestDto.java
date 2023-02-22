package com.example.oauth.project.web.dto;

import com.example.oauth.project.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    @NotBlank(message = "제목을 비우지 말아주세요.")
    @Size(max = 20, message = "최대 20글자 까지 가능합니다.")
    private String title;
    @NotBlank(message = "내용을 비우지 말아주세요.")
    @Size(max = 300, message = "최대 300글자 까지 가능합니다.")
    private String content;
    @NotBlank(message = "작성자를 비우지 말아주세요.")
    private String author;
    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}