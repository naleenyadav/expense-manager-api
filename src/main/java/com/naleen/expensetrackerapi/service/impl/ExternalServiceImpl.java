package com.naleen.expensetrackerapi.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naleen.expensetrackerapi.exception.ExpenseDataNotFoundException;
import com.naleen.expensetrackerapi.model.Expense;
import com.naleen.expensetrackerapi.repository.ExpenseRepository;
import com.naleen.expensetrackerapi.repository.UserRepository;
import com.naleen.expensetrackerapi.service.ExpenseService;
import com.naleen.expensetrackerapi.service.UserService;

@Service
public class ExternalServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAllExpenses(Pageable page) {

		return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);

	}

	@Override
	public Expense getExpenseById(int id) {

		Optional<Expense> data = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if (data.isPresent()) {
			return data.get();
		} else {
			throw new ExpenseDataNotFoundException("Expense is not found for the id - " + id);
		}
	}

	@Override
	public void deleteExpenseById(int id) {

		Expense expense = getExpenseById(id);
		expenseRepository.delete(expense);

	}

	@Override
	public Expense saveExpense(Expense expense) {
		expense.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		expense.setUser(userService.getLoggedInUser());
		return expenseRepository.save(expense);
	}

	@Override
	public Expense updateExpense(Integer id, Expense expense) {

		Expense existingExpense = getExpenseById(id);

		existingExpense.setExpenseName(
				expense.getExpenseName() != null ? expense.getExpenseName() : existingExpense.getExpenseName());
		existingExpense.setDescription(
				expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setExpenseAmount(expense.getExpenseAmount());
		existingExpense
				.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());

		return expenseRepository.save(existingExpense);
	}

	@Override
	public List<Expense> readByCategory(String category, Pageable page) {

		return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page)
				.toList();
	}

	@Override
	public List<Expense> readByExpenseName(String name, Pageable page) {

		return expenseRepository.findByUserIdAndExpenseNameContaining(userService.getLoggedInUser().getId(), name, page)
				.toList();
	}

	@Override
	public List<Expense> readByDate(Date start, Date end, Pageable page) {
		if (start == null) {
			start = new Date(0);
		}
		if (end == null) {
			end = new Date(System.currentTimeMillis());
		}

		return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),start, end, page).toList();
	}

}
