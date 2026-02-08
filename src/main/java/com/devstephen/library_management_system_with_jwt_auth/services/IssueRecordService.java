package com.devstephen.library_management_system_with_jwt_auth.services;

import com.devstephen.library_management_system_with_jwt_auth.entities.Book;
import com.devstephen.library_management_system_with_jwt_auth.entities.IssueRecord;
import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.repositories.BookRepo;
import com.devstephen.library_management_system_with_jwt_auth.repositories.IssueRecordRepo;
import com.devstephen.library_management_system_with_jwt_auth.repositories.UserRepo;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IssueRecordService {

  private IssueRecordRepo issueRecordRepo;
  private BookRepo bookRepo;
  private UserRepo userRepo;

  public IssueRecordService(IssueRecordRepo issueRecordRepo, BookRepo bookRepo, UserRepo userRepo) {
    this.issueRecordRepo = issueRecordRepo;
    this.bookRepo = bookRepo;
    this.userRepo = userRepo;
  }

  public IssueRecord issueThebook(Long bookId) {
    Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

    if (book.getQuantity() <= 0 || !book.getIsAvailable()){
      throw new RuntimeException("Book is not available.");
    }
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("user not found"));

    IssueRecord issueRecord = new IssueRecord();
    issueRecord.setIssuedDate(LocalDate.now());
    issueRecord.setDueDate(LocalDate.now().plusDays(14));
//    issueRecord.setReturnDate();
    issueRecord.setReturned(false);
    issueRecord.setUser(user);
    issueRecord.setBook(book);


    book.setQuantity(book.getQuantity() - 1);

    bookRepo.save(book);
    return issueRecordRepo.save(issueRecord);
  }


  public @Nullable IssueRecord returnTheBook(Long issueRecordId) {
    IssueRecord issueRecord = issueRecordRepo.findById(issueRecordId).orElseThrow(() -> new RuntimeException("Issue record not found."));

    //Added line
    User user = issueRecord.getUser();
    if (user == null){
      throw new RuntimeException("User not found.");
    }


    if (issueRecord.isReturned()){
      throw new RuntimeException("Book is already returned");
    }

    Book book = issueRecord.getBook();
    book.setQuantity(book.getQuantity() + 1);
    book.setIsAvailable(true);
    bookRepo.save(book);

    issueRecord.setReturnDate(LocalDate.now());
    issueRecord.setReturned(true);

    //Added line
    issueRecord.setBook(book);
    issueRecord.setUser(user);

    return issueRecordRepo.save(issueRecord);
  }













}
