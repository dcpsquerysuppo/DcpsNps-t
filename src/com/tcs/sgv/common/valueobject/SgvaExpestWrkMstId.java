package com.tcs.sgv.common.valueobject;

// Generated Nov 4, 2008 5:38:17 AM by Hibernate Tools 3.2.0.CR1

/**
 * SgvaExpestWrkMstId generated by hbm2java
 */
public class SgvaExpestWrkMstId implements java.io.Serializable {

	private long expestWrkMstId;
	private String langId;

	public SgvaExpestWrkMstId() {
	}

	public SgvaExpestWrkMstId(long expestWrkMstId, String langId) {
		this.expestWrkMstId = expestWrkMstId;
		this.langId = langId;
	}

	public long getExpestWrkMstId() {
		return this.expestWrkMstId;
	}

	public void setExpestWrkMstId(long expestWrkMstId) {
		this.expestWrkMstId = expestWrkMstId;
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
		if (!(other instanceof SgvaExpestWrkMstId))
			return false;
		SgvaExpestWrkMstId castOther = (SgvaExpestWrkMstId) other;

		return (this.getExpestWrkMstId() == castOther.getExpestWrkMstId())
				&& ((this.getLangId() == castOther.getLangId()) || (this
						.getLangId() != null
						&& castOther.getLangId() != null && this.getLangId()
						.equals(castOther.getLangId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getExpestWrkMstId();
		result = 37 * result
				+ (getLangId() == null ? 0 : this.getLangId().hashCode());
		return result;
	}

}
