// default package
// Generated Feb 29, 2008 6:47:05 PM by Hibernate Tools 3.2.0.beta8
package com.tcs.sgv.pensionpay.valueobject;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TrnPensionArrearDtls generated by hbm2java
 */
public class TrnPensionArrearDtls implements java.io.Serializable,Cloneable
{

    // Fields    

  	private Long pensionArrearDtlsId;

    private Long pensionRequestId;

    private String pensionerCode;

    private String arrearFieldType;

    private BigDecimal oldAmountPercentage;

    private BigDecimal newAmountPercentage;

    private Integer effectedFromYyyymm;

    private Integer effectedToYyyymm;

    private BigDecimal differenceAmount = BigDecimal.ZERO;

    private BigDecimal totalDifferenceAmt = BigDecimal.ZERO;

    private Integer paymentFromYyyymm;

    private Integer paymentToYyyymm;

    private Long billNo;

    private Integer trnCounter;

    private BigDecimal createdUserId;

    private BigDecimal createdPostId;

    private Date createdDate;

    private BigDecimal updatedUserId;

    private BigDecimal updatedPostId;

    private Date updatedDate;

    private String remarks;

    private String isManual;
    
    private Long arrearAttacmentId;
    
    private Character paidFlag;

    // Constructors

    /** default constructor */
    public TrnPensionArrearDtls()
    {
    }


    /** minimal constructor */
    public TrnPensionArrearDtls(Long pensionArrearDtlsId, Long pensionRequestId, String pensionerCode,
            String arrearFieldType, BigDecimal createdUserId, BigDecimal createdPostId, Date createdDate)
    {
        this.pensionArrearDtlsId = pensionArrearDtlsId;
        this.pensionRequestId = pensionRequestId;
        this.pensionerCode = pensionerCode;
        this.arrearFieldType = arrearFieldType;
        this.createdUserId = createdUserId;
        this.createdPostId = createdPostId;
        this.createdDate = createdDate;
    }


    /** full constructor */
    public TrnPensionArrearDtls(Long pensionArrearDtlsId, Long pensionRequestId, String pensionerCode, String arrearFieldType, BigDecimal oldAmountPercentage, BigDecimal newAmountPercentage,
			Integer effectedFromYyyymm, Integer effectedToYyyymm, BigDecimal differenceAmount, BigDecimal totalDifferenceAmt, Integer paymentFromYyyymm, Integer paymentToYyyymm, Long billNo,
			Integer trnCounter, BigDecimal createdUserId, BigDecimal createdPostId, Date createdDate, BigDecimal updatedUserId, BigDecimal updatedPostId, Date updatedDate, String remarks,
			String isManual, Long arrearAttacmentId, Character paidFlag) {

		super();
		this.pensionArrearDtlsId = pensionArrearDtlsId;
		this.pensionRequestId = pensionRequestId;
		this.pensionerCode = pensionerCode;
		this.arrearFieldType = arrearFieldType;
		this.oldAmountPercentage = oldAmountPercentage;
		this.newAmountPercentage = newAmountPercentage;
		this.effectedFromYyyymm = effectedFromYyyymm;
		this.effectedToYyyymm = effectedToYyyymm;
		this.differenceAmount = differenceAmount;
		this.totalDifferenceAmt = totalDifferenceAmt;
		this.paymentFromYyyymm = paymentFromYyyymm;
		this.paymentToYyyymm = paymentToYyyymm;
		this.billNo = billNo;
		this.trnCounter = trnCounter;
		this.createdUserId = createdUserId;
		this.createdPostId = createdPostId;
		this.createdDate = createdDate;
		this.updatedUserId = updatedUserId;
		this.updatedPostId = updatedPostId;
		this.updatedDate = updatedDate;
		this.remarks = remarks;
		this.isManual = isManual;
		this.arrearAttacmentId = arrearAttacmentId;
		this.paidFlag = paidFlag;
	}


    // Property accessors
    public Long getPensionArrearDtlsId()
    {
        return this.pensionArrearDtlsId;
    }

    public void setPensionArrearDtlsId(Long pensionArrearDtlsId)
    {
        this.pensionArrearDtlsId = pensionArrearDtlsId;
    }


    public Long getPensionRequestId()
    {
        return this.pensionRequestId;
    }


    public void setPensionRequestId(Long pensionRequestId)
    {
        this.pensionRequestId = pensionRequestId;
    }


    public String getPensionerCode()
    {
        return this.pensionerCode;
    }


    public void setPensionerCode(String pensionerCode)
    {
        this.pensionerCode = pensionerCode;
    }


    public String getArrearFieldType()
    {
        return this.arrearFieldType;
    }


    public void setArrearFieldType(String arrearFieldType)
    {
        this.arrearFieldType = arrearFieldType;
    }


    public BigDecimal getOldAmountPercentage()
    {
        return this.oldAmountPercentage;
    }


    public void setOldAmountPercentage(BigDecimal oldAmountPercentage)
    {
        this.oldAmountPercentage = oldAmountPercentage;
    }


    public BigDecimal getNewAmountPercentage()
    {
        return this.newAmountPercentage;
    }


    public void setNewAmountPercentage(BigDecimal newAmountPercentage)
    {
        this.newAmountPercentage = newAmountPercentage;
    }


    public Integer getEffectedFromYyyymm()
    {
        return this.effectedFromYyyymm;
    }


    public void setEffectedFromYyyymm(Integer effectedFromYyyymm)
    {
        this.effectedFromYyyymm = effectedFromYyyymm;
    }


    public Integer getEffectedToYyyymm()
    {
        return this.effectedToYyyymm;
    }


    public void setEffectedToYyyymm(Integer effectedToYyyymm)
    {
        this.effectedToYyyymm = effectedToYyyymm;
    }


    public BigDecimal getDifferenceAmount()
    {
        return this.differenceAmount;
    }


    public void setDifferenceAmount(BigDecimal differenceAmount)
    {
        this.differenceAmount = differenceAmount;
    }


    public BigDecimal getTotalDifferenceAmt()
    {
        return this.totalDifferenceAmt;
    }


    public void setTotalDifferenceAmt(BigDecimal totalDifferenceAmt)
    {
        this.totalDifferenceAmt = totalDifferenceAmt;
    }


    public Integer getPaymentFromYyyymm()
    {
        return this.paymentFromYyyymm;
    }


    public void setPaymentFromYyyymm(Integer paymentFromYyyymm)
    {
        this.paymentFromYyyymm = paymentFromYyyymm;
    }


    public Integer getPaymentToYyyymm()
    {
        return this.paymentToYyyymm;
    }


    public void setPaymentToYyyymm(Integer paymentToYyyymm)
    {
        this.paymentToYyyymm = paymentToYyyymm;
    }


    public Long getBillNo()
    {
        return this.billNo;
    }


    public void setBillNo(Long billNo)
    {
        this.billNo = billNo;
    }


    public Integer getTrnCounter()
    {
        return this.trnCounter;
    }


    public void setTrnCounter(Integer trnCounter)
    {
        this.trnCounter = trnCounter;
    }


    public BigDecimal getCreatedUserId()
    {
        return this.createdUserId;
    }


    public void setCreatedUserId(BigDecimal createdUserId)
    {
        this.createdUserId = createdUserId;
    }


    public BigDecimal getCreatedPostId()
    {
        return this.createdPostId;
    }


    public void setCreatedPostId(BigDecimal createdPostId)
    {
        this.createdPostId = createdPostId;
    }


    public Date getCreatedDate()
    {
        return this.createdDate;
    }


    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }


    public BigDecimal getUpdatedUserId()
    {
        return this.updatedUserId;
    }


    public void setUpdatedUserId(BigDecimal updatedUserId)
    {
        this.updatedUserId = updatedUserId;
    }


    public BigDecimal getUpdatedPostId()
    {
        return this.updatedPostId;
    }


    public void setUpdatedPostId(BigDecimal updatedPostId)
    {
        this.updatedPostId = updatedPostId;
    }


    public Date getUpdatedDate()
    {
        return this.updatedDate;
    }


    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }


    public String getRemarks()
    {
        return this.remarks;
    }


    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public void setIsManual(String isManual)
    {
    	this.isManual = isManual;
    }
    
    public String getIsManual()
    {
    	return this.isManual;
    }


	
	public Long getArrearAttacmentId()
	{
	
		return arrearAttacmentId;
	}


	
	public void setArrearAttacmentId(Long arrearAttacmentId)
	{
	
		this.arrearAttacmentId = arrearAttacmentId;
	}

	public Character getPaidFlag() {
	
		return paidFlag;
	}
	
	public void setPaidFlag(Character paidFlag) {
	
		this.paidFlag = paidFlag;
	}
	  
	public Object clone() throws CloneNotSupportedException {

		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}
}
