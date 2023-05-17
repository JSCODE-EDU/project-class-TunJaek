package com.example.jscode.dto;

import com.example.jscode.entity.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardContents;

    public BoardDTO(Board board){
        this.id=board.getId();
        this.boardTitle = board.getBoardTitle();
        this.boardContents = board.getBoardContents();
    }
}
