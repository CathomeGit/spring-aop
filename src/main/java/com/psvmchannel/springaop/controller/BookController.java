package com.psvmchannel.springaop.controller;

import com.psvmchannel.springaop.entity.Book;
import com.psvmchannel.springaop.service.BookService;
import com.psvmchannel.springaop.util.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public CustomResponse<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{title}")
    public CustomResponse<Book> getByTitle(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @PostMapping
// TODO add DTO
    public CustomResponse<Book> add(Book book) {
        return bookService.add(book);
    }
}