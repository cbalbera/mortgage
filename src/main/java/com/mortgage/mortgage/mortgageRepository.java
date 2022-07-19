package com.mortgage.mortgage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface mortgageRepository extends JpaRepository<mortgage,Long> {
    // implementing interface gives us ability to find by ID, etc.

    // TODO: potentially add add'l search functionality (by loan term, interest rate, etc.)
}
