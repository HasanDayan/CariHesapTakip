package com.hasandayan.cari.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hasandayan.cari.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
	
	List<Company> findByIsActiveOrderByCompanyNameAsc(Boolean isActive);
	
	Company findByOldId(Long oldId);
}
