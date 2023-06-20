package com.example.jscode.dto;

import com.example.jscode.entity.Board;
import lombok.Getter;
@Getter
public class BoardResponseDTO {

        private Long id;
        private String boardTitle;
        private String boardContents;

        private BoardResponseDTO(Long id, String boardTitle, String boardContents){
            this.id= id;
            this.boardTitle= boardTitle;
            this.boardContents= boardContents;
        }

        public static BoardResponseDTO from(Board board){
            return new BoardResponseDTO(board.getId(), board.getBoardTitle(), board.getBoardContents());
        }
    }
