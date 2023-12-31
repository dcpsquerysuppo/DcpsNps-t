package com.tcs.sgv.common.valueobject;

// Generated Mar 17, 2009 8:31:17 PM by Hibernate Tools 3.2.0.beta8



/**
 * HstOrgDdoMstId generated by hbm2java
 */
public class HstOrgDdoMstId implements java.io.Serializable {

	// Fields    

	private Long ddoId;

	private Integer trnCounter;

	// Constructors

	/** default constructor */
	public HstOrgDdoMstId() {
	}

	/** full constructor */
	public HstOrgDdoMstId(Long ddoId, Integer trnCounter) {
		this.ddoId = ddoId;
		this.trnCounter = trnCounter;
	}

	// Property accessors
	public Long getDdoId() {
		return this.ddoId;
	}

	public void setDdoId(Long ddoId) {
		this.ddoId = ddoId;
	}

	public Integer getTrnCounter() {
		return this.trnCounter;
	}

	public void setTrnCounter(Integer trnCounter) {
		this.trnCounter = trnCounter;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HstOrgDdoMstId))
			return false;
		HstOrgDdoMstId castOther = (HstOrgDdoMstId) other;

		return ((this.getDdoId() == castOther.getDdoId()) || (this.getDdoId() != null
				&& castOther.getDdoId() != null && this.getDdoId().equals(
				castOther.getDdoId())))
				&& (this.getTrnCounter() == castOther.getTrnCounter());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDdoId() == null ? 0 : this.getDdoId().hashCode());
		result = 37 * result + (int) this.getTrnCounter();
		return result;
	}

}
