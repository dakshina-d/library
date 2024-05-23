package com.example.library_system.repository;

import com.example.library_system.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    Borrower findByEmail(String email);
}
