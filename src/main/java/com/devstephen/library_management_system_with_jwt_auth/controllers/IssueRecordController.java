package com.devstephen.library_management_system_with_jwt_auth.controllers;

import com.devstephen.library_management_system_with_jwt_auth.entities.IssueRecord;
import com.devstephen.library_management_system_with_jwt_auth.services.IssueRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issuerecord")
public class IssueRecordController {

  private IssueRecordService recordService;

  public IssueRecordController(IssueRecordService recordService) {
    this.recordService = recordService;
  }

  @PostMapping("/issuebook/{bookId}")
  public ResponseEntity<IssueRecord> issueTheBook(@PathVariable Long bookId){
    return ResponseEntity.ok(recordService.issueThebook(bookId));
  }

  @PostMapping("/returnthebook/{issueRecordId}")
  public ResponseEntity<IssueRecord> returnTheBook(@PathVariable Long issueRecordId){
    return ResponseEntity.ok(recordService.returnTheBook(issueRecordId));
  }
}
