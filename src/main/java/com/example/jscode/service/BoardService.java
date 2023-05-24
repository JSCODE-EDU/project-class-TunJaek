package com.example.jscode.service;

import com.example.jscode.repository.BoardRepository;
import com.example.jscode.dto.BoardDTO;
import com.example.jscode.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public ResponseEntity<?> getBoard(long id){
        Optional<Board> board = boardRepository.findById(id);
        if(!board.isEmpty()){
           return ResponseEntity.ok().body(new BoardDTO(board.get())) ;
        }else{
            return ResponseEntity.badRequest().body("오류메세지");
        }
    }

    public ResponseEntity<?> createBoard(BoardDTO boardDTO) {
        if(boardDTO.getBoardTitle()!=null&&boardDTO.getBoardContents()!=null) {
            System.out.println(boardDTO.getBoardTitle()+"게시판제목");
            if(!(boardDTO.getBoardTitle().length() >=16)) {
                String replaceTitle = boardDTO.getBoardTitle().replace(" ", "");
                if (!replaceTitle.equals("")) {
                    if (!(boardDTO.getBoardContents().length() >=1001)) {
                        Board board = new Board(boardDTO.getId(), boardDTO.getBoardTitle(), boardDTO.getBoardContents(), LocalDateTime.now());
                        boardRepository.save(board);
                        return ResponseEntity.created(URI.create("/boards/" + board.getId())).body(new BoardDTO(board));
                    } else {
                        return ResponseEntity.badRequest().body("게시판 내용은 1글자 이상 1000글자 이하로 입력하십시오.");
                    }
                } else {
                    return ResponseEntity.badRequest().body("게시판 제목은 공백으로 이루어 질 수 없습니다.");
                }
            }else{
                return ResponseEntity.badRequest().body("게시판 제목은 1글자 이상 15글자 이하로 입력하십시오.");
            }
        }else{return ResponseEntity.badRequest().body("게시판 제목 또는 내용은 필수 작성 사항 입니다.");
        }
    }

    public ResponseEntity<?> getBoardList() {
        List<Board> boardList =boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .limit(100)
                .collect(Collectors.toList());
        ArrayList<BoardDTO> boardDTOArrayList = new ArrayList<>();
        if(boardList !=null){
            for(Board board : boardList){
                boardDTOArrayList.add(new BoardDTO(board));
            }
            return ResponseEntity.ok().body(boardDTOArrayList);
        }else{
            return ResponseEntity.badRequest().body("조회 결과가 없습니다.");
        }
    }

    public ResponseEntity<?> modifyBoard(Long id,BoardDTO boardDTO){
        Optional<Board> board = boardRepository.findById(id);
        if(boardDTO.getBoardTitle()!=null&&boardDTO.getBoardContents()!=null) {
            if(!(boardDTO.getBoardTitle().length() >=16)) {
                String replaceTitle = boardDTO.getBoardTitle().replace(" ", "");
                if (!replaceTitle.equals("")) {
                    if (!(boardDTO.getBoardContents().length() >=1001)) {
                        Board modifyBoard = new Board(id, boardDTO.getBoardTitle(), boardDTO.getBoardContents(),boardDTO.getCreateTime());
                        boardRepository.save(modifyBoard);
                        if(!modifyBoard.getBoardContents().equals(board.get().getBoardContents())||!modifyBoard.getBoardTitle().equals(board.get().getBoardTitle())){
                            return ResponseEntity.ok().body("수정이 완료되었습니다.");
                            }else{
                            return ResponseEntity.badRequest().body("수정사항이 없습니다.");
                            }
                    } else {
                        return ResponseEntity.badRequest().body("게시판 내용은 1글자 이상 1000글자 이하로 입력하십시오.");
                    }
                } else {
                    return ResponseEntity.badRequest().body("게시판 제목은 공백으로 이루어 질 수 없습니다");
                }
            }else{
                return ResponseEntity.badRequest().body("게시판 제목은 1글자 이상 15글자 이하로 입력하십시오.");
            }
        }else{return ResponseEntity.badRequest().body("게시판 제목 또는 내용은 필수 작성 사항 입니다.");
        }

    }
    public ResponseEntity<?> deleteBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(!board.isEmpty()) {
            boardRepository.delete(board.get());
            return ResponseEntity.ok().body("삭제가 완료되었습니다.");
        }else{
            return ResponseEntity.badRequest().body("삭제하려는 게시판이 존재하지 않습니다.");
        }

    }

    public ResponseEntity<?> searchBoard(String boardTitle){
        String replaceBoardTitle= boardTitle.replace(" ","");
        if(replaceBoardTitle.length()>0){
            List<Board> boardList = boardRepository.findByBoardTitleContains(boardTitle)
                    .stream()
                    .sorted(Comparator.comparingLong(Board::getId).reversed())
                    .limit(100)
                    .collect(Collectors.toList());

            ArrayList<BoardDTO> boardDTOArrayList = new ArrayList<>();
            if(!boardList.isEmpty()){
                for(Board board : boardList) {
                    boardDTOArrayList.add(new BoardDTO(board));
                }
                return ResponseEntity.ok().body(boardDTOArrayList);
            }else{
                return ResponseEntity.badRequest().body("검색결과가 없습니다.");
            }
        }else{
            return ResponseEntity.badRequest().body("검색 키워드는 공백을 제외한 1글자 이상이어야 합니다.");
        }
    }
}
