package com.blog.pwrwpw.board.dto.request;

import jakarta.validation.constraints.NotBlank;

public class BoardUpdateRequest {

    @NotBlank(message = "게시글 제목이 비어있습니다.")
    private String title;

    @NotBlank(message = "게시글 내용이 비어있습니다.")
    private String content;

    private BoardUpdateRequest() {
    }

    public BoardUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
