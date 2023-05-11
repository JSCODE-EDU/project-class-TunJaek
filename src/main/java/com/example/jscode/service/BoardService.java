package com.example.jscode.service;

import com.example.jscode.repository.BoardRepository;
import com.example.jscode.dto.BoardDTO;
import com.example.jscode.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private static BoardRepository boardRepository;
    public void getBoard(long id){
        Optional<Board> board = boardRepository.findById(id);

    }

    public BoardDTO createBoard(BoardDTO boardDTO) {
        return null;
    }
}
