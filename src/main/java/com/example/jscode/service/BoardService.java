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
        Board board = new Board(boardDTO.getId(),boardDTO.getBoardTitle(), boardDTO.getBoardContents(), LocalDateTime.now());
        boardRepository.save(board);
        return ResponseEntity.created(URI.create("/boards/" + board.getId())).body(new BoardDTO(board));
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
       Board modifyBoard = new Board(id, boardDTO.getBoardTitle(), boardDTO.getBoardContents(),boardDTO.getCreateTime());
       boardRepository.save(modifyBoard);
        if(modifyBoard.getBoardContents().equals(board.get().getBoardContents())){
            if(modifyBoard.getBoardTitle().equals(board.get().getBoardTitle())){
                return ResponseEntity.badRequest().body("수정사항이 없습니다.");
            }else{
                return ResponseEntity.ok().body("수정이 완료되었습니다.");
            }
        }else {
         return ResponseEntity.ok().body("수정이 완료되었습니다.");
        }
    }
    public ResponseEntity<?> deleteBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        boardRepository.delete(board.get());
        return null;
    }

    public ResponseEntity<?> searchBoard(String boardTitle){
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
    }
}
