

package com.tcs.sgv.lna.dao;

import java.util.Date;
import java.util.List;

import com.tcs.sgv.core.dao.GenericDao;

public interface LNAHealthInsuranceDAO extends GenericDao {

	public List getHealthInsurance(String lStrSevaarthId, Long lLngRequestType);

	public List getHealthInsuranceToDEOApprover(Long lLngComAdvnId);

	public Boolean requestDataAlreadyExists(String lStrSevaarthId);

	public Boolean requestPendingStatus(String lStrSevaarthId);
	
	public List<Long> getAllHierarchyRefIdsForLocation(Long LocationCode);
	
	public Boolean checkEntryInWfHierachyPostMpg(Long lLongHierarchyRefId,Long lLongPostId);
	
	public void insertWfHierachyPostMpg(Long lLongHierarchySeqId ,Long lLongHierarchyRefId ,Long lLongPostId, Long lLongCreatedByUserId,Date gDtCurDate,Long LocId ) throws Exception;
	
	public String getiNewOrUpdate(String lStrSevaarthId);
	
	public String getGroupDflag(String lStrSevaarthId);
	
	public String getEmpGrp(String lStrSevaarthId);
	
	public String getEmpNameforBill(String lStrSevaarthId);
	
	public Long getHealthSancAmtforBill(String lStrSevaarthId);
	
	public String getEmpsevaarthId(String lStrGpfAccNo);
	
	public String getEmpBillGrpId(String lStrSevaarthId);
	
	public List getBeamsDetails(String lStrSevaarthId);
	
	public String getParentDept(String lStrSevaarthid);
	
	public String getSchemeCode(String SchemeCode);
	
}