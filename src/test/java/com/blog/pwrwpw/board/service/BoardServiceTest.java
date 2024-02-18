package com.blog.pwrwpw.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.blog.pwrwpw.board.domain.Board;
import com.blog.pwrwpw.board.domain.BoardRepository;
import com.blog.pwrwpw.board.dto.request.BoardCreateRequest;
import com.blog.pwrwpw.board.dto.request.BoardUpdateRequest;
import com.blog.pwrwpw.board.exception.exceptions.BoardContentEmptyException;
import com.blog.pwrwpw.board.exception.exceptions.BoardNotFoundException;
import com.blog.pwrwpw.board.exception.exceptions.BoardTitleEmptyException;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    private Member member;
    private Board board;

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @BeforeEach
    void init() {
        member = Member.of("q@r.com", "qw123");
        board = Board.of("test","test",member);
    }

    @DisplayName("게시판 생성시 제목이 비어있다면 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenCreateBoardWithEmptyTitle() {
        //given
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest("","test");
        //when & then
        assertThatThrownBy(() -> boardService.createBoard(member,boardCreateRequest))
                .isInstanceOf(BoardTitleEmptyException.class);
    }

    @DisplayName("게시판 생성시 내용이 비어있다면 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenCreateBoardWithEmptyContent() {
        //given
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest("test","");
        //when & then
        assertThatThrownBy(() -> boardService.createBoard(member,boardCreateRequest))
                .isInstanceOf(BoardContentEmptyException.class);
    }

    @DisplayName("게시판 조회시 해당 게시판이 존재 하지 않는 경우 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenReadBoardWithNotFoundBoardId() {
        //given
        given(boardRepository.findById(anyLong())).willReturn(Optional.empty());
        //when & then
        assertThatThrownBy(() -> boardService.findBoard(anyLong()))
                .isInstanceOf(BoardNotFoundException.class);
    }

    @DisplayName("게시판 수정시 해당 게시판의 작성자와 다를 경우 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenUpdateBoardWithInvalidMember() {
        Member other = Member.of("not@not.com","none");

        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest("update","update");
        Board mockBoard = mock(Board.class);
        given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockBoard));
        doThrow(new MemberNotEqualException()).when(mockBoard).checkAuthor(member);

        assertThatThrownBy(() -> boardService.updateBoard(anyLong(), other, boardUpdateRequest))
                .isInstanceOf(MemberNotEqualException.class);

    }

    @DisplayName("게시판 수정시 제목이 비어있다면 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenUpdateBoardWithEmptyTitle() {
        //given
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest("", "updateContent");
        given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(board));

        //when & then
        assertThatThrownBy(() -> boardService.updateBoard(anyLong(), member, boardUpdateRequest))
                .isInstanceOf(BoardTitleEmptyException.class);
    }

    @DisplayName("게시판 갱신시 내용이 비어있다면 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenUpdateBoardWithEmptyContent() {
        //given
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest("updateTitle", "");
        given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(board));

        //when & then
        assertThatThrownBy(() -> boardService.updateBoard(anyLong(), member, boardUpdateRequest))
                .isInstanceOf(BoardContentEmptyException.class);
    }
}
