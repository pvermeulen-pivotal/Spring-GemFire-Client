package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.exampole.demo.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}