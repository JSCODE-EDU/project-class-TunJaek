package com.example.jscode.dto;

import com.example.jscode.entity.Board;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
public class BoardRequestDTO {

        @NotBlank(message = "제목은 필수로 입력해야 합니다.")
        @Size(min = 1, max = 15)
        private String boardTitle;

        @NotBlank(message = "게시판 내용은 필수 작성 사항 입니다.")
        @Size(min = 1, max = 1000)
        private String boardContents;

        private LocalDateTime createTime;

        @Builder
        public BoardRequestDTO(String boardTitle, String boardContents,LocalDateTime createTime){
            this.boardTitle= boardTitle;
            this.boardContents= boardContents;
            this.createTime= createTime;
        }

        public Board toEntity(){
            return Board.builder().boardTitle(boardTitle).boardContents(boardContents).createdTime(createTime).build();
        }
    }

