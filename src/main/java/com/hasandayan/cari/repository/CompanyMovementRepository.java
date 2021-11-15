package com.hasandayan.cari.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;

public interface CompanyMovementRepository extends CrudRepository<CompanyMovement, Long> {

	@Modifying
	@Query("update CompanyMovement cm set cm.isActive = :isActive where cm.company = :company")
	int deleteByCompany(@Param("isActive") Boolean isActive, @Param("company") Company company);
	
	List<CompanyMovement> findByIsActiveAndProcessDateOrderByProcessDateDesc(Boolean isActive, LocalDate date);
	
	CompanyMovement findByOldId(Long oldId);


	@Query("select distinct unit from CompanyMovement where unit is not null")
	List<String> getUnits();

	@Query("select distinct productType from CompanyMovement where productType is not null")
	List<String> getProductTypes();
}
