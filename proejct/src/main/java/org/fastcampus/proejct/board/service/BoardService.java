package org.fastcampus.proejct.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fastcampus.proejct.board.domain.Board;
import org.fastcampus.proejct.board.dto.BoardDto;
import org.fastcampus.proejct.board.repository.BoardRepository;
import org.fastcampus.proejct.board.repository.UserInfoRepository;
import org.fastcampus.proejct.user.domain.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserInfoRepository userInfoRepository;
    private final ObjectMapper mapper;

    @Transactional(readOnly = true)
    public List<BoardDto> getBoards() {
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

}
