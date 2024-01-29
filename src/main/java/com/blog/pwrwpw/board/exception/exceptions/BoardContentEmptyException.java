package com.blog.pwrwpw.board.exception.exceptions;

public class BoardContentEmptyException extends RuntimeException{

    public BoardContentEmptyException() {
        super("게시글 내용이 비어있습니다.");
    }
}
