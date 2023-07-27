/* Copyright TCS 2011, All Rights Reserved.
 * 
 * 
 ******************************************************************************
 ***********************Modification History***********************************
 *  Date   				Initials	     Version		Changes and additions
 ******************************************************************************
 * 	Mar 18, 2011		Kapil Devani								
 *******************************************************************************
 */
package com.tcs.sgv.dcps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tcs.sgv.apps.common.valuebeans.ComboValuesVO;
import com.tcs.sgv.core.dao.GenericDaoHibernateImpl;
import com.tcs.sgv.dcps.valueobject.HstDcpsChanges;
import com.tcs.sgv.dcps.valueobject.HstDcpsNomineeChanges;
import com.tcs.sgv.dcps.valueobject.MstEmp;
import com.tcs.sgv.dcps.valueobject.MstEmpNmn;
import com.tcs.sgv.dcps.valueobject.RltDcpsPayrollEmp;
import com.tcs.sgv.dcps.valueobject.TrnDcpsChanges;

/**
 * Class Description -
 * 
 * 
 * @author Kapil Devani
 * @version 0.1
 * @since JDK 5.0 Mar 18, 2011
 */
public class ChangesFormDAOImpl extends GenericDaoHibernateImpl implements
		ChangesFormDAO {

	private final Log gLogger = LogFactory.getLog(getClass());
	Session ghibSession = null;

	/**
	 * @param type
	 */
	public ChangesFormDAOImpl(Class type, SessionFactory sessionFactory) {

		super(type);
		ghibSession = sessionFactory.getCurrentSession();
		setSessionFactory(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	//changed by siddharth kothari
	public List getAllDcpsEmployees(String lStrDesignationId, String lStrDdoCode, String sevarthId, String employeeName) {

		StringBuilder lSBQuery = new StringBuilder();
		//Session session = getSession();
		List<MstEmp> EmpList = null;		
		
		//lSBQuery.append(" select emp.DCPS_EMP_ID, emp.EMP_NAME, emp.DCPS_ID, emp.SEVARTH_ID FROM MST_DCPS_EMP emp where emp.REG_STATUS IN (1,2) and emp.DDO_CODE = '"+lStrDdoCode+"'");
		
		lSBQuery.append(" select m.dcps_Emp_Id,m.emp_name,m.dcps_Id,m.sevarth_Id ");
		lSBQuery.append(" FROM Mst_dcps_Emp m join frm_form_s1_dtls f on m.SEVARTH_ID=f.SEVARTH_ID ");
		lSBQuery.append(" where m.reg_Status IN (1,2) ");
		lSBQuery.append(" and (m.ddo_Code = '"+lStrDdoCode+"' or f.dep_updated_ddo_cd = '"+lStrDdoCode+"') ");
		
		if(lStrDesignationId!=null && !lStrDesignationId.equals("-1"))
			lSBQuery.append(" and emp.DESIGNATION = '"+lStrDesignationId+"' ");
		
		if(sevarthId!=null && !sevarthId.equals(""))
			lSBQuery.append(" and emp.SEVARTH_ID = '"+sevarthId+"' ");
		
		if(employeeName!=null && !employeeName.equals(""))
			lSBQuery.append(" and emp.EMP_NAME = '"+employeeName+"' ");
		
		lSBQuery.append(" order by emp.EMP_NAME ");
		
		logger.info("Query for get getAllDcpsEmployees is---->>>>"+lSBQuery.toString());
		
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		logger.info("query is---->>>>"+lQuery.toString());
		
		//lQuery.setString("designationId", lStrDesignationId);
		//lQuery.setString("ddoCode", lStrDdoCode);
		//lQuery.setString("sevarthId", sevarthId);
		//lQuery.setString("employeeName", employeeName);
		
		gLogger.info("designationId is in  " + lStrDesignationId);
		gLogger.info("ddoCode is in  " + lStrDdoCode);
		gLogger.info("sevarthId is in  " + sevarthId);
		gLogger.info("employeeName is in  " + employeeName);
		
		gLogger.info("lQuery is in getAllDcpsEmployees " + lQuery);
		
		EmpList = lQuery.list();
		
		gLogger.info("EmpList size is  " + EmpList.size());
		
		return EmpList;
	}
	//changes end by siddharth kothari
	public MstEmp getEmpDetails(Long dcpsEmpId) {

		StringBuilder lSBQuery = new StringBuilder();

		MstEmp EmpList = null;

		lSBQuery.append("FROM MstEmp where dcpsEmpId = :dcpsEmpId)");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);

		gLogger.info("lSBQuery is in  " + lSBQuery);
		gLogger.info("lQuery is in  " + lQuery);
		gLogger.info("dcpsEmpId is   " + dcpsEmpId);
		
		EmpList = (MstEmp) lQuery.uniqueResult();
		//EmpList =  (MstEmp)(Object)lQuery.toString();
		gLogger.info("EmpList is   " + EmpList);
		
		return EmpList;
	}

	public List getCurrentOffices() {

		ArrayList<ComboValuesVO> finalList = new ArrayList<ComboValuesVO>();
		ComboValuesVO cmbVO;
		Object[] obj;

		String query = "select dcpsDdoOfficeIdPk,dcpsDdoOfficeName from DdoOffice";

		StringBuilder sb = new StringBuilder();
		sb.append(query);
		Query selectQuery = ghibSession.createQuery(sb.toString());
		List resultList = selectQuery.list();

		cmbVO = new ComboValuesVO();

		if (resultList != null && resultList.size() > 0) {
			cmbVO = new ComboValuesVO();
			cmbVO.setId("-1");
			cmbVO.setDesc("-- Select --");
			finalList.add(cmbVO);
			Iterator it = resultList.iterator();
			while (it.hasNext()) {
				cmbVO = new ComboValuesVO();
				obj = (Object[]) it.next();
				cmbVO.setId(obj[0].toString());
				cmbVO.setDesc(obj[1].toString());
				finalList.add(cmbVO);
			}
		}
		return finalList;
	}

	public List getNominees(String empId) {

		StringBuilder lSBQuery = new StringBuilder();

		List<MstEmpNmn> NomineesList = null;

		lSBQuery.append(" FROM MstEmpNmn WHERE dcpsEmpId.dcpsEmpId = :empId");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("empId", Long.parseLong(empId));

		NomineesList = lQuery.list();

		return NomineesList;
	}

	public List getChangesDraftsForDesig(String lStrDesignationId,
			String lStrUserType,String lStrDDOCode) {

		StringBuilder lSBQuery = new StringBuilder();

		List ChangesDraftsList = null;

		lSBQuery
				.append(" SELECT nvl(CN.UPDATED_DATE,CN.CREATED_DATE),EM.DCPS_ID,EM.EMP_NAME,EM.DCPS_EMP_ID,CN.TYPE_OF_CHANGES,"
						+ " CN.DCPS_CHANGES_ID,EM.SEVARTH_ID FROM hst_dcps_changes CN JOIN MST_DCPS_EMP EM ON CN.DCPS_EMP_ID=EM.DCPS_EMP_ID "
						+ " WHERE EM.DESIGNATION='" + lStrDesignationId + "' ");

		if (lStrUserType.equals("DDOAsst")) {
			lSBQuery
					.append(" AND (CN.FORM_STATUS IS NULL OR CN.FORM_STATUS = -1)");
		}
		if (lStrUserType.equals("DDO")) {
			lSBQuery.append(" AND (CN.FORM_STATUS = 0)");
		}
		
		if(lStrDDOCode != null)
		{
			if(!"".equals(lStrDDOCode))
			{
				lSBQuery.append(" AND EM.DDO_CODE = '" + lStrDDOCode.trim() + "'");
			}
		}

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		ChangesDraftsList = lQuery.list();

		return ChangesDraftsList;
	}

	public HstDcpsChanges getChangesDetails(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();

		HstDcpsChanges HstDcpsChangesObj = null;

		lSBQuery
				.append("FROM HstDcpsChanges where dcpsChangesId = :dcpsChangesId)");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		HstDcpsChangesObj = (HstDcpsChanges) lQuery.uniqueResult();

		return HstDcpsChangesObj;
	}

	public Long getPersonalChangesIdforChangesId(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Long changesPersonalId = 0L;

		lSBQuery
				.append(" select dcpsPersonalChangesId FROM HstDcpsPersonalChanges WHERE dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		tempList = lQuery.list();
		changesPersonalId = tempList.get(0);
		return changesPersonalId;

	}

	public RltDcpsPayrollEmp getEmpPayrollDetailsForEmpId(Long dcpsEmpId) {

		StringBuilder lSBQuery = new StringBuilder();

		RltDcpsPayrollEmp EmpList = null;

		lSBQuery.append("FROM RltDcpsPayrollEmp where dcpsEmpId = :dcpsEmpId)");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);

		EmpList = (RltDcpsPayrollEmp) lQuery.uniqueResult();

		return EmpList;
	}

	public Long getOfficeChangesIdforChangesId(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Long changesOfficeId = 0L;

		lSBQuery
				.append(" select dcpsOfficeChangesId FROM HstDcpsOfficeChanges WHERE dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		tempList = lQuery.list();
		changesOfficeId = tempList.get(0);
		return changesOfficeId;

	}

	public Long getOtherChangesIdforChangesId(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Long changesOtherId = 0L;

		lSBQuery
				.append(" select dcpsOtherChangesId FROM HstDcpsOtherChanges WHERE dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		tempList = lQuery.list();
		changesOtherId = tempList.get(0);
		return changesOtherId;

	}

	public void deleteNomineesFromHstForGivenEmployee(Long lLongDcpsHstChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery
				.append(" delete from HstDcpsNomineeChanges where dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", lLongDcpsHstChangesId);
		lQuery.executeUpdate();
	}

	public Long getLatestRefIdForNomineeChanges(Long dcpsEmpId,
			Long dcpsChangesId) {

		getSession();
		StringBuilder lSBQuery = new StringBuilder();
		Long maxRefId = null;

		lSBQuery
				.append(" select max(changesNomineeRefId) from HstDcpsNomineeChanges where dcpsEmpId= :dcpsEmpId and dcpsChangesId= :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		maxRefId = (Long) lQuery.list().get(0);

		return maxRefId;

	}

	public List getNomineesFromHst(Long changesNomineeRefId, Long dcpsEmpId) {

		StringBuilder lSBQuery = new StringBuilder();

		List<HstDcpsNomineeChanges> NomineesHstList = null;

		lSBQuery
				.append(" FROM HstDcpsNomineeChanges WHERE changesNomineeRefId = :changesNomineeRefId and dcpsEmpId = :dcpsEmpId");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("changesNomineeRefId", changesNomineeRefId);
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);

		NomineesHstList = lQuery.list();

		return NomineesHstList;
	}

	public List getChangesFromTrnForChangesId(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();

		List<TrnDcpsChanges> TrnDcpsChangesList = null;

		lSBQuery
				.append(" FROM TrnDcpsChanges WHERE dcpsChangesId = :dcpsChangesId");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		TrnDcpsChangesList = lQuery.list();

		return TrnDcpsChangesList;
	}

	public Boolean checkPkInTrnExistsForTheChange(String fieldName,
			String oldValue, Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Boolean flag = true;

		lSBQuery
				.append(" select dcpsChangesIdPk FROM TrnDcpsChanges WHERE fieldName = :fieldName and newValue = :oldValue and dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("fieldName", fieldName);
		lQuery.setParameter("oldValue", oldValue);
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		tempList = lQuery.list();
		if (tempList.size() == 0 && !oldValue.equals("")) {
			flag = false;
		}
		return flag;
	}

	public Long getPksFromTrnForTheChange(String fieldName, String oldValue,
			Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Long trnPkId = 0L;
		Query lQuery;

		if (oldValue.equals("")) {
			lSBQuery
					.append(" select dcpsChangesIdPk FROM TrnDcpsChanges WHERE fieldName = :fieldName and dcpsChangesId = :dcpsChangesId");
			lQuery = ghibSession.createQuery(lSBQuery.toString());
		} else {
			lSBQuery
					.append(" select dcpsChangesIdPk FROM TrnDcpsChanges WHERE fieldName = :fieldName and newValue = :oldValue and dcpsChangesId = :dcpsChangesId");
			lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("oldValue", oldValue);
		}

		lQuery.setParameter("fieldName", fieldName);
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);

		tempList = lQuery.list();
		trnPkId = tempList.get(0);
		return trnPkId;
	}

	public Long getPkFromTrnForTheChangeInPhotoSign(Long dcpsChangesId,
			String fieldName) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Long trnPkId = 0L;

		lSBQuery
				.append(" select dcpsChangesIdPk FROM TrnDcpsChanges WHERE fieldName = :fieldName and dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);
		lQuery.setParameter("fieldName", fieldName);

		tempList = lQuery.list();
		trnPkId = tempList.get(0);
		return trnPkId;
	}

	public void deleteTrnVOForPk(Long TrnIdPk) {

		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery
				.append(" delete from TrnDcpsChanges where dcpsChangesIdPk = :dcpsChangesIdPk");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesIdPk", TrnIdPk);
		lQuery.executeUpdate();
	}

	public void deleteTrnVOForDcpsChangesId(Long dcpsChangesId) {

		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery
				.append(" delete from TrnDcpsChanges where dcpsChangesId = :dcpsChangesId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsChangesId", dcpsChangesId);
		lQuery.executeUpdate();
	}

	public Date getDobForTheEmployee(Long dcpsEmpId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Date> tempList = new ArrayList();
		Date dob = null;

		lSBQuery.append(" select dob FROM MstEmp WHERE dcpsEmpId = :dcpsEmpId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);

		tempList = lQuery.list();
		dob = tempList.get(0);
		return dob;

	}

	public Long getNextRefIdForHstNomineeChanges(Long dcpsEmpId) {

		StringBuilder lSBQuery = new StringBuilder();
		Long count = null;

		lSBQuery
				.append(" select max(changesNomineeRefId) from HstDcpsNomineeChanges where dcpsEmpId= :dcpsEmpId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("dcpsEmpId", dcpsEmpId);

		count = (Long) lQuery.list().get(0);

		if (count == null) {
			count = 0l;
		}
		count = count + 1;
		return count;

	}

	public List getPhotoSignNewValue(Long lLngChangesId) {

		List lLstNewValue = new ArrayList<Long>();
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery
				.append(" select newValue from TrnDcpsChanges where dcpsChangesId = :changesId");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("changesId", lLngChangesId);
		lLstNewValue = lQuery.list();
		return lLstNewValue;

	}

	public String getGroupIdForCadreId(Long cadreId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<String> tempList = new ArrayList<String>();
		String groupId = null;

		lSBQuery
				.append(" Select groupId FROM DcpsCadreMst WHERE cadreId = :cadreId");

		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("cadreId", cadreId);

		tempList = lQuery.list();
		if (tempList != null && tempList.size() != 0) {
			groupId = tempList.get(0);
		}
		return groupId;
	}
	
	 public void updateEmpNameInOrgEmpMst(MstEmp objMstEmp)
	  {
	    StringBuilder lSBQuery = new StringBuilder();
	    String[] name = new String[3];
	    if (objMstEmp.getName() != null)
	    {
	      name = objMstEmp.getName().split(" ");
	      this.gLogger.info("name-----" + name.length);
	    }
	    lSBQuery.append("update ORG_EMP_MST set ");
	    
	      if ((name.length > 0) && (name[0] != null))
	        lSBQuery.append("EMP_FNAME = '" + name[0] + "', ");
	      if ((name.length > 1) && (name[1] != null))
	      {
	        if ((name.length > 2) && (name[2] != null))
	          lSBQuery.append("EMP_MNAME = '" + name[1] + "', ");
	        else
	          lSBQuery.append("EMP_LNAME = '" + name[1] + "',EMP_MNAME = '' ");
	      }

	      if ((name.length > 2) && (name[2] != null))
	        lSBQuery.append("EMP_LNAME = '" + name[2] + "' ");
	      else if (name.length == 1)
	        lSBQuery.append("EMP_LNAME = ' ' ");
	    
	    lSBQuery.append("where EMP_ID = " + objMstEmp.getOrgEmpMstId());
	    this.logger.info("update name query------" + lSBQuery.toString());
	    Query lQuery = this.ghibSession.createSQLQuery(lSBQuery.toString());
	    lQuery.executeUpdate();
	  }
	 //added by vamsi
	public String getGradeForGivenLevel(String level)
	{
		String grade = "";
		List<String> tempList = new ArrayList<String>();
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT ID FROM RLT_PAYBAND_GP_7PC WHERE LEVEL ='"+level+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getGradeForGivenLevel "+sqlQuery);
        tempList = sqlQuery.list();
		if (tempList != null && tempList.size() != 0) {
			grade = tempList.get(0);
		}
		return grade;
		
	}
	//added by pooja
	public String getGradeForGivenStateLevel(String level)
	{
		String grade = "";
		List<String> tempList = new ArrayList<String>();
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT ID FROM RLT_PAYBAND_GP_state_7pc WHERE LEVEL ='"+level+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getGradeForGivenStateLevel "+sqlQuery);
        tempList = sqlQuery.list();
		if (tempList != null && tempList.size() != 0) {
			grade = tempList.get(0);
		}
		return grade;
		
	}
	
	public List getBasicAsPerGrade(String grade)
	{
		List basicPay = null;
		List lLstReturnList=null;
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT "+grade+",cell FROM MST_MATRIX_7THPAY WHERE "+grade+">0 order by GRADE_1");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getBasicAsPerGrade "+sqlQuery);
        basicPay = sqlQuery.list();
        
        ComboValuesVO lObjComboValuesVO = null;
		if (basicPay != null && basicPay.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			lLstReturnList = new ArrayList<Object>();
			for (int liCtr = 0; liCtr < basicPay.size(); liCtr++) {
				obj = (Object[]) basicPay.get(liCtr);
				lObjComboValuesVO = new ComboValuesVO();
				lObjComboValuesVO.setId(obj[1].toString());
				lObjComboValuesVO.setDesc(obj[0].toString());
				lLstReturnList.add(lObjComboValuesVO);
			}
		} else {
			lLstReturnList = new ArrayList<Object>();
			lObjComboValuesVO = new ComboValuesVO();
			lObjComboValuesVO.setId("-----Select-----");
			lObjComboValuesVO.setDesc("-1");
			lLstReturnList.add(lObjComboValuesVO);
		}
		return lLstReturnList;
	}
	
	public List getStateBasicAsPerGrade(String grade)
	{
		List basicPay = null;
		List lLstReturnList=null;
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT "+grade+",cell FROM MST_STATE_MATRIX_7THPAY WHERE "+grade+">0 order by S_1");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getBasicAsPerGrade "+sqlQuery);
        basicPay = sqlQuery.list();
        
        ComboValuesVO lObjComboValuesVO = null;
		if (basicPay != null && basicPay.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			lLstReturnList = new ArrayList<Object>();
			for (int liCtr = 0; liCtr < basicPay.size(); liCtr++) {
				obj = (Object[]) basicPay.get(liCtr);
				lObjComboValuesVO = new ComboValuesVO();
				lObjComboValuesVO.setId(obj[1].toString());
				lObjComboValuesVO.setDesc(obj[0].toString());
				lLstReturnList.add(lObjComboValuesVO);
			}
		} else {
			lLstReturnList = new ArrayList<Object>();
			lObjComboValuesVO = new ComboValuesVO();
			lObjComboValuesVO.setId("-----Select-----");
			lObjComboValuesVO.setDesc("-1");
			lLstReturnList.add(lObjComboValuesVO);
		}
		return lLstReturnList;
	}
	
	public String getPayInPayBandAndGradePayForStateLevel(String level)
	{
		List tempList = null;
		String x = "";
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT PAY_IN_PAYBAND ||'~'|| GRADE_PAY FROM RLT_PAYBAND_GP_STATE_7PC WHERE LEVEL ='"+level+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getPayInPayBandAndGradePayForLevel "+sqlQuery);
        tempList = sqlQuery.list();
        if(tempList.size()>0 && tempList!=null){
    		x=(tempList.get(0).toString());
    		}
		return x;
		
	}
	//added by pooja

	public String getPayInPayBandAndGradePayForLevel(String level)
	{
		List tempList = null;
		String x = "";
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT PAY_IN_PAYBAND ||'~'|| GRADE_PAY FROM RLT_PAYBAND_GP_7PC WHERE LEVEL ='"+level+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getPayInPayBandAndGradePayForLevel "+sqlQuery);
        tempList = sqlQuery.list();
        if(tempList.size()>0 && tempList!=null){
    		x=(tempList.get(0).toString());
    		}
		return x;
		
	}
	
	public String getScaleForLevel(String scaleStartAmt,String scaleEndAmt,String lStrGradePay)
	{
		List tempList = null;
		String x = "";
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT SCALE_ID FROM HR_EIS_SCALE_MST WHERE SCALE_START_AMT = '"+scaleStartAmt+"' AND SCALE_END_AMT = '"+scaleEndAmt+"' AND SCALE_GRADE_PAY = '"+lStrGradePay+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getScaleForLevel "+sqlQuery);
        tempList = sqlQuery.list();
        if(tempList.size()>0 && tempList!=null){
    		x=(tempList.get(0).toString());
    		}
		return x;
		
	}
	
	public String getPayInPayBandAndGradePayForHTEDMELevel(String level,String tableName)
	{
		List tempList = null;
		String x = "";
		Session hibSession = getSession();
		StringBuilder SBQuery = new StringBuilder();
        SBQuery.append(" SELECT PAY_IN_PAYBAND ||'~'|| GRADE_PAY FROM "+tableName+" WHERE LEVEL ='"+level+"'");
        Query sqlQuery = hibSession.createSQLQuery(SBQuery.toString());
        gLogger.info("query for getPayInPayBandAndGradePayForLevel "+sqlQuery);
        tempList = sqlQuery.list();
        if(tempList.size()>0 && tempList!=null){
    		x=(tempList.get(0).toString());
    		}
		return x;
		
	}
    //ended by vamsi
	//added by akanksha
		public List getdetails(String empSevarthId) {
			Session hibSession =getSession();
			StringBuffer sb = new StringBuffer();	
			sb.append(" SELECT * FROM MST_EMP_GPF_ACC ");
			sb.append(" where SEVAARTH_ID = '"+empSevarthId+"'");	
			Query sql1query=hibSession.createSQLQuery(sb.toString());
			logger.error("sql query for getGPFDuplEmpList is"+sql1query.toString());

			return sql1query.list();
		}

		public void updateMstEmpGpfEmpAcc(String empSevarthId, String getPfAcNo,
				String getPfSeriesDesc) {
			Session hibSession =getSession();
			String newGpfAcctNo = null;
			newGpfAcctNo = getPfSeriesDesc+"/"+getPfAcNo;
			StringBuffer str2 = new StringBuffer();
			str2.append("update MST_EMP_GPF_ACC set GPF_ACC_NO ='"+newGpfAcctNo+"',GPF_UPDATE_FLAG = 'Y' where SEVAARTH_ID = '"+empSevarthId+"'");
			logger.error("update gpfaccdetails------"+str2.toString());
			Query query3 = hibSession.createSQLQuery(str2.toString());
			query3.executeUpdate();
			// TODO Auto-generated method stub
			
		}
	}
