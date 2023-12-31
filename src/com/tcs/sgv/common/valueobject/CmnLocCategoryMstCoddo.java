package com.tcs.sgv.common.valueobject;
// Generated Oct 1, 2009 2:31:15 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * CmnLocCategoryMstCoddo generated by hbm2java
 */
public class CmnLocCategoryMstCoddo implements java.io.Serializable {

	// Fields    

	private Long locCategoryId;
	private Long locCategoryCode;
	private String locCategoryName;
	private Long parentLocCategoryId;
	private byte langId;
	private long activateFlag;
	private long createdBy;
	private long createdByPost;
	private Date createdDate;
	private Long updatedBy;
	private Long updatedByPost;
	private Date updatedDate;
	private Integer trnCounter;

	// Constructors

	/** default constructor */
	public CmnLocCategoryMstCoddo() {
	}

	/** minimal constructor */
	public CmnLocCategoryMstCoddo(Long locCategoryId,
			Long locCategoryCode, String locCategoryName,
			Long parentLocCategoryId, byte langId, long activateFlag,
			long createdBy, long createdByPost, Date createdDate,
			Integer trnCounter) {
		this.locCategoryId = locCategoryId;
		this.locCategoryCode = locCategoryCode;
		this.locCategoryName = locCategoryName;
		this.parentLocCategoryId = parentLocCategoryId;
		this.langId = langId;
		this.activateFlag = activateFlag;
		this.createdBy = createdBy;
		this.createdByPost = createdByPost;
		this.createdDate = createdDate;
		this.trnCounter = trnCounter;
	}

	/** full constructor */
	public CmnLocCategoryMstCoddo(Long locCategoryId,
			Long locCategoryCode, String locCategoryName,
			Long parentLocCategoryId, byte langId, long activateFlag,
			long createdBy, long createdByPost, Date createdDate,
			Long updatedBy, Long updatedByPost, Date updatedDate,
			Integer trnCounter) {
		this.locCategoryId = locCategoryId;
		this.locCategoryCode = locCategoryCode;
		this.locCategoryName = locCategoryName;
		this.parentLocCategoryId = parentLocCategoryId;
		this.langId = langId;
		this.activateFlag = activateFlag;
		this.createdBy = createdBy;
		this.createdByPost = createdByPost;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedByPost = updatedByPost;
		this.updatedDate = updatedDate;
		this.trnCounter = trnCounter;
	}

	// Property accessors
	public Long getLocCategoryId() {
		return this.locCategoryId;
	}

	public void setLocCategoryId(Long locCategoryId) {
		this.locCategoryId = locCategoryId;
	}

	public Long getLocCategoryCode() {
		return this.locCategoryCode;
	}

	public void setLocCategoryCode(Long locCategoryCode) {
		this.locCategoryCode = locCategoryCode;
	}

	public String getLocCategoryName() {
		return this.locCategoryName;
	}

	public void setLocCategoryName(String locCategoryName) {
		this.locCategoryName = locCategoryName;
	}

	public Long getParentLocCategoryId() {
		return this.parentLocCategoryId;
	}

	public void setParentLocCategoryId(Long parentLocCategoryId) {
		this.parentLocCategoryId = parentLocCategoryId;
	}

	public byte getLangId() {
		return this.langId;
	}

	public void setLangId(byte langId) {
		this.langId = langId;
	}

	public long getActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(long activateFlag) {
		this.activateFlag = activateFlag;
	}

	public long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getCreatedByPost() {
		return this.createdByPost;
	}

	public void setCreatedByPost(long createdByPost) {
		this.createdByPost = createdByPost;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedByPost() {
		return this.updatedByPost;
	}

	public void setUpdatedByPost(Long updatedByPost) {
		this.updatedByPost = updatedByPost;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getTrnCounter() {
		return this.trnCounter;
	}

	public void setTrnCounter(Integer trnCounter) {
		this.trnCounter = trnCounter;
	}

}
