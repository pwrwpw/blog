package com.blog.pwrwpw.board.service;

import com.blog.pwrwpw.board.domain.Board;
import com.blog.pwrwpw.board.domain.BoardRepository;
import com.blog.pwrwpw.board.dto.request.BoardCreateRequest;
import com.blog.pwrwpw.board.dto.request.BoardUpdateRequest;
import com.blog.pwrwpw.board.exception.exceptions.BoardNotFoundException;
import com.blog.pwrwpw.member.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(final Member member, final BoardCreateRequest boardCreateRequest) {
        final Board board = Board.of(boardCreateRequest.getTitle(), boardCreateRequest.getContent(), member);
        return boardRepository.save(board);
    }

    public Board updateBoard(final Long boardId, final Member member, final BoardUpdateRequest boardUpdateRequest) {
        Board board = findBoard(boardId);
        board.checkAuthor(member);
        board.update(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());
        return board;
    }

    public void deleteBoard(final Long boardId, final Member member) {
        Board board = findBoard(boardId);
        board.checkAuthor(member);
        boardRepository.delete(board);
    }

    public Board findBoard(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
    }
}
