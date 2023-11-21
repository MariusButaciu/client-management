package com.bank.clientmanagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "COMPANY")
@Getter
@Setter
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CUI")
	private String cui;

	@Column(name = "ID_VALIDITY")
	private Date idValidity;

	@Column(name = "REPUTATION")
	private Long reputation;
}
