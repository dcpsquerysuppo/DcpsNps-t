package com.tcs.sgv.common.valueobject;

// Generated Jun 19, 2007 11:15:23 AM by Hibernate Tools 3.2.0.beta8

import java.math.BigDecimal;
import java.util.Date;

/**
 * HstTrnBillRegister generated by hbm2java
 */
public class HstTrnBillRegister implements java.io.Serializable {

	// Fields

	private HstTrnBillRegisterId id;

	private String billCntrlNo;

	private Date billDate;

	private long subjectId;

	private Long tokenNum;

	private String tcBill;

	private Short phyBill;

	private String demandCode;

	private String budmjrHd;

	private Date inwardDt;

	private Long prevBillNo;

	private BigDecimal billGrossAmount;

	private BigDecimal billNetAmount;

	private BigDecimal principle;

	private BigDecimal grossInterest;

	private BigDecimal incomeTax;

	private Long createdUserId;

	private Long createdPostId;

	private Date createdDate;

	private Long updatedUserId;

	private Long updatedPostId;

	private Date updatedDate;

	private Long dbId;

	private Long audDeptId;

	private Long audUserId;

	private Long ddoPostId;

	private String exempted;

	private String billCode;

	private String goNgo;

	private Long inwardUserId;

	private Short currBillStatus;

	private String finYearId;

	private Long audPostId;

	private Long attachmentId;

	private String ddoCode;

	private Date currBillStatusDate;

	private BigDecimal grantAmount;

	private String deptCode;

	private String tsryOfficeCode;

	private String locationCode;

	private Long refNum;

	private BigDecimal auditorNetAmount;

	private Long scannedDocId;

	private String ppoNo;

	private Short receivedFlag;

	private String bpnNo;

	private String budSubmjrHd;

	private String budMinHd;

	private String budSubHd;

	private String budDtlHd;

	private String schemeNo;

	private String fund;

	private String clsExp;

	private Short budType;

	private Long voucherNo;

	private Date voucherDate;

	private Date auditDate;

	private Long cardexNo;

	private Short cardexVer;

	private Long hierarchyRefId;

	private Short auditStatus;

	private Long inwardedPost;

	private BigDecimal deductionA;

	private BigDecimal deductionB;

	private Date billDispDate;

	private Long tcAdvice;

	private Short isAudit;

	private Long subtrsyVouchNo;

	private Short isUpdated;
	
	private Integer pensionHeadCode;

	private String branchCode;
	
	private Date postAuditDate;
	
	private Long postAuditUser;
	
	private Long postAuditPost;
	
	private BigDecimal releasedGrant;
	
	private BigDecimal totalExp;
	
	private Date postAuditRecvDt;
	// Constructors
	
	/** default constructor */
	public HstTrnBillRegister() {
	}

	public HstTrnBillRegister(HstTrnBillRegisterId id, Date billDate,
			long subjectId, Short phyBill, String locationCode,
			Long createdUserId, Long createdPostId, Date createdDate) {
		this.id = id;
		this.billDate = billDate;
		this.subjectId = subjectId;
		this.phyBill = phyBill;
		this.locationCode = locationCode;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.createdDate = createdDate;
	}

	/** full constructor */
	public HstTrnBillRegister(HstTrnBillRegisterId id, String billCntrlNo,
			Date billDate, long subjectId, Long tokenNum, String tcBill,
			Short phyBill, String demandCode, String budmjrHd, Date inwardDt,
			Long prevBillNo, BigDecimal billGrossAmount,
			BigDecimal billNetAmount, BigDecimal principle,
			BigDecimal grossInterest, BigDecimal incomeTax, Long createdUserId,
			Long createdPostId, Date createdDate, Long updatedUserId,
			Long updatedPostId, Date updatedDate, Long dbId, Long audDeptId,
			Long audUserId, Long ddoPostId, String exempted, String billCode,
			String goNgo, Long agStatus, Short currBillStatus,
			String finYearId, Long audPostId, Long attachmentId,
			String ddoCode, Date agStatusDate, Date currBillStatusDate,
			Short paymentStatus, Date paymentStatusDate,
			BigDecimal grantAmount, String deptCode, String tsryOfficeCode,
			String locationCode, Long refNum, BigDecimal auditorNetAmount,
			Long scannedDocId, Short receivedFlag, String bpnNo,
			String budSubmjrHd, String budMinHd, String budSubHd,
			String budDtlHd, String schemeNo, String fund, String clsExp,
			Short budType, Long voucherNo, Date voucherDate, Date auditDate,
			Short cardexVer, Long hierarchyRefId, Short auditStatus,
			Long inwardedPost, BigDecimal deductionA, BigDecimal deductionB,
			Date billDiDate, Short isAudit, Long inwardUserId, String branchCode
			, Date postAuditDate,Long postAuditUser,Long postAuditPost) {
		this.id = id;
		this.billCntrlNo = billCntrlNo;
		this.billDate = billDate;
		this.subjectId = subjectId;
		this.tokenNum = tokenNum;
		this.tcBill = tcBill;
		this.phyBill = phyBill;
		this.demandCode = demandCode;
		this.budmjrHd = budmjrHd;
		this.inwardDt = inwardDt;
		this.prevBillNo = prevBillNo;
		this.billGrossAmount = billGrossAmount;
		this.billNetAmount = billNetAmount;
		this.principle = principle;
		this.grossInterest = grossInterest;
		this.incomeTax = incomeTax;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.createdDate = createdDate;
		this.updatedUserId = updatedUserId;
		this.updatedPostId = updatedPostId;
		this.updatedDate = updatedDate;
		this.dbId = dbId;
		this.audDeptId = audDeptId;
		this.audUserId = audUserId;
		this.ddoPostId = ddoPostId;
		this.exempted = exempted;
		this.billCode = billCode;
		this.goNgo = goNgo;
		this.currBillStatus = currBillStatus;
		this.finYearId = finYearId;
		this.audPostId = audPostId;
		this.attachmentId = attachmentId;
		this.ddoCode = ddoCode;
		this.currBillStatusDate = currBillStatusDate;
		this.grantAmount = grantAmount;
		this.deptCode = deptCode;
		this.tsryOfficeCode = tsryOfficeCode;
		this.locationCode = locationCode;
		this.refNum = refNum;
		this.auditorNetAmount = auditorNetAmount;
		this.scannedDocId = scannedDocId;
		this.receivedFlag = receivedFlag;
		this.bpnNo = bpnNo;
		this.budSubmjrHd = budSubmjrHd;
		this.budMinHd = budMinHd;
		this.budSubHd = budSubHd;
		this.budDtlHd = budDtlHd;
		this.schemeNo = schemeNo;
		this.fund = fund;
		this.clsExp = clsExp;
		this.budType = budType;
		this.voucherNo = voucherNo;
		this.voucherDate = voucherDate;
		this.auditDate = auditDate;
		this.cardexVer = cardexVer;
		this.hierarchyRefId = hierarchyRefId;
		this.auditStatus = auditStatus;
		this.inwardedPost = inwardedPost;
		this.deductionA = deductionA;
		this.deductionB = deductionB;
		this.billDispDate = billDiDate;
		this.isAudit = isAudit;
		this.inwardUserId = inwardUserId;
		this.branchCode = branchCode;
		this.postAuditDate = postAuditDate;
		this.postAuditUser = postAuditUser;
		this.postAuditPost = postAuditPost;
	}

	// Property accessors
	public HstTrnBillRegisterId getId() {
		return this.id;
	}

	public void setId(HstTrnBillRegisterId id) {
		this.id = id;
	}

	public String getBillCntrlNo() {
		return this.billCntrlNo;
	}

	public void setBillCntrlNo(String billCntrlNo) {
		this.billCntrlNo = billCntrlNo;
	}

	public Date getBillDate() {
		return this.billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public long getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getTokenNum() {
		return this.tokenNum;
	}

	public void setTokenNum(Long tokenNum) {
		this.tokenNum = tokenNum;
	}

	public String getTcBill() {
		return this.tcBill;
	}

	public void setTcBill(String tcBill) {
		this.tcBill = tcBill;
	}

	public Short getPhyBill() {
		return this.phyBill;
	}

	public void setPhyBill(Short phyBill) {
		this.phyBill = phyBill;
	}

	public String getDemandCode() {
		return this.demandCode;
	}

	public void setDemandCode(String demandCode) {
		this.demandCode = demandCode;
	}

	public String getBudmjrHd() {
		return this.budmjrHd;
	}

	public void setBudmjrHd(String budmjrHd) {
		this.budmjrHd = budmjrHd;
	}

	public Date getInwardDt() {
		return this.inwardDt;
	}

	public void setInwardDt(Date inwardDt) {
		this.inwardDt = inwardDt;
	}

	public Long getPrevBillNo() {
		return this.prevBillNo;
	}

	public void setPrevBillNo(Long prevBillNo) {
		this.prevBillNo = prevBillNo;
	}

	public BigDecimal getBillGrossAmount() {
		return this.billGrossAmount;
	}

	public void setBillGrossAmount(BigDecimal billGrossAmount) {
		this.billGrossAmount = billGrossAmount;
	}

	public BigDecimal getBillNetAmount() {
		return this.billNetAmount;
	}

	public void setBillNetAmount(BigDecimal billNetAmount) {
		this.billNetAmount = billNetAmount;
	}

	public BigDecimal getPrinciple() {
		return this.principle;
	}

	public void setPrinciple(BigDecimal principle) {
		this.principle = principle;
	}

	public BigDecimal getGrossInterest() {
		return this.grossInterest;
	}

	public void setGrossInterest(BigDecimal grossInterest) {
		this.grossInterest = grossInterest;
	}

	public BigDecimal getIncomeTax() {
		return this.incomeTax;
	}

	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}

	public Long getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Long getCreatedPostId() {
		return this.createdPostId;
	}

	public void setCreatedPostId(Long createdPostId) {
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

	public Long getAudDeptId() {
		return this.audDeptId;
	}

	public void setAudDeptId(Long audDeptId) {
		this.audDeptId = audDeptId;
	}

	public Long getAudUserId() {
		return this.audUserId;
	}

	public void setAudUserId(Long audUserId) {
		this.audUserId = audUserId;
	}

	public Long getDdoPostId() {
		return this.ddoPostId;
	}

	public void setDdoPostId(Long ddoPostId) {
		this.ddoPostId = ddoPostId;
	}

	public String getExempted() {
		return this.exempted;
	}

	public void setExempted(String exempted) {
		this.exempted = exempted;
	}

	public String getBillCode() {
		return this.billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getGoNgo() {
		return this.goNgo;
	}

	public void setGoNgo(String goNgo) {
		this.goNgo = goNgo;
	}

	public Short getCurrBillStatus() {
		return this.currBillStatus;
	}

	public void setCurrBillStatus(Short currBillStatus) {
		this.currBillStatus = currBillStatus;
	}

	public String getFinYearId() {
		return this.finYearId;
	}

	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}

	public Long getAudPostId() {
		return this.audPostId;
	}

	public void setAudPostId(Long audPostId) {
		this.audPostId = audPostId;
	}

	public Long getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getDdoCode() {
		return this.ddoCode;
	}

	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}

	public Date getCurrBillStatusDate() {
		return this.currBillStatusDate;
	}

	public void setCurrBillStatusDate(Date currBillStatusDate) {
		this.currBillStatusDate = currBillStatusDate;
	}

	public BigDecimal getGrantAmount() {
		return this.grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getTsryOfficeCode() {
		return tsryOfficeCode;
	}

	public void setTsryOfficeCode(String tsryOfficeCode) {
		this.tsryOfficeCode = tsryOfficeCode;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getRefNum() {
		return refNum;
	}

	public void setRefNum(Long refNum) {
		this.refNum = refNum;
	}

	public BigDecimal getAuditorNetAmount() {
		return this.auditorNetAmount;
	}

	public void setAuditorNetAmount(BigDecimal auditorNetAmount) {
		this.auditorNetAmount = auditorNetAmount;
	}

	public Long getScannedDocId() {
		return scannedDocId;
	}

	public void setScannedDocId(Long scannedDocId) {
		this.scannedDocId = scannedDocId;
	}

	public String getPpoNo() {
		return this.ppoNo;
	}

	public void setPpoNo(String ppoNo) {
		this.ppoNo = ppoNo;
	}

	public Short getReceivedFlag() {
		return this.receivedFlag;
	}

	public void setReceivedFlag(Short receivedFlag) {
		this.receivedFlag = receivedFlag;
	}

	public void setBpnNo(String bpnNo) {
		this.bpnNo = bpnNo;
	}

	public String getBpnNo() {
		return this.bpnNo;
	}

	public void setBudSubmjrHd(String budSubmjrHd) {
		this.budSubmjrHd = budSubmjrHd;
	}

	public String getBudSubmjrHd() {
		return this.budSubmjrHd;
	}

	public void setBudMinHd(String budMinHd) {
		this.budMinHd = budMinHd;
	}

	public String getBudMinHd() {
		return this.budMinHd;
	}

	public void setBudSubHd(String budSubHd) {
		this.budSubHd = budSubHd;
	}

	public String getBudSubHd() {
		return this.budSubHd;
	}

	public void setBudDtlHd(String budDtlHd) {
		this.budDtlHd = budDtlHd;
	}

	public String getBudDtlHd() {
		return this.budDtlHd;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getSchemeNo() {
		return this.schemeNo;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFund() {
		return this.fund;
	}

	public void setClsExp(String clsExp) {
		this.clsExp = clsExp;
	}

	public String getClsExp() {
		return this.clsExp;
	}

	public void setBudType(Short budType) {
		this.budType = budType;
	}

	public Short getBudType() {
		return this.budType;
	}

	public void setVoucherNo(Long voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Long getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public Date getVoucherDate() {
		return this.voucherDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public Long getCardexNo() {
		return cardexNo;
	}

	public void setCardexNo(Long cardexNo) {
		this.cardexNo = cardexNo;
	}

	public Short getCardexVer() {
		return cardexVer;
	}

	public void setCardexVer(Short cardexVer) {
		this.cardexVer = cardexVer;
	}

	public Long getHierarchyRefId() {
		return hierarchyRefId;
	}

	public void setHierarchyRefId(Long hierarchyRefId) {
		this.hierarchyRefId = hierarchyRefId;
	}

	public Short getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Short auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long getInwardedPost() {
		return inwardedPost;
	}

	public void setInwardedPost(Long inwardedPost) {
		this.inwardedPost = inwardedPost;
	}

	public BigDecimal getDeductionA() {
		return deductionA;
	}

	public void setDeductionA(BigDecimal deductionA) {
		this.deductionA = deductionA;
	}

	public BigDecimal getDeductionB() {
		return deductionB;
	}

	public void setDeductionB(BigDecimal deductionB) {
		this.deductionB = deductionB;
	}

	public Date getBillDispDate() {
		return billDispDate;
	}

	public void setBillDispDate(Date billDispDate) {
		this.billDispDate = billDispDate;
	}

	public Long getTcAdvice() {
		return tcAdvice;
	}

	public void setTcAdvice(Long tcAdvice) {
		this.tcAdvice = tcAdvice;
	}

	public Short getIsAudit() {
		return this.isAudit;
	}

	public void setIsAudit(Short isAudit) {
		this.isAudit = isAudit;
	}

	public Long getSubtrsyVouchNo() {
		return subtrsyVouchNo;
	}

	public void setSubtrsyVouchNo(Long subtrsyVouchNo) {
		this.subtrsyVouchNo = subtrsyVouchNo;
	}

	public Short getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Short isUpdated) {
		this.isUpdated = isUpdated;
	}

	public Long getInwardUserId() {
		return this.inwardUserId;
	}

	public void setInwardUserId(Long inwardUserId) {
		this.inwardUserId = inwardUserId;
	}

	public Integer getPensionHeadCode() {
		return pensionHeadCode;
	}

	public void setPensionHeadCode(Integer pensionHeadCode) {
		this.pensionHeadCode = pensionHeadCode;
	}
	
	public String getBranchCode(){
		return branchCode;
	}
	public void setBranchCode(String branchCode){
		this.branchCode = branchCode;
	}

	public Date getPostAuditDate() {
		return postAuditDate;
	}

	public void setPostAuditDate(Date postAuditDate) {
		this.postAuditDate = postAuditDate;
	}
	
	public void setPostAuditUser(Long postAuditUser) {
		this.postAuditUser = postAuditUser;
	}
	
	public Long getPostAuditUser() {
		return postAuditUser;
	}
	public void setPostAuditPost(Long postAuditPost) {
		this.postAuditPost = postAuditPost;
	}
	
	public Long getPostAuditPost() {
		return postAuditPost;
	}

	public BigDecimal getReleasedGrant() {
		return releasedGrant;
	}

	public void setReleasedGrant(BigDecimal releasedGrant) {
		this.releasedGrant = releasedGrant;
	}

	public BigDecimal getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(BigDecimal totalExp) {
		this.totalExp = totalExp;
	}
	public Date getPostAuditRecvDt() {
		return postAuditRecvDt;
	}

	public void setPostAuditRecvDt(Date postAuditRecvDt) {
		this.postAuditRecvDt = postAuditRecvDt;
	}	
}