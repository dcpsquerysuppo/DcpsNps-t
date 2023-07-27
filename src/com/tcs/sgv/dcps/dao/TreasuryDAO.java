package com.tcs.sgv.dcps.dao;

import com.tcs.sgv.core.dao.GenericDao;
import com.tcs.sgv.dcps.valueobject.MstDcpsContriVoucherDtls;
import com.tcs.sgv.dcps.valueobject.MstDummyOffice;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface TreasuryDAO extends GenericDao
{
  public abstract List getYears()
    throws Exception;

  public abstract List getAllFormsForDDO(String paramString1, String paramString2)
    throws Exception;

  public abstract List getDummyOffices(String paramString1, String paramString2)
    throws Exception;

  public abstract List getEmpDeptn(String paramString1, String paramString2, String paramString3, Long paramLong, Boolean paramBoolean)
    throws Exception;

  public abstract Long getHstEmpDeputationPkVal(Long paramLong)
    throws Exception;

  public abstract List getYearsForSixPCYearly()
    throws Exception;

  public abstract List getEmpSearchDeptn(String paramString1, String paramString2)
    throws Exception;

  public abstract List getEmpListForSixPCArrearsYearlyTO(String paramString1, Long paramLong, String paramString2)
    throws Exception;

  public abstract List getAllDDOListForPhyFormRcvd(String paramString1, String paramString2)
    throws Exception;

  public abstract List getDummyOfficesList(String paramString)
    throws Exception;

  public abstract MstDummyOffice getDummyOfficeInfo(String paramString1, String paramString2)
    throws Exception;

  public abstract String getSchemeCodeForBillGroupId(Long paramLong);
////$t IRRI
  public abstract List getEmployeesFromDummyOffice(String paramString1, Long paramLong1, Long paramLong2, String paramString2, String paramString3, String paramString4,String paramString5)
    throws Exception;

  /*public abstract List getEmployeesFromDummyOffice(String paramString1, Long paramLong1, Long paramLong2, String paramString2, String paramString3, String paramString4)
		    throws Exception;*/
  
  public abstract List getEmployeesFromDummyOfficeNPS(String paramString1, Long paramLong1, Long paramLong2, String paramString2, String paramString3, String paramString4,String Irrigation)
    throws Exception;
  
  /*public abstract List getEmployeesFromDummyOfficeNPS(String paramString1, Long paramLong1, Long paramLong2, String paramString2, String paramString3, String paramString4)
		    throws Exception;*/
  //$t 2019
  public abstract List getEmployeesFromDummyOfficeNPSFour(String paramString1, Long paramLong1, Long paramLong2, String paramString2, String paramString3, String paramString4)
		    throws Exception;

  public abstract Long getCountofChallanOfficesForGivenTreasury(String paramString);

  public abstract List getTowns(Long paramLong)
    throws Exception;

  public abstract MstDcpsContriVoucherDtls getContriVoucherVOForInputDtlsForChallan(Long paramLong1, Long paramLong2, String paramString1, Long paramLong3, String paramString2, Long paramLong4, Date paramDate)
    throws Exception;

  public abstract Double getTotalVoucherAmountForGivenChallan(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate, String paramString3);

  public abstract void updateDummyOfficeDetails(MstDummyOffice paramMstDummyOffice, Map paramMap)
    throws Exception;

  public abstract List getFinyears();

  public abstract List getFinyearsForNPS();
  //$t 2019
  public abstract List getFinyearsForNPS1();

  public abstract List getDeptnInfo(String paramString);

  public abstract List getTOPInfo(String paramString1, String paramString2);

  public abstract List getDelRegInfo(String paramString1, String paramString2);

  public abstract List getEmpDeptnList(String paramString1, String paramString2, String paramString3, Long paramLong, Boolean paramBoolean, String paramString4)
    throws Exception;

  public abstract String getEmpDDOCode(String paramString1, String paramString2, Long paramLong, Boolean paramBoolean)
    throws Exception;

  public abstract Boolean chkIfDdoOfHodDefined(String paramString1, String paramString2)
    throws Exception;

  public abstract String getEmpDept(String paramString1, String paramString2)
    throws Exception;

  public abstract List getEmpDeptnListAll(String paramString1, Long paramLong, Boolean paramBoolean, String paramString2)
    throws Exception;

  public abstract String getEmpCmpCode(Long paramLong)
    throws Exception;

  public abstract List getFieldDept(long paramLong);
  
  public abstract String getOfficeName (String lStrSevarthId,String lStrEmpName) throws Exception;

public abstract List getDummyOfficesDdoAst(String gStrLocationCode,
		String lStrWithOrWithoutEmplrContri, String gStrPostId);

public abstract List getDummyOfficesDdo(String gStrLocationCode,
		String lStrWithOrWithoutEmplrContri, String gStrPostId);

public abstract List getEmployeesFromDummyOfficeAstNPS(
		String lStrDummyOfficeId, Long monthId, Long yearId,
		String gStrLocationCode, String lStrSubmittedOrNot,
		String lStrWithOrWithoutEmplrContri, String gStrPostId);

public abstract List getEmployeesFromDummyOfficeDdoNPS(
		String lStrDummyOfficeId, Long monthId, Long yearId,
		String gStrLocationCode, String lStrSubmittedOrNot,
		String lStrWithOrWithoutEmplrContri, String gStrPostId);

public abstract List getEmployeesFromDummyOfficeAst(String lStrDummyOfficeId,
		Long monthId, Long yearId, String gStrLocationCode,
		String lStrSubmittedOrNot, String lStrWithOrWithoutEmplrContri,
		String gStrPostId);

public abstract List getEmployeesFromDummyOfficeDdo(String lStrDummyOfficeId,
		Long monthId, Long yearId, String gStrLocationCode,
		String lStrSubmittedOrNot, String lStrWithOrWithoutEmplrContri,
		String gStrPostId);

public abstract void updateFlag(Long long1, Long long2,
		Long long3, Date date, Date date2);

/*///////$t 20-04-2020 IRRI
public abstract void updateContriFlagDDO(String string, String yearId,
		String monthId, String string2, String string3) throws Exception;
/////$t 20-04-2020 IRRI
public abstract void updateContriFlagTO(String string, String monthId,
		String yearId, String string2, String string3,String string4,String string5) throws Exception;
/////$t 20-04-2020 IRRI
public abstract void rejectContriFlagDDO(String string, String monthId,
		String yearId, String string2, String string3) throws Exception;
/////$t 20-04-2020 IRRI
public abstract void rejectContriFlagTO(String string, String monthId,
		String yearId, String string2, String string3) throws Exception;
////$t $t irri 13-1-2021
public abstract void updateContriFlagDdoAsst(String string, String monthId,
		String yearId, String string2, String string3) throws Exception;*/

}