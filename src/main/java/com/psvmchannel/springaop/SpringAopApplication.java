package com.psvmchannel.springaop;

import com.psvmchannel.springaop.entity.Book;
import com.psvmchannel.springaop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner {

    private final BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }

    @Override
    public void run(String... args) {
        bookRepository.saveAll(
                List.of(new Book("1984", "G. Orwell"),
                        new Book("Мы", "Е. Замятин")));
    }
}