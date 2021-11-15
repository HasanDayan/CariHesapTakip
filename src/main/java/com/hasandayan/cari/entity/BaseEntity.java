package com.hasandayan.cari.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	@Column(name = "CREATE_DATE", columnDefinition = "TIMESTAMP")
	private LocalDateTime createDate = LocalDateTime.now();

	@Column(name = "UPDATE_DATE", columnDefinition = "TIMESTAMP")
	private LocalDateTime updateDate = LocalDateTime.now();

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

}
