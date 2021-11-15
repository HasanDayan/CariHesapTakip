package com.hasandayan.cari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hasandayan.cari.enums.MovementMode;
import com.hasandayan.cari.enums.MovementType;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPANY_MOVEMENT",
		indexes = {@Index(name = "IDX_OLD_ID", columnList = "OLD_ID", unique = true),
					@Index(name = "IDX_PROCESS_DATE", columnList = "PROCESS_DATE", unique = false)})
public class CompanyMovement extends BaseEntity {

	@Id
	@SequenceGenerator(name = "SEQ_COMPANY_MOVEMENT", sequenceName = "SEQ_COMPANY_MOVEMENT", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "SEQ_COMPANY_MOVEMENT")
	@Column(name="ID")
	private Long id;

	@Column(name= "PROCESS_DATE", columnDefinition = "DATE")
	private LocalDate processDate;

	@Enumerated(EnumType.STRING)
	@Column(name="MOVEMENT_TYPE")
	private MovementType movementType;

	@Enumerated(EnumType.STRING)
	@Column(name="MOVEMENT_MODE")
	private MovementMode movementMode;

	@Column(name="PRODUCT_TYPE")
	private String productType;

	@Column(name="MOVEMENT_DESCRIPTION")
	private String movementDescription;

	@Column(name="MOVEMENT_AMOUNT")
	private BigDecimal movementAmount;

	@Column(name="QUANTITY")
	private BigDecimal quantity;

	@Column(name="UNIT")
	private String unit;

	@Column(name="UNIT_PRICE")
	private BigDecimal unitPrice;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")
	private Company company;

	@Column(name="IS_ACTIVE")
	private Boolean isActive = Boolean.TRUE;
	
	@Column(name="OLD_ID", unique = true)
	private Long oldId;
	
	@Column(name="TERM")
	private Long term;
	
	@Column(name="TERM_DATE")
	private LocalDate termDate;

	public CompanyMovement() {

	}

	public CompanyMovement(Long id, LocalDate processDate, MovementType movementType, MovementMode movementMode,
			String movementDescription, BigDecimal movementAmount, BigDecimal quantity, String unit,
			BigDecimal unitPrice, Boolean isActive) {
		this.id = id;
		this.processDate = processDate;
		this.movementType = movementType;
		this.movementMode = movementMode;
		this.movementDescription = movementDescription;
		this.movementAmount = movementAmount;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getProcessDate() {
		return processDate;
	}

	public void setProcessDate(LocalDate processDate) {
		this.processDate = processDate;
	}

	public MovementMode getMovementMode() {
		return movementMode;
	}

	public void setMovementMode(MovementMode movementMode) {
		this.movementMode = movementMode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMovementDescription() {
		return movementDescription;
	}

	public void setMovementDescription(String movementDescription) {
		this.movementDescription = movementDescription;
	}

	public BigDecimal getMovementAmount() {
		return movementAmount;
	}

	public void setMovementAmount(BigDecimal movementAmount) {
		this.movementAmount = movementAmount;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public LocalDate getTermDate() {
		return termDate;
	}

	public void setTermDate(LocalDate termDate) {
		this.termDate = termDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyMovement [id=").append(id).append(", processDate=").append(processDate)
				.append(", movementType=").append(movementType).append(", movementMode=").append(movementMode)
				.append(", productType=").append(productType).append(", movementDescription=")
				.append(movementDescription).append(", movementAmount=").append(movementAmount).append(", quantity=")
				.append(quantity).append(", unit=").append(unit).append(", unitPrice=").append(unitPrice)
				.append(", isActive=").append(isActive).append(", oldId=").append(oldId)
				.append(", term=").append(term).append(", termDate=").append(termDate).append("]");
		return builder.toString();
	}
	
}
