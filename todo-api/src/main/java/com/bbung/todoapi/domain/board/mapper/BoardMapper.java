package com.bbung.todoapi.domain.board.mapper;

import com.bbung.todoapi.domain.board.dto.BoardDto;
import com.bbung.todoapi.domain.board.dto.BoardListDto;
import com.bbung.todoapi.domain.board.dto.BoardSearchParam;
import com.bbung.todoapi.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    int insertBoard(Board board);

    List<BoardListDto> findByUserBoardList(BoardSearchParam param);

    int findByUserBoardTotalCount(BoardSearchParam param);

    int updateTitle(@Param("id") Integer id, @Param("title") String title);

    int deleteBoard(Integer id);

    Optional<BoardDto> findById(Integer id);
}
