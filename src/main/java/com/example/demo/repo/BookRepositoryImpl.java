package com.example.demo.repo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;

import com.exampole.demo.domain.Book;

public class BookRepositoryImpl implements BookRepository {

	@Resource(name = "Books")
	private Region<Long, Book> books;

	@Override
	public <S extends Book> S save(S entity) {
		books.put(entity.getIsbn(), entity);
		return entity;
	}

	@Override
	public <S extends Book> Iterable<S> saveAll(Iterable<S> entities) {
		books.putAll((Map<? extends Long, ? extends Book>) entities);
		return entities;
	}

	@Override
	public Optional<Book> findById(Long id) {
		Book book = books.get(id);
		return Optional.ofNullable(book);
	}

	@Override
	public boolean existsById(Long id) {
		if (books.get(id) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Iterable<Book> findAll() {
		ArrayList<Book> bookList = new ArrayList<Book>();
		Set<Long> keys = books.keySetOnServer();
		for (Long key : keys) {
			bookList.add(books.destroy(key));
		}
		return bookList;
	}

	@Override
	public Iterable<Book> findAllById(Iterable<Long> ids) {
		ArrayList<Book> bookList = new ArrayList<Book>();
		for (Long id : ids) {
			bookList.add(books.get(id));
		}
		return bookList;
	}

	@Override
	public long count() {
		return books.size();
	}

	@Override
	public void deleteById(Long id) {
		books.destroy(id);
	}

	@Override
	public void delete(Book entity) {
		books.destroy(entity.getIsbn());
	}

	@Override
	public void deleteAll(Iterable<? extends Book> entities) {
		for (Book book : entities) {
			books.destroy(book.getIsbn());
		}
	}

	@Override
	public void deleteAll() {
		Set<Long> keys = books.keySetOnServer();
		for (Long key : keys) {
			books.destroy(key);
		}
	}
}
