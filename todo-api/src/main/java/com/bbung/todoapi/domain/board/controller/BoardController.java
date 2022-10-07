package com.bbung.todoapi.domain.board.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController {

    @GetMapping("{id}")
    public String boardDetailView(@PathVariable Integer id, Model model){

        model.addAttribute("id", id);

        return "board";
    }
}
