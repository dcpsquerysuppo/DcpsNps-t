/* Copyright TCS 2011, All Rights Reserved.
 * 
 * 
 ******************************************************************************
 ***********************Modification History***********************************
 *  Date   				Initials	     Version		Changes and additions
 ******************************************************************************
 * 	Jun 29, 2011		Meeta Thacker								
 *******************************************************************************
 */
package com.tcs.sgv.gpf.dao;

import java.util.Date;
import java.util.List;

import com.tcs.sgv.core.dao.GenericDao;
import com.tcs.sgv.eis.valueobject.HrEisScaleMst;


/**
 * Class Description -
 * 
 * 
 * @author Meeta Thacker
 * @version 0.1
 * @since JDK 5.0 Jun 29, 2011
 */
public interface GPFRequestProcessDAO extends GenericDao {
	public List getEmployeeDetail(String lStrSevaarthId, String empName, String criteria, String lStrLocationCode,
			String lStrUser) throws Exception;

	public List getEmployeeDetailDeputation(String lStrSevaarthId, String empName, String criteria,String lStrUser) throws Exception;

	/*public List getGPFRequestList(String lStrUserType, String lStrPostId, String lStrLocationCode, String lStrCriteria,
			String lStrName, Date lDtSaveDate);*/
	public List getGPFRequestList(String lStrUserType, String lStrPostId, String lStrLocationCode, 
			String lStrSearchBy,String lStrSearchValue);

	//public List getWithdrawalDetail(String lStrGpfAccNo, String lStrAdvanceType);
	public List getWithdrawalDetail(String lStrGpfAccNo, String lStrAdvanceType,String lStrSevaarthId); //swt 09/07/2020

	//List getAdvanceHistory(String lStrGpfAccNo, Long lLngYearId);
	
	List getAdvanceHistory(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId); //swt 01/06/2020

	Long getDPOrGP(Long lLngEmpId, String lStrPayComm);

	//List getGPFAccountBalance(String lStrGpfAccNo, Long lLngYearId);
	
	List getGPFAccountBalance(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId); //swt 01/06/2020

	String getNewTransactionId(String lStrSevaarthId);

	public String getDdoCode(String lStrLocationCode);
	
	public String getLocationCode(String lStrddocode);

	public String getEmployeeDdoCode(String sevarthId);

	public Long getDDOPostIdForVerifierHo(Long lLngPostId, String lStrUserType);

	public String getDdoCodeForDDO(Long lLngPostId);

	// public Double getMonthlySubscription(String lStrGpfAccNo, Integer
	// lIntMonthId, Integer lIntFinYearId);

	//List getAdvanceDetail(String lStrGpfAccNo, String lStrAdvanceType);
	List getAdvanceDetail(String lStrGpfAccNo, String lStrAdvanceType,String lStrSevaarthId); //swt 01/06/2020

	HrEisScaleMst getPayScaleData(Long lLngEmpId);

	Boolean withdrawalExistsForFinYear(String strGpfAccNo, Long lLngFinYearId);

	List getEmpNameForAutoComplete(String searchKey, String lStrDdoCode);

	//Double getOpeningBalForCurrYear(String lStrGpfAccNo, Long lLngYearId) throws Exception;
	Double getOpeningBalForCurrYear(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId) throws Exception; //swt 09/06/2020

	String getLocationCodeOfUser(Long lLngPostId) throws Exception;

	/*List getDraftRequestList(String lStrPostId, String lStrLocationCode, String lStrCriteria, String lStrName,
			Date lDtSaveDate) throws Exception;*/
	List getDraftRequestList(String lStrPostId, String lStrLocationCode, String lStrCriteria, String lStrSearchBy,
			String lStrSearchValue) throws Exception;

	String getDistrictNameForId(Long lLngDistrictId);

	String getStateNameForId(Long lLngStateId);

	public boolean isDataEntryComplete(String lStrSevaarthId);

	String getDdoCodeForDEO(String lStrLocationCode)throws Exception;

	List getDsgnAndOfficeName(String lStrSevaarthId);

	//Double getClosingBalance(String lStrGPFAccNo);
	
	Double getClosingBalance(String lStrGPFAccNo,String lStrSevaarthId);//swt 01/06/2020

	String getEmployerOfficeName(String lStrDDOCode);

	String getEmployerDsgnName(Long lLngPostId);

	String getTreasuryNameOfEmp(String lStrDDOCode);

	Date getEmpRetirmentDate(String lStrSevaarthId);

	//Double getLatestSubscription(String lStrGpfAccNo);
	
	Double getLatestSubscription(String lStrGpfAccNo,String lStrSevaarthId); //swt 09/07/2020

	String getSevaarthIdFromName(String lStrName);

	//public List getRegularSubscription(String strSevaarthId, String strDdoCode);
	public String getRegularSubscription(String lStrSevaarthId,Long lLngCurrMonth, Long lLngCurrYr);
	public String getMonthlySubscription(String lStrSevaarthId);

	public String getGradePayFrmSevaarthId(String lStrSevaarthId);
	public Long getAdvanceHistoryByAdvanceType(String lStrGpfAccNo, Long lLngYearId,String advanceType) ;
	public String getEmployerNameFrmPostId(String postId);
	public String getPurposeNameFrmId(String lookUpID);	
	public String getPrpsFrmTransactionId(String transactionId);
	public List getDDODeptDetails(String empSevaarthCode);
	public Date getApplicationDateFrmTranId(String strTransactionId);
	public Date getFwApplicationDateFrmTranId(String strFwTransactionId);

	//added by kiranvir 
	public String getSpecialCase90(String strTransactionId);
	public String getOtherPurpose(String strTransactionId);
	public List getDeputationOfficeDetails(String strTransactionId);

	public String getDDODesignforOrder(String locationcode);

	//public List getRecoveryDetails(String lStrGpfaccno, Long lLngFinyrId);

	public List getLoanDetails(String gpfaccno, int curryear, int i);

	//public Long getRecoveryAmt(String lStrGpfaccNo,Long lLngCurrYear, Long lLngPrevYr);

	public Long getPaybillMonth(String lStrSevaarthId,Long lLngCurrYr);
	public String getRecoveryAmt(String lStrGpfaccNo,Long lLngCurrYear,String lStrSevaarthId,Long lLngPaybillMonth);

	public String getPostId(String post);

	public String getDistrictIdforddocode(String lStrEmpDdocode);

	//public List getSixPayAmounts(String lStrGpfaccNo,Long FinyrId);
	public List getSixPayAmounts(String lStrGpfaccNo,Long FinyrId,String lStrSevaarthId); //swt 01/06/2020

	public Double getAmtSanctioned(String lStrGpfAccno);
	//public Double getWithdrawalSanc(String lStrGpfAccno,Long FinyrId);
	public Double getWithdrawalSanc(String lStrGpfAccno,Long FinyrId,String lStrSevaarthId); //swt 01/06/2020

	public String getEmployeeDDOCode(String lStrSevaarthid);
	public List getInstallmentvalues(String lStrTransactionId);

	public Long getLocationCode(Long lLngPostId);
	public String getOfficename(Long lLngLocationCode);

	public List getInitials(String lStrSevarthid);

	//gokarna
	public String getSpecialCaseValue(String transactionid);
	public String  getCurrentFlag (String transactionid) throws Exception;

	public List getDDODetails(String lStrddocode);


	//public List getRecoveredAndOutstandingAmt(String gpfAcNo);
	public List getRecoveredAndOutstandingAmt(String gpfAcNo,String lStrSevaarthId); //swt 01/06/2020

/*	public List CheckEmploeeTakenLoanAlreadyOrNotNRA(String strGpfAccNo,
			Long lngCurrYr);*/
	public List CheckEmploeeTakenLoanAlreadyOrNotNRA(String strGpfAccNo,
			Long lngCurrYr,String lStrSevaarthId); //swt 19/06/2020

	public int CheckFinalWithdrawnLoanIsTaken(String strSevaarthId);

	/*public List CheckEmploeeTakenLoanForOnceInSErvice(String strGpfAccNo,
			Long lngCurrYr);*/
	public List CheckEmploeeTakenLoanForOnceInSErvice(String strGpfAccNo,
			Long lngCurrYr,String lStrSevaarthId); //swt 19/06/2020

	public String getDesignName(Long lngPostId);

	public List gettypeOfAdv(String strTransactionId);

	public Long getCurrentRecoveryAmt(String gpfAccNo, Long lngCurrYr,
			String strSevaarthId, Long lngCurrMonth);

	public Long getregsub(String strSevaarthId, Long lngCurrYr,
			Long lngPaybillMonth);

	public String getDesignforBillDDO(String strTransactionId);

	public String getSanctionedDateFrmTranId(String strTransactionId);

	public List gettypeOfAdv1(String strTransactionId);

	//public List<Object[]> getAdvanceDetail1(String gpfAccNo, String reqType);
	public List<Object[]> getAdvanceDetail1(String gpfAccNo, String reqType,String lStrSevaarthId); //swt 09/06/2020


	public String getSanctionedDateFrmTranId1(String strTransactionId);

	//public Double getOpeningBalForCurrYearNew(String lStrGpfAccNo, Long lLngYearId) throws Exception;
	public Double getOpeningBalForCurrYearNew(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId, String isSevenpc) throws Exception;  //swt 01/06/2020

	//public List getSixPayAmountsNew(String lStrGpfAccNo,Long FinyrId);
	public List getSixPayAmountsNew(String lStrGpfAccNo,Long FinyrId,String lStrSevaarthId, String isSevenpc);  //swt 01/06/2020

	//public Double getOpeningBalance(String lStrGpfAccNo, Long lLngYearId) throws Exception;
	public Double getOpeningBalance(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId, String isSevenpc) throws Exception; //swt 01/06/2020

	//public List getAdvWithSanctionedAmnt(String lStrGpfAccNo,Long FinyrId);
	public List getAdvWithSanctionedAmnt(String lStrGpfAccNo,Long FinyrId,String lStrSevaarthId, String isSevenpc);  //swt 01/06/2020

	public String getSanctionedDateFrmTranIdFinalWithdrwal(
			String strTransactionId);

	public String getSanctionedDateFrmTranId1Final(String strTransactionId);
	//public List getInitialSixPayAmnts(String lStrGpfAccNo);
	public List getInitialSixPayAmnts(String lStrGpfAccNo,String lStrSevaarthId); //swt 22/07/2020
	public Long getFinYearCode(Long finYearId);
	public Double getInterestAmount(Long month,Long year,String gpfAccNo);

	//public Double getNetBalance(Long month,Long year,String gpfAccNo);
	public Double getNetBalance(Long month,Long year,String gpfAccNo,String lStrSevaarthId, String isSevenpc); //swt 10/07/2020

	public List getMonths();

	public List getYears();
	//public List getLoanDetails(String strSevaarthId, int curryear, int i);

	//public List getLoanDetails(String strSevaarthId, int curryear, int i);



	// Added by Akshay Kumar
	public List getPanNumber(String lStrSevaarthId);
	public Double getSanctionedAmount(String trasanId,String reqtype);
	public List getNameAndDesignation(String lStrSevaarthId);
	public String getBillGenerationDate(String trasanId);
	public String getOrderNumber(String trasanId);

	public String getDDOLocName(String strTransactionId);

	public String getGenderOfEmployee(String strSevaarthId);

	public int CheckFinalWithdrawnLoanDraft(String strSevaarthId);

	//public List getPrvsPurpzRst(String gpfAccNo,String advanceType, String lStrPurposeIdOfRequest);
	public List getPrvsPurpzRst(String gpfAccNo,String advanceType, String lStrPurposeIdOfRequest,String lStrSevaarthId); //swt 01/06/2020

	public String getPrpsIdFrmTransactionId(String strTransactionId);

	public Double getArrearsTotalAmt(String strSevId, Long CurrFinYearId, String strGpfAccNo); //ADDED METHOD BY VIVEK 24 JULY 2017 

	public String getEmpSevarthIdNew(String strEmpName);  //ADDED METHOD BY VIVEK 14 Sep 2017
	
	public List getYears(Integer finYearId); //Added by Sooraj 12/3/2018
	
	//public List getEmployeeInterestDtls(String gpfAccNo,Integer finYearId); //Added by Sooraj 12/3/2018
	
	public List getEmployeeInterestDtls(String gpfAccNo,Integer finYearId,String lStrSevaarthId, String isSevenpc); //swt 19/06/2020
	
	public Double getInterestRate(String intDate);   // Added by Sooraj 12/3/2018
	
	//public List getOpeningBalanceApril(String strGpfAccNo,Integer lIntCurrFinYearId); //Added by Sooraj 5/4/2018
	
	public List getOpeningBalanceApril(String strGpfAccNo,Integer lIntCurrFinYearId,String lStrSevaarthId, String isSevenpc);

	public String getSuperAnDate(String strGpfAccNo);
	
	
	public List getNomineeDetailsForPartiSevaa(String sevaarth);// Added by brijoy 06022019
	
	
	//public String getServiceEnd(String gpfAccNo,Integer finYearId);// Added by brijoy 13022019
	
	public String getServiceEnd(String gpfAccNo,Integer finYearId,String lStrSevaarthId); //swt 19/06/2020

	//public List getEmployeePay(String empName);

	public Integer getbilldata(String sevaarthID, String lStrDdoCode); //ADD BY KAVITA

	public List getbilldetails(String sevaarthID, String gStrLocationCode);//ADD BY KAVITA

	public Integer getbilldataBS(String gStrLocationCode);//ADD BY KAVITA

	public List getbilldetailsBS(String gStrLocationCode);//ADD BY KAVITA

	public List getbillnotLockdetailsBS(String gStrLocationCode);//ADD BY KAVITA

	public String getUserType(Long gLngUserId); //add by kavita 

	public List getInitialSixSevenPay(String sevaarthID, String fY);/// add by kavita seven pc

	public List getEmployeePay(String empName, String lStrSevaarthId);
	
	public List getEmployeePay(String empName);

	public String getEmployeePaymatri(String seven_level); // matri

	//public Object getEmpName(String sevaarthID, String lStrDdoCode);

	public String getEmpName(String lStrSevaarthId,String lStrDdoCode); //swt 24/06/2020

	public String getSpecialCase75(String transactionId);// add by kavita seven pc 75PER

	public int getmonthlyInt(String gpfAccNo, String lStrSevaarthId,
			Integer finYearId);

	public int getmonthlyIntsixpc(String gpfAccNo, String lStrSevaarthId,
			Integer finYearId);

	public String requestSTATUSISr(String strGpfAccNo, String lStrSevaarthId); // add by kavita 
	
	public List getUserPendingNameAdvance(String lStrSevaarthId, String userIdHO); //swt 24/04/2021
	
	int CheckFinalWithdrawnLoanDeoDraft(String lStrSevarthid); //swt
	
	public List getUserPendingNameFW(String lStrSevaarthId, String userIdHO); //swt
	
	public int CheckFinalWithdrawnEntry(String lStrSevarthid); //swt
	
	//public String checkBillEntryForFW(String strSevaarthId); //swt
	
	public String checkBillEntryForFinal(String strSevaarthId); //swt
	
	//public String checkBillEntryForAdv(String strSevaarthId);
	
	public String checkBillEntryForAdvance(String strSevaarthId);
	
	public String checkPreBilldata(String lStrSevaarthId);
	
	Boolean requestDataAlreadyExistsDraft(String strSevaarthId);
	
	Boolean requestDataAlreadyExistsDeoDraft(String lStrSevaarthId); //swt
	
	public List getUserNameDDO(String TransId);
	
	 public List getUserPendingNameAdvanceAndFw(String lStrSevaarthId);
	
	 //public  List getReportType();
	  
	 //public  List getYearsAG();


	//public List getReportType();// add by kavita AG

	//public List getYearsAG();// add by kavita AG

	public String getActiveHO(String gStrLocationCode);

	public String getSpecialCase75Draft(String lStrSevaarthId);
	public String getSpecialCase90Draft(String lStrSevaarthId);

	public Long getCurrDis(String lStrSevaarthId);

	public List getSevenPcArrDetails(String empCode, String empName);

	public Long getpkInitialDataEntry(String empCode);

	public List getInterestCalculationFlag(String lStrSevaarthId);

	public boolean isSecondIsEdit(String lStrSevaarthId, String lStrName);

	public boolean isSecondIserviceEnd(String lStrSevaarthId, String lStrName);

	public Long getCountThirdIntPaidOrNot(String lStrSevaarthId);

/*	public List getEmployeeDetailes(String txtSevaarthId,String gStrLocationCode);

	public List getEmployeeDetailesLNA(String txtSevaarthId,String gStrLocationCode);*/
}
