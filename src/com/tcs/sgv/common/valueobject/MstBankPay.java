package com.tcs.sgv.common.valueobject;

// Generated May 22, 2007 11:50:41 AM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * MstBank generated by hbm2java
 */
public class MstBankPay implements java.io.Serializable {

	// Fields    

	private long bankId;

	private String bankCode;

	private String bankName;

	private String BShortName;

	private String bankAddress;

	private Long langId;

	private long activateFlag;

	private Date startDate;

	private Date endDate;

	private long createdUserId;

	private long createdPostId;

	private Date createdDate;

	private Long updatedUserId;

	private Long updatedPostId;

	private Date updatedDate;

	private long dbId;
	
	private String locationCode;
	
	private String micrCode;

	// Constructors

	/** default constructor */
	public MstBankPay() {
	}

	/** minimal constructor */
	public MstBankPay(long bankId, String bankName, long activateFlag,
			long createdUserId, long createdPostId,String locationCode,
			Date startDate, Date createdDate) {
		this.bankId = bankId;
		this.bankName = bankName;
		this.activateFlag = activateFlag;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.locationCode = locationCode;
		this.createdDate = createdDate;
		this.startDate = startDate;
	}

	/** full constructor */
	public MstBankPay(long bankId, String bankCode, String bankName,
			String BShortName, String bankAddress, Long langId,
			long activateFlag, Date startDate, Date endDate,
			long createdUserId, long createdPostId, Date createdDate,
			Long updatedUserId, Long updatedPostId, Date updatedDate,
			long dbId, String locationCode) {
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.BShortName = BShortName;
		this.bankAddress = bankAddress;
		this.langId = langId;
		this.activateFlag = activateFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.createdDate = createdDate;
		this.updatedUserId = updatedUserId;
		this.updatedPostId = updatedPostId;
		this.updatedDate = updatedDate;
		this.dbId = dbId;
		this.locationCode=locationCode;
	}

	// Property accessors
	public long getBankId() {
		return this.bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBShortName() {
		return this.BShortName;
	}

	public void setBShortName(String BShortName) {
		this.BShortName = BShortName;
	}

	public String getBankAddress() {
		return this.bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Long getLangId() {
		return this.langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public long getActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(long activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public long getCreatedPostId() {
		return this.createdPostId;
	}

	public void setCreatedPostId(long createdPostId) {
		this.createdPostId = createdPostId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedUserId() {
		return this.updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Long getUpdatedPostId() {
		return this.updatedPostId;
	}

	public void setUpdatedPostId(Long updatedPostId) {
		this.updatedPostId = updatedPostId;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getDbId() {
		return this.dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

}
