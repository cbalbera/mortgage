package com.mortgage.mortgage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface mortgageRepository extends JpaRepository<mortgage,Long> {
    // gives us ability to find by ID, etc. via interface
}
