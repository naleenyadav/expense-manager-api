package com.naleen.expensetrackerapi.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.naleen.expensetrackerapi.model.Expense;
import com.naleen.expensetrackerapi.service.ExpenseService;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/expenses")
	public List<Expense> getAllExpenses(Pageable page) {

		return expenseService.getAllExpenses(page).toList();

	}

	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable("id") Integer id) {

		return expenseService.getExpenseById(id);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses/{id}")
	public void deleteById(@PathVariable("id") Integer id) {

		expenseService.deleteExpenseById(id);

	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveExpense(@Valid @RequestBody Expense expense) {

		return expenseService.saveExpense(expense);

	}

	@PutMapping("/expenses/{id}")
	public Expense updateExpense(@PathVariable("id") Integer id, @RequestBody Expense expense) {

		return expenseService.updateExpense(id, expense);
	}

	@GetMapping("/expenses/category")
	public List<Expense> getAllExpensesByCategory(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page);
	}

	@GetMapping("/expenses/name")
	public List<Expense> getAllExpensesByName(@RequestParam String name, Pageable page) {
		return expenseService.readByExpenseName(name, page);
	}

	@GetMapping("/expenses/date")
	public List<Expense> getAllExpensesBetweenDates(@RequestParam(required = false) Date start,
			@RequestParam(required = false) Date end, Pageable page) {
		return expenseService.readByDate(start, end, page);
	}

}
