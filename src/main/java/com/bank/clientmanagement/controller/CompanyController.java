package com.bank.clientmanagement.controller;

import java.rmi.ServerException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.clientmanagement.model.Company;
import com.bank.clientmanagement.service.CompanyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CompanyController {

	CompanyService companyService;

	@PostMapping(path = "company",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Company> create(@RequestBody Company newCompany) throws ServerException {
		if (!idValid(newCompany.getIdValidity())) {
			return new ResponseEntity<>(newCompany, HttpStatus.BAD_REQUEST);
		}

		Company company = companyService.createCompany(newCompany);

		if (company == null) {
			throw new ServerException("Cannot create company");
		} else {
			return new ResponseEntity<>(company, HttpStatus.CREATED);
		}
	}

	private boolean idValid(Date date) {
		return convertToLocalDateViaSqlDate(date).isAfter(LocalDate.now());
	}

	public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
}
