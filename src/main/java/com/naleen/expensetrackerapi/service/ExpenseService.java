package com.naleen.expensetrackerapi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naleen.expensetrackerapi.model.Expense;

@Service
public interface ExpenseService {
	
	public Page<Expense> getAllExpenses(Pageable page);
	
	public Expense getExpenseById(int id);
	
	public void deleteExpenseById(int id);

	public Expense saveExpense(Expense expense);
	
	public Expense updateExpense(Integer id,Expense expense);
	
	public List<Expense> readByCategory(String category, Pageable page);
	
	public List<Expense> readByExpenseName(String name,Pageable page);
	
	public List<Expense> readByDate(Date start, Date end, Pageable page);

}
