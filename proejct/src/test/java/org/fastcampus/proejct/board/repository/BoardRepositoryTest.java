package org.fastcampus.proejct.board.repository;

import org.aspectj.lang.annotation.Before;
import org.fastcampus.proejct.board.domain.Board;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Import(BoardRepositoryTest.TestJpaConfig.class)
@DataJpaTest
@DisplayName("게시글 JPA 연결 테스트")
class BoardRepositoryTest {
    private final BoardRepository repository;

//    @BeforeEach
//    public void createBoard() {
//        repository.save(Board.of(1L, "제목", "본문"));
//    }

    @Autowired
    public BoardRepositoryTest(BoardRepository repository) {
        this.repository = repository;
    }

    @DisplayName("[READ] 게시글 전체 조회")
    @Test
    void getBoardAll() {
        //given

        //when
        List<Board> actual = repository.findAll();

        //then
        assertThat(actual)
                .isNotNull()
                .size().isEqualTo(1);
    }

    @DisplayName("[READ] 게시글 조회")
    @Test
    void testGetBoard() {
        //given

        //when
        Optional<Board> actual = repository.findById(1L);
//        List<Board> all = repository.findAll();
//        all.size();
        //then
        assertThat(actual).get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "제목")
                .hasFieldOrPropertyWithValue("content", "본문");
    }


    @DisplayName("[CREATED]게시글 생성")
    @Test
    void testCreateBoard() {
        //given

        //when

        //then
    }

    @DisplayName("[UPDATED]게시글 수정")
    @Test
    void testUpdatedBoard() {
        //given
        Board expected = repository.findById(1L).orElseThrow();
        //when
        expected.setContent("변경된 내용");
        repository.save(expected);
        //then
        assertThat(repository.findById(1L)).get()
                .isNotNull()
                .hasFieldOrPropertyWithValue("content", "변경된 내용");

    }

    @DisplayName("[DELETE]게시글 삭제")
    @Test
    void testDeletedBoard() {
        //given
        int before = repository.findAll().size();
        //when
        repository.deleteById(1L);
        //then
        assertEquals(before - 1, repository.findAll().size());
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("test1");
        }
    }
}