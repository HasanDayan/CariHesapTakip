package com.hasandayan.cari.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;
import com.hasandayan.cari.enums.MovementType;
import com.hasandayan.cari.pojo.CompanyMovementDTO;
import com.hasandayan.cari.repository.CompanyMovementRepository;
import com.hasandayan.cari.service.CompanyMovementService;
import com.hasandayan.cari.service.CompanyService;
import com.hasandayan.cari.util.Base64Util;

@Service("companyMovementService")
public class CompanyMovementServiceImpl implements CompanyMovementService {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMovementRepository companyMovementRepository;

	@Override
	public CompanyMovement save(CompanyMovement companyMovement) {
		return companyMovementRepository.save(companyMovement);
	}

	@Override
	public CompanyMovement findById(Long id) {
		return companyMovementRepository.findById(id).get();
	}

	@Transactional
	@Override
	public List<CompanyMovementDTO> getCompanyMovementDTOs(String cariHesapId) {

		Company company = companyService.findById(Base64Util.decode(cariHesapId));

		List<CompanyMovementDTO> companyMovementDTOs = new ArrayList<>();

		BigDecimal balance = new BigDecimal("0.00");
		
		List<CompanyMovement> movements = company.getMovements().stream().filter(m -> m.getIsActive())
				.collect(Collectors.toList());
	    
		Comparator<CompanyMovement> comparator = Comparator.comparing(CompanyMovement::getProcessDate);
		
		Collections.sort(movements, comparator);

		for (CompanyMovement companyMovement : movements) {

			CompanyMovementDTO companyMovementDTO = new CompanyMovementDTO();

			MovementType movementType = companyMovement.getMovementType();

			companyMovementDTO.setId(Base64Util.encode(companyMovement.getId()));
			companyMovementDTO.setAmount(companyMovement.getMovementAmount());
			companyMovementDTO.setDesc(companyMovement.getMovementDescription());
			companyMovementDTO.setProductType(companyMovement.getProductType());
			
			companyMovementDTO.setQuantity(companyMovement.getQuantity());
			companyMovementDTO.setUnit(companyMovement.getUnit());
			companyMovementDTO.setUnitPrice(companyMovement.getUnitPrice());

			if (Objects.nonNull(companyMovement.getMovementMode()))
				companyMovementDTO.setMode(companyMovement.getMovementMode().getValue());

			if (Objects.nonNull(movementType))
				companyMovementDTO.setType(movementType.getShortValue());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			companyMovementDTO.setDate(formatter.format(companyMovement.getProcessDate()));
			
			companyMovementDTO.setDataTimestamp(companyMovement.getProcessDate().toEpochDay());

			if (MovementType.BORROW.equals(movementType)) {
				balance = balance.subtract(companyMovement.getMovementAmount());
			} else if (MovementType.LOAN.equals(movementType)) {
				balance = balance.add(companyMovement.getMovementAmount());
			}

			companyMovementDTO.setBalance(balance);

			companyMovementDTOs.add(companyMovementDTO);
		}

		return companyMovementDTOs;
	}

	@Override
	public void delete(Long id) {

		CompanyMovement companyMovement = findById(id);

		companyMovement.setIsActive(false);

		companyMovementRepository.save(companyMovement);
	}

	@Override
	public void deleteByCompany(Company company) {
		companyMovementRepository.deleteByCompany(false, company);
	}

	@Override
	public List<CompanyMovementDTO> getCompanyMovementDTOs(LocalDate date) {
		

		List<CompanyMovementDTO> companyMovementDTOs = new ArrayList<>();

		BigDecimal balance = new BigDecimal("0.00");
		
		List<CompanyMovement> movements = companyMovementRepository.findByIsActiveAndProcessDateOrderByProcessDateDesc(true, date);
	    
		for (CompanyMovement companyMovement : movements) {

			CompanyMovementDTO companyMovementDTO = new CompanyMovementDTO();

			MovementType movementType = companyMovement.getMovementType();

			companyMovementDTO.setId(Base64Util.encode(companyMovement.getId()));
			companyMovementDTO.setAmount(companyMovement.getMovementAmount());
			companyMovementDTO.setDesc(companyMovement.getMovementDescription());
			companyMovementDTO.setProductType(companyMovement.getProductType());
			
			companyMovementDTO.setQuantity(companyMovement.getQuantity());
			companyMovementDTO.setUnit(companyMovement.getUnit());
			companyMovementDTO.setUnitPrice(companyMovement.getUnitPrice());

			if (Objects.nonNull(companyMovement.getMovementMode()))
				companyMovementDTO.setMode(companyMovement.getMovementMode().getValue());

			if (Objects.nonNull(movementType))
				companyMovementDTO.setType(movementType.getShortValue());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			companyMovementDTO.setDate(formatter.format(companyMovement.getProcessDate()));
			
			companyMovementDTO.setDataTimestamp(companyMovement.getProcessDate().toEpochDay());

			if (MovementType.BORROW.equals(movementType)) {
				balance = balance.subtract(companyMovement.getMovementAmount());
			} else if (MovementType.LOAN.equals(movementType)) {
				balance = balance.add(companyMovement.getMovementAmount());
			}

			companyMovementDTO.setBalance(balance);
			
			companyMovementDTO.setCompanyId(Base64Util.encode(companyMovement.getCompany().getId()));
			companyMovementDTO.setCompanyName(companyMovement.getCompany().getCompanyName());

			companyMovementDTOs.add(companyMovementDTO);
		}

		return companyMovementDTOs;
	}

	@Override
	public CompanyMovement findByOldId(Long oldId) {
		CompanyMovement companyMovement = companyMovementRepository.findByOldId(oldId);
		
		return companyMovement == null ? new CompanyMovement(): companyMovement;
	}

	@Override
	public List<String> getUnits() {
		return companyMovementRepository.getUnits();
	}

	@Override
	public List<String> getProductTypes() {
		return companyMovementRepository.getProductTypes();
	}

}
