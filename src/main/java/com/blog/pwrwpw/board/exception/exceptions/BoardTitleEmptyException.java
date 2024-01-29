package com.blog.pwrwpw.board.exception.exceptions;

public class BoardTitleEmptyException extends RuntimeException{

    public BoardTitleEmptyException() {
        super("게시글 제목이 비어있습니다.");
    }
}
