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

    @NotBlank(message = "제목은 비워두지 말아주세요. ")
    @Size(min = 2, max = 20, message = "최대 20글자 까지 가능합니다.")
    private String title;
    @NotBlank(message = "내용은 비워두지 말아주세요. ")
    @Size(min = 2, max = 300, message = "최대 300글자 까지 가능합니다.")
    private String content;
    @NotBlank(message = "작성자은 비워두지 말아주세요. ")
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