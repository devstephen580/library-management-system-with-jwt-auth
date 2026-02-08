package com.devstephen.library_management_system_with_jwt_auth.services;

import com.devstephen.library_management_system_with_jwt_auth.dtos.BookDto;
import com.devstephen.library_management_system_with_jwt_auth.entities.Book;
import com.devstephen.library_management_system_with_jwt_auth.repositories.BookRepo;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private BookRepo bookRepo;

  public BookService(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  public @Nullable List<Book> getAllBooks() {
    return bookRepo.findAll();
  }

  public @Nullable Book getBookById(Long id) {
    return bookRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found for id: " + id));
  }

  public @Nullable Book addBook(BookDto bookDto) {
    var newBook = Book.builder()
        .author(bookDto.getAuthor())
        .title(bookDto.getTitle())
        .isbn(bookDto.getIsbn())
        .isAvailable(bookDto.getIsAvailable())
        .quantity(bookDto.getQuantity())
        .build();

    return bookRepo.save(newBook);
  }

  public void deleteBookById(Long id) {
    bookRepo.deleteById(id);
  }

  public @Nullable Book updateBook(Long id, BookDto bookDto) {
    Book oldBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("No oldBook found for id " + id));
    oldBook.setTitle(bookDto.getTitle());
    oldBook.setIsbn(bookDto.getIsbn());
    oldBook.setQuantity(bookDto.getQuantity());
    oldBook.setAuthor(bookDto.getAuthor());
    oldBook.setIsAvailable(bookDto.getIsAvailable());

    return bookRepo.save(oldBook);
  }
}
