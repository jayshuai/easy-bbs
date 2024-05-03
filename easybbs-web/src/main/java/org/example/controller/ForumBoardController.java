package org.example.controller;

import org.example.entity.ForumBoard;
import org.example.entity.vo.ResponseVO;
import org.example.service.ForumBoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("ForumBoardController")
@RequestMapping("board")
public class ForumBoardController extends ABaseController {
    @Resource
    private ForumBoardService boardService;

    @RequestMapping("/loadBoard")
    public ResponseVO loadBoard(Integer postType) {
        List<ForumBoard> boardTree = boardService.getBoardTree(postType);
        return getSuccessResponseVO(boardTree);
    }
}
