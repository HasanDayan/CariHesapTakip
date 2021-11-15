package com.hasandayan.cari.service;

import java.time.LocalDate;
import java.util.List;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;
import com.hasandayan.cari.pojo.CompanyMovementDTO;

public interface CompanyMovementService {

	CompanyMovement save(CompanyMovement companyMovement);

	CompanyMovement findById(Long id);

	List<CompanyMovementDTO> getCompanyMovementDTOs(String cariHesapId);

	void delete(Long id);

	void deleteByCompany(Company company);

	List<CompanyMovementDTO> getCompanyMovementDTOs(LocalDate date);

	CompanyMovement findByOldId(Long oldId);

	List<String> getUnits();

	List<String> getProductTypes();

}
