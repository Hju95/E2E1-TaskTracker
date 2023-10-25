package org.fastcampus.proejct.board.dto;

import org.fastcampus.proejct.board.domain.Board;

import java.time.LocalDateTime;

/**
 * DTO for {@link org.fastcampus.proejct.board.domain.Board}
 */
public record BoardDto(
        String title,
        String content,
        String createdBy,
        LocalDateTime createdAt,
        String modifiedBy,
        LocalDateTime modifiedAt
) {

    public static BoardDto of(
            String title,
            String content
    ) {
        return BoardDto.of(
                title,
                content,
                null,
                null,
                null,
                null
        );
    }

    public static BoardDto of(
            String title,
            String content,
            String createdBy,
            LocalDateTime createdAt,
            String modifiedBy,
            LocalDateTime modifiedAt
    ) {
        return new BoardDto(title, content, createdBy, createdAt, modifiedBy, modifiedAt);
    }

    public static BoardDto from(Board entity) {
        return BoardDto.of(
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getModifiedBy(),
                entity.getModifiedAt()
        );
    }

    public static Board toEntity(BoardDto dto) {
        return Board.of(
                null,
                dto.title(),
                dto.content()
        );
    }
}