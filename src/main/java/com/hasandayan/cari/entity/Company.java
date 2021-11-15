package com.hasandayan.cari.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hasandayan.cari.enums.CompanyGroup;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPANY",
	indexes = {@Index(name = "IDX_OLD_ID", columnList = "OLD_ID", unique = true),
			   @Index(name = "IDX_COMPANY_NAME", columnList = "COMPANY_NAME", unique = false)})
public class Company extends BaseEntity {

	@Id
	@SequenceGenerator(name = "SEQ_COMPANY", sequenceName = "SEQ_COMPANY", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "SEQ_COMPANY")
	@Column(name = "ID")
	private Long id;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "COMPANY_OFFICIAL")
	private String companyOfficial;

	@Enumerated(EnumType.STRING)
	@Column(name = "COMPANY_GROUP")
	private CompanyGroup companyGroup;

	@Column(name = "OTHER_INFO")
	private String otherInfo;

	@OneToMany(mappedBy = "company")
	private List<CompanyMovement> movements = new ArrayList<>();

	@Column(name = "IS_ACTIVE")
	private Boolean isActive = Boolean.TRUE;

	@Column(name = "OLD_ID", unique = true)
	private Long oldId;

	public Company() {

	}

	public Company(Long id, String companyName, String companyOfficial, CompanyGroup companyGroup, String otherInfo,
			Boolean isActive) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.companyOfficial = companyOfficial;
		this.companyGroup = companyGroup;
		this.otherInfo = otherInfo;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyOfficial() {
		return companyOfficial;
	}

	public void setCompanyOfficial(String companyOfficial) {
		this.companyOfficial = companyOfficial;
	}

	public CompanyGroup getCompanyGroup() {
		return companyGroup;
	}

	public void setCompanyGroup(CompanyGroup companyGroup) {
		this.companyGroup = companyGroup;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public List<CompanyMovement> getMovements() {
		return movements;
	}

	public void setMovements(List<CompanyMovement> movements) {
		this.movements = movements;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [id=").append(id).append(", companyName=").append(companyName)
				.append(", companyOfficial=").append(companyOfficial).append(", companyGroup=").append(companyGroup)
				.append(", otherInfo=").append(otherInfo).append(", isActive=").append(isActive).append(", oldId=")
				.append(oldId).append("]");
		return builder.toString();
	}

}
