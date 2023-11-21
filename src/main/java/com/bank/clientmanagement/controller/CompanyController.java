package com.bank.clientmanagement.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.time.LocalDate;
import java.util.Arrays;
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

	//Response body will be the pdf in byte array format
	//The byte array can be transformed in pdf file using online tool https://anaoktaa.github.io/bytearraytopdf/
	@PostMapping(path = "company",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody Company newCompany) throws IOException {
		if (!idValid(newCompany.getIdValidity())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		byte[] document = companyService.createCompany(newCompany);

		if (document == null) {
			throw new ServerException("Cannot create company");
		} else {
			return new ResponseEntity<>(Arrays.toString(document), HttpStatus.CREATED);
		}
	}


	private boolean idValid(Date date) {
		return convertToLocalDateViaSqlDate(date).isAfter(LocalDate.now());
	}

	public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
}
