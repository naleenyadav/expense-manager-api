package com.naleen.expensetrackerapi.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naleen.expensetrackerapi.model.Expense;
import com.naleen.expensetrackerapi.model.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	Page <Expense> findByUserIdAndCategory(Long userId,String category,Pageable page);
	
	Page <Expense> findByUserIdAndExpenseNameContaining(Long userId,String name, Pageable page);
	
	Page <Expense> findByUserIdAndDateBetween(Long userId,Date start, Date end, Pageable page);
	
	Page <Expense> findByUserId(Long id, Pageable page);
	
	Optional <Expense> findByUserIdAndId(Long userId, int id);
}
