package com.tcs.sgv.common.valueobject;

import java.math.BigDecimal;

// Generated Jun 18, 2008 9:40:33 AM by Hibernate Tools 3.2.0.CR1


/**
 * RltBillEmdChallan generated by hbm2java
 */
public class RltBillEmdChallan implements java.io.Serializable {

	private Long billEmdChallanId;
	private Long billNo;
	private Long chlnSrNo;
	private Long trnEmdChallanDetlId;
    private BigDecimal chlAmount;
    
	public RltBillEmdChallan() {
	}

	public RltBillEmdChallan(Long billEmdChallanId, Long billNo,
			Long chlnSrNo, Long trnEmdChallanDetlId,BigDecimal chlAmount) {
		this.billEmdChallanId = billEmdChallanId;
		this.billNo = billNo;
		this.chlnSrNo = chlnSrNo;
		this.trnEmdChallanDetlId = trnEmdChallanDetlId;
		this.chlAmount = chlAmount;
	}

	public Long getBillEmdChallanId() {
		return this.billEmdChallanId;
	}

	public void setBillEmdChallanId(Long billEmdChallanId) {
		this.billEmdChallanId = billEmdChallanId;
	}

	public Long getBillNo() {
		return this.billNo;
	}

	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}

	public Long getChlnSrNo() {
		return this.chlnSrNo;
	}
	
	public void setChlnSrNo(Long chlnSrNo) {
		this.chlnSrNo = chlnSrNo;
	}
	
	public Long getTrnEmdChallanDetlId() {
		return this.trnEmdChallanDetlId;
	}
	public void setTrnEmdChallanDetlId(Long trnEmdChallanDetlId) {
		this.trnEmdChallanDetlId = trnEmdChallanDetlId;
	}
    public BigDecimal getChlAmount()
    {
        return this.chlAmount;
    }
    public void setChlAmount(BigDecimal chlAmount)
    {
        this.chlAmount = chlAmount;
    }
}
