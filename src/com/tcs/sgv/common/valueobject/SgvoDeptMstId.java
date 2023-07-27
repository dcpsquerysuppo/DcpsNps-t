package com.tcs.sgv.common.valueobject;

// Generated May 28, 2007 10:36:50 AM by Hibernate Tools 3.2.0.beta8

/**
 * SgvoDeptMstId generated by hbm2java
 */
public class SgvoDeptMstId implements java.io.Serializable {

	// Fields    

	private String deptId;

	private String langId;

	// Constructors

	/** default constructor */
	public SgvoDeptMstId() {
	}

	/** full constructor */
	public SgvoDeptMstId(String deptId, String langId) {
		this.deptId = deptId;
		this.langId = langId;
	}

	// Property accessors
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getLangId() {
		return this.langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SgvoDeptMstId))
			return false;
		SgvoDeptMstId castOther = (SgvoDeptMstId) other;

		return ((this.getDeptId() == castOther.getDeptId()) || (this
				.getDeptId() != null
				&& castOther.getDeptId() != null && this.getDeptId().equals(
				castOther.getDeptId())))
				&& ((this.getLangId() == castOther.getLangId()) || (this
						.getLangId() != null
						&& castOther.getLangId() != null && this.getLangId()
						.equals(castOther.getLangId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDeptId() == null ? 0 : this.getDeptId().hashCode());
		result = 37 * result
				+ (getLangId() == null ? 0 : this.getLangId().hashCode());
		return result;
	}

}