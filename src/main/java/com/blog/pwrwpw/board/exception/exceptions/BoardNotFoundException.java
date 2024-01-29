package com.blog.pwrwpw.board.exception.exceptions;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(final Long id) {
        super("요청하신 " + id + "의 게시글을 찾을 수 없습니다.");
    }
}

