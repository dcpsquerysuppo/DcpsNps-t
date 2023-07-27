/* Copyright TCS 2011, All Rights Reserved.
 * 
 * 
 ******************************************************************************
 ***********************Modification History***********************************
 *  Date   				Initials	     Version		Changes and additions
 ******************************************************************************
 * 	Apr 7, 2011		Vihan Khatri								
 *******************************************************************************
 */
package com.tcs.sgv.dcps.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tcs.sgv.apps.common.valuebeans.ComboValuesVO;
import com.tcs.sgv.common.valueobject.OrgDdoMst;
import com.tcs.sgv.core.dao.GenericDao;
import com.tcs.sgv.dcps.valueobject.DdoOffice;
import com.tcs.sgv.dcps.valueobject.MstEmp;

/**
 * Class Description -
 * 
 * 
 * @author Vihan Khatri
 * @version 0.1
 * @since JDK 5.0 Apr 7, 2011
 */
public interface DcpsCommonDAO extends GenericDao {

	public List getMonths();
	//$t 2019
	public List getMonths1();

	public List getYears();
	
	public  List getYears1();

	public List getFinyears();
	
	public List getFinyearsAfterCurrYear();

	public String getDdoCode(Long lLngAsstPostId);

	public String getDdoCodeForDDO(Long lLngPostId);

	List<ComboValuesVO> getAllDesignation(Long lLngDeptId, Long langId)
			throws Exception;

	List<ComboValuesVO> getAllDepartment(Long lLngDepartmentId, Long langId)
			throws Exception;

	List<ComboValuesVO> getAllHODDepartment(Long lLngDepartmentId, Long langId)
			throws Exception;

	public List getBillGroups() throws Exception;

	public Date getLastDate(Integer month, Integer year);

	public Date getFirstDate(Integer month, Integer year);

	public Object[] getSchemeNameFromBillGroup(Long billGroupId);

	public String getYearCodeForYearId(Long yearId);

	public String getMonthForId(Long monthId);

	public List getCadres();

	public List getBankNames() throws Exception;

	public List getBranchNames(Long lLngBankCode) throws Exception;

	public List getBranchNamesWithBsrCodes(Long lLngBankCode) throws Exception;

	public Long getIFSCCodeForBranch(Long branchName) throws Exception;

	public List getStateNames(Long langId) throws Exception;

	public List getDistricts(Long lStrCurrState) throws Exception;

	public List getTaluka(Long lStrCurrDst) throws Exception;

	public List getDesignations(String lStrCurrOffice) throws Exception;

	public String getCmnLookupNameFromId(Long lookupId);

	public String getDesigNameFromId(Long lookupId);

	public List getCurrentOffices(String lStrDdoCode);

	public OrgDdoMst getDDOInfoVOForDDOCode(String ddoCode);

	public String getDdoNameForCode(String lStrDdoCode);

	public OrgDdoMst getDdoVOForDdoCode(String ddoCode);

	public String getTreasuryNameForDDO(String lStrDdoCode);

	public String getTreasuryCodeForDDO(String lStrDdoCode);

	public List getCadreForDept(Long lLngDeptCode) throws Exception;

	public List getParentDeptForDDO(String lStrDdoCode);

	public List getDesigsForPFDAndCadre(Long fieldDeptId) throws Exception;

	List getDeptNameFromDdoCode(String lStrDdoCode);

	public List getAllTreasuries() throws Exception;
	
	public List getAllTreasuriesAndSubTreasuries() throws Exception ;

	public String getLocNameforLocId(Long locId);

	public String getCadreNameforCadreId(Long cadreId);
	
	public Long getCadreCodeforCadreId(Long cadreId) ;
	
	public Long getCadreIdforCadreCodeAndFieldDept(Long cadreCode,Long fieldDeptId);

	public String getGroupIdforCadreId(Long cadreId);

	public String getDddoOfficeNameNameforId(Long dcpsDdoOfficeIdPk);

	public List getOfficesForPost(Long lLongPostId) throws Exception;

	List<ComboValuesVO> getAllOrgType() throws Exception;

	public MstEmp getEmpVOForEmpId(Long dcpsEmpId) throws Exception;

	List getDatesFromFinYearId(Long yearId) throws Exception;

	String getCurrentInterestRate();

	String getFinYearForYearId(Long yearId);

	Float getCurrentDARate(String payComm);

	String getTreasuryNameForTreasuryId(Long treasuryId);

	public String getBranchNameForBranchCode(String branchId);

	public String getBankNameForBankCode(String bankCode);

	public List getAllDDOForTreasury(String lStrTreasuryLocCode);

	public List getLookupValuesForParent(Long lLngParentLookupId)
			throws Exception;

	public Boolean checkPFDForDDO(String lStrDdoCode);

	public List getBillGroups(String lStrDDOCode) throws Exception;

	public Long getDDOPostIdForDDOAsst(Long lLngPostId);

	public Long getFinYearIdForYearCode(String yearCode);

	public DdoOffice getDdoMainOffice(String lStrDdoCode);

	public String getTreasuryCityForDDO(String lStrDdoCode);

	public String getTreasuryShortNameForDDO(String lStrDdoCode);
	
	public String getFinYearCodeForYearId(Long yearId);
	
	public List getAllOffices() ;
	
	public String getFinYearIdForDate(Date FinDate);
	
	public List getStates(Long langId);
	
	public String getAdminBudgetCodeForDDO(String lStrDDOCode) throws Exception ;
	
	public Long getDDOAsstPostIdForDDO(String lStrDDOCode);
	
	public Long getUserIdForPostId(Long lLongPostId) ;

	public Long getDDOPostIdForDDO(String lStrDDOCode);
	
	public String getFieldDeptOfDDO(String lStrDdoCode) ;
	
	public String getFinYearDescForYearCode(String finYearCode) ;
	
	public List getBillGroupsNotDeletedAndNotDCPS(String lStrDDOCode) throws Exception ;
	
	public List getFinyearsForDelayedType() ;
	
	public List getAllAdminDeptsForReportIncludingAllDepts() throws Exception ;
	
	public List getAllDDOInclAll(String lStrTreasuryLocCode);

	public String getFinYearIdForYearDesc(String year);

	public String getMonthIdForName(String month);

	public List getFinyear();

	public List getAllTreasuriesForInterest() throws Exception;

	public List getAllTreasuriesAndSubTreasuriesForInterest() throws Exception;

	void UpdatePwd(String empUserName);
	
	public String getAstDDONameForAstDDO(String empUserName, String strDdoCode);

	public  String validateEmpDobDojForResetPwd(String empUserName, String strDdoCode, String dob, String doj);

	public List<ComboValuesVO> getReasonValues(Long lngLangId);

	public List getDDOoficeDesgn(String ddoCode);

	public List<ComboValuesVO> getDeptReasonValues(Long prntLookupId);

	public String checkDDOCodePresent(String ddoCode);

	public List getDDOoficeTreasury(String ddoCode);

	public List getNewTreasury(String ddoCode);
	public List<ComboValuesVO> getTreasuryList();

	public List<ComboValuesVO> getSubTreasuryList(Long treasuryId);

	public List getDdoCodeForAutoCompleteTresury(String ddoCode, Long locId);

	public String getMaxDDOCode(String newLocId);

	public int updateDDOCode(String tableName, String oldDdoCode,
			String newDdoCode);

	public String getAstUsername(String oldDdoCode);

	public int updateAstUserName(String newASTuserName, String astUserName);

	public String getDDOUsername(String oldDdoCode,String newDdoCode);

	public String getLocationCode(String oldDdoCode);

	public int updatenewTreasuryDDOCode(String tableName, String oldDdoCode,
			String newDdoCode, String locationCode,String ddoPostId,String asstDdoPostId);

	public List getPostId(String oldDdoCode);

	public void updateOldDDOCodePostIdLocId(String oldDdoCode, String ddoPostId,String newDdoPostId);

	public List getnewDDOPostId(String newDdoCode);

	public void updatenewTreasuryAsstDDOPostId(String asstDdoPostId,
			String newAsstDdoPostId);

	public String checkAstUserName(String newASTuserName);

	public String checkdeleteDDOcode();

	public Long getdeleteDDOcode();

	public int updateNewDDOCodeDelete(String crontableName,
			Long newDDocodeDelete, String newDdoCode);

		 public abstract List getLevel();
	  
	  public abstract List getPIPBForSevenPayEmployee(String paramString);
	  
	  public abstract String getLevelForPayBand(String paramString1, String paramString2);
	  
	  public abstract String getStateLevelForPayBand(String paramString1, String paramString2);
	  
	  public abstract int getLevelIdForGivenLevel(String paramString);
	  
	  public abstract int getStateLevelIdForGivenLevel(String paramString);
	  
	  public abstract List getStateLevel();
	  
	  public List getNEwMEDHTEBasicAsPerMAtrixForBunchPayPost(String gradeId,String tableName,String resStr);
		//ended by vamsi
	  public int getHTEMEDLevelIdForGivenLevel(String sevenLevel,String tableName);
	  
	  public List getHTEMEDLevel(String tableName);
		
	  public String getHTEDMELevelForPayBand(String payBand,String gradePay,String tableName);
	  
	  public List getAllDDO(String gStrLocationCode);
	  
	  public List getAllTreasury(String gStrLocationCode, String strRoleId);
	
	  MstEmp getEmployeeDetails(String var1);
}

