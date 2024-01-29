package com.blog.pwrwpw.board.controller;

import com.blog.pwrwpw.auth.login.JwtLogin;
import com.blog.pwrwpw.board.domain.Board;
import com.blog.pwrwpw.board.dto.request.BoardCreateRequest;
import com.blog.pwrwpw.board.dto.request.BoardUpdateRequest;
import com.blog.pwrwpw.board.dto.response.BoardReadResponse;
import com.blog.pwrwpw.board.dto.response.BoardWriteResponse;
import com.blog.pwrwpw.board.service.BoardService;
import com.blog.pwrwpw.member.domain.Member;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardWriteResponse> createBoard(@JwtLogin final Member member,
                                                          @RequestBody @Valid final BoardCreateRequest boardCreateRequest) {
        Board board = boardService.createBoard(member, boardCreateRequest);
        return ResponseEntity.ok(BoardWriteResponse.from(board));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardReadResponse> getBoard(@PathVariable final Long boardId) {
        Board board = boardService.findBoard(boardId);
        return ResponseEntity.ok(BoardReadResponse.from(board));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardWriteResponse> updateBoard(@PathVariable final Long boardId,
                                                          @JwtLogin final Member member,
                                                          @RequestBody @Valid final BoardUpdateRequest boardUpdateRequest) {
        Board board = boardService.updateBoard(boardId, member, boardUpdateRequest);
        return ResponseEntity.ok(BoardWriteResponse.from(board));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable final Long boardId,
                                            @JwtLogin final Member member) {
        boardService.deleteBoard(boardId, member);
        return ResponseEntity.noContent().build();
    }
}
