package com.bbung.todoapi.board.controller;

import com.bbung.todoapi.board.dto.*;
import com.bbung.todoapi.board.exception.BoardValidationException;
import com.bbung.todoapi.board.service.BoardService;
import com.bbung.todoapi.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/board")
public class ApiBoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity insertBoard(@RequestBody @Valid BoardFormDto boardFormDto, BindingResult result){

        if(result.hasErrors()){
            throw new BoardValidationException(result);
        }

        int id = boardService.saveBoard(boardFormDto);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping
    public ResponseEntity findBoardList(BoardSearchParam param){

        PageResponse<BoardListDto> boardList = boardService.findBoardList(param);

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
    }

    @GetMapping("{id}")
    public ResponseEntity findBoard(@PathVariable Integer id){

        BoardDto boardDto = boardService.findBoard(id);

        return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    }

    @PatchMapping("{id}")
    public ResponseEntity updateBoardTitle(@PathVariable Integer id, @RequestBody BoardUpdateForm boardUpdateForm){

        int result = boardService.updateBoard(id, boardUpdateForm);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBoard(@PathVariable Integer id){

        int result = boardService.deleteBoard(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
