package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comment")
    public String commentBook(@RequestParam("bookId") long bookId, Model model) {
        BookDto book = bookService.getBookDtoById(bookId).get();
        model.addAttribute("book", book);
        List<CommentDto> commentList = commentService.getCommentByBookId(bookId);
        model.addAttribute("comments", commentList);
        return "commentBook";
    }

    @GetMapping("/comment/edit")
    public String editComment(@RequestParam("commentId") long commentId, Model model) {
        CommentDto comment = commentService.getCommentById(commentId).get();
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/comment/edit")
    public String saveComment(@ModelAttribute("comment") CommentDto commentDto) {
        CommentDto comment = commentService.getCommentById(commentDto.getId()).get();
        comment.setText(commentDto.getText());
        commentService.updateComment(comment);
        return "redirect:/";
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") long commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/";
    }

    @GetMapping("/comment/create")
    public String addComment(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "createComment";
    }

    @PostMapping("/comment/create")
    public String createComment(@RequestParam("bookId") long bookId,
                                @ModelAttribute("comment") String commentText) {
        BookDto book = bookService.getBookDtoById(bookId).get();
        CommentDto comment = new CommentDto(0L, commentText, book);
        commentService.insert(comment);
        return "redirect:/comment?bookId=" + bookId;
    }
}
