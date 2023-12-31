// default package
// Generated Sep 14, 2009 5:50:44 PM by Hibernate Tools 3.2.0.beta8
package com.tcs.sgv.common.valueobject;
/**
 * HstRltDdoOrgCoddoId generated by hbm2java
 */
public class HstRltDdoOrgCoddoId implements java.io.Serializable {

	// Fields    

	private long ddoOrgId;

	private Integer trnCounter;

	// Constructors

	/** default constructor */
	public HstRltDdoOrgCoddoId() {
	}

	/** full constructor */
	public HstRltDdoOrgCoddoId(long ddoOrgId, Integer trnCounter) {
		this.ddoOrgId = ddoOrgId;
		this.trnCounter = trnCounter;
	}

	// Property accessors
	public long getDdoOrgId() {
		return this.ddoOrgId;
	}

	public void setDdoOrgId(long ddoOrgId) {
		this.ddoOrgId = ddoOrgId;
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
		if (!(other instanceof HstRltDdoOrgCoddoId))
			return false;
		HstRltDdoOrgCoddoId castOther = (HstRltDdoOrgCoddoId) other;

		return (this.getDdoOrgId() == castOther.getDdoOrgId())
				&& (this.getTrnCounter() == castOther.getTrnCounter());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getDdoOrgId();
		result = 37 * result + this.getTrnCounter();
		return result;
	}

}
