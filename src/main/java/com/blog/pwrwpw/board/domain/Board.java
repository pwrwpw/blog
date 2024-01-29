package com.blog.pwrwpw.board.domain;

import com.blog.pwrwpw.board.exception.exceptions.BoardContentEmptyException;
import com.blog.pwrwpw.board.exception.exceptions.BoardTitleEmptyException;
import com.blog.pwrwpw.global.dto.BaseTimeEntity;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "BOARD")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    protected Board() {
    }

    private Board(final Long id, final String title, final String content, final Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Board of(final String title, final String content, final Member member) {
        validateBoard(title, content);
        return new Board(null, title, content, member);
    }

    public void update(final String title, final String content) {
        validateBoard(title, content);
        this.title = title;
        this.content = content;
    }

    public void checkAuthor(final Member member) {
        if (!this.member.equals(member)) {
            throw new MemberNotEqualException();
        }
    }

    private static void validateBoard(String title, String content) {
        if (isEmpty(title)) {
            throw new BoardTitleEmptyException();
        }

        if (isEmpty(content)) {
            throw new BoardContentEmptyException();
        }
    }

    private static boolean isEmpty(final String text) {
        return text == null || text.isBlank();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
}
