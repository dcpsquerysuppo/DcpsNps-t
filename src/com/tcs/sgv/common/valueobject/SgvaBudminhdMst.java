package com.tcs.sgv.common.valueobject;

// Generated Dec 18, 2007 11:40:47 AM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * SgvaBudminhdMst generated by hbm2java
 */
public class SgvaBudminhdMst implements java.io.Serializable {

	// Fields    

	private long budminhdId;

	private String budminhdCode;

	private String budminhdDescLong;

	private String budminhdDescShort;

	private String bpnCode;

	private String demandCode;

	private String budmjrhdCode;

	private String budsubmjrhdCode;

	private String langId;

	private String locId;

	private Date crtDt;

	private String crtUsr;

	private Date lstUpdDt;

	private String lstUpdUsr;

	private String status;

	private Long terminatedYrId;

	private Long finYrId;

	// Constructors

	/** default constructor */
	public SgvaBudminhdMst() {
	}

	/** minimal constructor */
	public SgvaBudminhdMst(long budminhdId) {
		this.budminhdId = budminhdId;
	}

	/** full constructor */
	public SgvaBudminhdMst(long budminhdId, String budminhdCode,
			String budminhdDescLong, String budminhdDescShort,
			String bpnCode, String demandCode, String budmjrhdCode,
			String budsubmjrhdCode, String langId, String locId, Date crtDt,
			String crtUsr, Date lstUpdDt, String lstUpdUsr, String status,
			Long terminatedYrId, Long finYrId) {
		this.budminhdId = budminhdId;
		this.budminhdCode = budminhdCode;
		this.budminhdDescLong = budminhdDescLong;
		this.budminhdDescShort = budminhdDescShort;
		this.bpnCode = bpnCode;
		this.demandCode = demandCode;
		this.budmjrhdCode = budmjrhdCode;
		this.budsubmjrhdCode = budsubmjrhdCode;
		this.langId = langId;
		this.locId = locId;
		this.crtDt = crtDt;
		this.crtUsr = crtUsr;
		this.lstUpdDt = lstUpdDt;
		this.lstUpdUsr = lstUpdUsr;
		this.status = status;
		this.terminatedYrId = terminatedYrId;
		this.finYrId = finYrId;
	}

	// Property accessors
	public long getBudminhdId() {
		return this.budminhdId;
	}

	public void setBudminhdId(long budminhdId) {
		this.budminhdId = budminhdId;
	}

	public String getBudminhdCode() {
		return this.budminhdCode;
	}

	public void setBudminhdCode(String budminhdCode) {
		this.budminhdCode = budminhdCode;
	}

	public String getBudminhdDescLong() {
		return this.budminhdDescLong;
	}

	public void setBudminhdDescLong(String budminhdDescLong) {
		this.budminhdDescLong = budminhdDescLong;
	}

	public String getBudminhdDescShort() {
		return this.budminhdDescShort;
	}

	public void setBudminhdDescShort(String budminhdDescShort) {
		this.budminhdDescShort = budminhdDescShort;
	}

	public String getBpnCode() {
		return this.bpnCode;
	}

	public void setBpnCode(String bpnCode) {
		this.bpnCode = bpnCode;
	}

	public String getDemandCode() {
		return this.demandCode;
	}

	public void setDemandCode(String demandCode) {
		this.demandCode = demandCode;
	}

	public String getBudmjrhdCode() {
		return this.budmjrhdCode;
	}

	public void setBudmjrhdCode(String budmjrhdCode) {
		this.budmjrhdCode = budmjrhdCode;
	}

	public String getBudsubmjrhdCode() {
		return this.budsubmjrhdCode;
	}

	public void setBudsubmjrhdCode(String budsubmjrhdCode) {
		this.budsubmjrhdCode = budsubmjrhdCode;
	}

	public String getLangId() {
		return this.langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public String getLocId() {
		return this.locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public Date getCrtDt() {
		return this.crtDt;
	}

	public void setCrtDt(Date crtDt) {
		this.crtDt = crtDt;
	}

	public String getCrtUsr() {
		return this.crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}

	public Date getLstUpdDt() {
		return this.lstUpdDt;
	}

	public void setLstUpdDt(Date lstUpdDt) {
		this.lstUpdDt = lstUpdDt;
	}

	public String getLstUpdUsr() {
		return this.lstUpdUsr;
	}

	public void setLstUpdUsr(String lstUpdUsr) {
		this.lstUpdUsr = lstUpdUsr;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTerminatedYrId() {
		return this.terminatedYrId;
	}

	public void setTerminatedYrId(Long terminatedYrId) {
		this.terminatedYrId = terminatedYrId;
	}

	public Long getFinYrId() {
		return this.finYrId;
	}

	public void setFinYrId(Long finYrId) {
		this.finYrId = finYrId;
	}

}
