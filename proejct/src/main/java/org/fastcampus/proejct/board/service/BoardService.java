package org.fastcampus.proejct.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fastcampus.proejct.board.converter.SortType;
import org.fastcampus.proejct.board.db.model.Board;
import org.fastcampus.proejct.board.converter.dto.BoardDto;
import org.fastcampus.proejct.board.db.repository.BoardRepository;
import org.fastcampus.proejct.board.db.repository.TaskRepository;
import org.fastcampus.proejct.user.db.repository.UserInfoRepository;
import org.fastcampus.proejct.user.db.model.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserInfoRepository userInfoRepository;
    private final TaskRepository taskRepository;
    private final ObjectMapper mapper;

    // TODO: 11/6/23 Sorted 값 별 List<Board> 조회
    @Transactional(readOnly = true)
    public List<BoardDto> getBoards(SortType sorted) {
//        return switch (sorted) {
//            case SORT_DEFAULT -> {
//
//            }
//            case SORT_ALL -> {
//
//            }
//            case SORT_SELF -> {
//
//            }
//            case SORT_FINISHED -> {
//
//            }
//        }
        return boardRepository.findAll().stream()
                .map(BoardDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(Long id) {
        return BoardDto.from(boardRepository.findById(id).orElseThrow());
    }

    public void writeBoard(BoardDto dto) {
        UserInfo userInfo = userInfoRepository.findById(dto.userInfo().id()).orElseThrow();
        Board board = dto.toEntity(userInfo);
        board.setTasks(List.of());
        boardRepository.save(board);
    }

    public void updateBoard(Long id, BoardDto dto) {
//        Board board = boardRepository.getReferenceById(id);
        UserInfo userInfo = userInfoRepository.getReferenceById(dto.userInfo().id());
        boardRepository.save(dto.toEntity(userInfo));
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardDto> search(String keyword) {
        return boardRepository.findByKeyword(keyword).stream()
                .map(BoardDto::from)
                .toList();
    }
}
