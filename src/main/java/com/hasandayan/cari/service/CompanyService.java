package com.hasandayan.cari.service;

import java.util.List;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.pojo.CompanyDTO;

public interface CompanyService {
	
	Company save(Company company);

	Iterable<Company> findAll();

	Company findById(Long id);

	List<CompanyDTO> getCompanyDTOs();
	
	CompanyDTO getCompanyDTO(Long id);

	void delete(Long id);

	Company findByOldId(Long cariKartId);


}
