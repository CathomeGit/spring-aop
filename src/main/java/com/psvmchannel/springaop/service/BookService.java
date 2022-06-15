package com.psvmchannel.springaop.service;

import com.psvmchannel.springaop.entity.Book;
import com.psvmchannel.springaop.repository.BookRepository;
import com.psvmchannel.springaop.util.CustomResponse;
import com.psvmchannel.springaop.util.CustomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public CustomResponse<Book> getAll() {
        return new CustomResponse<>(bookRepository.findAll(), CustomStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public CustomResponse<Book> getByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .map(b -> new CustomResponse<>(Collections.singleton(b), CustomStatus.SUCCESS))
                .orElseThrow();
    }

    @Transactional
    public CustomResponse<Book> add(Book book) {
        return new CustomResponse<>(Collections.singleton(bookRepository.save(book)), CustomStatus.SUCCESS);
    }
}