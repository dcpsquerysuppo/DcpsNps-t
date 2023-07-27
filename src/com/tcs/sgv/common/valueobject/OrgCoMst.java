package com.tcs.sgv.common.valueobject;

// Generated Mar 16, 2009 11:48:10 AM by Hibernate Tools 3.2.0.beta8

import java.io.Serializable;

import java.util.Date;

/**
 * OrgCoMst generated by hbm2java
 */
public class OrgCoMst implements java.io.Serializable {

	// Fields    

	private Long officerId;

	private String officerCode;

	private String officerName;

	private String officerNo;

	private Long postId;

	private String deptId;

	private Long langId;

	private Date startDate;

	private Date endDate;

	private Long activateFlag;

	private Long createdBy;

	private Long createdByPost;

	private Date createdDate;

	private Long updatedBy;

	private Long updatedByPost;

	private Date updatedDate;

	private Long dbId;

	private Integer trnCounter;

	private String officeCode;

	private String locationCode;

	private String deptLocCode;

	private String shortName;

	// Constructors

	/** default constructor */
	public OrgCoMst() {
	}

	/** minimal constructor */
	public OrgCoMst(Long officerId, String officerCode,
			String officerName, Long postId, Long langId,
			Date startDate, Long activateFlag, Long createdBy,
			Long createdByPost, Date createdDate, Long dbId) {
		this.officerId = officerId;
		this.officerCode = officerCode;
		this.officerName = officerName;
		this.postId = postId;
		this.langId = langId;
		this.startDate = startDate;
		this.activateFlag = activateFlag;
		this.createdBy = createdBy;
		this.createdByPost = createdByPost;
		this.createdDate = createdDate;
		this.dbId = dbId;
	}

	/** full constructor */
	public OrgCoMst(Long officerId, String officerCode,
			String officerName, String officerNo, Long postId,
			String deptId, Long langId, Date startDate, Date endDate,
			Long activateFlag, Long createdBy,
			Long createdByPost, Date createdDate, Long updatedBy,
			Long updatedByPost, Date updatedDate, Long dbId,
			Integer trnCounter, String officeCode, String locationCode,
			String deptLocCode, String shortName) {
		this.officerId = officerId;
		this.officerCode = officerCode;
		this.officerName = officerName;
		this.officerNo = officerNo;
		this.postId = postId;
		this.deptId = deptId;
		this.langId = langId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.activateFlag = activateFlag;
		this.createdBy = createdBy;
		this.createdByPost = createdByPost;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedByPost = updatedByPost;
		this.updatedDate = updatedDate;
		this.dbId = dbId;
		this.trnCounter = trnCounter;
		this.officeCode = officeCode;
		this.locationCode = locationCode;
		this.deptLocCode = deptLocCode;
		this.shortName = shortName;
	}

	// Property accessors
	public Long getOfficerId() {
		return this.officerId;
	}

	public void setOfficerId(Long officerId) {
		this.officerId = officerId;
	}

	public String getOfficerCode() {
		return this.officerCode;
	}

	public void setOfficerCode(String officerCode) {
		this.officerCode = officerCode;
	}

	public String getOfficerName() {
		return this.officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public String getOfficerNo() {
		return this.officerNo;
	}

	public void setOfficerNo(String officerNo) {
		this.officerNo = officerNo;
	}

	public Long getPostId() {
		return this.postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Long getLangId() {
		return this.langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Long getActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(Long activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedByPost() {
		return this.createdByPost;
	}

	public void setCreatedByPost(Long createdByPost) {
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

	public Long getDbId() {
		return this.dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	public Integer getTrnCounter() {
		return this.trnCounter;
	}

	public void setTrnCounter(Integer trnCounter) {
		this.trnCounter = trnCounter;
	}

	public String getOfficeCode() {
		return this.officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getDeptLocCode() {
		return this.deptLocCode;
	}

	public void setDeptLocCode(String deptLocCode) {
		this.deptLocCode = deptLocCode;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
