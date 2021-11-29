package com.hasandayan.cari.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;
import com.hasandayan.cari.enums.MovementType;
import com.hasandayan.cari.pojo.CompanyDTO;
import com.hasandayan.cari.repository.CompanyRepository;
import com.hasandayan.cari.service.CompanyMovementService;
import com.hasandayan.cari.service.CompanyService;
import com.hasandayan.cari.util.Base64Util;

@Service("companyCardService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyMovementService companyMovementService;

	@Override
	public Company save(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Iterable<Company> findAll() {
		return companyRepository.findByIsActiveOrderByCompanyNameAsc(true);
	}

	@Override
	public Company findById(Long id) {
		return companyRepository.findById(id).get();
	}

	@Transactional
	@Override
	public List<CompanyDTO> getCompanyDTOs() {

		List<CompanyDTO> companyDTOs = new ArrayList<>();

		for (Company company : findAll()) {

			CompanyDTO companyDTO = new CompanyDTO();

			companyDTO.setId(Base64Util.encode(company.getId()));
			companyDTO.setName(company.getCompanyName());
			companyDTO.setOfficial(company.getCompanyOfficial());
			companyDTO.setGroup(company.getCompanyGroup().getValue());

			BigDecimal totalLoan = new BigDecimal("0.00");
			BigDecimal totalBorrow = new BigDecimal("0.00");

			for (CompanyMovement companyMovement : company.getMovements()) {

				if (companyMovement.getIsActive()) {
					MovementType movementType = companyMovement.getMovementType();

					if (MovementType.LOAN.equals(movementType)) {
						totalLoan = totalLoan.add(companyMovement.getMovementAmount());
					} else if (MovementType.BORROW.equals(movementType)) {
						totalBorrow = totalBorrow.add(companyMovement.getMovementAmount());
					}
				}
			}

			companyDTO.setLoan(totalLoan);
			companyDTO.setBorrow(totalBorrow);

			companyDTOs.add(companyDTO);
		}

		return companyDTOs;
	}

	@Transactional
	@Override
	public CompanyDTO getCompanyDTO(Long id) {

		Company company = findById(id);

		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(Base64Util.encode(company.getId()));
		companyDTO.setName(company.getCompanyName());
		companyDTO.setOfficial(company.getCompanyOfficial());
		companyDTO.setGroup(company.getCompanyGroup().getValue());

		BigDecimal totalLoan = new BigDecimal("0.00");
		BigDecimal totalBorrow = new BigDecimal("0.00");

		for (CompanyMovement companyMovement : company.getMovements()) {

			if (companyMovement.getIsActive()) {
				MovementType movementType = companyMovement.getMovementType();

				if (MovementType.LOAN.equals(movementType)) {
					totalLoan = totalLoan.add(companyMovement.getMovementAmount());
				} else if (MovementType.BORROW.equals(movementType)) {
					totalBorrow = totalBorrow.add(companyMovement.getMovementAmount());
				}
			}
		}

		companyDTO.setLoan(totalLoan);
		companyDTO.setBorrow(totalBorrow);

		return companyDTO;
	}

	@Transactional
	@Override
	public void delete(Long id) {

		Company company = findById(id);

		companyMovementService.deleteByCompany(company);

		company.setIsActive(false);

		companyRepository.save(company);
	}

	@Override
	public Company findByOldId(Long cariKartId) {
		
		Company company = companyRepository.findByOldId(cariKartId);
		
		return company == null ? new Company(): company;
	}

}
