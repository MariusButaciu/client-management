package com.bank.clientmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.clientmanagement.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
