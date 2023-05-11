package com.example.jscode.controller;

import com.example.jscode.dto.BoardDTO;
import com.example.jscode.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private static BoardService boardService;

    @PostMapping("/posts")
    public BoardDTO createBoard(BoardDTO boardDTO) {
        return boardService.createBoard(boardDTO);


    }
}