package com.example.jscode.controller;

import com.example.jscode.dto.BoardDTO;
import com.example.jscode.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards") //게시판 만들기
    public ResponseEntity<?> createBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.createBoard(boardDTO);
    }
    @GetMapping("/boards") //게시글 목록 조회
    public ResponseEntity<?> getBoardList(){
        return boardService.getBoardList();
    }
    @GetMapping("/boards/{id}") //특정 게시판 조회
    public ResponseEntity<?> getBoard(@PathVariable("id") Long id){
        return boardService.getBoard(id);
    }
    @DeleteMapping("boards/{id}") //게시판 삭제
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id){
        return boardService.deleteBoard(id);
    }
    @PutMapping("boards/{id}") //게시판 수정
    public ResponseEntity<?> modifyBoard(@PathVariable("id") Long id,@RequestBody BoardDTO boardDTO){
        return boardService.modifyBoard(id, boardDTO);
    }
    @GetMapping("/boards/search") //게시판 검색
    public ResponseEntity<?> searchBoard(@RequestParam(value ="boardTitle") String boardTitle){ return boardService.searchBoard(boardTitle); }
}