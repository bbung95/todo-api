package com.bbung.todoapi.domain.board.service;

import com.bbung.todoapi.domain.board.dto.*;
import com.bbung.todoapi.domain.board.exception.BoardNotFoundException;
import com.bbung.todoapi.domain.board.mapper.BoardMapper;
import com.bbung.todoapi.common.PageResponse;
import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.entity.Board;
import com.bbung.todoapi.domain.task.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    private final TaskMapper taskMapper;

    private final ModelMapper modelMapper;

    public int saveBoard(BoardFormDto boardFormDto){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = modelMapper.map(boardFormDto, Board.class);
        board.setWriter(userInfo.getId());

        boardMapper.insertBoard(board);

        return board.getId();
    }

    public PageResponse<BoardListDto> findBoardList(BoardSearchParam param){

        PageResponse<BoardListDto> result = new PageResponse<>();

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        param.setWriter(userInfo.getId());
        param.setRole(userInfo.getRole());

        List<BoardListDto> list = boardMapper.findByUserBoardList(param);
        int count = boardMapper.findByUserBoardTotalCount(param);

        result.setList(list);
        result.setTotalCount(count);

        return result;
    }

    public int updateBoard(Integer id, BoardUpdateForm boardUpdateForm){

        int result = boardMapper.updateTitle(id, boardUpdateForm.getValue());

        return result;
    }

    public int deleteBoard(Integer id){

        int result = boardMapper.deleteBoard(id);
        if(result > 0){
            taskMapper.deleteTaskByBoardId(id);
        }

        return result;
    }

    public BoardDto findBoard(Integer id) {

        Optional<BoardDto> boardDto = boardMapper.findById(id);

        if(!boardDto.isPresent()){
            throw new BoardNotFoundException(Integer.toString(id));
        }

        return boardDto.get();
    }
}
