package com.psvmchannel.springaop.service;

import com.psvmchannel.springaop.entity.Book;
import com.psvmchannel.springaop.repository.BookRepository;
import com.psvmchannel.springaop.util.CustomResponse;
import com.psvmchannel.springaop.util.CustomStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public CustomResponse<Book> getAll() {
        try {
            log.info("Getting all the books");
            List<Book> bookList = bookRepository.findAll();
            log.info("Book list received");
            return new CustomResponse<>(bookList, CustomStatus.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
    }

    @Transactional(readOnly = true)
    public CustomResponse<Book> getByTitle(String title) {
        try {
            log.info("Getting a book titled {}", title);
            CustomResponse<Book> book = bookRepository.findBookByTitle(title)
                    .map(b -> new CustomResponse<>(Collections.singleton(b), CustomStatus.SUCCESS))
                    .orElseThrow();
            log.info("A book titled {} found", title);
            return book;
        } catch (NoSuchElementException e) {
            log.error("Not found a book titled {}", title);
            return new CustomResponse<>(null, CustomStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
    }

    @Transactional
    public CustomResponse<Book> add(Book book) {
        try {
            log.info("Saving a book {}", book);
            Book saved = bookRepository.save(book);
            log.info("A book {} saved", book);
            return new CustomResponse<>(Collections.singleton(saved), CustomStatus.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
    }
}