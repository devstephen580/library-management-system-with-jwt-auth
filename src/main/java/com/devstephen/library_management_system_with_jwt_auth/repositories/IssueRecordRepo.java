package com.devstephen.library_management_system_with_jwt_auth.repositories;

import com.devstephen.library_management_system_with_jwt_auth.entities.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepo extends JpaRepository<IssueRecord, Long> {



}
