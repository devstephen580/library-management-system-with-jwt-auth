package com.devstephen.library_management_system_with_jwt_auth.controllers;

import com.devstephen.library_management_system_with_jwt_auth.dtos.BookDto;
import com.devstephen.library_management_system_with_jwt_auth.entities.Book;
import com.devstephen.library_management_system_with_jwt_auth.services.BookService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/getallbooks")
  public ResponseEntity<List<Book>> getAllBooks() {
    return ResponseEntity.ok(bookService.getAllBooks());
  }

  @GetMapping("/getbookbyid/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.getBookById(id));
  }

  @PostMapping("/addbook")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) {
    return ResponseEntity.ok(bookService.addBook(bookDto));
  }

  @DeleteMapping("/deletebook/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBookById(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
    return ResponseEntity.ok(bookService.updateBook(id, bookDto));
  }

}
