package com.tcs.sgv.common.valueobject;

// Generated Jun 12, 2007 4:44:35 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * MstBillType generated by hbm2java
 */
public class MstBillType implements java.io.Serializable {

	// Fields    

	private long billTypeId;

	private Long subjectId;

	private String subjectDesc;

	private Long reqFlag;

	private Short langId;

	private Short activateFlag;

	private Date startDate;

	private Date endDate;

	private long createdUserId;

	private long createdPostId;

	private Date createdDate;

	private Long updatedUserId;

	private Long updatedPostId;

	private Date updatedDate;	

	private Long dbId;

	private String billTypeCode;
	
	private String locationCode;
	
	private String shortName;
	
	private String billShrtCode;
	
	private String displayForDdo;

	// Constructors

	public String getBillShrtCode() {
		return billShrtCode;
	}

	public void setBillShrtCode(String billShrtCode) {
		this.billShrtCode = billShrtCode;
	}

	/** default constructor */
	public MstBillType() {
	}

	/** minimal constructor */
	public MstBillType(long billTypeId, long createdUserId, long createdPostId,
			Date createdDate, String locationCode, Date startDate, String displayForDdo) 
	{
		this.billTypeId = billTypeId;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.createdDate = createdDate;
		this.locationCode = locationCode;
		this.startDate = startDate;
		this.displayForDdo= displayForDdo;
	}

	/** full constructor */
	public MstBillType(long billTypeId, Long subjectId, String subjectDesc,
			Long reqFlag, Short langId, Short activateFlag, Date startDate,
			Date endDate, long createdUserId, long createdPostId,
			Date createdDate, Long updatedUserId, Long updatedPostId,
			Date updatedDate, Long dbId, String billTypeCode, String locationCode,
			String shortName,String billShortCode, String displayForDdo) 
	{
		this.billTypeId = billTypeId;
		this.subjectId = subjectId;
		this.subjectDesc = subjectDesc;
		this.reqFlag = reqFlag;
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
		this.billTypeCode = billTypeCode;
		this.locationCode = locationCode;
		this.shortName=shortName;
		this.billShrtCode = billShortCode;
		this.displayForDdo = displayForDdo;
	}

	// Property accessors
	public long getBillTypeId() {
		return this.billTypeId;
	}

	public void setBillTypeId(long billTypeId) {
		this.billTypeId = billTypeId;
	}

	public Long getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectDesc() {
		return this.subjectDesc;
	}

	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public Long getReqFlag() {
		return this.reqFlag;
	}

	public void setReqFlag(Long reqFlag) {
		this.reqFlag = reqFlag;
	}

	public Short getLangId() {
		return this.langId;
	}

	public void setLangId(Short langId) {
		this.langId = langId;
	}

	public Short getActivateFlag() {
		return this.activateFlag;
	}

	public void setActivateFlag(Short activateFlag) {
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

	public Long getDbId() {
		return this.dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	public String getBillTypeCode() {
		return this.billTypeCode;
	}

	public void setBillTypeCode(String billTypeCode) {
		this.billTypeCode = billTypeCode;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getDisplayForDdo()
	{
		return displayForDdo;
	}
	
	public void setDisplayForDdo(String displayForDdo)
	{
		this.displayForDdo = displayForDdo;
	}
	
}
