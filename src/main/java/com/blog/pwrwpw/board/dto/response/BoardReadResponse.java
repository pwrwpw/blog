package com.blog.pwrwpw.board.dto.response;

import com.blog.pwrwpw.board.domain.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardReadResponse {

    private Long boardId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    private BoardReadResponse() {
    }

    public BoardReadResponse(final Long boardId, final String title, final String content, final String author, final LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    public static BoardReadResponse from(final Board board) {
        return new BoardReadResponse(board.getId(), board.getTitle(), board.getContent(), board.getMember().getEmail(), board.getCreatedAt());
    }
}
