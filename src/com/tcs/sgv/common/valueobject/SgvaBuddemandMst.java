package com.tcs.sgv.common.valueobject;

// Generated Jun 9, 2007 11:57:28 AM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * SgvaBuddemandMst generated by hbm2java
 */
public class SgvaBuddemandMst implements java.io.Serializable
{

    // Fields    

    private long demandId;

    private String demandCode;

    private String demandDesc;

    private String langId;

    private String locId;

    private Date crtDt;

    private String crtUsr;

    private Date lstUpdDt;

    private String lstUpdUsr;

    private String status;

    private Long terminatedYrId;

    private String demandType;

    private String bpncode;

    private String incharge;
    
    private Long finYrId ;


    // Constructors

    /** default constructor */
    public SgvaBuddemandMst()
    {
    }


    /** minimal constructor */
    public SgvaBuddemandMst(long demandId, Date crtDt)
    {
        this.demandId = demandId;
        this.crtDt = crtDt;
    }


    /** full constructor */
    public SgvaBuddemandMst(long demandId, String demandCode,
            String demandDesc, String langId, String locId, Date crtDt,
            String crtUsr, Date lstUpdDt, String lstUpdUsr, String status,
            Long terminatedYrId, String demandType, String bpncode,
            String incharge, Long finYrId)
    {
        this.demandId = demandId;
        this.demandCode = demandCode;
        this.demandDesc = demandDesc;
        this.langId = langId;
        this.locId = locId;
        this.crtDt = crtDt;
        this.crtUsr = crtUsr;
        this.lstUpdDt = lstUpdDt;
        this.lstUpdUsr = lstUpdUsr;
        this.status = status;
        this.terminatedYrId = terminatedYrId;
        this.demandType = demandType;
        this.bpncode = bpncode;
        this.incharge = incharge;
        this.finYrId = finYrId;
    }


    // Property accessors
    public long getDemandId()
    {
        return this.demandId;
    }


    public void setDemandId(long demandId)
    {
        this.demandId = demandId;
    }


    public String getDemandCode()
    {
        return this.demandCode;
    }


    public void setDemandCode(String demandCode)
    {
        this.demandCode = demandCode;
    }


    public String getDemandDesc()
    {
        return this.demandDesc;
    }


    public void setDemandDesc(String demandDesc)
    {
        this.demandDesc = demandDesc;
    }


    public String getLangId()
    {
        return this.langId;
    }


    public void setLangId(String langId)
    {
        this.langId = langId;
    }


    public String getLocId()
    {
        return this.locId;
    }


    public void setLocId(String locId)
    {
        this.locId = locId;
    }


    public Date getCrtDt()
    {
        return this.crtDt;
    }


    public void setCrtDt(Date crtDt)
    {
        this.crtDt = crtDt;
    }


    public String getCrtUsr()
    {
        return this.crtUsr;
    }


    public void setCrtUsr(String crtUsr)
    {
        this.crtUsr = crtUsr;
    }


    public Date getLstUpdDt()
    {
        return this.lstUpdDt;
    }


    public void setLstUpdDt(Date lstUpdDt)
    {
        this.lstUpdDt = lstUpdDt;
    }


    public String getLstUpdUsr()
    {
        return this.lstUpdUsr;
    }


    public void setLstUpdUsr(String lstUpdUsr)
    {
        this.lstUpdUsr = lstUpdUsr;
    }


    public String getStatus()
    {
        return this.status;
    }


    public void setStatus(String status)
    {
        this.status = status;
    }


    public Long getTerminatedYrId()
    {
        return this.terminatedYrId;
    }


    public void setTerminatedYrId(Long terminatedYrId)
    {
        this.terminatedYrId = terminatedYrId;
    }


    public String getDemandType()
    {
        return this.demandType;
    }


    public void setDemandType(String demandType)
    {
        this.demandType = demandType;
    }


    public String getBpncode()
    {
        return this.bpncode;
    }


    public void setBpncode(String bpncode)
    {
        this.bpncode = bpncode;
    }


    public String getIncharge()
    {
        return this.incharge;
    }


    public void setIncharge(String incharge)
    {
        this.incharge = incharge;
    }
    public void setFinYrId(Long finYrId)
    {
        this.finYrId = finYrId;
    }
    public long getFinYrId()
    {
        return this.finYrId;
    }

}
