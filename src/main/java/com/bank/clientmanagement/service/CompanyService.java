package com.bank.clientmanagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.bank.clientmanagement.model.Company;
import com.bank.clientmanagement.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

	CompanyRepository companyRepository;

	public byte[] createCompany(Company company) throws IOException {
		company.setReputation(getReputation(company.getCui()));
		companyRepository.save(company);

		createInmemoryPDF(company);

		Path pdfPath = Paths.get("EnrollmentDocument.pdf");

		return Files.readAllBytes(pdfPath);
	}

	public void createInmemoryPDF(Company company) throws IOException {

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		final PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.beginText();
		contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
		contentStream.showText(objectMapper.writeValueAsString(company));
		contentStream.endText();
		contentStream.close();

		document.save("EnrollmentDocument.pdf");
		document.close();

	}

	public Long getReputation(String cui) {
		//TODO: the reputation server should be called, using Rest Template

		//if reputation server takes longer than x milliseconds to responde,
		// the simplest solution is to retrieve to the client the id of the company on the response
		// Provide for client a new GET endpoint to check later for the document based on Company ID

		//mock the server at the moment

		long leftLimit = 0L;
		long rightLimit = 200L;

		return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	}

}
