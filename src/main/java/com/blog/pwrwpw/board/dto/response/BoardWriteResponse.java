package com.blog.pwrwpw.board.dto.response;

import com.blog.pwrwpw.board.domain.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardWriteResponse {

    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private BoardWriteResponse() {
    }

    public BoardWriteResponse(final Long boardId, final String title, final String content, final LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static BoardWriteResponse from(final Board board) {
        System.out.println(board.getId());
        System.out.println(board.getTitle());
        System.out.println(board.getContent());
        System.out.println(board.getCreatedAt());
        return new BoardWriteResponse(board.getId(), board.getTitle(), board.getContent(), board.getCreatedAt());
    }
}
