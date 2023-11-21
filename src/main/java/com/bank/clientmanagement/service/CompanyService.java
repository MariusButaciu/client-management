package com.bank.clientmanagement.service;

import org.springframework.stereotype.Service;

import com.bank.clientmanagement.model.Company;
import com.bank.clientmanagement.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

	CompanyRepository companyRepository;

	public Company createCompany(Company company) {
		company.setReputation(getReputation(company.getCui()));
		return companyRepository.save(company);
	}

	public Long getReputation(String cui){
		//here the reputation server should be called, using Rest Template

		return 10L;
	}



}
