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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tcs.sgv.apps.common.valuebeans.ComboValuesVO;
import com.tcs.sgv.core.dao.GenericDaoHibernateImpl;
import com.tcs.sgv.core.service.ServiceLocator;
import com.tcs.sgv.eis.valueobject.HrEisScaleMst;

/**
 * Class Description -
 * 
 * 
 * @author Meeta Thacker
 * @version 0.1
 * @since JDK 5.0 Jun 29, 2011
 */
public class GPFRequestProcessDAOImpl extends GenericDaoHibernateImpl implements GPFRequestProcessDAO {
	Session ghibSession = null;

	public GPFRequestProcessDAOImpl(Class type, SessionFactory sessionFactory) {
		super(type);
		ghibSession = sessionFactory.getCurrentSession();
		setSessionFactory(sessionFactory);
	}

	/*
	 * Method to get employee basic information (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getEmployeeDetail(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public List getEmployeeDetail(String lStrSevaarthId, String empName, String criteria, String lStrDdoCode,
			String lStrUser) throws Exception {
		logger.info("getEmployeeDetail::::::::::::::::::");
		logger.info("empName::::::::::::::::::"+empName);
		logger.info("lStrSevaarthId::::::::::::::::::"+lStrSevaarthId);
		List gpfEmpList = new ArrayList();
		try {

			StringBuilder lSBQuery = new StringBuilder();

			Query lQuery = null;

			/*lSBQuery.append(" select ME.emp_name,MEG.current_Balance,ME.gender,ME.basic_Pay,OM.dsgn_Name,MEG.monthly_Subscription,");
			lSBQuery.append(" MEG.gpf_Acc_No,ME.DOJ,ME.ADDRESS_BUILDING,");
			lSBQuery.append(" ME.ADDRESS_STREET,ME.LANDMARK,ME.district,ME.state,ME.PPINCODE,ME.cntct_No,ME.cell_No,");
			lSBQuery.append(" OEM.emp_Srvc_Exp,CLM.lookup_Name,ME.PAYSCALE,CAST(OEM.emp_Id AS VARCHAR),ME.BASIC_PAY,ME.dob,MEG.sevaarth_Id,ME.PAY_IN_PAY_BAND ");
			lSBQuery.append("  ,ME.grade_Pay,TO_CHAR(ME.EMP_SERVEND_DT,'dd/MM/yyyy') "); //24 5
			lSBQuery.append(" FROM Mst_dcps_Emp ME,MST_EMP_GPF_ACC MEG,ORG_EMP_MST OEM,ORG_DESIGNATION_MST OM,CMN_LOOKUP_MST CLM ");
			lSBQuery.append(" WHERE CLM.lookup_Id = ME.pay_Commission and ME.dcps_Emp_Id = MEG.mst_Gpf_Emp_Id and ME.dcps_Or_Gpf='N' and OEM.emp_Id = ME.org_Emp_Mst_Id ");
			lSBQuery.append(" and ME.designation = OM.dsgn_Id AND ME.emp_group ='D'  ");

			 */


			/* select ME.emp_name,MEG.current_Balance,ME.gender,ME.basic_Pay,OM.dsgn_Name,MEG.monthly_Subscription,
					 MEG.gpf_Acc_No,TO_CHAR(ME.DOJ,'dd/MM/yyyy'),ME.ADDRESS_BUILDING,
					 ME.ADDRESS_STREET,ME.LANDMARK,ME.district,ME.state,ME.PPINCODE,ME.cntct_No,ME.cell_No,
					 TO_CHAR(OEM.emp_Srvc_Exp,'dd/MM/yyyy'),CLM.lookup_Name,ME.PAYSCALE,CAST(OEM.emp_Id AS VARCHAR),ME.BASIC_PAY,ME.dob,MEG.sevaarth_Id,ME.PAY_IN_PAY_BAND
			,ME.grade_Pay,TO_CHAR(ME.EMP_SERVEND_DT,'dd/MM/yyyy'),ME.DOJ, TO_CHAR(ME.DOB,'dd/MM/YYYY'),ME.SEVEN_PC_BASIC,LVL.ID
					 FROM Mst_dcps_Emp ME,MST_EMP_GPF_ACC MEG,ORG_EMP_MST OEM,ORG_DESIGNATION_MST OM,CMN_LOOKUP_MST CLM,RLT_PAYBAND_GP_STATE_7PC LVL 
					 WHERE CLM.lookup_Id = ME.pay_Commission and ME.dcps_Emp_Id = MEG.mst_Gpf_Emp_Id and ME.dcps_Or_Gpf='N' and OEM.emp_Id = ME.org_Emp_Mst_Id
					 and ME.designation = OM.dsgn_Id AND ME.SEVEN_PC_LEVEL=LVL.LEVEL_ID
					 
					 lSBQuery.append(" select ME.emp_name,MEG.current_Balance,ME.gender,ME.basic_Pay,OM.dsgn_Name,MEG.monthly_Subscription,");
			lSBQuery.append(" MEG.gpf_Acc_No,TO_CHAR(ME.DOJ,'dd/MM/yyyy'),ME.ADDRESS_BUILDING,");
			lSBQuery.append(" ME.ADDRESS_STREET,ME.LANDMARK,ME.district,ME.state,ME.PPINCODE,ME.cntct_No,ME.cell_No,");
			lSBQuery.append(" TO_CHAR(OEM.emp_Srvc_Exp,'dd/MM/yyyy'),CLM.lookup_Name,ME.PAYSCALE,CAST(OEM.emp_Id AS VARCHAR),ME.BASIC_PAY,ME.dob,MEG.sevaarth_Id,ME.PAY_IN_PAY_BAND ");
			lSBQuery.append("  ,ME.grade_Pay,TO_CHAR(ME.EMP_SERVEND_DT,'dd/MM/yyyy'),ME.DOJ, TO_CHAR(ME.DOB,'dd/MM/YYYY') "); //24 5 //01-09-2018 brijoy
			lSBQuery.append(" FROM Mst_dcps_Emp ME,MST_EMP_GPF_ACC MEG,ORG_EMP_MST OEM,ORG_DESIGNATION_MST OM,CMN_LOOKUP_MST CLM ");
			lSBQuery.append(" WHERE CLM.lookup_Id = ME.pay_Commission and ME.dcps_Emp_Id = MEG.mst_Gpf_Emp_Id and ME.dcps_Or_Gpf='N' and OEM.emp_Id = ME.org_Emp_Mst_Id ");
			lSBQuery.append(" and ME.designation = OM.dsgn_Id  ");
*/
	  lSBQuery.append(" select ME.emp_name,MEG.current_Balance,ME.gender,ME.basic_Pay,OM.dsgn_Name,MEG.monthly_Subscription,");
				lSBQuery.append(" MEG.gpf_Acc_No,TO_CHAR(ME.DOJ,'dd/MM/yyyy'),ME.ADDRESS_BUILDING,");
				lSBQuery.append(" ME.ADDRESS_STREET,ME.LANDMARK,ME.district,ME.state,ME.PPINCODE,ME.cntct_No,ME.cell_No, ");
				lSBQuery.append(" TO_CHAR(OEM.emp_Srvc_Exp,'dd/MM/yyyy'),CLM.lookup_Name,ME.PAYSCALE,CAST(OEM.emp_Id AS VARCHAR),ME.BASIC_PAY,ME.dob,MEG.sevaarth_Id,ME.PAY_IN_PAY_BAND ");
				lSBQuery.append("  ,ME.grade_Pay,TO_CHAR(ME.EMP_SERVEND_DT,'dd/MM/yyyy'),ME.DOJ, TO_CHAR(ME.DOB,'dd/MM/YYYY'),ME.SEVEN_PC_BASIC ,OEM.emp_Srvc_Exp "); //24 5 //01-09-2018 brijoy
				lSBQuery.append(" FROM Mst_dcps_Emp ME,MST_EMP_GPF_ACC MEG,ORG_EMP_MST OEM,ORG_DESIGNATION_MST OM,CMN_LOOKUP_MST CLM ");
				lSBQuery.append(" WHERE CLM.lookup_Id = ME.pay_Commission and ME.dcps_Emp_Id = MEG.mst_Gpf_Emp_Id and ME.dcps_Or_Gpf='N' and OEM.emp_Id = ME.org_Emp_Mst_Id ");
				lSBQuery.append(" and ME.designation = OM.dsgn_Id  ");

			if (lStrUser.equals("DEO")) {
				lSBQuery.append(" AND MEG.ddo_Code='"+lStrDdoCode+"' ");
			}

			if (criteria.equals("1")) {

				lSBQuery.append(" and MEG.sevaarth_Id = '"+lStrSevaarthId+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);

			} else if (criteria.equals("2")) {

				lSBQuery.append(" and ME.emp_name ='"+empName+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("name", empName);
			} else {
				lSBQuery.append(" and MEG.sevaarth_Id = '"+lStrSevaarthId+"' and ME.EMP_name = '"+empName+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				//lQuery.setParameter("name", empName);
			}
			/*if (lStrUser.equals("DEO")) {
				lQuery.setParameter("ddoCode", lStrDdoCode);
			}*/

			logger.info("lSBQuery::::::getEmployeeDetail::::::"+lSBQuery.toString());
			gpfEmpList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getEmployeeDetail of GPFRequestProcessDAOImpl  : ", e);			
			throw e;
		}
		return gpfEmpList;
	}

	public List getEmployeeDetailDeputation(String lStrSevaarthId, String empName, String criteria,String lStrUser) throws Exception {
		List gpfEmpList = new ArrayList();
		try {

			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery = null;
			lSBQuery
			.append("select ME.name,MEG.currentBalance,ME.gender,ME.basicPay,OM.dsgnName,MEG.monthlySubscription,");
			lSBQuery.append("MEG.gpfAccNo,ME.doj,ME.building_address,");
			lSBQuery.append("ME.building_street,ME.landmark,ME.district,ME.state,ME.pincode,ME.cntctNo,ME.cellNo,");
			lSBQuery.append("OEM.empSrvcExp,CLM.lookupName,ME.payScale,OEM.empId,ME.basicPay,ME.dob,MEG.sevaarthId,ME.payInPayBand");
			lSBQuery.append(" ,ME.gradePay ");
			lSBQuery.append(" FROM MstEmp ME,MstEmpGpfAcc MEG,OrgEmpMst OEM,OrgDesignationMst OM,CmnLookupMst CLM");
			lSBQuery
			.append(" WHERE CLM.lookupId = ME.payCommission and ME.dcpsEmpId = MEG.mstGpfEmpId and ME.dcpsOrGpf='N' and OEM.empId = ME.orgEmpMstId");
			lSBQuery.append(" and ME.designation = OM.dsgnId AND ME.group ='D' ");
			/*if (lStrUser.equals("DEO")) {
				lSBQuery.append(" AND MEG.ddoCode=:ddoCode");
			}*/

			if (criteria.equals("1")) {

				lSBQuery.append(" and MEG.sevaarthId = :sevaarthId");

				lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("sevaarthId", lStrSevaarthId);

			} else if (criteria.equals("2")) {

				lSBQuery.append(" and ME.name = :name");

				lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("name", empName);
			} else {
				lSBQuery.append(" and MEG.sevaarthId = :sevaarthId and ME.name = :name");

				lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("sevaarthId", lStrSevaarthId);
				lQuery.setParameter("name", empName);
			}
			/*if (lStrUser.equals("DEO")) {
				lQuery.setParameter("ddoCode", lStrDdoCode);
			}*/
			gpfEmpList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getEmployeeDetail of GPFRequestProcessDAOImpl  : ", e);			
			throw e;
		}
		return gpfEmpList;
	}

	/*public String getRegularSubscription(String sevarthId, String ddoCode){
		String substr = "";
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		lSBQuery.append(" SELECT ded.EMP_DEDUC_AMOUNT FROM HR_PAY_DEDUCTION_DTLS ded inner join HR_EIS_EMP_MST eis ");
		lSBQuery.append(" on ded.EMP_ID = eis.EMP_ID inner join mst_dcps_emp emp on eis.EMP_MPG_ID = emp.ORG_EMP_MST_ID ");
		lSBQuery.append( " WHERE ded.EMP_DEDUC_ID = 36 and emp.SEVARTH_ID = '"+sevarthId+"'");

		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		if(lQuery != null){
			if(lQuery.uniqueResult() != null)
			substr = lQuery.uniqueResult().toString();
		}
		return substr;
	}*/

	//gokarna for regular subcription

	// paybill If employees January 2015's pay bill is generated with regular subscription and recovery of advance deducted in it. Then this deduction should be shown and added in his GPF account in February 2015.
	//i.e whatever deduction of regular subscription and recovery of advance takes place from his pay bill in current month should be added to his GPF account in next month


	/*public String getRegularSubscription(String SevaarthId,Long lLngCurrMonth, Long lLngCurrYr){


		logger.error("inside getRegularSubscription dao method");
		logger.error("SevaarthId"+SevaarthId);
		logger.error("lLngCurrMonth"+lLngCurrMonth);
		logger.error("lLngCurrYr"+lLngCurrYr);

		String lStrSevaarthId = SevaarthId;
		List regular = null;


		String lStrRegSub = "0";
		String lStrRegsub = "";
		String lStrRegSubJantoMarch = "";
		String lStrRegSubAprtoDec = "";
		Long lLngTotal = 0l;
		Long lLngTotalJantoMarch = 0l;
		Long lLngTotalAprtoDec = 0l;
		List Data=null;
		Long empid=0l;
		StringBuilder lSBQuery1 = new StringBuilder();
		Query lQuery1 = null;
		lSBQuery1.append(" SELECT eis.emp_id FROM mst_dcps_emp dcps , hr_eis_emp_mst eis ");
		lSBQuery1.append("  where eis.EMP_MPG_ID = dcps.ORG_EMP_MST_ID  and dcps.SEVARTH_ID='"+lStrSevaarthId+"' ");
		lQuery1 = ghibSession.createSQLQuery(lSBQuery1.toString());
		Data = lQuery1.list();
		logger.info("lQuery1::***********::::::::::"+lQuery1.toString());
		if (Data != null && Data.size() > 0) {
			empid = Long.parseLong(Data.get(0).toString());
			logger.info("empid::***********::::::::::"+empid);

		}



		logger.error("getRegularSubscription lLngCurrMonth::::::::"+lLngCurrMonth);
		if(lLngCurrMonth==1)
		{
			logger.error("in Iff");
			lLngCurrMonth=12l;
			logger.error("in Iff"+lLngCurrMonth);
		}
		else
		{
			logger.error("in else");
			lLngCurrMonth=lLngCurrMonth-1;
			logger.error("in else"+lLngCurrMonth);
		}

		//if(lLngCurrMonth > 2){ change on 24/12/2015
		if(lLngCurrMonth > 3){
			logger.error("curr month > 3 is "+lLngCurrMonth);


			for(int x = 3; x <= lLngCurrMonth; x++){
				//for(int x = 4; x <= lLngCurrMonth; x++){
				logger.error("x"+x);


				StringBuilder lSBQuery = new StringBuilder();


				Query lQuery = null;



				//commented for correct regular subscription
				//lSBQuery.append(" SELECT sum(cast(deduct.EMP_DEDUC_AMOUNT as bigint)) ");
				lSBQuery.append(" SELECT nvl(sum(paybill.GPF_GRP_D),0) ");
				lSBQuery.append(" FROM mst_dcps_emp dcps inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = dcps.ORG_EMP_MST_ID "); 
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID  ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 ");
				//lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID=eis.EMP_ID and deduct.EMP_DEDUC_ID=36 ");
				lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID='"+empid+"' and deduct.EMP_DEDUC_ID=36 ");

				lSBQuery.append(" and paybill.PAYBILL_YEAR=head.PAYBILL_YEAR and head.PAYBILL_MONTH=paybill.PAYBILL_MONTH ");
				lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' ");
				lSBQuery.append(" and head.PAYBILL_YEAR ="+lLngCurrYr+" AND head.PAYBILL_MONTH ="+(x+1)+" ");



				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				//lQuery.setParameter("lLngCurrYr", lLngCurrYr);
				//lQuery.setParameter("x", x);




				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){

					lStrRegsub = lQuery.uniqueResult().toString();
					logger.error("lStrRegsub::::::::::"+lStrRegsub);

					if(lStrRegsub != null && lStrRegsub != ""){

						lLngTotal = lLngTotal + Long.valueOf(lStrRegsub);
						logger.error("lLngTotal::::::::::"+lLngTotal);
						logger.error("lSBQuery***************"+lSBQuery.toString());
					}
				}

				logger.error("lStrRegsub"+lStrRegsub);
				logger.error("lLngTotal"+lLngTotal);
			}



			lStrRegSub = lLngTotal.toString();
			logger.error("lStrRegSub ::: final:::"+lStrRegSub);
		}

		//if(lLngCurrMonth <= 2){
		if(lLngCurrMonth <= 3){
			logger.error("curr month <= 3 is "+lLngCurrMonth);

			for(int x = 1; x <= lLngCurrMonth; x++){
				logger.error("x"+x+"lStrSevaarthId"+lStrSevaarthId+"lLngCurrYr"+lLngCurrYr);

				StringBuilder lSBQuery = new StringBuilder();
				Query lQuery = null;

				lSBQuery.append(" SELECT nvl(sum(paybill.GPF_GRP_D),0) ");
				lSBQuery.append(" FROM mst_dcps_emp dcps inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = dcps.ORG_EMP_MST_ID "); 
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID  ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 ");
				//lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID=eis.EMP_ID and deduct.EMP_DEDUC_ID=36 ");
				lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID='"+empid+"' and deduct.EMP_DEDUC_ID=36 ");

				lSBQuery.append(" and paybill.PAYBILL_YEAR=head.PAYBILL_YEAR and head.PAYBILL_MONTH=paybill.PAYBILL_MONTH ");
				lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' ");
				lSBQuery.append(" and head.PAYBILL_YEAR ="+lLngCurrYr+" AND head.PAYBILL_MONTH ="+(x+1)+" ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				//lQuery.setParameter("lLngCurrYr", lLngCurrYr);
				//lQuery.setParameter("x", x);



				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){
					lStrRegSubJantoMarch = lQuery.uniqueResult().toString();

					if(lStrRegSubJantoMarch != null && lStrRegSubJantoMarch != ""){
						lLngTotalJantoMarch = lLngTotalJantoMarch + Long.valueOf(lStrRegSubJantoMarch);
					}
					logger.error("lStrRegSubJantoMarch"+lStrRegSubJantoMarch);
					logger.error("lSBQuery***************"+lSBQuery.toString());
				}
			}

			logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch);


			//for(int x = 3; x <= 12; x++){
			for(int x = 4; x <= 12; x++){
				logger.error("x"+x);

				StringBuilder lSBQuery = new StringBuilder();
				Query lQuery = null;

				lSBQuery.append(" SELECT nvl(sum(paybill.GPF_GRP_D),0) ");
				lSBQuery.append(" FROM mst_dcps_emp dcps inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = dcps.ORG_EMP_MST_ID "); 
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID  ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 ");
				//lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID=eis.EMP_ID and deduct.EMP_DEDUC_ID=36 ");
				lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID='"+empid+"' and deduct.EMP_DEDUC_ID=36 ");

				lSBQuery.append(" and paybill.PAYBILL_YEAR=head.PAYBILL_YEAR and head.PAYBILL_MONTH=paybill.PAYBILL_MONTH ");
				lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' ");
				lSBQuery.append(" and head.PAYBILL_YEAR =:lLngCurrYr AND head.PAYBILL_MONTH ="+(x+1)+" ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				lQuery.setParameter("lLngCurrYr", Long.parseLong(String.valueOf((Integer.valueOf(String.valueOf(lLngCurrYr)))-1)));
				//lQuery.setParameter("x", x);

				logger.error("Query is::::::"+lSBQuery.toString());

				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){
					lStrRegSubAprtoDec = lQuery.uniqueResult().toString();

					if(lStrRegSubAprtoDec != null && lStrRegSubAprtoDec != ""){
						lLngTotalAprtoDec = lLngTotalAprtoDec + Long.valueOf(lStrRegSubAprtoDec);
					}
					logger.error("lStrRegSubAprtoDec"+lStrRegSubAprtoDec);
				}
			}

			logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec);



			lStrRegSub = String.valueOf(lLngTotalAprtoDec + lLngTotalJantoMarch);

		}

		logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec);
		logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch);
		logger.error("lStrRegSub"+lStrRegSub);
		logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec.toString());
		logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch.toString());


		logger.error("lStrRegSub final value"+lStrRegSub);
		return lStrRegSub;

	}*/

	/* Above method is commented by Akshay on 04/07/2017 for correction in GPF regular subscription
	 * and below method is get value of subscription from MST_GPF_INTEREST_DTLS */

	public String getRegularSubscription(String SevaarthId,Long lLngCurrMonth, Long lLngCurrYr){
		logger.error("inside getRegularSubscription dao method");
		logger.error("SevaarthId"+SevaarthId);
		logger.error("lLngCurrMonth"+lLngCurrMonth);
		logger.error("lLngCurrYr"+lLngCurrYr);
		String lStrRegSub = "";
		Long lStrRegsub = 0L;
		List Data=null;
		List<Object> lLstReturnList = null;
		logger.error("getRegularSubscription lLngCurrMonth::::::::"+lLngCurrMonth);
		if(lLngCurrMonth < 4){
			lLngCurrYr = lLngCurrYr -1;
		}
		Long lLngNxtyr = lLngCurrYr + 1;
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		/*lSBQuery.append(" SELECT Cast(nvl(MGID.REGULAR_SUBSCRIPTION,0) as varchar(20)) FROM MST_GPF_INTEREST_DTLS MGID ");
		lSBQuery.append(" join MST_EMP_GPF_ACC MEGA on MEGA.GPF_ACC_NO = MGID.GPF_ACC_NO "); 
		lSBQuery.append(" join SGVC_FIN_YEAR_MST SFYM on SFYM.FIN_YEAR_ID = MGID.FIN_YEAR_ID  ");
		lSBQuery.append(" where MEGA.SEVAARTH_ID='"+SevaarthId+"' and SFYM.FIN_YEAR_CODE='"+lLngCurrYr+"' ");
		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		Data = lQuery.list();
		if(Data !=null && Data.size()>0){
			for(int  i =0 ; i < Data.size() ; i++  ){
				lStrRegsub = lStrRegsub + Long.parseLong(Data.get(i).toString());	
			}				
		}
		lStrRegSub = lStrRegsub.toString();
		return lStrRegSub;*/
		
		StringBuilder lSBQueryy = new StringBuilder();
		try {
			String isSevenpc="";
			lSBQueryy.append(" SELECT SIXPAYAMOUNT_09_10,SIXPAYAMOUNT_10_11,SIXPAYAMOUNT_11_12,SIXPAYAMOUNT_12_13,SIXPAYAMOUNT_13_14,SEVENPAYAMOUNT_16_17,SEVENPAYAMOUNT_17_18,SEVENPAYAMOUNT_18_19,SEVENPAYAMOUNT_19_20,SEVENPAYAMOUNT_20_21,IS_SEVENPC from MST_GPF_YEARLY  where ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");
			lSBQueryy.append("  Sevaarth_Id='"+SevaarthId+"' order by updated_date DESC fetch first rows only ");

			Query lQueryy = ghibSession.createSQLQuery(lSBQueryy.toString());

		//	lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
		//lQuery.setParameter("FinyrId", FinyrId);

			 
			List sixpay = lQueryy.list();
			if (sixpay != null && sixpay.size() > 0) {

				Object[] lArrObj = (Object[]) sixpay.get(0);

				if (lArrObj[10] != null) {
					isSevenpc = lArrObj[10].toString();
					logger
							.info("isSevenpc:::::::::"
									+ isSevenpc);
				}
			}
			List FinYearList = null;


			//StringBuilder lSBQuery = new StringBuilder();

			if (isSevenpc.equalsIgnoreCase("Y")) {
				lSBQuery.append(" SELECT Cast(nvl(MGID.REGULAR_SUBSCRIPTION,0) as varchar(20)) FROM MST_GPF_7PC_INTEREST_DTLS MGID ");
				lSBQuery.append(" join MST_EMP_GPF_ACC MEGA on MEGA.GPF_ACC_NO = MGID.GPF_ACC_NO and MEGA.sevaarth_id = MGID.sevaarth_id  "); 
				lSBQuery.append(" join SGVC_FIN_YEAR_MST SFYM on SFYM.FIN_YEAR_ID = MGID.FIN_YEAR_ID  ");
				lSBQuery.append(" where MEGA.SEVAARTH_ID='"+SevaarthId+"' and SFYM.FIN_YEAR_CODE='"+lLngCurrYr+"' AND MGID.STATUS IS NULL ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				Data = lQuery.list();
				if(Data !=null && Data.size()>0){
					for(int  i =0 ; i < Data.size() ; i++  ){
						lStrRegsub = lStrRegsub + Long.parseLong(Data.get(i).toString());	
					}				
				}
				lStrRegSub = lStrRegsub.toString();
				//return lStrRegSub;
			}else {
				lSBQuery.append(" SELECT Cast(nvl(MGID.REGULAR_SUBSCRIPTION,0) as varchar(20)) FROM MST_GPF_INTEREST_DTLS MGID ");
				lSBQuery.append(" join MST_EMP_GPF_ACC MEGA on MEGA.GPF_ACC_NO = MGID.GPF_ACC_NO and MEGA.sevaarth_id = MGID.sevaarth_id  "); 
				lSBQuery.append(" join SGVC_FIN_YEAR_MST SFYM on SFYM.FIN_YEAR_ID = MGID.FIN_YEAR_ID  ");
				lSBQuery.append(" where MEGA.SEVAARTH_ID='"+SevaarthId+"' and SFYM.FIN_YEAR_CODE='"+lLngCurrYr+"'  AND MGID.STATUS IS NULL ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				Data = lQuery.list();
				if(Data !=null && Data.size()>0){
					for(int  i =0 ; i < Data.size() ; i++  ){
						lStrRegsub = lStrRegsub + Long.parseLong(Data.get(i).toString());	
					}				
				}
				lStrRegSub = lStrRegsub.toString();
				//return lStrRegSub;
			}
			
		}catch(Exception e){
			
		}
		return lStrRegSub;
		
		/// ADDD COMMENT 
		/*lSBQuery.append(" select temp.x,temp.month,temp.GRP_D,temp.ADV_GRP_D,temp.y,temp.year from (SELECT case when paybill.PAYBILL_MONTH< 12 then cast((paybill.PAYBILL_MONTH+1) as varchar) when paybill.PAYBILL_MONTH= 12 then cast(1 as varchar) end as x,month.month_name as month,  ");
		lSBQuery.append(" paybill.GPF_GRP_D as GRP_D ,paybill.GPF_ADV_GRP_D as ADV_GRP_D , (paybill.GPF_GRP_D+paybill.GPF_ADV_GRP_D) as y ,paybill.PAYBILL_YEAR as year  ");
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID   ");
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID   ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID   ");
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1  ");
		lSBQuery.append(" inner join SGVA_MONTH_MST month on month.MONTH_ID = case when ((paybill.PAYBILL_MONTH+1)=13) then cast(1 as varchar) else (paybill.PAYBILL_MONTH+1) end  ");
		lSBQuery.append(" where emp.SEVAARTH_ID ='"+SevaarthId+"' AND paybill.PAYBILL_YEAR = '"+lLngCurrYr+"' AND paybill.PAYBILL_MONTH=3 ");
		lSBQuery.append(" union all ");
		lSBQuery.append(" SELECT case when paybill.PAYBILL_MONTH< 12 then cast((paybill.PAYBILL_MONTH+1) as varchar) when paybill.PAYBILL_MONTH= 12 then cast(1 as varchar) end as x,month.month_name as month,  ");
		lSBQuery.append(" paybill.GPF_GRP_D as GRP_D ,paybill.GPF_ADV_GRP_D as ADV_GRP_D , (paybill.GPF_GRP_D+paybill.GPF_ADV_GRP_D) as y ,paybill.PAYBILL_YEAR as year ");
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID   ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID   ");
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1  ");
		lSBQuery.append(" inner join SGVA_MONTH_MST month on month.MONTH_ID = case when ((paybill.PAYBILL_MONTH+1)=13) then cast(1 as varchar) else (paybill.PAYBILL_MONTH+1) end  ");
		lSBQuery.append(" where emp.SEVAARTH_ID ='"+SevaarthId+"'  ");
		lSBQuery.append(" AND ((paybill.PAYBILL_YEAR = '"+lLngCurrYr+"' AND paybill.PAYBILL_MONTH>3 ) or (paybill.PAYBILL_YEAR = '"+lLngNxtyr+"' AND paybill.PAYBILL_MONTH<4)) ) as temp ");
		lSBQuery.append(" order by temp.year ");
		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		Data = lQuery.list();
		if (Data != null && Data.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			for (int liCtr = 0; liCtr < Data.size(); liCtr++) {
				obj = (Object[]) Data.get(liCtr);
				lStrRegsub  = lStrRegsub +  Long.parseLong(obj[2].toString());
			}
		} 
		lStrRegSub = lStrRegsub.toString();
		return lStrRegSub;*/

	}




	/*
	 * Method to get the Request Worklist for the workflow users (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getGPFRequestList(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	//public List getGPFRequestList(String lStrUserType, String lStrPostId, String lStrLocationCode, String lStrCriteria,String lStrName, Date lDtSaveDate) {
	public List getGPFRequestList(String lStrUserType, String lStrPostId, String lStrLocationCode, 
			String lStrSearchBy,String lStrSearchValue) {
		logger.info("getGPFRequestList called");
		logger.info("lStrPostId"+lStrPostId);
		logger.info("lStrLocationCode"+lStrLocationCode);
		logger.info("lStrUserType"+lStrUserType);
		logger.info("lStrSearchBy"+lStrSearchBy);
		logger.info("lStrSearchValue"+lStrSearchValue);
		List empListForDeoAppover = new ArrayList();
		List advanceRequestList = new ArrayList();
		List finalWithdrawReqList = new ArrayList();

		StringBuilder lSBQuery = new StringBuilder();
		StringBuilder lSBQueryForAdvance = new StringBuilder();
		StringBuilder lSBQueryForFinal = new StringBuilder();

		Query lQuery = null;
		Query lQueryForAdvance = null;
		Query lQueryForFinalWithdraw = null;

		Date lDtToDate = null;
		//Date lDtTemp = lDtSaveDate;
		try {
			/*if (lDtSaveDate != null) {
				lDtToDate = (Date) lDtSaveDate.clone();
				lDtToDate.setDate(lDtTemp.getDate() + 1);
			}*/

			lSBQuery.append("select ME.emp_name,CS.transaction_Id,MEG.sevaarth_Id,CS.gpf_Acc_No,CS.created_Date,'CS',CS.gpf_Emp_Subscription_Id ");

			if (lStrUserType.equals("DEOAPP")) {
				lSBQuery.append(",CS.DEO_ACTION_DATE,'no'");
			} 

			else if (lStrUserType.equals("HO")) {
				lSBQuery.append(",CS.HO_RECEIVE_DATE,CS.STATUS_FLAG");
			}
			lSBQuery.append(" FROM MST_GPF_EMP_SUBSCRIPTION CS,wf_job_mst WJ,mst_emp_gpf_acc MEG,Mst_dcps_Emp ME ");
			lSBQuery.append(" WHERE ME.sevarth_Id = MEG.sevaarth_Id AND CS.gpf_Acc_No = MEG.gpf_Acc_No AND CS.status_Flag LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQuery.append(" AND WJ.job_Ref_Id =CS.gpf_Emp_Subscription_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id =800001 AND ME.emp_group ='D'");
/*

			lSBQuery.append(" FROM MST_GPF_EMP_SUBSCRIPTION CS,wf_job_mst WJ,mst_emp_gpf_acc MEG,Mst_dcps_Emp ME ");
			lSBQuery.append(" WHERE CS.gpf_Acc_No = MEG.gpf_Acc_No AND CS.status_Flag LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQuery.append(" AND WJ.job_Ref_Id =CS.gpf_Emp_Subscription_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id =800001 AND ME.emp_group ='D'");
*/
			/*if (lStrCriteria.equals("name")) {
				lSBQuery.append(" And ME.emp_name = '"+lStrName+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("name", lStrName);
			}

			else if (lStrCriteria.equals("date")) {
				lSBQuery.append(" And CS.created_Date >= '"+lDtSaveDate+"' AND CS.created_Date < '"+lDtToDate+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setDate("fromDate", lDtSaveDate);
				//lQuery.setDate("toDate", lDtToDate);
			} 

			else if (lStrCriteria.equals("both")) {
				lSBQuery.append(" And ME.EMP_name = '"+lStrName+"' ");
				lSBQuery.append(" And CS.created_Date >= '"+lDtSaveDate+"' AND CS.created_Date < '"+lDtToDate+"' ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("name", lStrName);
			//	lQuery.setDate("fromDate", lDtSaveDate);
				//lQuery.setDate("toDate", lDtToDate);
			} 
			 */	
			//Added By vivek 05 jun 2017
			if (lStrSearchBy.equals("SEVID")) {
				lSBQuery.append(" And MEG.sevaarth_Id= '"+lStrSearchValue+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			}

			else if (lStrSearchBy.equals("TRANID")) {
				lSBQuery.append(" And CS.transaction_Id = '"+lStrSearchValue+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			} 

			else if (lStrSearchBy.equals("EMPNAME")) {
				lSBQuery.append(" And ME.EMP_name = '"+lStrSearchValue+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			} 
			else if (lStrSearchBy.equals("GPFACCNO")) {
				lSBQuery.append(" And CS.gpf_Acc_No = '"+lStrSearchValue+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			} 
			else {
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			}
			//Ended By vivek 05 jun 2017
			logger.info("query**********!11111111111"+lSBQuery.toString());
			//lQuery.setParameter("docId", 800001L);
			//lQuery.setParameter("postId", lStrPostId);
			// lQuery.setParameter("locCode", lStrLocationCode);
			empListForDeoAppover = lQuery.list();





			lSBQueryForAdvance
			.append("select ME.EMP_name ,MGA.transaction_Id,MEG.sevaarth_Id,MGA.gpf_Acc_No,MGA.created_Date,MGA.advance_Type,MGA.mst_Gpf_Advance_Id ");
			if (lStrUserType.equals("DEOAPP")) {
				lSBQueryForAdvance.append(",MGA.deo_Action_Date,'no'");
			} else if (lStrUserType.equals("HO")) {
				lSBQueryForAdvance.append(",MGA.ho_Receive_Date,MGA.STATUS_FLAG");
			}
		/*	lSBQueryForAdvance.append(" FROM IFMS.MST_GPF_ADVANCE MGA,Wf_Job_Mst WJ, Mst_Emp_Gpf_Acc MEG, Mst_DCPS_Emp ME ");
			lSBQueryForAdvance.append(" WHERE MGA.gpf_Acc_No = MEG.gpf_Acc_No AND MGA.status_Flag LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQueryForAdvance.append(" AND WJ.job_Ref_Id = MGA.mst_Gpf_Advance_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id = 800002 AND ME.EMP_group ='D' AND MGA.data_Entry is null ");
*/
			lSBQueryForAdvance.append(" FROM IFMS.MST_GPF_ADVANCE MGA,Wf_Job_Mst WJ, Mst_Emp_Gpf_Acc MEG, Mst_DCPS_Emp ME ");
			lSBQueryForAdvance.append(" WHERE MGA.gpf_Acc_No = MEG.gpf_Acc_No AND MGA.sevaarth_Id = MEG.sevaarth_Id AND MGA.status_Flag LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQueryForAdvance.append(" AND WJ.job_Ref_Id = MGA.mst_Gpf_Advance_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id = 800002 AND ME.EMP_group ='D' AND MGA.data_Entry is null ");


			/*if (lStrCriteria.equals("name")) {
				lSBQueryForAdvance.append(" And ME.EMP_name = '"+lStrName+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());
				//lQueryForAdvance.setParameter("name", lStrName);
			} 


			else if (lStrCriteria.equals("date")) {
				lSBQueryForAdvance.append(" And MGA.created_Date >= '"+lDtSaveDate+"' AND MGA.created_Date < '"+lDtToDate+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());
			//	lQueryForAdvance.setDate("fromDate", lDtSaveDate);
				//lQueryForAdvance.setDate("toDate", fromDate);
			} 


			else if (lStrCriteria.equals("both")) {
				lSBQueryForAdvance.append(" And ME.EMP_name = '"+lStrName+"' ");
				lSBQueryForAdvance.append(" And MGA.created_Date >='"+lDtSaveDate+"' AND MGA.created_Date < '"+lDtToDate+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());
				//lQueryForAdvance.setParameter("name", lStrName);
				//lQueryForAdvance.setDate("fromDate", lDtSaveDate);
				//lQueryForAdvance.setDate("toDate", lDtToDate);
			} */
			//Added By vivek 05 jun 2017
			if (lStrSearchBy.equals("SEVID")) {
				lSBQueryForAdvance.append(" And MEG.sevaarth_Id = '"+lStrSearchValue+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());

			} 


			else if (lStrSearchBy.equals("TRANID")) {
				lSBQueryForAdvance.append(" And MGA.transaction_Id = '"+lStrSearchValue+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());

			} 


			else if (lStrSearchBy.equals("EMPNAME")) {
				lSBQueryForAdvance.append(" And ME.EMP_name = '"+lStrSearchValue+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());

			} 
			else if (lStrSearchBy.equals("GPFACCNO")) {
				lSBQueryForAdvance.append(" And MGA.gpf_Acc_No = '"+lStrSearchValue+"' ");
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());

			} 

			//Ended By vivek 05 jun 2017
			else {
				lQueryForAdvance = ghibSession.createSQLQuery(lSBQueryForAdvance.toString());
			}

			//lQueryForAdvance.setParameter("docId", 800002L);
			//lQueryForAdvance.setParameter("postId", lStrPostId);
			// lQueryForAdvance.setParameter("locCode", lStrLocationCode);


			logger.error("lSBQueryForAdvance::22222222222222::::::"+lSBQueryForAdvance.toString());


			advanceRequestList = lQueryForAdvance.list();

			lSBQueryForFinal.append("select ME.EMP_name ,TGF.transaction_Id,MEG.sevaarth_Id,TGF.gpf_Acc_No,TGF.created_Date,cast(substr('FW',1,2) as varchar),cast(TGF.trn_Gpf_Final_Withdrawal_Id as bigint) ");
			if (lStrUserType.equals("DEOAPP")) {
				lSBQueryForFinal.append(",TGF.deo_Action_Date,'no'");
			} else if (lStrUserType.equals("HO")) {
				lSBQueryForFinal.append(",TGF.ho_Receive_Date,TGF.REQ_STATUS");
			}

			lSBQueryForFinal.append(" FROM Trn_Gpf_Final_Withdrawal TGF,Wf_Job_Mst WJ, Mst_Emp_Gpf_Acc MEG, Mst_DCPS_Emp ME ");
			lSBQueryForFinal.append(" WHERE TGF.gpf_Acc_No = MEG.gpf_Acc_No AND TGF.sevaarth_Id = MEG.sevaarth_Id AND TGF.req_Status LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQueryForFinal.append(" AND WJ.job_Ref_Id = TGF.trn_Gpf_Final_Withdrawal_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id =800003 AND ME.EMP_group ='D'");
		
			/*lSBQueryForFinal.append(" FROM Trn_Gpf_Final_Withdrawal TGF,Wf_Job_Mst WJ, Mst_Emp_Gpf_Acc MEG, Mst_DCPS_Emp ME ");
			lSBQueryForFinal.append(" WHERE TGF.gpf_Acc_No = MEG.gpf_Acc_No AND TGF.req_Status LIKE 'F%' AND MEG.mst_Gpf_Emp_Id = ME.dcps_Emp_Id and ME.dcps_Or_Gpf='N' ");
			lSBQueryForFinal.append(" AND WJ.job_Ref_Id = TGF.trn_Gpf_Final_Withdrawal_Id AND WJ.lst_Act_Post_Id = '"+lStrPostId+"' AND WJ.doc_Id =800003 AND ME.EMP_group ='D'");
			*///Added By vivek 05 jun 2017
			if (lStrSearchBy.equals("SEVID")) {
				lSBQueryForFinal.append(" And MEG.sevaarth_Id = '"+lStrSearchValue+"' ");
				lQueryForFinalWithdraw = ghibSession.createSQLQuery(lSBQueryForFinal.toString());
				//lQueryForFinalWithdraw.setParameter("name", lStrName);
			} 


			else if (lStrSearchBy.equals("TRANID")) {
				lSBQueryForFinal.append(" And TGF.transaction_Id = '"+lStrSearchValue+"' ");
				lQueryForFinalWithdraw = ghibSession.createSQLQuery(lSBQueryForFinal.toString());
				//lQueryForFinalWithdraw.setDate("fromDate", lDtSaveDate);
				//lQueryForFinalWithdraw.setDate("toDate", lDtToDate);
			} 



			else if (lStrSearchBy.equals("EMPNAME")) {
				lSBQueryForFinal.append(" And ME.EMP_name = '"+lStrSearchValue+"' ");
				lQueryForFinalWithdraw = ghibSession.createSQLQuery(lSBQueryForFinal.toString());

			} 
			else if (lStrSearchBy.equals("GPFACCNO")) {
				lSBQueryForFinal.append(" And TGF.gpf_Acc_No = '"+lStrSearchValue+"' ");
				lQueryForFinalWithdraw = ghibSession.createSQLQuery(lSBQueryForFinal.toString());

			} 

			//Ended By vivek 05 jun 2017
			else {
				lQueryForFinalWithdraw = ghibSession.createSQLQuery(lSBQueryForFinal.toString());
			}
			//lQueryForFinalWithdraw.setParameter("docId", 800003L);
			//lQueryForFinalWithdraw.setParameter("postId", lStrPostId);
			// lQueryForFinalWithdraw.setParameter("locCode", lStrLocationCode);
			finalWithdrawReqList = lQueryForFinalWithdraw.list();



			logger.error("lSBQueryForFinal:::::3333333333:::"+lSBQueryForFinal.toString());

			empListForDeoAppover.addAll(advanceRequestList);
			empListForDeoAppover.addAll(finalWithdrawReqList);

			int a = empListForDeoAppover.size();
			int b = advanceRequestList.size();
			int c = finalWithdrawReqList.size();

			logger.info("a::"+a);
			logger.info("b::"+b);
			logger.info("c::"+c);


		} catch (Exception e) {
			logger.error("Exception in getGPFRequestList of GPFRequestProcessDAOImpl  : ", e);			
		}
		return empListForDeoAppover;
	}

	/*
	 * Method to get the Withdrawal(Non-refundable advance )details for the
	 * employee (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getWithdrawalDetail(java.lang
	 * .String, java.lang.String)
	 */
	//public List getWithdrawalDetail(String lStrGpfAccNo, String lStrAdvanceType) {
	//swt 09/07/2020
	public List getWithdrawalDetail(String lStrGpfAccNo, String lStrAdvanceType,String lStrSevaarthId) {
		List withdrawalList = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {
			lSBQuery
			.append("select transactionId,createdDate,advanceAmt,amountSanctioned,sancAuthorityName,sanctionedDate");
			lSBQuery.append(" FROM MstGpfAdvance");
			lSBQuery.append(" WHERE gpfAccNo = :gpfAccNo AND sevaarthId = :sevaarthId AND statusFlag = 'A'");
			lSBQuery.append(" AND advanceType = :advanceType");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);
			lQuery.setParameter("advanceType", lStrAdvanceType);

			withdrawalList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getWithdrawalDetail of GPFRequestProcessDAOImpl  : ", e);			
		}
		return withdrawalList;
	}

	/*
	 * Method to get the Advance total of employee from starting of the year to
	 * till date (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getAdvanceHistory(java.lang.
	 * String, java.lang.Long)
	 */
	
	//swt 01/06/2020 (for SevaarthId)
	//public List getAdvanceHistory(String lStrGpfAccNo, Long lLngYearId) {
	public List getAdvanceHistory(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId) {
		List advanceHistoryList = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {
			/*lSBQuery.append("SELECT advanceType, SUM(amountSanctioned)");
			lSBQuery.append(" FROM MstGpfAdvance");
			lSBQuery
					.append(" WHERE gpfAccNo = :gpfAccNo AND finYearId = :finYearId AND statusFlag = 'A' GROUP BY advanceType ORDER BY advanceType");
			 */
			//AS BIGINT
			lSBQuery.append(" SELECT adv.ADVANCE_TYPE,sum(CAST(adv.AMOUNT_SANCTIONED AS BIGINT)),sum(CAST(adv.PAYABLE_AMOUNT AS BIGINT)) FROM IFMS.MST_GPF_ADVANCE adv ");
			lSBQuery.append(" where adv.GPF_ACC_NO = :gpfAccNo and adv.sevaarth_Id = :sevaarthId and adv.FIN_YEAR_ID = :finYearId and adv.STATUS_FLAG = 'A' and ");
			lSBQuery.append(" (adv.TRANSACTION_ID in (SELECT bill.TRANSACTION_ID FROM MST_GPF_BILL_DTLS bill  ");
			lSBQuery.append("  where bill.GPF_ACC_NO = :gpfAccNo  and  bill.STATUS_FLAG  = 1) or adv.DATA_ENTRY =1  ) ");
			lSBQuery.append("  group by adv.ADVANCE_TYPE ,adv.PAYABLE_AMOUNT order by adv.ADVANCE_TYPE,adv.PAYABLE_AMOUNT ");

			logger.info(" in getAdvanceHistory  : "+lSBQuery+" lStrGpfAccNo "+lStrGpfAccNo+" lLngYearId "+lLngYearId+"lStrSevaarthId "+lStrSevaarthId+"");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("finYearId", lLngYearId);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);

			advanceHistoryList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getAdvanceHistory of GPFRequestProcessDAOImpl  : ", e);			
		}

		return advanceHistoryList;

	}

	/*
	 * Method to get the GPF Account balance of Employee (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getGPFAccountBalance(java.lang
	 * .String, java.lang.Long)
	 */
	
	//swt 01/06/2020 (for SevaarthId)
	//public List getGPFAccountBalance(String lStrGpfAccNo, Long lLngYearId) {
	public List getGPFAccountBalance(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId) {
		List lLstAccountBalance = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		Long lLngNxtYearId = lLngYearId + 1l;
		try {
			lSBQuery
			.append("SELECT COALESCE(SUM(MGM.regular_Subscription),0),COALESCE(SUM(MGM.advance_Recovery),0),COALESCE(SUM(deputation_Challan),0),COALESCE(SUM(pre_Pay_Of_Advance),0),COALESCE(SUM(excess_Payment),0),COUNT(*)");
			lSBQuery.append(" FROM Mst_Gpf_Monthly MGM");
			lSBQuery.append(" WHERE ((fin_Year_Id = " + lLngYearId + " AND month_Id>3) OR (fin_Year_Id = "
					+ lLngNxtYearId + " AND month_Id<=3)) AND MGM.gpf_Acc_No = '" + lStrGpfAccNo + "' AND STATUS != 'X' " );
							//lSBQuery.append(" AND MGM.Sevaarth_Id = '" + lStrSevaarthId + "'  ");

			
			
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			
			
			// lQuery.setParameter("finYearId", lLngYearId);
			// lQuery.setParameter("nxtFinYearId", lLngNxtYearId);
			// / lQuery.setParameter("monthId", lLngMonthId);
			// lQuery.setParameter("gpfAccNo", lStrGpfAccNo);

			lLstAccountBalance = lQuery.list();
			
		} catch (Exception e) {
			logger.error("Exception in getGPFAccountBalance of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLstAccountBalance;
	}

	/*
	 * Method to get DP or GP according to Pay Commission of employee
	 * (non-Javadoc)
	 * 
	 * @see com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDPOrGP(java.lang.Long,
	 * java.lang.String)
	 */
	public Long getDPOrGP(Long lLngEmpId, String lStrPayComm) {
		List salaryList = new ArrayList();
		Long lLngGradePay = 0L;
		StringBuilder lSBQuery = new StringBuilder();
		try {
			if (lStrPayComm.equals("6th Pay Commission")) {
				lSBQuery.append("SELECT HPEM.empAllowAmount");
				lSBQuery.append("   FROM HrEisOtherDtls HOD, HrPayEmpallowMpg HPEM");
				lSBQuery
				.append("  WHERE HPEM.hrEisEmpMst.orgEmpMst.empId = :empId AND HPEM.hrPayAllowTypeMst.allowCode = 145 AND HPEM.month=-1 AND HPEM.year=-1");

				Query lQuery = ghibSession.createQuery(lSBQuery.toString());

				lQuery.setParameter("empId", lLngEmpId);

				salaryList = lQuery.list();
				if (salaryList != null && salaryList.size() > 0) {

					lLngGradePay = (Long) salaryList.get(0);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in getDPOrGP of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLngGradePay;
	}

	/*
	 * Method to generate the transaction id for new GPF request (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getNewTransactionId(java.lang
	 * .String)
	 */
	public String getNewTransactionId(String lStrSevaarthId) {

		StringBuilder lSBQuery = new StringBuilder();
		new StringBuilder();
		new StringBuilder();
		List tempList = new ArrayList<Long>();
		new ArrayList<Long>();
		new ArrayList<Long>();
		Long lLngNewTransactionId = 0L;
		String lStrNewTrnId = null;
		String lStrTrnsId = "";
		String lStrMonth = "";

		Calendar cal = Calendar.getInstance();
		try {
			Integer lIntMonth = cal.get(Calendar.MONTH) + 1;
			Integer lIntYear = cal.get(Calendar.YEAR);
			if (lIntMonth.toString().length() == 1) {
				lStrMonth = "0" + lIntMonth;
			} else {
				lStrMonth = lIntMonth.toString();
			}
			// code to get the First letter of Transaction Id (i.e. organization
			// id) from Sevaarth Id of employee

			// lStrTrnsId = lStrSevaarthId.charAt(0) + lStrMonth +
			// lIntYear.toString().substring(2, 4);
			lStrTrnsId = "2" + lStrMonth + lIntYear.toString().substring(2, 4);
			lSBQuery.append(" SELECT MAX(transactionId) FROM MstGpfReq WHERE transactionId LIKE :lStrTrnsId");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("lStrTrnsId", lStrTrnsId + '%');
			tempList = lQuery.list();

			if (tempList != null && tempList.size() > 0 && tempList.get(0) != null) {
				lLngNewTransactionId = (Long.parseLong(tempList.get(0).toString())) + 1L;
				lStrNewTrnId = lLngNewTransactionId.toString();
			} else {
				lStrNewTrnId = String.format(lStrTrnsId + "%06d", 1);
			}
		} catch (Exception e) {
			logger.error("Exception in getNewTransactionId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrNewTrnId;
	}

	/*
	 * Method to get the DDO Code for DDO Assistant post id (non-Javadoc)
	 * 
	 * @see com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDdoCode(java.lang.Long)
	 */
	public String getDdoCode(String lStrLocationCode) {

		logger.error("lStrLocationCode"+lStrLocationCode);
		StringBuilder lSBQuery = new StringBuilder();
		List lLstCodeList = null;
		String lStrDdoCode = "";
		try {
			lSBQuery.append(" SELECT ddoCode ");
			lSBQuery.append(" FROM OrgDdoMst ");
			lSBQuery.append(" WHERE locationCode = :locationCode ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("locationCode", lStrLocationCode);

			lLstCodeList = lQuery.list();


			lStrDdoCode = lLstCodeList.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getDdoCode of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrDdoCode;
	}

	public String getLocationCode(String lStrddocode) {

		StringBuilder lSBQuery = new StringBuilder();
		List lLstCodeList = null;
		String lStrDdoCode = "";
		try {
			lSBQuery.append(" SELECT locationCode ");
			lSBQuery.append(" FROM OrgDdoMst ");
			lSBQuery.append(" WHERE ddoCode = :lStrddocode ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("lStrddocode", lStrddocode);

			lLstCodeList = lQuery.list();

			lStrDdoCode = lLstCodeList.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getDdoCode of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrDdoCode;
	}

	/*
	 * Method to get the DDO Code for Verifier or HO post id (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDDOPostIdForVerifierHo(java
	 * .lang.Long, java.lang.String)
	 */
	public Long getDDOPostIdForVerifierHo(Long lLngPostId, String lStrUserType) {

		Long lLongDdoPostId = null;
		List lLstDdoDtls = null;

		try {
			getSession();
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT ddoPostId");
			lSBQuery.append(" FROM  RltGpfDdoVerifierHo");

			if (lStrUserType.equals("DEOAPP")) {
				lSBQuery.append(" WHERE verifierPostId = :postId ");
			} else if (lStrUserType.equals("HO")) {
				lSBQuery.append(" WHERE hoPostId = :postId ");
			}

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("postId", lLngPostId);

			lLstDdoDtls = lQuery.list();

			if (lLstDdoDtls.size() != 0) {
				lLongDdoPostId = Long.valueOf(lLstDdoDtls.get(0).toString());
			}

		} catch (Exception e) {
			logger.error("Exception in getDDOPostIdForVerifierHo of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLongDdoPostId;
	}

	/*
	 * Method to ge the DDO Code for DDO post id (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDdoCodeForDDO(java.lang.Long)
	 */
	public String getDdoCodeForDDO(Long lLngPostId) {

		String lStrDdoCode = null;
		List lLstDdoDtls = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT ddo.DDO_CODE FROM ORG_POST_DETAILS_RLT post inner join ORG_DDO_MST ddo ");
			lSBQuery.append(" on post.LOC_ID = ddo.LOCATION_CODE ");
			lSBQuery.append("where post.post_id = :postId ");
			
		
  
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("postId", lLngPostId);
			lLstDdoDtls = lQuery.list();
			lStrDdoCode = lLstDdoDtls.get(0).toString();
			
			
		
		} catch (Exception e) {
			logger.error("Exception in getDdoCodeForDDO of GPFRequestProcessDAOImpl  : ", e);			
		}

		return lStrDdoCode;
	}

	// 
	// public Double getMonthlySubscription(String lStrGpfAccNo, Integer
	// lIntMonthId, Integer lIntFinYearId) {
	//
	// List<Double> lMonthlySubsList = new ArrayList<Double>();
	// Double lDblMonthlySubs = null;
	// StringBuilder lSBQuery = new StringBuilder();
	// lSBQuery.append("select monthlySubscription");
	// lSBQuery.append(" FROM MstGpfEmpSubscription");
	// lSBQuery.append(" WHERE gpfAccNo = :gpfAccNo AND activeFlag = 1");
	// lSBQuery.append(" AND effectFromMonth <= :MonthId AND finYearId = :finYearId");
	// Query lQuery = ghibSession.createQuery(lSBQuery.toString());
	// lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
	// lQuery.setParameter("MonthId", lIntMonthId);
	// lQuery.setParameter("finYearId",
	// Long.parseLong(lIntFinYearId.toString()));
	//
	// lMonthlySubsList = lQuery.list();
	// if (lMonthlySubsList != null && lMonthlySubsList.size() != 0) {
	// lDblMonthlySubs = lMonthlySubsList.get(0);
	// }
	// return lDblMonthlySubs;
	// }

	/*
	 * Method to get the Advance(Refundable advance )details for the employee
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getAdvanceDetail(java.lang.String
	 * , java.lang.String)
	 */
	
	//swt 01/06/2020 (for SevaarthId)
	//public List getAdvanceDetail(String lStrGpfAccNo, String lStrAdvanceType) {
    public List getAdvanceDetail(String lStrGpfAccNo, String lStrAdvanceType,String lStrSevaarthId) {
		logger.error("getAdvanceDetail:::::::::lStrGpfAccNo: lStrAdvanceType:"+lStrAdvanceType);
		List advanceList = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {
			/*lSBQuery
					.append("select transactionId,createdDate,advanceAmt,amountSanctioned,noOfInstallments,installmentAmount,outstandingAmount,sancAuthorityName,sanctionedDate");
			lSBQuery.append(" FROM MstGpfAdvance");
			lSBQuery.append(" WHERE gpfAccNo = :gpfAccNo AND statusFlag = 'A'");
			lSBQuery.append(" AND advanceType = :advanceType");
			lSBQuery.append(" ORDER BY transactionId DESC");
			lSBQuery.append(" FETCH FIRST ROW ONLY ");*/
			lSBQuery.append(" select TRANSACTION_ID,CREATED_DATE,AMOUNT_REQUESTED,AMOUNT_SANCTIONED,TOTAL_INSTALLMENTS,INSTALLMENT_AMOUNT,OUTSTANDING_AMOUNT,SANC_AUTHORITY_NAME,TO_CHAR(SANCTIONED_DATE,'MM') ");
			lSBQuery.append(" FROM IFMS.MST_GPF_ADVANCE ");
			lSBQuery.append(" WHERE GPF_ACC_NO = '"+lStrGpfAccNo+"' AND Sevaarth_Id = '"+lStrSevaarthId+"' AND STATUS_FLAG = 'A' ");
			lSBQuery.append(" AND ADVANCE_TYPE = '"+lStrAdvanceType+"' ");
			lSBQuery.append(" ORDER BY TRANSACTION_ID DESC ");
			lSBQuery.append(" FETCH FIRST ROW ONLY ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			logger.error("lSBQuery:::::::::::"+lSBQuery.toString());
			//lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			//lQuery.setParameter("advanceType", lStrAdvanceType);
			logger.error("lSBQuery:::::::::::"+lSBQuery.toString());

			advanceList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getAdvanceDetail of GPFRequestProcessDAOImpl  : ", e);			
		}
		return advanceList;
	}

	/*
	 * Method to get the pay scale data from payroll module (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getPayScaleData(java.lang.Long)
	 */
	public HrEisScaleMst getPayScaleData(Long lLngEmpId) {
		HrEisScaleMst hrOtherInfo = new HrEisScaleMst();
		Session hibSession = getSession();
		try {
			String query1 = "select  empLookup.hrEisSgdMpg.hrEisScaleMst  from HrEisOtherDtls as empLookup where empLookup.hrEisEmpMst.orgEmpMst.empId = "
				+ lLngEmpId;
			Query sqlQuery1 = hibSession.createQuery(query1);

			if (sqlQuery1.list().size() < 1) {
				hrOtherInfo = null;
			} else {
				hrOtherInfo = (HrEisScaleMst) sqlQuery1.uniqueResult();
				logger.info("setting sqlQuery's uniqueResult");
			}
		} catch (Exception e) {
			logger.error("Exception in getPayScaleData of GPFRequestProcessDAOImpl  : ", e);			
		}
		return hrOtherInfo;
	}

	/*
	 * Method to check if employee has already taken a non-refundable advance
	 * within 1 year (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#withdrawalExistsForFinYear(java
	 * .lang.String, java.lang.Long)
	 */
	public Boolean withdrawalExistsForFinYear(String strGpfAccNo, Long lLngFinYearId) {
		List savedRA = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {
			lSBQuery.append("select MGA.transactionId");
			lSBQuery.append(" FROM MstGpfAdvance MGA");
			lSBQuery
			.append(" WHERE MGA.gpfAccNo = :gpfAccNo AND MGA.statusFlag = 'A' AND MGA.advanceType = 'NRA' AND ((current_date - MGA.sanctionedDate)/365) < 1");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", strGpfAccNo);
			// lQuery.setParameter("finYear", lLngFinYearId);
			savedRA = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in withdrawalExistsForFinYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		if (savedRA.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Method to get the employee name list for auto complete (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getEmpNameForAutoComplete(java
	 * .lang.String, java.lang.String)
	 */
	public List getEmpNameForAutoComplete(String searchKey, String lStrDdoCode) {
		ArrayList<ComboValuesVO> finalList = new ArrayList<ComboValuesVO>();
		ComboValuesVO cmbVO;
		Object[] obj;

		StringBuilder sb = new StringBuilder();
		Query selectQuery = null;
		try {
			sb.append("select dcpsEmpId,name from MstEmp where UPPER(name) LIKE UPPER(:searchKey) AND group ='D'");



			if (lStrDdoCode != null) {
				if (!"".equals(lStrDdoCode)) {
					sb.append(" and ddoCode = :ddoCode");
				}
			}
			selectQuery = ghibSession.createQuery(sb.toString());
			selectQuery.setParameter("searchKey", searchKey + '%');
			selectQuery.setParameter("ddoCode", lStrDdoCode);

			List resultList = selectQuery.list();

			cmbVO = new ComboValuesVO();

			if (resultList != null && resultList.size() > 0) {
				Iterator it = resultList.iterator();
				while (it.hasNext()) {
					cmbVO = new ComboValuesVO();
					obj = (Object[]) it.next();
					cmbVO.setId(obj[1].toString());
					cmbVO.setDesc(obj[1].toString());
					finalList.add(cmbVO);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in getEmpNameForAutoComplete of GPFRequestProcessDAOImpl  : ", e);			
		}
		return finalList;
	}



	/*
	 * Method to get the Opening balance of current financial year (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getOpeningBalForCurrYear(java
	 * .lang.String, java.lang.Long)
	 */
	//public Double getOpeningBalForCurrYear(String lStrGpfAccNo, Long lLngYearId) throws Exception {
	
		//swt 09/06/2020 (sevaarthId added)
		public Double getOpeningBalForCurrYear(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId) throws Exception {

		logger.info("lStrGpfAccNo"+lStrGpfAccNo);
		logger.info("lLngYearId"+lLngYearId);
		logger.info("lstrsevaarthId"+lStrSevaarthId);

		Double lDblOpeningBal = null;
		List lLstOpeningBal = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		try {
			lSBQuery
			.append("SELECT closing_Balance FROM Mst_Gpf_Yearly WHERE gpf_Acc_No = :gpfAccNo AND sevaarth_Id=:sevaarthId AND fin_Year_Id = :finYearId");/* commented by Brijoy 19012019*/
			/*lSBQuery
			.append("SELECT closing_Balance FROM Mst_Gpf_Yearly WHERE gpf_Acc_No = '"+lStrGpfAccNo+"' ");*/

			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);
			lQuery.setParameter("finYearId", lLngYearId);

			lLstOpeningBal = lQuery.list();
			if (lLstOpeningBal != null && lLstOpeningBal.size() > 0) {
				lDblOpeningBal = Double.parseDouble((lLstOpeningBal.get(0).toString()));
				logger.info("lDblOpeningBal"+lDblOpeningBal);
			} else {
				lDblOpeningBal = 0d;
			}
		} catch (Exception e) {
			logger.error("Exception in getOpeningBalForCurrYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDblOpeningBal;
	}

	/*
	 * Method to get location code from post id of user (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getLocationCodeOfUser(java.lang
	 * .Long)
	 */
	public String getLocationCodeOfUser(Long lLngPostId) throws Exception {
		logger.error("getLocationCodeOfUser"+lLngPostId);
		StringBuilder lSBQuery = new StringBuilder();
		List lLstCodeList = null;
		String lStrLocationCode = "";
		try {
			lSBQuery.append(" SELECT PM.location_Code ");
			lSBQuery.append(" FROM Org_Post_Mst PM ");
			lSBQuery.append(" WHERE PM.post_Id = '"+lLngPostId+"' ");



			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("postId", lLngPostId);
			logger.error("lSBQuery::::::::::"+lSBQuery.toString());
			lLstCodeList = lQuery.list();
			if(lLstCodeList.size() !=0 && !lLstCodeList.isEmpty())
			{
				lStrLocationCode = lLstCodeList.get(0).toString();
				logger.error("lStrLocationCode::::::::::"+lStrLocationCode);
			}
		} catch (Exception e) {
			logger.error("Exception in getLocationCodeOfUser of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrLocationCode;
	}

	/*
	 * Method to get Draft/Rejected GPF Request List (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDraftRequestList(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.util.Date)
	 */
	//Commented By Vivek 05 Jun 2017
	/*public List getDraftRequestList(String lStrPostId, String lStrDdoCode, String lStrCriteria, String lStrName,
			Date lDtSaveDate) throws Exception {*/
	public List getDraftRequestList(String lStrPostId, String lStrDdoCode, String lStrCriteria, String lStrSearchBy,
			String lStrSearchValue) throws Exception {
		List lLstChangeSubs = new ArrayList();
		List lLstAdvance = new ArrayList();
		List lLstFinalWithdraw = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		StringBuilder lSBQueryForAdvance = new StringBuilder();
		StringBuilder lSBQueryForFinal = new StringBuilder();
		//Date lDtToDate = null;
		//Date lDtTemp = lDtSaveDate;
		try {
			/*if (lDtSaveDate != null) {
				lDtToDate = (Date) lDtSaveDate.clone();
				lDtToDate.setDate(lDtTemp.getDate() + 1);
			}*/

			lSBQuery
			.append("select ME.name,CS.transactionId,MEG.sevaarthId,CS.gpfAccNo,CS.applicationDate,'CS' ,CS.gpfEmpSubscriptionId,CS.approverRemarks,CS.hoRemarks,CS.statusFlag,CS.createdDate");
			lSBQuery.append(" FROM MstGpfEmpSubscription CS, MstEmpGpfAcc MEG, MstEmp ME");
			lSBQuery
			.append(" WHERE CS.gpfAccNo = MEG.gpfAccNo AND CS.statusFlag IN ('D','R') AND MEG.mstGpfEmpId = ME.dcpsEmpId and ME.dcpsOrGpf='N' ");
			lSBQuery.append(" AND ME.ddoCode=:ddoCode AND ME.group ='D' ");

			/*if (lStrCriteria.equalsIgnoreCase("name")) {
				lSBQuery.append(" AND ME.name = :name ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("name", lStrName);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			} else if (lStrCriteria.equalsIgnoreCase("date")) {
				lSBQuery.append(" AND CS.createdDate >= :fromDate AND CS.createdDate < :toDate");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setDate("fromDate", lDtSaveDate);
				lQuery.setDate("toDate", lDtToDate);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			} else if (lStrCriteria.equalsIgnoreCase("both")) {
				lSBQuery.append(" AND ME.name = :name AND CS.createdDate >= :fromDate AND CS.createdDate < :toDate ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("name", lStrName);
				lQuery.setDate("fromDate", lDtSaveDate);
				lQuery.setDate("toDate", lDtToDate);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			} */
			//Added By vivek 05 Jun 2017
			if (lStrSearchBy.equalsIgnoreCase("SEVID")) {
				lSBQuery.append(" AND MEG.sevaarthId = :SevId ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("SevId", lStrSearchValue);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			} else if (lStrSearchBy.equalsIgnoreCase("TRANID")) {
				lSBQuery.append(" AND CS.transactionId = :TranId");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("TranId", lStrSearchValue);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			} else if (lStrSearchBy.equalsIgnoreCase("EMPNAME")) {
				lSBQuery.append(" AND ME.name = :name ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("name", lStrSearchValue);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			}else if (lStrSearchBy.equalsIgnoreCase("GPFACCNO")) {
				lSBQuery.append(" AND CS.gpfAccNo = :gpfAccNo ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("gpfAccNo", lStrSearchValue);
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			}
			//Ended By vivek 05 Jun 2017
			else {
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("ddoCode", lStrDdoCode);
				lLstChangeSubs = lQuery.list();
			}

			lSBQueryForAdvance.append("select ME.name ,MGA.transactionId,MEG.sevaarthId,MGA.gpfAccNo,MGA.applicationDate,MGA.advanceType,MGA.mstGpfAdvanceId,MGA.verifierRemarks,MGA.approverRemarks,MGA.statusFlag,MGA.createdDate,MGA.purposeCategory");
			lSBQueryForAdvance.append(",MGA.subType,MGA.advanceAmt,MGA.applicationDate,MGA.noOfInstallments,MGA.payableAmount");
			//12 SUB_TYPE,AMOUNT_REQUESTED,APPLICATION_DATE,TOTAL_INSTALLMENTS,PAYABLE_AMOUNT ,SUB_TYPE
			lSBQueryForAdvance.append(" FROM MstGpfAdvance MGA, MstEmpGpfAcc MEG, MstEmp ME");
			lSBQueryForAdvance
			.append(" WHERE MGA.gpfAccNo = MEG.gpfAccNo AND MGA.statusFlag IN ('D','R','DR') AND MEG.mstGpfEmpId = ME.dcpsEmpId and ME.dcpsOrGpf='N' AND MGA.dataEntry is null ");
			lSBQueryForAdvance.append(" AND ME.ddoCode=:ddoCode AND ME.group ='D' ");

			/*if (lStrCriteria.equalsIgnoreCase("name")) {
				lSBQueryForAdvance.append(" AND ME.name = :name ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("name", lStrName);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			} else if (lStrCriteria.equalsIgnoreCase("date")) {
				lSBQueryForAdvance.append(" AND MGA.createdDate >= :fromDate AND MGA.createdDate < :toDate ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setDate("fromDate", lDtSaveDate);
				lQueryForAdvance.setDate("toDate", lDtToDate);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			} else if (lStrCriteria.equalsIgnoreCase("both")) {
				lSBQueryForAdvance
						.append(" AND ME.name = :name AND MGA.createdDate >= :fromDate AND MGA.createdDate < :toDate ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("name", lStrName);
				lQueryForAdvance.setDate("fromDate", lDtSaveDate);
				lQueryForAdvance.setDate("toDate", lDtToDate);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			}*/
			//Added By vivek 05 Jun 2017
			if (lStrSearchBy.equalsIgnoreCase("SEVID")) {
				lSBQueryForAdvance.append(" AND MEG.sevaarthId = :SevId ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("SevId", lStrSearchValue);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			} else if (lStrSearchBy.equalsIgnoreCase("TRANID")) {
				lSBQueryForAdvance.append(" AND MGA.transactionId = :TranId ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("TranId", lStrSearchValue);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			} else if (lStrSearchBy.equalsIgnoreCase("EMPNAME")) {
				lSBQueryForAdvance.append(" AND ME.name = :name ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("name", lStrSearchValue);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			}
			else if (lStrSearchBy.equalsIgnoreCase("GPFACCNO")) {
				lSBQueryForAdvance.append(" AND MGA.gpfAccNo = :gpfAccNo ");
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("gpfAccNo", lStrSearchValue);
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			}
			//Ended By vivek 05 Jun 2017
			else {
				Query lQueryForAdvance = ghibSession.createQuery(lSBQueryForAdvance.toString());
				lQueryForAdvance.setParameter("ddoCode", lStrDdoCode);
				lLstAdvance = lQueryForAdvance.list();
			}

			lSBQueryForFinal
			.append("select ME.name ,TGF.transactionId,MEG.sevaarthId,TGF.gpfAccNo,TGF.applicationDate,'FW',TGF.trnGpfFinalWithdrawalId,TGF.approverRemarks,TGF.hoRemarks,TGF.reqStatus,TGF.createdDate,TGF.reason");
			lSBQueryForFinal.append(" FROM TrnGpfFinalWithdrawal TGF, MstEmpGpfAcc MEG, MstEmp ME");
			lSBQueryForFinal
			.append(" WHERE TGF.gpfAccNo = MEG.gpfAccNo AND TGF.reqStatus IN ('D','R','DR') AND MEG.mstGpfEmpId = ME.dcpsEmpId and ME.dcpsOrGpf='N' ");
			lSBQueryForFinal.append(" AND ME.ddoCode=:ddoCode AND ME.group ='D' ");

			/*if (lStrCriteria.equalsIgnoreCase("name")) {
				lSBQueryForFinal.append(" AND ME.name = :name ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("name", lStrName);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} else if (lStrCriteria.equalsIgnoreCase("date")) {
				lSBQueryForFinal.append(" AND TGF.createdDate >= :fromDate AND TGF.createdDate < :toDate ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setDate("fromDate", lDtSaveDate);
				lQueryForFinalWithdraw.setDate("toDate", lDtToDate);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} else if (lStrCriteria.equalsIgnoreCase("both")) {
				lSBQueryForFinal
						.append(" AND ME.name = :name AND TGF.createdDate >= :fromDate AND TGF.createdDate < :toDate ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("name", lStrName);
				lQueryForFinalWithdraw.setDate("fromDate", lDtSaveDate);
				lQueryForFinalWithdraw.setDate("toDate", lDtToDate);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} */
			//Added By vivek 05 Jun 2017
			if (lStrSearchBy.equalsIgnoreCase("SEVID")) {
				lSBQueryForFinal.append(" AND MEG.sevaarthId = :SevId ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("SevId", lStrSearchValue);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} else if (lStrSearchBy.equalsIgnoreCase("TRANID")) {
				lSBQueryForFinal.append(" AND TGF.transactionId = :TranId ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("TranId", lStrSearchValue);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} else if (lStrSearchBy.equalsIgnoreCase("EMPNAME")) {
				lSBQueryForFinal.append(" AND ME.name = :name ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("name", lStrSearchValue);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} else if (lStrSearchBy.equalsIgnoreCase("GPFACCNO")) {
				lSBQueryForFinal.append(" AND TGF.gpfAccNo = :gpfAccNo ");
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("gpfAccNo", lStrSearchValue);
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			} 
			//Ended By vivek 05 Jun 2017
			else {
				Query lQueryForFinalWithdraw = ghibSession.createQuery(lSBQueryForFinal.toString());
				lQueryForFinalWithdraw.setParameter("ddoCode", lStrDdoCode);
				lLstFinalWithdraw = lQueryForFinalWithdraw.list();
			}

			logger.error("Query for Draft is::::::::::::::::::::: "+lSBQuery.toString());
			logger.error("Query for Draft is::::::::::::::::::::: "+lSBQueryForAdvance.toString());
			logger.error("Query for Draft is::::::::::::::::::::: "+lSBQueryForFinal.toString());
			logger.error("Query for Draft is::::::::::::::::::::: "+lSBQueryForFinal.toString());

			lLstChangeSubs.addAll(lLstAdvance);
			lLstChangeSubs.addAll(lLstFinalWithdraw);
		} catch (Exception e) {
			logger.error("Exception in getDraftRequestList of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLstChangeSubs;

	}

	public String getDistrictIdforddocode(String lStrEmpDdocode) {
		String lStrDistrictName = null;
		List lLstDistrict = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT DDO.dcpsDdoOfficeDistrict");
			lSBQuery.append(" FROM  DdoOffice DDO");
			lSBQuery.append(" WHERE DDO.dcpsDdoCode = :lStrEmpDdocode ");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());

			lQuery.setParameter("lStrEmpDdocode", lStrEmpDdocode);

			lLstDistrict = lQuery.list();
			logger.error("Query is::::"+lSBQuery.toString());
			lStrDistrictName = lLstDistrict.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getDistrictIdforddocode of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrDistrictName;
	}

	/*
	 * Method to get the District Name from Id (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getDistrictNameForId(java.lang
	 * .Long)
	 */
	public String getDistrictNameForId(Long lLngDistrictId) {
		String lStrDistrictName = null;
		List lLstDistrict = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT CDM.districtName");
			lSBQuery.append(" FROM  CmnDistrictMst CDM");
			lSBQuery.append(" WHERE CDM.districtId = :districtId ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("districtId", lLngDistrictId);

			lLstDistrict = lQuery.list();

			lStrDistrictName = lLstDistrict.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getDistrictNameForId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrDistrictName;
	}

	/*
	 * Method to get the State Name from Id (non-Javadoc)
	 * 
	 * @see
	 * com.tcs.sgv.gpf.dao.GPFRequestProcessDAO#getStateNameForId(java.lang.
	 * Long)
	 */
	public String getStateNameForId(Long lLngStateId) {

		String lStrStateName = null;
		List lLstState = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT CSM.stateName");
			lSBQuery.append(" FROM  CmnStateMst CSM");
			lSBQuery.append(" WHERE CSM.stateId = :stateId ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("stateId", lLngStateId);

			lLstState = lQuery.list();

			lStrStateName = lLstState.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getStateNameForId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrStateName;
	}

	public boolean isDataEntryComplete(String lStrSevaarthId)
	{
		Boolean lBlData = false;
		List lLstDataEntry = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("FROM  TrnEmpGpfAcc ");
			lSBQuery.append(" WHERE statusFlag = 'A' AND sevaarthId =:sevaarthId ");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("sevaarthId", lStrSevaarthId);

			lLstDataEntry = lQuery.list();

			if(lLstDataEntry != null && lLstDataEntry.size() > 0)
			{
				lBlData =  true;
			}else{
				lBlData = false;
			}

			logger.info("lBlData"+lBlData);
		} catch (Exception e) {
			logger.error("Exception in isDataEntryComplete of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lBlData;
	}

	public String getDdoCodeForDEO(String lStrLocationCode)throws Exception
	{
		List lLstResData = null;
		String lStrDdoCode = "";

		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT ddoCode FROM OrgDdoMst WHERE locationCode =:locCode");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("locCode", lStrLocationCode);
			lLstResData = lQuery.list();

			if(lLstResData != null && lLstResData.size() > 0){
				lStrDdoCode = lLstResData.get(0).toString();
			}
		} catch (Exception e) {
			logger.error("Exception in getDdoCodeForDEO of GPFRequestProcessDAOImpl  : ", e);
			throw e;
		}

		return lStrDdoCode;
	}
	//Current office name of the employee. I.e office name where loan request of employee is raised from DDO AST
	public List getDsgnAndOfficeName(String lStrSevaarthId) {
		logger.error("lStrSevaarthId::::"+lStrSevaarthId);
		List lLstDsgnAndOfficeName = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT d.DSGN_NAME,o.OFF_NAME,s.SCALE_DESC,m.BASIC_PAY,m.GRADE_PAY,m.EMP_NAME_MARATHI FROM mst_dcps_emp m,ORG_DESIGNATION_MST d,MST_DCPS_DDO_OFFICE o,HR_EIS_SCALE_MST s ");
			lSBQuery.append(" where m.SEVARTH_ID = :sevaarthId and m.DESIGNATION = d.DSGN_ID and o.DCPS_DDO_OFFICE_MST_ID=m.CURR_OFF and s.SCALE_ID = m.PAYSCALE ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("sevaarthId", lStrSevaarthId);
			logger.info("getDsgnAndOfficeName:::::::"+lSBQuery.toString());
			lLstDsgnAndOfficeName = lQuery.list();

		} catch (Exception e) {
			logger.error("Exception in getDsgnAndOfficeName of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLstDsgnAndOfficeName;
	}




	//swt 01/06/2020 (for SevaarthId)
	//public Double getClosingBalance(String lStrGPFAccNo) {
	public Double getClosingBalance(String lStrGPFAccNo,String lStrSevaarthId) {
		List<Double> lLstClosingBalance = null;
		Double lDbClosingBalance = 0D;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT closingBalance FROM MstGpfMonthly where gpfAccNo =:GPFAccNo and sevaarthId =:sevaarthId and status = 'A' order by finYearId desc,monthId desc ");		
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("GPFAccNo", lStrGPFAccNo);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);

			lLstClosingBalance = lQuery.list();

			if(!lLstClosingBalance.isEmpty()){
				lDbClosingBalance = lLstClosingBalance.get(0);
			}

		} catch (Exception e) {
			logger.error("Exception in getClosingBalance of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDbClosingBalance;

	}


	public String getEmployerDsgnName(Long lLngPostId) {

		List<String> lLstDsgnName = null;
		String lStrDsgnName = "";
		try {
			StringBuilder lSBQuery = new StringBuilder();			
			/*lSBQuery.append("SELECT ODM.dsgnName FROM OrgPostDetailsRlt OPR,OrgDesignationMst ODM WHERE ");
			lSBQuery.append("OPR.orgPostMst.postId = :postId and OPR.orgDesignationMst.dsgnId = ODM.dsgnId ");*/

			//lSBQuery.append(" SELECT upper(ODM.DSGN_SHRT_NAME) FROM Org_Post_Details_Rlt OPR,Org_Designation_Mst ODM WHERE "); 
			lSBQuery.append(" SELECT upper(ODM.DSGN_NAME) FROM Org_Post_Details_Rlt OPR,Org_Designation_Mst ODM WHERE "); // Changes DSGN_NAME in place of DSGN_SHRT_NAME
			lSBQuery.append(" OPR.post_Id = '"+lLngPostId+"' and OPR.dsgn_Id = ODM.dsgn_Id ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("postId", lLngPostId);

			lLstDsgnName = lQuery.list();
			if(!lLstDsgnName.isEmpty())
				lStrDsgnName = lLstDsgnName.get(0);

		} catch (Exception e) {
			logger.error("Exception in getEmployerDsgnName of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrDsgnName;
	}


	public String getEmployerOfficeName(String lStrDDOCode) {

		logger.error("lStrDDOCode"+lStrDDOCode);
		List<String> lLstOfficeName = null;
		String lStrOfficeName = "";
		try {
			StringBuilder lSBQuery = new StringBuilder();	

			//gokarna 13-08-2015 for correct ddo office main office selected.
			//			lSBQuery.append("SELECT dcpsDdoOfficeName FROM DdoOffice WHERE dcpsDdoCode = :ddoCode ");	

			lSBQuery.append("SELECT  nvl(OFF_NAME,0) FROM  mst_dcps_ddo_office where DDO_CODE = :ddoCode and ddo_office = 'Yes' ");	

			//
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("ddoCode", lStrDDOCode);

			lLstOfficeName = lQuery.list();
			if(!lLstOfficeName.isEmpty())
				lStrOfficeName = lLstOfficeName.get(0).toString().trim();
			logger.error("lStrOfficeName"+lLstOfficeName.get(0).toString());
			//logger.error("lStrOfficeName"+lLstOfficeName.get(1));
			//logger.error("lStrOfficeName"+lLstOfficeName.get(2));

		} catch (Exception e) {
			logger.error("Exception in getEmployerOfficeName of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrOfficeName;
	}

	//work on it
	public String getTreasuryNameOfEmp(String lStrDDOCode) {
		List<String> lLstTreasuryName = null;
		String lStrTreasuryName = "";
		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT CLM.locName FROM RltDdoOrg RDO,CmnLocationMst CLM WHERE RDO.ddoCode = :ddoCode AND RDO.locationCode = CLM.locId");			
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("ddoCode", lStrDDOCode);

			lLstTreasuryName = lQuery.list();
			if(!lLstTreasuryName.isEmpty())
				lStrTreasuryName = lLstTreasuryName.get(0);

		} catch (Exception e) {
			logger.error("Exception in getTreasuryNameOfEmp of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrTreasuryName;
	}


	public Date getEmpRetirmentDate(String lStrSevaarthId) {
		List<Date> lLstDOR  = null;
		Date lDtDOR = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT OEM.empSrvcExp FROM MstEmp ME,OrgEmpMst OEM WHERE ME.sevarthId = :sevaarthId AND OEM.empId = ME.orgEmpMstId");			
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("sevaarthId", lStrSevaarthId);

			lLstDOR = lQuery.list();
			if(!lLstDOR.isEmpty())
				lDtDOR = lLstDOR.get(0);

		} catch (Exception e) {
			logger.error("Exception in getEmpRetirmentDate of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDtDOR;
	}	
	
    //swt 09/07/2020
	public Double getLatestSubscription(String lStrGpfAccNo,String lStrSevaarthId)
	//public Double getLatestSubscription(String lStrGpfAccNo)
	{
		Double lDblRedularSubscription = null;
		List lLstData = null;
		Date lObjDate = new Date();
		Integer lIntMonth = lObjDate.getMonth()+1;

		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT monthlySubscription FROM MstGpfEmpSubscription ");
			lSBQuery.append("WHERE gpfAccNo = :gpfAccNo AND statusFlag = 'A' AND effectFromMonth <= :curMonth ");
			lSBQuery.append("ORDER BY finYearId, effectFromMonth DESC");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo",	lStrGpfAccNo);
			lQuery.setParameter("curMonth",	lIntMonth);

			lLstData = lQuery.list();
			if(lLstData != null && lLstData.size() > 0){
				lDblRedularSubscription = (Double) lLstData.get(0);
			}else{
				StringBuilder lSBQueryAcc = new StringBuilder();	
				//lSBQueryAcc.append("SELECT monthlySubscription FROM MstEmpGpfAcc WHERE gpfAccNo = :gpfAccNo ");

				lSBQueryAcc.append("SELECT monthlySubscription FROM MstEmpGpfAcc WHERE gpfAccNo = :gpfAccNo AND sevaarthId=:sevaarthId ");
				Query lQueryAcc = ghibSession.createQuery(lSBQueryAcc.toString());
				lQueryAcc.setParameter("gpfAccNo", lStrGpfAccNo);
				lQueryAcc.setParameter("sevaarthId", lStrSevaarthId);

				lLstData = lQueryAcc.list();
				if(lLstData != null && lLstData.size() > 0){
					lDblRedularSubscription = (Double) lLstData.get(0);
				}
			}

		} catch (Exception e) {
			logger.error("Exception in getLatestSubscription of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDblRedularSubscription;
	}


	public String getSevaarthIdFromName(String lStrName)
	{
		String lStrSevaarthId = "";
		List lLstData = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT sevarthId FROM MstEmp WHERE UPPER(name) = UPPER(:name)");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("name", lStrName);
			lLstData = lQuery.list();

			if(lLstData != null && lLstData.size() > 0){
				lStrSevaarthId = lLstData.get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getSevaarthIdFromName of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrSevaarthId;
	}
	public String getGradePayFrmSevaarthId(String sevarthId)
	{
		String lStrGrdPay = "";
		List lLstData = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT gradePay  FROM MstEmp WHERE sevarthId = :sevarthId");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("sevarthId", sevarthId);
			lLstData = lQuery.list();

			if(lQuery != null && lLstData != null && lLstData.size() > 0){
				if(lLstData.get(0) != null)
					lStrGrdPay = lLstData.get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getGradePayFrmSevaarthId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrGrdPay;
	}

	public Long getAdvanceHistoryByAdvanceType(String lStrGpfAccNo, Long lLngYearId,String advanceType) {
		List advanceHistoryList = new ArrayList();
		Long amount = null;
		StringBuilder lSBQuery = new StringBuilder();
		try {
			/*lSBQuery.append("SELECT advanceType, SUM(amountSanctioned)");
			lSBQuery.append(" FROM MstGpfAdvance");
			lSBQuery
					.append(" WHERE gpfAccNo = :gpfAccNo AND finYearId = :finYearId AND statusFlag = 'A' GROUP BY advanceType ORDER BY advanceType");
			 */
			lSBQuery.append(" SELECT sum(adv.AMOUNT_SANCTIONED) FROM IFMS.MST_GPF_ADVANCE adv ");
			lSBQuery.append(" where adv.GPF_ACC_NO = :gpfAccNo and adv.FIN_YEAR_ID = :finYearId and adv.STATUS_FLAG = 'A' and ");
			lSBQuery.append(" ( adv.TRANSACTION_ID in (SELECT bill.TRANSACTION_ID FROM MST_GPF_BILL_DTLS bill  ");
			lSBQuery.append("  where bill.GPF_ACC_NO = :gpfAccNo  and  bill.STATUS_FLAG  = 1) or adv.DATA_ENTRY =1  ) and adv.ADVANCE_TYPE = 'NRA' ");
			lSBQuery.append("  group by adv.ADVANCE_TYPE order by adv.ADVANCE_TYPE ");

			logger.info(" in getAdvanceHistory  : "+lSBQuery+" lStrGpfAccNo "+lStrGpfAccNo+" lLngYearId "+lLngYearId);
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("finYearId", lLngYearId);

			advanceHistoryList = lQuery.list();


			if (advanceHistoryList != null && advanceHistoryList.size() > 0) {
				Object[] historyObj = (Object[]) advanceHistoryList.get(0);

				amount = (Long) historyObj[0];

			}
		} catch (Exception e) {
			logger.error("Exception in getAdvanceHistory of GPFRequestProcessDAOImpl  : ", e);			
		}
		logger.info("amount"+amount);
		return amount;
	}

	public String getEmployerNameFrmPostId(String postId)
	{
		String lStrGrdPay = "";
		List lLstData = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT emp.EMP_FNAME ||' '||emp.emp_mname||' '||emp.emp_lname FROM org_emp_mst emp inner join  ORG_USERPOST_RLT post ");
			lSBQuery.append(" on emp.USER_ID = post.user_id and post.ACTIVATE_FLAG = 1 ");
			lSBQuery.append(" where post.POST_ID = :postId ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("postId", postId);
			lLstData = lQuery.list();

			if(lQuery != null && lLstData != null && lLstData.size() > 0){
				if(lLstData.get(0) != null)
					lStrGrdPay = lLstData.get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getEmployerNameFrmPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrGrdPay;
	}

	public String getPurposeNameFrmId(String lookUpID)
	{
		String lStrPurposeName = "";
		List lLstData = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT LOOKUP_NAME FROM CMN_LOOKUP_MST where LOOKUP_ID = :lookUpId ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lookUpId", lookUpID);
			lLstData = lQuery.list();

			if(lQuery != null && lLstData != null && lLstData.size() > 0){
				if(lLstData.get(0) != null)
					lStrPurposeName = lLstData.get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getPurposeNameFrmId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrPurposeName;
	}



	public String getPrpsFrmTransactionId(String transactionId)
	{
		String lStrPurposeName = "";

		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT look.LOOKUP_NAME FROM IFMS.MST_GPF_ADVANCE gpf inner join CMN_LOOKUP_MST look ");
			lSBQuery.append(" on gpf.PURPOSE_CATEGORY = look.LOOKUP_ID where TRANSACTION_ID =  :transactionId ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("transactionId", transactionId);


			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){

				lStrPurposeName = lQuery.list().get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getPrpsFrmTransactionId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrPurposeName;
	}

	public List getDDODeptDetails(String empSevaarthCode){
		logger.error("getDDODeptDetails"+empSevaarthCode);
		List ddoDeptDtls = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT ddo.DDO_CODE,loc.LOC_NAME,ddo.ddo_name,ddo.DDO_OFFICE,ofc.ADDRESS1,ofc.EMAIL,ofc.TEL_NO1 ");
			lSBQuery.append(" FROM org_ddo_mst ddo inner join mst_dcps_emp emp on ddo.DDO_CODE = emp.DDO_CODE ");
			lSBQuery.append(" inner join CMN_LOCATION_mst loc on loc.LOC_ID = ddo.DEPT_LOC_CODE ");
			lSBQuery.append(" inner join MST_DCPS_DDO_OFFICE ofc on ofc.DDO_CODE = ddo.DDO_CODE and ofc.DDO_OFFICE = 'Yes' ");
			lSBQuery.append(" where emp.SEVARTH_ID = :sevaarthID  ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("sevaarthID", empSevaarthCode);


			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){

				ddoDeptDtls = lQuery.list();
			}
			logger.error("lSBQuery:::::::::"+lSBQuery.toString());
			logger.error("ddoDeptDtls;,lokl:::::::::"+ddoDeptDtls.size());


		}catch (Exception e) {
			logger.error("Exception in getDDODeptDetails of GPFRequestProcessDAOImpl  : ", e);			
		}
		return ddoDeptDtls;
	}

	public Date getApplicationDateFrmTranId(String trasanId){
		Date appDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT ad.applicationDate FROM MstGpfAdvance ad where ad.transactionId = :transactionID ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				appDate = (Date)lQuery.list().get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return appDate; 
	}
	public String getSanctionedDateFrmTranId(String trasanId){
		String appDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT fin.FIN_YEAR_DESC from SGVC_FIN_YEAR_MST fin , MST_GPF_ADVANCE adv where  fin.FIN_YEAR_ID=adv.FIN_YEAR_ID  and TRANSACTION_ID='"+trasanId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				appDate = lQuery.list().get(0).toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return appDate; 
	}
	public String getSanctionedDateFrmTranId1(String trasanId){
		String appDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT TO_CHAR(ADV.SANCTIONED_DATE,'dd/MM/yyyy') from SGVC_FIN_YEAR_MST fin , MST_GPF_ADVANCE adv where  fin.FIN_YEAR_ID=adv.FIN_YEAR_ID  and TRANSACTION_ID='"+trasanId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				appDate = lQuery.list().get(0).toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return appDate; 
	}


	public Date getFwApplicationDateFrmTranId(String trasanId){
		Date FwappDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT fw.applicationDate FROM TrnGpfFinalWithdrawal fw where fw.transactionId = :transactionID ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				FwappDate = (Date)lQuery.list().get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return FwappDate; 
	}



	//added by Kiranvir for 90% withdrawal
	public String getSpecialCase90(String trasanId){
		String NinetyFlag = "";
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT NVL(mst.SPECIAL_CASE_90_PERCENT,0) FROM Mst_Gpf_Advance mst where mst.transaction_Id = :transactionID ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				NinetyFlag = (String)lQuery.list().get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return NinetyFlag; 
	}
	
	//added by KAVITA for 75% withdrawal
		public String getSpecialCase75(String trasanId){
			String NinetyFlag = "";
			try{
				StringBuilder lSBQuery = new StringBuilder();			
				lSBQuery.append(" SELECT NVL(mst.SPECIAL_CASE_75_PERCENT,0) FROM Mst_Gpf_Advance mst where mst.transaction_Id = :transactionID ");
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lQuery.setParameter("transactionID", trasanId);			

				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					NinetyFlag = (String)lQuery.list().get(0);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}		
			return NinetyFlag; 
		}

		//added by KAVITA for 75% withdrawal draft
		/*public String getCurrDis(String sevaarthid){
			long NinetyFlag = 0l;
			try{
				StringBuilder lSBQuery = new StringBuilder();			
				lSBQuery.append("SELECT mst.CurrentDisamount FROM MstGpfAdvance mst where mst.sevaarthId = :sevaarthid and mst.statusFlag in ('D','F1','R','F2','F3','DR','RD') and mst.advanceType='NRA' ");
				Query lQuery = ghibSession.createQuery(lSBQuery.toString());
				lQuery.setParameter("sevaarthid", sevaarthid);			

				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					NinetyFlag =  Long.valueOf(lQuery.get(0).toString());
				}
				if (pay.size() != 0 && pay.get(0) != null) {
					lLngPaybillMonth = Long.valueOf(pay.get(0).toString());
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}		
			return NinetyFlag; 	
		}*/
		public Long getCurrDis(String sevaarthid){
			List advanceHistoryList = new ArrayList();
			Long amount = 0l;
			StringBuilder lSBQuery = new StringBuilder();
			try {
				//lSBQuery.append("SELECT NVL(mst.CurrentDisamount,0) FROM MstGpfAdvance mst where mst.sevaarthId = :sevaarthid and mst.statusFlag in ('D','F1','R','F2','F3','DR','RD') and mst.advanceType='NRA' ");
				lSBQuery.append(" SELECT NVL(mst.Current_Dis_amount,0) FROM Mst_Gpf_Advance mst where mst.sevaarth_Id = :sevaarthid  and mst.status_Flag in ('D','F1','R','F2','F3','DR','RD') and mst.advance_Type='NRA' ");
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lQuery.setParameter("sevaarthid", sevaarthid);	

				advanceHistoryList = lQuery.list();
				if (advanceHistoryList.size() != 0) {

					amount = Long.valueOf(advanceHistoryList.get(0).toString());

				}
				
			} catch (Exception e) {
				logger.error("Exception in getAdvanceHistory of GPFRequestProcessDAOImpl  : ", e);			
			}
			logger.info("amount"+amount);
			return amount;
		}
		
				public String getSpecialCase75Draft(String sevaarthid){
					String NinetyFlag = "";
					try{
						StringBuilder lSBQuery = new StringBuilder();			
						lSBQuery.append(" SELECT NVL(mst.SPECIAL_CASE_75_PERCENT,0) FROM Mst_Gpf_Advance mst where mst.sevaarth_Id = :sevaarthid and mst.status_Flag='D' and mst.advance_Type='NRA' ");
						Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
						lQuery.setParameter("sevaarthid", sevaarthid);			

						if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
							NinetyFlag = (String)lQuery.list().get(0);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}		
					return NinetyFlag; 
				}
				public String getSpecialCase90Draft(String sevaarthid){
					String NinetyFlag = "";
					try{
						StringBuilder lSBQuery = new StringBuilder();			
						lSBQuery.append(" SELECT NVL(mst.SPECIAL_CASE_90_PERCENT,0) FROM Mst_Gpf_Advance mst where mst.sevaarth_Id = :sevaarthid and mst.status_Flag='D' and mst.advance_Type='NRA' ");
						Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
						lQuery.setParameter("sevaarthid", sevaarthid);			

						if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
							NinetyFlag = (String)lQuery.list().get(0);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}		
					return NinetyFlag; 
				}
// END				

	//added by Kiranvir for dropdown
	public String getOtherPurpose(String trasanId){
		String otherPurpose = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT mst.otherPurpose FROM MstGpfAdvance mst where mst.transactionId = :transactionID ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				otherPurpose = (String)lQuery.list().get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return otherPurpose; 
	}

	//added by Kiranvir for deputation employees ie ddo code is null



	public String getEmployeeDdoCode(String lStrSevaarthId){

		logger.info("lStrSevaarthId"+lStrSevaarthId);
		List ddo = null;
		String lStrDdoCode = "";
		StringBuilder SBQuery = new StringBuilder();

		try{
			SBQuery.append( " SELECT ME.ddoCode " );
			SBQuery.append( " FROM MstEmpGpfAcc ME  " );
			SBQuery.append( " WHERE ME.sevaarthId  = :Id " );	

			Query selectQuery = ghibSession.createQuery(SBQuery.toString());
			selectQuery.setParameter("Id", lStrSevaarthId);

			ddo = selectQuery.list();

			if (ddo != null && ddo.size() > 0) {
				if(((String)ddo.get(0)) != null)
					lStrDdoCode = (String)ddo.get(0);
			}

		}
		catch(Exception e){
			logger.error("Exception in getEmployeeDdoCode of GPFRequestProcessDAOImpl  : ", e);	

		}

		return lStrDdoCode;

	}


	public List getDeputationEmployeeDetail(String lStrSevaarthId) throws Exception {

		logger.info("lStrSevaarthId fetched getDeputationEmployeeDetail"+lStrSevaarthId);

		List gpfDepEmpList = null;
		try {

			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery = null;
			lSBQuery.append("select ME.name,MEG.currentBalance,ME.gender,ME.basicPay,OM.dsgnName,MEG.monthlySubscription,");
			lSBQuery.append("MEG.gpfAccNo,ME.doj,ME.building_address,");
			lSBQuery.append("ME.building_street,ME.landmark,ME.district,ME.state,ME.pincode,ME.cntctNo,ME.cellNo,");
			lSBQuery.append("OEM.empSrvcExp,CLM.lookupName,ME.payScale,OEM.empId,ME.basicPay,ME.dob,MEG.sevaarthId,ME.payInPayBand,ME.gradePay ");
			lSBQuery.append(" FROM MstEmp ME,MstEmpGpfAcc MEG,OrgEmpMst OEM,OrgDesignationMst OM,CmnLookupMst CLM");
			lSBQuery.append(" WHERE CLM.lookupId = ME.payCommission and ME.dcpsEmpId = MEG.mstGpfEmpId and ME.dcpsOrGpf='N' and OEM.empId = ME.orgEmpMstId");
			lSBQuery.append(" and ME.designation = OM.dsgnId AND ME.group ='D' ");
			lSBQuery.append(" and ME.sevarthId=:lStrSevaarthId ");


			lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);

			gpfDepEmpList = lQuery.list();

			if (gpfDepEmpList != null && gpfDepEmpList.size() > 0) {
				logger.info("gpfDepEmpList not null");
				String lStrname = gpfDepEmpList.get(0).toString();
				logger.info("lStrname query"+lStrname);
			}





		} catch (Exception e) {
			logger.error("Exception in getDeputationEmployeeDetail of GPFRequestProcessDAOImpl  : ", e);			
			throw e;
		}
		return gpfDepEmpList;
	}


	public List getDeputationOfficeDetails(String trasanId){

		List deputation = null;

		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append("SELECT mst.deputation,mst.depoffname,mst.depoffaddress,mst.deplocation,mst.depdesignation FROM MstGpfAdvance mst where mst.transactionId = :transactionID ");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){

				deputation = lQuery.list();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return deputation; 
	}





	public String getDDODesignforOrder(String locationcode) {

		logger.info("locationcode"+locationcode);

		List ddo = null;
		String ddodesign = "";

		try {
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append( " SELECT d.DSGN_NAME FROM ORG_DDO_MST org,ORG_DESIGNATION_MST d " );
			lSBQuery.append( " where org.LOCATION_CODE=:locationcode and d.DSGN_ID=org.DSGN_CODE "  );

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("locationcode", locationcode);

			ddo = lQuery.list();

			if (ddo != null && ddo.size() > 0) {

				ddodesign = (String)ddo.get(0);
			}



		} catch (Exception e) {
			logger.error("Exception in getDDODesignforOrder of GPFRequestProcessDAOImpl  : ", e);			
		}
		return ddodesign;
	}


	/*public List getRecoveryDetails(String lStrGpfaccno, Long lLngFinyrId){

	List recovery = null;
	logger.info("getRecoveryDetails called");
	logger.info("lStrGpfaccno"+lStrGpfaccno);
	logger.info("lLngFinyrId"+lLngFinyrId);

	try{
		StringBuilder lSBQuery = new StringBuilder();			

		lSBQuery.append(" SELECT monthMst.MONTH_ID,paybill.GPF_ADV ");
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID ");
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 ");
		lSBQuery.append(" left join MST_GPF_ADVANCE advance on emp.GPF_ACC_NO = advance.GPF_ACC_NO and month(advance.APPLICATION_DATE) = head.PAYBILL_MONTH and advance.STATUS_FLAG = 'A' ");
		lSBQuery.append(" inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID = advance.TRANSACTION_ID and bill.STATUS_FLAG = 1 ");
		lSBQuery.append(" inner join SGVA_MONTH_MST monthMst on monthMst.MONTH_ID = head.PAYBILL_MONTH ");
		lSBQuery.append(" inner join SGVC_FIN_YEAR_MST yearmst on yearmst.FIN_YEAR_ID = advance.FIN_YEAR_ID ");
		lSBQuery.append(" where emp.GPF_ACC_NO =:GPF_ACC_NO  and yearmst.FIN_YEAR_ID =:FIN_YEAR_ID order by monthMst.MONTH_ID ");



		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		lQuery.setParameter("GPF_ACC_NO", lStrGpfaccno);	
		lQuery.setParameter("FIN_YEAR_ID", lLngFinyrId);	

		if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){

			recovery = lQuery.list();
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}		
	return recovery; 
}*/

	public List getLoanDetails(String lStrSevaarthid,int cueerentYr,int nextYear){

		logger.info("getLoanDetails lStrSevaarthid"+lStrSevaarthid);

		List loandetails = null;
		String EmpName = "";
		StringBuilder SBQuery = new StringBuilder();

		try{
			//already commented 
			/*SBQuery.append( " SELECT loan.LOAN_DATE,loan.LOAN_PRIN_AMT " ); 
		SBQuery.append( " FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID " ); 
		SBQuery.append( " inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID " );
		SBQuery.append( " inner join HR_LOAN_EMP_DTLS loan on eis.EMP_ID = loan.EMP_ID " );
		SBQuery.append( " where loan.LOAN_ACTIVATE_FLAG=1 and loan.LOAN_TYPE_ID=55 and emp.GPF_ACC_NO=:GPF_ACC_NO" );
			 */





			// commented by  Gokarna  for correct principle amount in ddo ast --> employee --> loan Details . on 11/09/2015
			//13-05-2016 for geeting finncial wise value of ad
			/*
        SBQuery.append( " SELECT loan.LOAN_DATE,loan.LOAN_PRIN_AMT "); 
		SBQuery.append( " FROM mst_dcps_emp dcps inner join hr_eis_emp_mst EIS on dcps.ORG_EMP_MST_ID=EIS.EMP_MPG_ID " ); 
		SBQuery.append( " INNER JOIN HR_LOAN_EMP_DTLS loan ON loan.EMP_ID=EIS.EMP_ID " );
		SBQuery.append(" WHERE dcps.SEVARTH_ID=:lStrSevaarthid AND loan.LOAN_ACTIVATE_FLAG=1 AND loan.LOAN_TYPE_ID=55 " );

			 */


			SBQuery.append(" SELECT loan.LOAN_DATE ,loan.LOAN_PRIN_AMT ");
			SBQuery.append(" FROM mst_dcps_emp dcps inner join hr_eis_emp_mst EIS on dcps.ORG_EMP_MST_ID=EIS.EMP_MPG_ID ");
			SBQuery.append(" INNER JOIN HR_LOAN_EMP_DTLS loan ON loan.EMP_ID=EIS.EMP_ID "); 
			SBQuery.append(" WHERE dcps.SEVARTH_ID= '"+lStrSevaarthid+"' AND loan.LOAN_ACTIVATE_FLAG=1 AND loan.LOAN_TYPE_ID=55 ");
			SBQuery.append(" AND ((to_char(loan.VOUCHER_DATE,'yyyy')= '"+cueerentYr+"' AND to_char(loan.VOUCHER_DATE,'MM')>3 ) OR (to_char(loan.VOUCHER_DATE,'yyyy')= '"+nextYear+"' AND to_char(loan.VOUCHER_DATE,'MM')<4 )) ");






			/*SBQuery.append(" SELECT  loan.LOAN_DATE,mgf.AMOUNT_SANCTIONEDq FROM mst_dcps_emp dcps "); 
		SBQuery.append(" inner join hr_eis_emp_mst EIS on dcps.ORG_EMP_MST_ID=EIS.EMP_MPG_ID ");  
		SBQuery.append(" INNER JOIN HR_LOAN_EMP_DTLS loan ON loan.EMP_ID=EIS.EMP_ID ");
		SBQuery.append(" inner join TRN_EMP_GPF_ACC trn on trn.sevaarth_id = dcps.sevarth_id "); 
		SBQuery.append(" inner join mst_emp_gpf_acc mega on mega.SEVAARTH_ID=dcps.SEVARTH_ID ");
		SBQuery.append(" inner join mst_gpf_advance mgf on mgf.GPF_ACC_NO= mega.GPF_ACC_NO ");
		SBQuery.append(" inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID= mgf.TRANSACTION_ID ");
		SBQuery.append(" WHERE dcps.SEVARTH_ID=:lStrSevaarthid AND loan.LOAN_ACTIVATE_FLAG=1 AND loan.LOAN_TYPE_ID=55 order by mgf.TRANSACTION_ID desc fetch first row only ");
			 */







			Query lQuery = ghibSession.createSQLQuery(SBQuery.toString());

			//lQuery.setParameter("lStrSevaarthid", lStrSevaarthid);

			loandetails = lQuery.list();
		}
		catch(Exception e){
			logger.error("Exception in getLoanDetails of GPFLedgerReportQueryDAOImpl  : ", e);	

		}

		return loandetails;
	}

	public Long getPaybillMonth(String lStrSevaarthId,Long lLngCurrYr){

		Long lLngPaybillMonth = 0l;
		List pay = null;

		logger.error("inside getPaybillMonth");
		logger.error("lLngCurrYr"+lLngCurrYr);

		try {
			getSession();
			StringBuilder lSBQuery = new StringBuilder();

			lSBQuery.append(" SELECT max(head.paybill_month) " ); 
			lSBQuery.append("FROM paybill_head_mpg head inner join hr_pay_paybill pay on head.paybill_id=pay.PAYBILL_GRP_ID " ); 
			lSBQuery.append("inner join hr_eis_emp_mst eis on eis.emp_id=pay.emp_id " ); 
			lSBQuery.append("inner join mst_dcps_emp dcps on dcps.org_emp_mst_id = eis.EMP_MPG_ID " ); 
			lSBQuery.append("where pay.paybill_year =:lLngCurrYr and head.approve_flag=1 and dcps.sevarth_id=:lStrSevaarthId " ); 

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
			lQuery.setParameter("lLngCurrYr", lLngCurrYr);

			pay = lQuery.list();

			if (pay.size() != 0 && pay.get(0) != null) {
				lLngPaybillMonth = Long.valueOf(pay.get(0).toString());
			}

			logger.error("lLngRecovery from query:"+lLngPaybillMonth);
		} catch (Exception e) {
			logger.error("Exception in getPaybillMonth of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLngPaybillMonth;

	}

	/* Above method is commented by Akshay on 04/07/2017 for correction in GPF Recovery of amount
	 * and below method is get value of Recovery of amount from MST_GPF_INTEREST_DTLS */

	public String getRecoveryAmt(String lStrGpfaccNo,Long lLngCurrYear,String lStrSevaarthId,Long lLngCurrMonth){
		logger.error("inside getRecoveryAmt dao method");
		logger.error("lStrSevaarthId"+lStrSevaarthId);
		logger.error("lLngCurrMonth"+lLngCurrMonth);
		logger.error("lLngCurrYear"+lLngCurrYear);
		String lStrRecAmnt = "";
		Long lStrTemp = 0L;
		List Data=null;
		List<Object> lLstReturnList = null;
		logger.error("getRecoveryAmt lLngCurrMonth::::::::"+lLngCurrMonth);
	/*	if(lLngCurrMonth < 4){
			lLngCurrYear = lLngCurrYear -1;
		}
		Long lLngNxtyr = lLngCurrYear + 1;
		
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;*/
		
		
		try {
		StringBuilder lSBQueryy = new StringBuilder();
		String isSevenpc="";
		lSBQueryy.append(" SELECT SIXPAYAMOUNT_09_10,SIXPAYAMOUNT_10_11,SIXPAYAMOUNT_11_12,SIXPAYAMOUNT_12_13,SIXPAYAMOUNT_13_14,SEVENPAYAMOUNT_16_17,SEVENPAYAMOUNT_17_18,SEVENPAYAMOUNT_18_19,SEVENPAYAMOUNT_19_20,SEVENPAYAMOUNT_20_21,IS_SEVENPC from MST_GPF_YEARLY  where ");
		//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");
		lSBQueryy.append("  Sevaarth_Id='"+lStrSevaarthId+"' order by updated_date DESC fetch first rows only ");

		Query lQueryy = ghibSession.createSQLQuery(lSBQueryy.toString());

		//lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
	//lQuery.setParameter("FinyrId", FinyrId);

		 
		List sixpay = lQueryy.list();
		if (sixpay != null && sixpay.size() > 0) {

			Object[] lArrObj = (Object[]) sixpay.get(0);

			if (lArrObj[10] != null) {
				isSevenpc = lArrObj[10].toString();
				logger
						.info("isSevenpc:::::::::"
								+ isSevenpc);
			}
		}
		List FinYearList = null;
		
		
		if(lLngCurrMonth < 4){
			lLngCurrYear = lLngCurrYear -1;
		}
		Long lLngNxtyr = lLngCurrYear + 1;
		
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		if(isSevenpc.equalsIgnoreCase("Y")){
		lSBQuery.append(" SELECT Cast(nvl(MGID.RECOVERY_OF_ADVANCE,0) as varchar(20)) FROM MST_GPF_7PC_INTEREST_DTLS MGID ");
		lSBQuery.append(" join MST_EMP_GPF_ACC MEGA on MEGA.GPF_ACC_NO = MGID.GPF_ACC_NO  and MEGA.SEVAARTH_ID=MGID.sevaarth_id "); 
		lSBQuery.append(" join SGVC_FIN_YEAR_MST SFYM on SFYM.FIN_YEAR_ID = MGID.FIN_YEAR_ID  ");
		lSBQuery.append(" where MEGA.SEVAARTH_ID='"+lStrSevaarthId+"' and SFYM.FIN_YEAR_CODE='"+lLngCurrYear+"' and MGID.STATUS IS NULL ");
		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		Data = lQuery.list();
		if(Data !=null && Data.size()>0){
			for(int  i =0 ; i < Data.size() ; i++  ){
				lStrTemp = lStrTemp + Long.parseLong(Data.get(i).toString());	
			}				
		}
		lStrRecAmnt = lStrTemp.toString();
		}
		else{
			lSBQuery.append(" SELECT Cast(nvl(MGID.RECOVERY_OF_ADVANCE,0) as varchar(20)) FROM MST_GPF_INTEREST_DTLS MGID ");
			lSBQuery.append(" join MST_EMP_GPF_ACC MEGA on MEGA.GPF_ACC_NO = MGID.GPF_ACC_NO  and MEGA.SEVAARTH_ID=MGID.sevaarth_id "); 
			lSBQuery.append(" join SGVC_FIN_YEAR_MST SFYM on SFYM.FIN_YEAR_ID = MGID.FIN_YEAR_ID  ");
			lSBQuery.append(" where MEGA.SEVAARTH_ID='"+lStrSevaarthId+"' and SFYM.FIN_YEAR_CODE='"+lLngCurrYear+"' and MGID.STATUS IS NULL ");
			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			Data = lQuery.list();
			if(Data !=null && Data.size()>0){
				for(int  i =0 ; i < Data.size() ; i++  ){
					lStrTemp = lStrTemp + Long.parseLong(Data.get(i).toString());	
				}				
			}
			lStrRecAmnt = lStrTemp.toString();
			}
		
		}catch(Exception e){
			
		}
		return lStrRecAmnt;
		
/*		lSBQuery.append(" select temp.x,temp.month,temp.GRP_D,temp.ADV_GRP_D,temp.y,temp.year from (SELECT case when paybill.PAYBILL_MONTH< 12 then cast((paybill.PAYBILL_MONTH+1) as varchar) when paybill.PAYBILL_MONTH= 12 then cast(1 as varchar) end as x,month.month_name as month,  ");
		lSBQuery.append(" paybill.GPF_GRP_D as GRP_D ,paybill.GPF_ADV_GRP_D as ADV_GRP_D , (paybill.GPF_GRP_D+paybill.GPF_ADV_GRP_D) as y ,paybill.PAYBILL_YEAR as year  ");
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID   ");
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID   ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID   ");
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1  ");
		lSBQuery.append(" inner join SGVA_MONTH_MST month on month.MONTH_ID = case when ((paybill.PAYBILL_MONTH+1)=13) then cast(1 as varchar) else (paybill.PAYBILL_MONTH+1) end  ");
		lSBQuery.append(" where emp.SEVAARTH_ID ='"+lStrSevaarthId+"' AND paybill.PAYBILL_YEAR = '"+lLngCurrYear+"' AND paybill.PAYBILL_MONTH=3 ");
		lSBQuery.append(" union all ");
		lSBQuery.append(" SELECT case when paybill.PAYBILL_MONTH< 12 then cast((paybill.PAYBILL_MONTH+1) as varchar) when paybill.PAYBILL_MONTH= 12 then cast(1 as varchar) end as x,month.month_name as month,  ");
		lSBQuery.append(" paybill.GPF_GRP_D as GRP_D ,paybill.GPF_ADV_GRP_D as ADV_GRP_D , (paybill.GPF_GRP_D+paybill.GPF_ADV_GRP_D) as y ,paybill.PAYBILL_YEAR as year ");
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID   ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID   ");
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1  ");
		lSBQuery.append(" inner join SGVA_MONTH_MST month on month.MONTH_ID = case when ((paybill.PAYBILL_MONTH+1)=13) then cast(1 as varchar) else (paybill.PAYBILL_MONTH+1) end  ");
		lSBQuery.append(" where emp.SEVAARTH_ID ='"+lStrSevaarthId+"'  ");
		lSBQuery.append(" AND ((paybill.PAYBILL_YEAR = '"+lLngCurrYear+"' AND paybill.PAYBILL_MONTH>3 ) or (paybill.PAYBILL_YEAR = '"+lLngNxtyr+"' AND paybill.PAYBILL_MONTH<4)) ) as temp ");
		lSBQuery.append(" order by temp.year ");
		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		Data = lQuery.list();
		if (Data != null && Data.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			for (int liCtr = 0; liCtr < Data.size(); liCtr++) {
				obj = (Object[]) Data.get(liCtr);
				lStrTemp  = lStrTemp +  Long.parseLong(obj[3].toString());
			}
		} 
		lStrRecAmnt = lStrTemp.toString();
		return lStrRecAmnt;*/

	}

	/*public String getRecoveryAmt(String lStrGpfaccNo,Long lLngCurrYear,String lStrSevaarthId,Long lLngCurrMonth){
		logger.error("getRecoveryAmt::::::"+lStrGpfaccNo+lLngCurrYear+lStrSevaarthId+lLngCurrMonth);
		Long lLngRecovery = 0l;
		List Rec = null;

		ArrayList year = new ArrayList();

	year.add(lLngCurrYear);
	year.add(lLngPrevYr);


		logger.error("inside getRecoveryAmt");
		logger.error("lStrGpfaccNo"+lStrGpfaccNo);
		logger.error("lLngCurrYear"+lLngCurrYear);
		logger.error("lStrSevaarthId"+lStrSevaarthId);
		logger.error("lLngPaybillMonth"+lLngCurrMonth);

		//try {
		getSession();

		//StringBuilder lSBQuery = new StringBuilder();
		//it was alreday commented query
		lSBQuery.append(" SELECT recover.TOTAL_RECOVERED_AMT " );
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID " ); 
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID  " );
		lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID " );
		lSBQuery.append(" inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID=loan.EMP_LOAN_ID " );
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID " );
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 " );
		lSBQuery.append(" where emp.GPF_ACC_NO =:lStrGpfaccNo and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG=1 " );
		lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR in (:lLstYear) " );
		lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) desc ");

		//new query
		SELECT paybill.GPF_ADV_GRP_D
		FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID 
		inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID 
		inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID 
		--inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID=loan.EMP_LOAN_ID 
		inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID 
		inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 and head.paybill_month =3
		where emp.GPF_ACC_NO ='TEST/123' and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG=1 and mst.sevarth_id ='CCGHTY76GTY'
		AND PAYBILL.PAYBILL_YEAR =2016
		order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint)

		//by gokarna  08-03-2016

		lSBQuery.append(" SELECT recover.TOTAL_RECOVERED_AMT " );
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID " );
		lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID " );
		lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID " );
		lSBQuery.append(" inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID=loan.EMP_LOAN_ID " );
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID " );
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 and head.paybill_month =:lLngPaybillMonth " );
		lSBQuery.append(" where emp.GPF_ACC_NO =:lStrGpfaccNo and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG=1 and mst.sevarth_id =:lStrSevaarthId " );
		lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR =:lLngCurrYear " );
		lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) " );


		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lQuery.setParameter("lStrGpfaccNo", lStrGpfaccNo);
		lQuery.setParameter("lLngCurrYear", lLngCurrYear);
		lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
		lQuery.setParameter("lLngPaybillMonth", lLngPaybillMonth);



		Rec = lQuery.list();

		if (Rec.size() != 0 && Rec.get(0) != null) {
			lLngRecovery = Long.valueOf(Rec.get(0).toString());
		}
		logger.error("query:getRecoveryAmt::::::::"+lSBQuery.toString());
		logger.error("lLngRecovery from query:"+lLngRecovery);
	} catch (Exception e) {
		logger.error("Exception in getRecoveryAmt of GPFRequestProcessDAOImpl  : ", e);			
	}


		//logger.error("inside getRegularSubscription dao method");
		//logger.error("SevaarthId"+SevaarthId);
		///logger.error("lLngCurrMonth"+lLngCurrMonth);
		//logger.error("lLngCurrYr"+lLngCurrYr);

		//String lStrSevaarthId = SevaarthId;
		List regular = null;


		String lStrRegSub = "";
		String lStrRegsub = "";
		String lStrRegSubJantoMarch = "";
		String lStrRegSubAprtoDec = "";
		Long lLngTotal = 0l;
		Long lLngTotalJantoMarch = 0l;
		Long lLngTotalAprtoDec = 0l;

		logger.error("getRegularSubscription lLngCurrMonth::::::::"+lLngCurrMonth);
		if(lLngCurrMonth==1)
		{
			logger.error("in Iff");
			lLngCurrMonth=12l;
			logger.error("in Iff"+lLngCurrMonth);
		}
		else
		{
			logger.error("in else");
			lLngCurrMonth=lLngCurrMonth-1;
			logger.error("in else"+lLngCurrMonth);
		}

		//if(lLngCurrMonth > 2){ change on 24/12/2015
		if(lLngCurrMonth > 3){
			logger.error("curr month > 3 is "+lLngCurrMonth);

			for(int x = 3; x <= lLngCurrMonth; x++){
				//for(int x = 4; x <= lLngCurrMonth; x++){
				logger.error("x"+x);


				StringBuilder lSBQuery = new StringBuilder();
				Query lQuery = null;


				//commented for correct regular subscription
				//lSBQuery.append(" SELECT sum(cast(deduct.EMP_DEDUC_AMOUNT as bigint)) ");
				lSBQuery.append(" SELECT nvl(paybill.GPF_ADV_GRP_D,0) ");
				lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
				lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID ");
				lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID ");
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 and head.paybill_month = '"+(x+1)+"' ");
				lSBQuery.append(" where emp.GPF_ACC_NO ='"+lStrGpfaccNo+"' and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG in (1,7) and mst.sevarth_id ='"+lStrSevaarthId+"' ");
				lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR = '"+lLngCurrYear+"' ");
				lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) FETCH FIRST ROW ONLY ");



				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				//lQuery.setParameter("lLngCurrYr", lLngCurrYr);
				//lQuery.setParameter("x", x);

				logger.error("lSBQuery"+lSBQuery.toString());


				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){

					lStrRegsub = lQuery.uniqueResult().toString();
					logger.error("lStrRegsub::::::::::"+lStrRegsub);

					if(lStrRegsub != null && lStrRegsub != "")
					{

						lLngTotal = lLngTotal + Long.valueOf(lStrRegsub);
						logger.error("lLngTotal::::::::::"+lLngTotal);

					}
				}

				logger.error("lStrRegsub"+lStrRegsub);
				logger.error("lLngTotal"+lLngTotal);
			}



			lStrRegSub = lLngTotal.toString();
			logger.error("lStrRegSub ::: final:::"+lStrRegSub);
		}

		//if(lLngCurrMonth <= 2){
		if(lLngCurrMonth <= 3){
			logger.error("curr month <= 3 is "+lLngCurrMonth);

			for(int x = 1; x <= lLngCurrMonth; x++){
				logger.error("x"+x);

				StringBuilder lSBQuery = new StringBuilder();
				Query lQuery = null;

				lSBQuery.append(" SELECT nvl(paybill.GPF_ADV_GRP_D,0) ");
				lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
				lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID ");
				lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID ");
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 and head.paybill_month = '"+(x+1)+"' ");
				lSBQuery.append(" where emp.GPF_ACC_NO ='"+lStrGpfaccNo+"' and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG in (1,7) and mst.sevarth_id ='"+lStrSevaarthId+"' ");
				lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR = '"+lLngCurrYear+"' ");
				lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) FETCH FIRST ROW ONLY ");



				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

				logger.error("lSBQuery"+lSBQuery.toString());

				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){
					lStrRegSubJantoMarch = lQuery.uniqueResult().toString();

					if(lStrRegSubJantoMarch != null && lStrRegSubJantoMarch != ""){
						lLngTotalJantoMarch = lLngTotalJantoMarch + Long.valueOf(lStrRegSubJantoMarch);
					}
					logger.error("lStrRegSubJantoMarch"+lStrRegSubJantoMarch);
					logger.error("Qury is::::::::::"+lSBQuery.toString());

				}
			}

			logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch);

			//Long lLngCurrYear1=lLngCurrYear-1;
			//for(int x = 3; x <= 12; x++){
			for(int x = 4; x <= 12; x++){
				logger.error("x"+x);

				StringBuilder lSBQuery = new StringBuilder();
				Query lQuery = null;
				lSBQuery.append(" SELECT nvl(paybill.GPF_ADV_GRP_D,0) ");
				lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
				lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID ");
				lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID ");
				lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID ");
				lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 and head.paybill_month = '"+(x+1)+"' ");
				lSBQuery.append(" where emp.GPF_ACC_NO ='"+lStrGpfaccNo+"' and loan.LOAN_TYPE_ID=55 and loan.LOAN_ACTIVATE_FLAG in (1,7) and mst.sevarth_id ='"+lStrSevaarthId+"' ");
				lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR =:lLngCurrYr ");
				lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) FETCH FIRST ROW ONLY ");



				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				//lQuery.setParameter("sevaarthId", lStrSevaarthId);
				lQuery.setParameter("lLngCurrYr", Long.parseLong(String.valueOf((Integer.valueOf(String.valueOf(lLngCurrYear)))-1)));
				//lQuery.setParameter("x", x);

				logger.error("Query is::::::"+lSBQuery.toString());

				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){
					lStrRegSubAprtoDec = lQuery.uniqueResult().toString();

					if(lStrRegSubAprtoDec != null && lStrRegSubAprtoDec != ""){
						lLngTotalAprtoDec = lLngTotalAprtoDec + Long.valueOf(lStrRegSubAprtoDec);
					}
					logger.error("lStrRegSubAprtoDec"+lStrRegSubAprtoDec);
				}
			}

			logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec);



			lStrRegSub = String.valueOf(lLngTotalAprtoDec + lLngTotalJantoMarch);

		}

		logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec);
		logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch);
		logger.error("lStrRegSub"+lStrRegSub);
		logger.error("lLngTotalAprtoDec"+lLngTotalAprtoDec.toString());
		logger.error("lLngTotalJantoMarch"+lLngTotalJantoMarch.toString());


		logger.error("lStrRegSub final value"+lStrRegSub);	

		return lStrRegSub;
	}*/

	/*public Long getRecoveryAmt(String lStrSevaarthId,Long lLngCurrMonth,Long lLngCurrYear){


	logger.info("inside getRecoveryAmt dao method");
	logger.info("lStrSevaarthId"+lStrSevaarthId);
	logger.info("lLngCurrMonth"+lLngCurrMonth);
	logger.info("lLngCurrYear"+lLngCurrYear);


	String lStrRecsub = "";
	String lStrRecSubJantoMarch = "";
	String lStrRecSubAprtoDec = "";
	Long lLngTotal = 0l;
	Long lLngTotalJantoMarch = 0l;
	Long lLngTotalAprtoDec = 0l;

	if(lLngCurrMonth > 2){
		logger.info("curr month > 2");

		for(int x = 3; x < lLngCurrMonth; x++){
			logger.info("x"+x);


			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery = null;



			lSBQuery.append(" SELECT recover.TOTAL_RECOVERED_AMT FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID = loan.EMP_LOAN_ID ");
			lSBQuery.append(" inner join HR_PAY_PAYBILL pay on pay.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = pay.PAYBILL_GRP_ID and pay.PAYBILL_MONTH = head.PAYBILL_MONTH ");
			lSBQuery.append(" and pay.PAYBILL_YEAR=head.PAYBILL_YEAR where emp.SEVARTH_ID =:lStrSevaarthId ");
			lSBQuery.append(" and loan.LOAN_TYPE_ID = 55 and loan.LOAN_ACTIVATE_FLAG = 1 and head.APPROVE_FLAG =1 ");
			lSBQuery.append(" and pay.PAYBILL_MONTH =:x and pay.PAYBILL_YEAR =:lLngCurrYear  ");



			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
			lQuery.setParameter("x", x);
			lQuery.setParameter("lLngCurrYear", lLngCurrYear);




				if(lQuery.list()!=null && lQuery.list().size()>0 && lQuery.uniqueResult() != null){

					lStrRecsub = lQuery.uniqueResult().toString();			

				if(lStrRecsub != null && lStrRecsub != ""){

				lLngTotal = lLngTotal + Long.valueOf(lStrRecsub);


		}
		}

				logger.info("lStrRegsub"+lStrRecsub);
				logger.info("lLngTotal"+lLngTotal);
		}



		//lStrRecSub = lLngTotal.toString();

		}


	logger.info("lLngTotal for >3"+lLngTotal);

	if(lLngCurrMonth < 3){
		logger.info("currmonth < 3");

		for(int x = 1; x < lLngCurrMonth; x++){
			logger.info("x"+x);

			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery = null;

			lSBQuery.append(" SELECT recover.TOTAL_RECOVERED_AMT FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID = loan.EMP_LOAN_ID ");
			lSBQuery.append(" inner join HR_PAY_PAYBILL pay on pay.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = pay.PAYBILL_GRP_ID and pay.PAYBILL_MONTH = head.PAYBILL_MONTH ");
			lSBQuery.append(" and pay.PAYBILL_YEAR=head.PAYBILL_YEAR where emp.SEVARTH_ID =:lStrSevaarthId ");
			lSBQuery.append(" and loan.LOAN_TYPE_ID = 55 and loan.LOAN_ACTIVATE_FLAG = 1 and head.APPROVE_FLAG =1 ");
			lSBQuery.append(" and pay.PAYBILL_MONTH =:x and pay.PAYBILL_YEAR =:lLngCurrYear  ");

			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
			lQuery.setParameter("x", x);
			lQuery.setParameter("lLngCurrYear", lLngCurrYear);

			if(lQuery != null){
				if(lQuery.uniqueResult() != null)
				lStrRecSubJantoMarch = lQuery.uniqueResult().toString();

				if(lStrRecSubJantoMarch != null){
				lLngTotalJantoMarch = lLngTotalJantoMarch + Long.parseLong(lStrRecSubJantoMarch);
				}
				logger.info("lStrRegSubJantoMarch"+lStrRecSubJantoMarch);
		}
		}

		logger.info("lLngTotalJantoMarch"+lLngTotalJantoMarch);


		for(int x = 3; x < 12; x++){
			logger.info("x"+x);

			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery = null;


			lSBQuery.append(" SELECT recover.TOTAL_RECOVERED_AMT FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID = loan.EMP_LOAN_ID ");
			lSBQuery.append(" inner join HR_PAY_PAYBILL pay on pay.EMP_ID = eis.EMP_ID ");
			lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = pay.PAYBILL_GRP_ID and pay.PAYBILL_MONTH = head.PAYBILL_MONTH ");
			lSBQuery.append(" and pay.PAYBILL_YEAR=head.PAYBILL_YEAR where emp.SEVARTH_ID =:lStrSevaarthId ");
			lSBQuery.append(" and loan.LOAN_TYPE_ID = 55 and loan.LOAN_ACTIVATE_FLAG = 1 and head.APPROVE_FLAG =1 ");
			lSBQuery.append(" and pay.PAYBILL_MONTH =:x and pay.PAYBILL_YEAR =:lLngCurrYear  ");

			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
			lQuery.setParameter("x", x);
			lQuery.setParameter("lLngCurrYear", lLngCurrYear);

			if(lQuery != null){
				if(lQuery.uniqueResult() != null)
				lStrRecSubAprtoDec = lQuery.uniqueResult().toString();

				if(lStrRecSubAprtoDec != null){
				lLngTotalAprtoDec = lLngTotalAprtoDec + Long.parseLong(lStrRecSubAprtoDec);
				}
				logger.info("lStrRegSubAprtoDec"+lStrRecSubAprtoDec);
		}
		}

		logger.info("lLngTotalAprtoDec"+lLngTotalAprtoDec);

		lLngTotal = lLngTotalAprtoDec + lLngTotalJantoMarch;



	}

	logger.info("lLngTotal final value"+lLngTotal);
	return lLngTotal;

}*/


	public String getPostId(String postid) {

		StringBuilder lSBQuery = new StringBuilder();
		List post = null;
		String lStrpost = "";
		String flag = "N";

		try {
			lSBQuery.append(" SELECT post.POST_ID FROM ACL_POSTROLE_RLT post where POST_ID=:postid ");
			lSBQuery.append(" where post.ACTIVATE_FLAG=1 and post.ROLE_ID=800001 ");

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("postid", postid);

			post = lQuery.list();
			lStrpost = post.get(0).toString();

			if(lStrpost != null && lStrpost != ""){
				flag = "Y";
			}


		} catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return flag;
	}

	//swt 01/06/2020 (SevaarthId added)
	//public List getSixPayAmounts(String lStrGpfAccNo,Long FinyrId) {
	public List getSixPayAmounts(String lStrGpfAccNo,Long FinyrId,String lStrSevaarthId) {
		logger.info("FinyrId:::"+FinyrId);
		String SevaarthId="";
		List sixpay = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {

			lSBQuery.append(" SELECT nvl(SIXPAYAMOUNT_09_10,0) ,nvl(SIXPAYAMOUNT_10_11,0),nvl(SIXPAYAMOUNT_11_12,0),nvl(SIXPAYAMOUNT_12_13,0),nvl(SIXPAYAMOUNT_13_14,0),nvl(SEVENPAYAMOUNT_16_17,0),nvl(SEVENPAYAMOUNT_17_18,0),nvl(SEVENPAYAMOUNT_18_19,0),nvl(SEVENPAYAMOUNT_19_20,0),nvl(SEVENPAYAMOUNT_20_21,0),IS_SEVENPC from MST_GPF_YEARLY  where GPF_ACC_NO='"+lStrGpfAccNo+"' ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");
			lSBQuery.append(" and Sevaarth_Id='"+lStrSevaarthId+"' order by updated_date DESC fetch first rows only ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
		lQuery.setParameter("FinyrId", FinyrId);

			 */
			sixpay = lQuery.list();
			
			/*if (sixpay == null && sixpay.size() == 0) {
				SevaarthId = sixpay.get(0).toString();
			}*/
			
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}

	public Double getAmtSanctioned(String lStrGpfAccno){

		List sanction = null;
		Double lDblSanctionedAmt = 0d;

		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append( " SELECT advance.payableAmount " ); 
			lSBQuery.append( " FROM MstGpfAdvance advance where advance.gpfAccNo=:lStrGpfAccno " );

			Query lQuery = ghibSession.createQuery(lSBQuery.toString());


			lQuery.setParameter("lStrGpfAccno", lStrGpfAccno);

			sanction = lQuery.list();
			if (sanction.size() != 0) {

				lDblSanctionedAmt = (Double)sanction.get(0);
			}



		} catch (Exception e) {
			logger.error("Exception in getAmtSanctioned : ", e);
		}
		return lDblSanctionedAmt;

	}

    //swt 01/06/2020(for SevaarthId)
	//public Double getWithdrawalSanc(String lStrGpfAccno,Long lLngFinyrId){
	public Double getWithdrawalSanc(String lStrGpfAccno,Long lLngFinyrId,String lStrSevaarthId){

		List with = null;
		Double lDblWithAmt = 0d;

		StringBuilder lSBQuery = new StringBuilder();

		try {

			/*lSBQuery.append( " SELECT sum(GPF.amountSanctioned) FROM MstGpfAdvance GPF, MstGpfBillDtls BILL WHERE GPF.gpfAccNo=BILL.gpfAccNo " );
		lSBQuery.append( " AND GPF.gpfAccNo=:lStrGpfAccno AND GPF.statusFlag='A' AND GPF.advanceType='NRA' " );
		lSBQuery.append( " AND GPF.finYearId=:lLngFinyrId AND BILL.statusFlag=1 " );*/

			//COMMENTED by Gokarna -- 05/05/2016 -- for getting current disbursement aount as sanction amount it is for all subtypes . sum 


			/*lSBQuery.append(" SELECT amountSanctioned ");
		lSBQuery.append(" FROM MstGpfAdvance");
		lSBQuery.append(" WHERE gpfAccNo=:lStrGpfAccno AND installmentsLeft>0 AND statusFlag IN ('A','AC') AND advanceType='NRA' and finYearId=:lLngFinyrId");
			 */



			lSBQuery.append(" SELECT cast(sum(DIS_AMOUNT) as double) FROM IFMS.MST_GPF_DISBURSE_DETAILS dis inner join  ");
			lSBQuery.append(" (SELECT COUNT(1) as cnt,MIN(MST_GPF_ADVANCE_ID) as gadv_id FROM IFMS.MST_GPF_ADVANCE  ADV ");
			lSBQuery.append(" INNER JOIN MST_GPF_BILL_DTLS BILL ON  ADV.ORDER_NO= BILL.ORDER_NO AND BILL.STATUS_FLAG= 1 ");
			lSBQuery.append(" WHERE ADV.GPF_ACC_NO='"+lStrGpfAccno+"' AND ADV.Sevaarth_Id='"+lStrSevaarthId+"'  AND ADV.STATUS_FLAG= 'A' AND ADV.ADVANCE_TYPE='NRA' AND  ADV.FIN_YEAR_ID = '"+lLngFinyrId+"' GROUP BY ADV.PURPOSE_CATEGORY  ) tmp on dis.MST_GPF_ADV_ID =tmp.gadv_id and dis.DIS_NUMBER between 1 and tmp.cnt  "); //and ADV.FIN_YEAR_ID = 32 ADD BY KAVITA


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());


			//	lQuery.setParameter("lStrGpfAccno", lStrGpfAccno);
			//lQuery.setParameter("lLngFinyrId", lLngFinyrId);
			logger.info("Query is::::::::"+lSBQuery.toString());


			with = lQuery.list();
			if (with.size() != 0) {

				lDblWithAmt = (Double)with.get(0);
			}



		} catch (Exception e) {
			logger.error("Exception in getWithdrawalSanc : ", e);
		}
		return lDblWithAmt;

	}
	@Override
	public List getEmployeePay(String empName,String sevaId) {
		List NomineDetails = new ArrayList();
		List flagList = new ArrayList();
		
		StringBuilder lSBQuery = new StringBuilder();
		
		List lLstReturnList = new ArrayList<Object>();
		try {
			lSBQuery.append(" SELECT ME.SEVEN_PC_BASIC,LVL.ID FROM Mst_dcps_Emp ME,ifms.RLT_PAYBAND_GP_STATE_7PC LVL WHERE ME.SEVEN_PC_LEVEL = LVL.LEVEL and  ");
			lSBQuery.append(" ME.emp_name = '"+empName+"' and ME.sevarth_id='"+sevaId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**** EMployeeNomineeDetails *******"+lQuery);
			NomineDetails = lQuery.list();
			if (NomineDetails.size() > 0) {	
				return NomineDetails;
				}
		} 
		catch (Exception e) {
			logger.error("Exception in GPFAccountNumber of GPFAdvanceProcessDAOImpl  : ", e);
		}
		return NomineDetails;
	}
	@Override
	public List getEmployeePay(String empName) {
		List NomineDetails = new ArrayList();
		List flagList = new ArrayList();
		
		StringBuilder lSBQuery = new StringBuilder();
		
		List lLstReturnList = new ArrayList<Object>();
		try {
			lSBQuery.append(" SELECT ME.SEVEN_PC_BASIC,LVL.ID FROM Mst_dcps_Emp ME,ifms.RLT_PAYBAND_GP_STATE_7PC LVL WHERE ME.SEVEN_PC_LEVEL = LVL.LEVEL and  ");
			lSBQuery.append(" ME.emp_name = '"+empName+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**** EMployeeNomineeDetails *******"+lQuery);
			NomineDetails = lQuery.list();
			if (NomineDetails.size() > 0) {	
				return NomineDetails;
				}
		} 
		catch (Exception e) {
			logger.error("Exception in GPFAccountNumber of GPFAdvanceProcessDAOImpl  : ", e);
		}
		return NomineDetails;
	}
	public String getMonthlySubscription(String SevaarthId){


		logger.info("inside getMonthlySubscription dao method");
		logger.info("SevaarthId"+SevaarthId);

		String lStrMonthlySub = "";
		List monthly = null;
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;



		lSBQuery.append(" SELECT cast(deduct.EMP_DEDUC_AMOUNT as varchar(20)) ");
		lSBQuery.append(" FROM mst_dcps_emp dcps inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = dcps.ORG_EMP_MST_ID ");
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID ");   
		lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1 ");  
		lSBQuery.append(" inner join HR_PAY_DEDUCTION_DTLS deduct on deduct.EMP_ID=eis.EMP_ID and deduct.EMP_DEDUC_ID=36 ");  
		lSBQuery.append(" and paybill.PAYBILL_YEAR=head.PAYBILL_YEAR and head.PAYBILL_MONTH=paybill.PAYBILL_MONTH ");  
		lSBQuery.append(" where dcps.SEVARTH_ID=:lStrSevaarthId ");
		lSBQuery.append(" order by cast(head.PAYBILL_YEAR || head.PAYBILL_MONTH as bigint) desc ");




		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lQuery.setParameter("lStrSevaarthId", SevaarthId);

		monthly = lQuery.list();
		if (monthly.size() != 0) {

			lStrMonthlySub = monthly.get(0).toString();
		}


		return lStrMonthlySub;

	}


	public String getEmployeeDDOCode(String lStrSevaarthid) {

		String lStrDdoCode = null;
		List lLstddo = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT dcps.ddoCode FROM MstEmp dcps where dcps.sevarthId=:lStrSevaarthid ");


			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevaarthid", lStrSevaarthid);

			lLstddo = lQuery.list();

			lStrDdoCode = lLstddo.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getEmployeeDDOCode of GPFRequestProcessDAOImpl  : ", e);			
		}

		return lStrDdoCode;
	}

	public List getInstallmentvalues(String lStrTransactionId) {
		logger.error("getInstallmentvalues"+lStrTransactionId);
		List installment = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {
			//lSBQuery.append(" SELECT TOTAL_INSTALLMENTS,INSTALLMENT_AMOUNT,RECOVERED_AMOUNT,PAYABLE_AMOUNT,BALANCE_OUTSTANDING FROM MST_GPF_ADVANCE where transaction_Id='20416000013' ");
			//lSBQuery.append(" SELECT gpf.noOfInstallments,gpf.installmentAmount,gpf.recoveredAmount FROM MstGpfAdvance gpf where gpf.transactionId=:lStrTransactionId ");
			
			//lSBQuery.append(" SELECT nvl(ADV.TOTAL_INSTALLMENTS,0),nvl(ADV.INSTALLMENT_AMOUNT,0),nvl(ADV.RECOVERED_AMOUNT,0),nvl(ADV.PAYABLE_AMOUNT,0),nvl(ADV.BALANCE_OUTSTANDING,0),DECIMAL(GPF.OUTSTANDING_AMOUNT+ADV.PAYABLE_AMOUNT) FROM IFMS.MST_GPF_ADVANCE ADV ");
			//lSBQuery.append(" INNER JOIN MST_GPF_YEARLY GPF ON GPF.GPF_ACC_NO=ADV.GPF_ACC_NO  where ADV.transaction_Id='"+lStrTransactionId+"' AND ADV.FIN_YEAR_ID=GPF.FIN_YEAR_ID ");

			
			lSBQuery.append(" SELECT nvl(ADV.TOTAL_INSTALLMENTS,0),nvl(ADV.INSTALLMENT_AMOUNT,0),nvl(ADV.RECOVERED_AMOUNT,0),nvl(ADV.PAYABLE_AMOUNT,0),nvl(ADV.BALANCE_OUTSTANDING,0),nvl(DECIMAL(loan.LOAN_PRIN_AMT-recover.TOTAL_RECOVERED_AMT+ADV.PAYABLE_AMOUNT),0),nvl(ADV.ODD_INSTALLMENT,0) "); 
			lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
			lSBQuery.append("  inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID ");   
			lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID=loan.EMP_LOAN_ID  ");
			lSBQuery.append(" inner join IFMS.MST_GPF_ADVANCE ADV  on  ADV.GPF_ACC_NO= emp.GPF_ACC_NO AND EMP.SEVAARTH_ID=ADV.SEVAARTH_ID");
			lSBQuery.append(" and loan.LOAN_TYPE_ID=55 and (loan.LOAN_ACTIVATE_FLAG=1 or loan.LOAN_ACTIVATE_FLAG=2 or loan.LOAN_ACTIVATE_FLAG=7) and  ADV.transaction_Id='"+lStrTransactionId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("lStrTransactionId", lStrTransactionId);


			installment = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getInstallmentvalues of GPFRequestProcessDAOImpl  : ", e);			
		}
		return installment;
	}

	public Long getLocationCode(Long lLngPostId){

		List location = null;
		Long lLngLocationCode = 0l;

		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append( " SELECT post.LOC_ID FROM ORG_POST_DETAILS_RLT post where post.POST_ID=:lLngPostId " ); 

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());


			lQuery.setParameter("lLngPostId", lLngPostId);

			location = lQuery.list();
			if (location.size() != 0) {

				lLngLocationCode = Long.valueOf(location.get(0).toString());

			}



		} catch (Exception e) {
			logger.error("Exception in getLocationCode : ", e);
		}
		return lLngLocationCode;

	}


	public String getOfficename(Long lLngLocationCode) {

		String lStrDdoCode = null;
		List lLstddo = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT ddo.OFF_NAME FROM MST_DCPS_DDO_OFFICE ddo where ddo.LOC_ID=:lLngLocationCode ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lLngLocationCode", lLngLocationCode);

			lLstddo = lQuery.list();

			lStrDdoCode = lLstddo.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getOfficename of GPFRequestProcessDAOImpl  : ", e);			
		}

		return lStrDdoCode;
	}

	public List getInitials(String lStrSevarthid) {
		List initials = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append(" SELECT ORG.EMP_FNAME,ORG.EMP_MNAME,ORG.EMP_LNAME FROM MST_DCPS_EMP DCPS, ORG_EMP_MST ORG WHERE ORG.EMP_ID=DCPS.ORG_EMP_MST_ID ");
			lSBQuery.append(" AND DCPS.SEVARTH_ID=:lStrSevarthid ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			lQuery.setParameter("lStrSevarthid", lStrSevarthid);

			initials = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getInitials of GPFRequestProcessDAOImpl  : ", e);			
		}
		return initials;
	}

	//gokarna


	public String getSpecialCaseValue(String transactionid) {


		logger.error("in dao  getSpecialCaseValue : " );	

		logger.error("transactionid: "+transactionid );	


		StringBuilder lSBQuery = new StringBuilder();
		List post = null;

		String lStrpost = "";
		String flag = "N";

		try {
			lSBQuery.append(" SELECT nvl(SPECIAL_CASE,0) FROM  IFMS.MST_GPF_ADVANCE where TRANSACTION_ID=:transactionid ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("transactionid", transactionid);

			post = lQuery.list();
			int size= post.size();

			if(size != 0)
			{
				lStrpost = post.get(0).toString();
				logger.error("lStrpost : "+lStrpost );
			}
			else
			{
				flag = "N";
			}

			logger.error("in dao  getSpecialCaseValue : "+lStrpost );


		} catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrpost;
	}


	public String getSpecialCaseValue90(String transactionid) {


		logger.error("in dao  getSpecialCaseValue : " );	

		logger.error("transactionid: "+transactionid );	


		StringBuilder lSBQuery = new StringBuilder();
		List post = null;

		String lStrpost = "";
		String flag = "N";

		try {
			lSBQuery.append(" SELECT SPECIAL_CASE_90_PERCENT FROM  mst_gpf_advance where TRANSACTION_ID=:transactionid ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("transactionid", transactionid);

			post = lQuery.list();
			int size= post.size();

			if(size != 0 )
			{
				lStrpost = post.get(0).toString();
				logger.error("lStrpost : "+lStrpost );
			}

			logger.error("in dao  getSpecialCaseValue : "+lStrpost );


		} catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrpost;
	}
	public List getDDODetails(String lstrddocode) {
		List initials = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("lstrddocode:::::::::"+lstrddocode);
		try {

			lSBQuery.append(" SELECT ddo_name,DDO_CODE,DDO_OFFICE FROM org_ddo_mst where DDO_CODE= '"+lstrddocode+"' ");
			//lSBQuery.append(" AND DCPS.SEVARTH_ID=:lStrSevarthid ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.error("lSBQuery:::::::::"+lSBQuery.toString());

			initials = lQuery.list();
			logger.error("lSBQuery:::::::::"+initials.size());
		} catch (Exception e) {
			logger.error("Exception in getInitials of GPFRequestProcessDAOImpl  : ", e);			
		}
		return initials;
	}
	public String getCurrentFlag(String lStrPkval) {


		logger.error("in dao  getCurrentFlag : " );	

		logger.error("transactionid: "+lStrPkval );	


		StringBuilder lSBQuery = new StringBuilder();
		List post = null;

		String lStrpost = "";
		String flag = "N";

		try {
			lSBQuery.append(" SELECT nvl(FRWDRD_TO_RHO_POST_ID,0) FROM  IFMS.MST_GPF_ADVANCE where MST_GPF_ADVANCE_ID = '"+lStrPkval+"' ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//	lQuery.setParameter("transactionid", transactionid);

			post = lQuery.list();
			int size= post.size();

			if(size != 0 )
			{
				lStrpost = post.get(0).toString();
				logger.error("lStrpost : "+lStrpost );
			}

			logger.error("in dao  getSpecialCaseValue : "+lStrpost );


		} catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrpost;
	}

	//swt 01/06/2020 (for Sevarthid)
	//public List getRecoveredAndOutstandingAmt(String gpfAcNo)
	public List getRecoveredAndOutstandingAmt(String gpfAcNo,String lStrSevaarthId )
	{
		List initials = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("gpfAcNo:::::::::"+gpfAcNo);
		logger.error("lStrSevaarthId:::::::::"+lStrSevaarthId);
		try {

			lSBQuery.append(" SELECT NVL(loan.LOAN_PRIN_AMT-recover.TOTAL_RECOVERED_AMT,0) ,nvl(recover.TOTAL_RECOVERED_AMT,0),to_char(loan.VOUCHER_DATE,'dd/MM/yyy'),NVL(loan.LOAN_PRIN_AMT,0),NVL(TOTAL_RECOVERED_AMT,0) ");
			lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID ");
			lSBQuery.append(" inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID    ");
			lSBQuery.append(" inner join HR_LOAN_EMP_DTLS loan on loan.EMP_ID=eis.EMP_ID inner join HR_LOAN_EMP_PRIN_RECOVER_DTLS recover on recover.EMP_LOAN_ID=loan.EMP_LOAN_ID    ");
			lSBQuery.append(" and loan.LOAN_TYPE_ID=55 and (loan.LOAN_ACTIVATE_FLAG=1 or loan.LOAN_ACTIVATE_FLAG=2 or loan.LOAN_ACTIVATE_FLAG=7)  and emp.gpf_acc_no='"+gpfAcNo+"'   ");
			lSBQuery.append(" AND emp.SEVAARTH_ID='"+lStrSevaarthId+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.error("lSBQuery:::::::::"+lSBQuery.toString());

			initials = lQuery.list();
			logger.error("lSBQuery:::::::::"+initials.size());
		} catch (Exception e) {
			logger.error("Exception in getInitials of GPFRequestProcessDAOImpl  : ", e);                    
		}
		return initials;
	}

	//Kinjal
	//public List CheckEmploeeTakenLoanForOnceInSErvice(String lStrGpfAccNO,Long CurrentYear) {
	
	//swt 19/06/2020 (SevaarthId addded)
	public List CheckEmploeeTakenLoanForOnceInSErvice(String lStrGpfAccNO,Long CurrentYear,String lStrSevaarthId) {
		List lLstUser = new ArrayList();
		String lStrUserName = "";
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("**********CheckEmploeeTakenLoanAlreadyOrNotNRA***********");
		try {
			lSBQuery.append(" SELECT NVL(MST_GPF_ADVANCE_ID,0),ADV.PURPOSE_CATEGORY  FROM IFMS.MST_GPF_ADVANCE ADV "); 
			lSBQuery.append(" INNER JOIN MST_GPF_BILL_DTLS BILL ON ");
			lSBQuery.append(" BILL.TRANSACTION_ID=ADV.TRANSACTION_ID ");
			lSBQuery.append("where ADV.GPF_ACC_NO='"+lStrGpfAccNO+"' and ADV.Sevaarth_Id='"+lStrSevaarthId+"' and ADV.STATUS_FLAG ='A' AND  ADV.PURPOSE_CATEGORY IN (800026,10001198367,10001198368,10001198370,10001198372,10001198362,10001198366,10001198371) AND BILL.STATUS_FLAG=1 ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**********lSBQuery*****CheckEmploeeTakenLoanAlreadyOrNot******"+lSBQuery.toString());


			lLstUser = lQuery.list();
			lStrUserName = lLstUser.toString();
			logger.error("**********lSBQuery*****CheckEmploeeTakenLoanAlreadyOrNot******"+lStrUserName);
			/*if (lLstUser != null && lLstUser.size() > 0) {
	lStrUserName = lLstUser.get(0).toString() ;
	logger.error("**************lStrUserName**************"+lStrUserName);
	//lStrUserName="Y";
	}
	else 
	{
	//lStrUserName="N";
	}*/
		} catch (Exception e) {
			logger.error("Exception in getUserName of GPFAdvanceProcessDAOImpl  : ", e); 
		}
		return lLstUser;
	}

	//public List CheckEmploeeTakenLoanAlreadyOrNotNRA(String lStrGpfAccNO,Long CurrentYear) {
		
	//swt 19/06/2020 (SevaarthId added)
	public List CheckEmploeeTakenLoanAlreadyOrNotNRA(String lStrGpfAccNO,Long CurrentYear,String lStrSevaarthId) {
		List lLstUser = new ArrayList();
		String lStrUserName = "N";
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("**********CheckEmploeeTakenLoanAlreadyOrNotNRA***********");
		try {


			lSBQuery.append(" SELECT NVL(MST_GPF_ADVANCE_ID,0),ADV.PURPOSE_CATEGORY  FROM IFMS.MST_GPF_ADVANCE ADV ");
			lSBQuery.append(" INNER JOIN MST_GPF_BILL_DTLS BILL ON ");
			lSBQuery.append(" BILL.TRANSACTION_ID=ADV.TRANSACTION_ID ");
			lSBQuery.append(" where ADV.GPF_ACC_NO='"+lStrGpfAccNO+"' and ADV.Sevaarth_Id='"+lStrSevaarthId+"' and ADV.STATUS_FLAG ='A' AND  ADV.PURPOSE_CATEGORY IN (800022,10001198365,10001198369)  AND BILL.STATUS_FLAG=1 ");
			lSBQuery.append("  and ADV.FIN_YEAR_ID = (SELECT FIN_YEAR_ID FROM SGVC_FIN_YEAR_MST WHERE FIN_YEAR_CODE='"+CurrentYear+"') ");


			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**********lSBQuery*****CheckEmploeeTakenLoanAlreadyOrNot******"+lSBQuery.toString());

			lLstUser = lQuery.list();
			/*if (lLstUser != null && lLstUser.size() > 0) {
			lStrUserName = lLstUser.get(0).toString();
			logger.error("**************lStrUserName**************"+lStrUserName);
			lStrUserName="Y";
		}
		else 
		{
			lStrUserName="N";

		}*/

		} catch (Exception e) {
			logger.error("Exception in getUserName of GPFAdvanceProcessDAOImpl  : ", e);			
		}
		return lLstUser;
	}
	//added by poonam
	public int CheckFinalWithdrawnLoanIsTaken(String lStrSevarthid)
	{
		StringBuilder lSBQuery = new StringBuilder();

		logger.info("Inside the CheckFinalWithdrawnLoanIsTaken");
		int size=0;
		try {
			lSBQuery.append(" SELECT NVL(TRN.TRN_GPF_FINAL_WITHDRAWAL_ID,0) FROM TRN_GPF_FINAL_WITHDRAWAL TRN ");
			lSBQuery.append(" INNER JOIN MST_GPF_BILL_DTLS BILL ON BILL.TRANSACTION_ID=TRN.TRANSACTION_ID AND TRN.REQ_STATUS='A' and BIll.STATUS_FLAG=1");
			//lSBQuery.append(" where TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )");
			lSBQuery.append(" where trn.SEVAARTH_ID = :lStrSevarthid ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.info("The Query for Given function is"+lQuery.toString());
			List FinalWithdrawnId = null;
			FinalWithdrawnId = lQuery.list();
			size= FinalWithdrawnId.size();
			logger.info("The size of FinalWithdrawnId :"+size);

		}


		catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return size;

	}
	public String getDesignName(Long lngPostId) {
		String loggedInDesgName = null;
		List lLstddo = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" SELECT office.OFF_NAME FROM MST_DCPS_DDO_OFFICE office inner join mst_dcps_emp emp on emp.CURR_OFF = office.DCPS_DDO_OFFICE_MST_ID ");
			lSBQuery.append("  inner join org_emp_mst org on org.EMP_ID = emp.ORG_EMP_MST_ID ");
			lSBQuery.append(" inner join ORG_USERPOST_RLT user on user.USER_ID = org.USER_ID and user.ACTIVATE_FLAG = 1 and POST_ID = "+lngPostId+" ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			lLstddo = lQuery.list();
			loggedInDesgName = lLstddo.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getOfficename of GPFRequestProcessDAOImpl  : ", e);			
		}

		return loggedInDesgName;
	}
	public List gettypeOfAdv(String transactionid) {


		List initials = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("transactionid:::::::::"+transactionid);
		try {

			lSBQuery.append(" SELECT cmn.LOOKUP_NAME,to_char(adv.SANCTIONED_DATE,'DD/MM/YYYY') FROM IFMS.MST_GPF_ADVANCE adv inner join CMN_LOOKUP_MST cmn on cmn.LOOKUP_ID = adv.PURPOSE_CATEGORY where adv.TRANSACTION_ID ='"+transactionid+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.error("lSBQuery:::::::::"+lSBQuery.toString());

			initials = lQuery.list();
			logger.error("lSBQuery:::::::::"+initials.size());
		} catch (Exception e) {
			logger.error("Exception in getInitials of GPFRequestProcessDAOImpl  : ", e);                    
		}
		return initials;
	}
	public Long getCurrentRecoveryAmt(String gpfAccNo, Long lngCurrYr,
			String strSevaarthId, Long lngCurrMonth) {
		Long  currRegSubscription = 0l;
		List lLstddo = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("  SELECT nvl(paybill.GPF_GRP_D,0)  ");
			lSBQuery.append("  FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID  ");
			lSBQuery.append("  inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID  ");
			lSBQuery.append("  inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID  ");
			lSBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG in(0,1,5) and head.paybill_month = '"+lngCurrMonth+"'  ");
			lSBQuery.append("  where emp.GPF_ACC_NO ='"+gpfAccNo+"' and mst.sevarth_id ='"+strSevaarthId+"'  ");
			lSBQuery.append(" AND PAYBILL.PAYBILL_YEAR = '"+lngCurrYr+"'  ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());


			lLstddo = lQuery.list();
			logger.error("lSBQuery*******getCurrentRecoveryAmt****"+lSBQuery.toString());
			if(lLstddo.size()!=0 && lLstddo!=null)
			{
				currRegSubscription = Long.parseLong(lLstddo.get(0).toString());
			}
			else
			{
				currRegSubscription=0l;
			}
		} catch (Exception e) {
			logger.error("Exception in getOfficename of GPFRequestProcessDAOImpl  : ", e);			
		}

		return currRegSubscription;
	}

	public Long getregsub(String strSevaarthId, Long lngCurrYr, Long lngPaybillMonth) {
		List location = null;
		Long lLngLocationCode = 0l;

		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append( "SELECT pay.GPF_GRP_D FROM HR_PAY_PAYBILL pay inner join PAYBILL_HEAD_MPG head on pay.PAYBILL_GRP_ID = head.PAYBILL_ID " ); 
			lSBQuery.append( " inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID " ); 
			lSBQuery.append( " inner join mst_dcps_emp emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID " ); 
			lSBQuery.append( " where emp.SEVARTH_ID = '"+strSevaarthId+"' and head.PAYBILL_MONTH = "+lngPaybillMonth+" and head.PAYBILL_YEAR = "+lngCurrYr+" and head.APPROVE_FLAG = 1 " ); 

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());




			location = lQuery.list();
			if (location.size() != 0) {

				lLngLocationCode = Long.valueOf(location.get(0).toString());

			}



		} catch (Exception e) {
			logger.error("Exception in getLocationCode : ", e);
		}
		return lLngLocationCode;

	}
	public String getDesignforBillDDO(String strTransactionId) {
		String loggedInDesgName = null;
		List lLstddo = null;
		try {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("   SELECT nvl(des.DSGN_NAME,0) FROM ORG_DDO_MST post inner join ORG_DESIGNATION_MST des on post.DSGN_CODE = des.DSGN_ID   ");
			lSBQuery.append("   inner join MST_GPF_BILL_DTLS gpf on gpf.LOCATION_CODE = post.LOCATION_CODE where gpf.TRANSACTION_ID = '"+strTransactionId+"'  ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());


			lLstddo = lQuery.list();
			if(lLstddo.size()>0 && !lLstddo.isEmpty())
				loggedInDesgName = lLstddo.get(0).toString();
		} catch (Exception e) {
			logger.error("Exception in getOfficename of GPFRequestProcessDAOImpl  : ", e);			
		}

		return loggedInDesgName;
	}

	@Override
	public List gettypeOfAdv1(String transactionid) {


		List initials = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		logger.error("transactionid:::::::::"+transactionid);
		try {

			lSBQuery.append(" SELECT cmn.LOOKUP_NAME,to_char(adv.HO_ACTION_DATE,'DD/MM/YYYY') FROM IFMS.TRN_GPF_FINAL_WITHDRAWAL adv inner join CMN_LOOKUP_MST cmn on cmn.LOOKUP_ID = adv.reason where adv.TRANSACTION_ID = '"+transactionid+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.error("lSBQuery:::::::::"+lSBQuery.toString());

			initials = lQuery.list();
			logger.error("lSBQuery:::::::::"+initials.size());
		} catch (Exception e) {
			logger.error("Exception in getInitials of GPFRequestProcessDAOImpl  : ", e);                    
		}
		return initials;
	}
	
	//swt 09/06/2020 (SevaarthId added)
	//public List getAdvanceDetail1(String lStrGpfAccNo, String lStrAdvanceType) {
	public List getAdvanceDetail1(String lStrGpfAccNo, String lStrAdvanceType,String lStrSevaarthId) {
		logger.error("getAdvanceDetail:::::::::lStrGpfAccNo: lStrAdvanceType:"+lStrAdvanceType);
		logger.error("SevaarthId:::::::::"+lStrSevaarthId);
		List advanceList = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		try {
			/*lSBQuery
				.append("select transactionId,createdDate,advanceAmt,amountSanctioned,noOfInstallments,installmentAmount,outstandingAmount,sancAuthorityName,sanctionedDate");
		lSBQuery.append(" FROM MstGpfAdvance");
		lSBQuery.append(" WHERE gpfAccNo = :gpfAccNo AND statusFlag = 'A'");
		lSBQuery.append(" AND advanceType = :advanceType");
		lSBQuery.append(" ORDER BY transactionId DESC");
		lSBQuery.append(" FETCH FIRST ROW ONLY ");*/
			lSBQuery.append(" select TRANSACTION_ID,CREATED_DATE,FINAL_AMOUNT,AMOUNT_SANCTIONED,0,0,BALANCE_OUTSTANDING,UPDATED_POST_ID,TO_CHAR(HO_ACTION_DATE,'MM')  ");
			lSBQuery.append(" FROM IFMS.TRN_GPF_FINAL_WITHDRAWAL  ");
			lSBQuery.append(" WHERE GPF_ACC_NO = '"+lStrGpfAccNo+"' AND Sevaarth_Id = '"+lStrSevaarthId+"' AND REQ_STATUS = 'A'  ");
			lSBQuery.append(" ORDER BY TRANSACTION_ID DESC  ");
			lSBQuery.append(" FETCH FIRST ROW ONLY ");
			//lSBQuery.append(" FETCH FIRST ROW ONLY ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			logger.error("lSBQuery:::::::::::"+lSBQuery.toString());
			//lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			//lQuery.setParameter("advanceType", lStrAdvanceType);
			logger.error("lSBQuery:::::::::::"+lSBQuery.toString());

			advanceList = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getAdvanceDetail of GPFRequestProcessDAOImpl  : ", e);			
		}
		return advanceList;
	}

	//swt 01/06/2020 (SevaarthId added)
	//public Double getOpeningBalForCurrYearNew(String lStrGpfAccNo, Long lLngYearId) throws Exception {
	public Double getOpeningBalForCurrYearNew(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId,String isSevenpc) throws Exception {

		logger.info("lStrGpfAccNo"+lStrGpfAccNo);
		logger.info("lLngYearId"+lLngYearId);
		logger.info("lStrSevaarthId"+lStrSevaarthId);

		Double lDblOpeningBal = null;
		List lLstOpeningBal = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		try {
			if(isSevenpc.equalsIgnoreCase("Y")){
				lSBQuery
				.append(" SELECT nvl(CLOSING_BALANCE,0) FROM MST_GPF_7PC_INTEREST_DTLS WHERE gpf_Acc_No = :gpfAccNo AND sevaarth_Id = :sevaarthId AND fin_Year_Id = :finYearId and MONTH is null " );
						lSBQuery.append(" and STATUS IS NULL ");

			}else {
				lSBQuery
				.append(" SELECT nvl(CLOSING_BALANCE,0) FROM MST_GPF_INTEREST_DTLS WHERE gpf_Acc_No = :gpfAccNo AND sevaarth_Id = :sevaarthId AND fin_Year_Id = :finYearId and MONTH is null " );
						lSBQuery.append(" and STATUS IS NULL ");

			}
			
			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("finYearId", lLngYearId-1);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);

			logger.info("getOpeningBalForCurrYearNew"+lSBQuery.toString());

			lLstOpeningBal = lQuery.list();
			if (lLstOpeningBal != null && lLstOpeningBal.size() > 0) {
				lDblOpeningBal = Double.parseDouble((lLstOpeningBal.get(0).toString()));
				logger.info("lDblOpeningBal"+lDblOpeningBal);
			} else {
				lDblOpeningBal = 0d;
			}
		} catch (Exception e) {
			logger.error("Exception in getOpeningBalForCurrYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDblOpeningBal;
	}
	
	//swt 01/06/2020 (SevaarthId added)
	//public List getSixPayAmountsNew(String lStrGpfAccNo,Long FinyrId) {
	public List getSixPayAmountsNew(String lStrGpfAccNo,Long FinyrId,String lStrSevaarthId,String isSevenpc) {
		logger.info("FinyrId:::"+FinyrId);
		List sixpay = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {
       if(isSevenpc.equalsIgnoreCase("Y")){
    	   lSBQuery.append("SELECT distinct SEVENPAYINTAMNT_16_17,SEVENPAYINTAMNT_17_18,SEVENPAYINTAMNT_18_19,SEVENPAYINTAMNT_19_20,SEVENPAYINTAMNT_20_21 from MST_GPF_7PC_INTEREST_DTLS  where  GPF_ACC_NO='"+lStrGpfAccNo+"' and Sevaarth_Id='"+lStrSevaarthId+"' and FIN_YEAR_ID='"+FinyrId+"' and month is not null ");
			lSBQuery.append(" and STATUS IS NULL ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");

        }else {
        	lSBQuery.append("SELECT distinct SIXPAYINTAMNT_09_10,SIXPAYINTAMNT_10_11,SIXPAYINTAMNT_11_12,SIXPAYINTAMNT_12_13,SIXPAYINTAMNT_13_14 from MST_GPF_INTEREST_DTLS  where  GPF_ACC_NO='"+lStrGpfAccNo+"' and Sevaarth_Id='"+lStrSevaarthId+"' and FIN_YEAR_ID='"+FinyrId+"' and month is not null ");
			lSBQuery.append(" and STATUS IS NULL ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");

		}
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
		lQuery.setParameter("FinyrId", FinyrId);

			 */

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				sixpay = lQuery.list();
			}
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}
	
    //swt 01/06/2020 (for SevaarthId)
	//public Double getOpeningBalance(String lStrGpfAccNo, Long lLngYearId) throws Exception {
	public Double getOpeningBalance(String lStrGpfAccNo, Long lLngYearId,String lStrSevaarthId,String isSEVENPC) throws Exception {

		logger.info("getOpeningBalance :::::::::::lStrGpfAccNo"+lStrGpfAccNo);
		logger.info("lLngYearId"+lLngYearId);

		Double lDblOpeningBal = 0d;
		List lLstOpeningBal = null;
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		try {
        if(isSEVENPC.equalsIgnoreCase("Y")){
        	lSBQuery.append(" SELECT distinct nvl(OPENING_BALANCE,0) FROM MST_GPF_7PC_INTEREST_DTLS WHERE gpf_Acc_No = '"+lStrGpfAccNo+"' AND Sevaarth_Id = '"+lStrSevaarthId+"' AND fin_Year_Id = '"+lLngYearId+"' and MONTH is not null ");
			lSBQuery.append(" and STATUS IS NULL ");
			lSBQuery.append(" and nvl(OPENING_BALANCE,0) > 0 ");  //swt 01/07/2020

        }else {
        	lSBQuery.append(" SELECT distinct nvl(OPENING_BALANCE,0) FROM MST_GPF_INTEREST_DTLS WHERE gpf_Acc_No = '"+lStrGpfAccNo+"' AND Sevaarth_Id = '"+lStrSevaarthId+"' AND fin_Year_Id = '"+lLngYearId+"' and MONTH is not null ");
			lSBQuery.append(" and STATUS IS NULL ");
			lSBQuery.append(" and nvl(OPENING_BALANCE,0) > 0 ");  //swt 01/07/2020

		}
			

			//lSBQuery.append(" SELECT distinct nvl(OPENING_BALANCE,0) FROM MST_GPF_YEARLY WHERE gpf_Acc_No = '"+lStrGpfAccNo+"' AND fin_Year_Id = '"+lLngYearId+"' ");
			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			//lQuery.setParameter("finYearId", lLngYearId-1);

			logger.info("lSBQuery:::::getOpeningBalance::::::::"+lSBQuery.toString());
			lLstOpeningBal = lQuery.list();
			logger.info("lSBQuery:::::getOpeningBalance::::::::"+lLstOpeningBal.size());
			if (!lLstOpeningBal.isEmpty() && lLstOpeningBal.size() > 0) {

				logger.info("lLstOpeningBal condition"+lDblOpeningBal);
				lDblOpeningBal = Double.parseDouble((lLstOpeningBal.get(0).toString()));
				logger.info("lDblOpeningBal"+lDblOpeningBal);

			} else {
				logger.error("in elsee::::::::::");
				lDblOpeningBal = 0d;
			}
			logger.info("lDblOpeningBal gggg"+lDblOpeningBal);
		} catch (Exception e) {
			logger.error("Exception in getOpeningBalForCurrYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDblOpeningBal;
	}
	//swt 01/06/2020 (for SevaarthId)
	//public List getAdvWithSanctionedAmnt(String lStrGpfAccNo,Long FinyrId) {
		public List getAdvWithSanctionedAmnt(String lStrGpfAccNo,Long FinyrId,String lStrSevaarthId,String is_sevenpc) {
		logger.info("FinyrId:::"+FinyrId);
		List sixpay = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {

			if(is_sevenpc.equalsIgnoreCase("Y")){
			lSBQuery.append(" SELECT  sum(nvl(cast (mpg.ADVANCE_SANCTIONED as integer),0)),sum(nvl(cast(mpg.WITHDRAWAL_SANCTIONED as integer),0)),mst.UPDATED_DATE from MST_GPF_YEARLY  mst,MST_GPF_YEAR_SANCTION_MPG mpg where mpg.MST_GPF_YEARLY_ID=mst.MST_GPF_YEARLY_ID and mst.GPF_ACC_NO='"+lStrGpfAccNo+"' and mst.Sevaarth_Id='"+lStrSevaarthId+"' and mst.FIN_YEAR_ID='"+FinyrId+"' and mst.IS_NEW='Y' AND mst.IS_SEVENPC = 'Y' group by mst.UPDATED_DATE ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");
			}
			else {
			lSBQuery.append(" SELECT  sum(nvl(cast (mpg.ADVANCE_SANCTIONED as integer),0)),sum(nvl(cast(mpg.WITHDRAWAL_SANCTIONED as integer),0)),mst.UPDATED_DATE from MST_GPF_YEARLY  mst,MST_GPF_YEAR_SANCTION_MPG mpg where mpg.MST_GPF_YEARLY_ID=mst.MST_GPF_YEARLY_ID and mst.GPF_ACC_NO='"+lStrGpfAccNo+"' and mst.Sevaarth_Id='"+lStrSevaarthId+"' and mst.FIN_YEAR_ID='"+FinyrId+"' and mst.IS_NEW='Y' AND (mst.IS_SEVENPC='N' OR mst.IS_SEVENPC IS NULL) group by mst.UPDATED_DATE ");
				
			}
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
		lQuery.setParameter("FinyrId", FinyrId);

			 */
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				sixpay = lQuery.list();
			}
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}
	public String getSanctionedDateFrmTranIdFinalWithdrwal(String trasanId){
		String appDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT fin.FIN_YEAR_DESC from SGVC_FIN_YEAR_MST fin , TRN_GPF_FINAL_WITHDRAWAL adv "); 
			lSBQuery.append(" where fin.FIN_YEAR_code=to_char(adv.HO_ACTION_DATE,'yyyy') and  TRANSACTION_ID='"+trasanId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				appDate = lQuery.list().get(0).toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return appDate; 
	}
	public String getSanctionedDateFrmTranId1Final(String trasanId){
		String appDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT TO_CHAR(ADV.HO_ACTION_DATE,'dd/MM/yyyy') from SGVC_FIN_YEAR_MST fin , TRN_GPF_FINAL_WITHDRAWAL adv where  fin.FIN_YEAR_code=to_char(adv.HO_ACTION_DATE,'yyyy') and TRANSACTION_ID='"+trasanId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("transactionID", trasanId);			

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				appDate = lQuery.list().get(0).toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return appDate; 
	}
	
	//public List getInitialSixPayAmnts(String lStrGpfAccNo) {
	//swt 22/07/2020
	public List getInitialSixPayAmnts(String lStrGpfAccNo,String lStrSevaarthId) {

		List sixpay = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append("SELECT SIXPAYAMOUNT_09_10,SIXPAYAMOUNT_10_11,SIXPAYAMOUNT_11_12,SIXPAYAMOUNT_12_13,SIXPAYAMOUNT_13_14 ,SEVENPAYAMOUNT_16_17,SEVENPAYAMOUNT_17_18,SEVENPAYAMOUNT_18_19,SEVENPAYAMOUNT_19_20,SEVENPAYAMOUNT_20_21,IS_SEVENPC from MST_GPF_YEARLY  where GPF_ACC_NO='"+lStrGpfAccNo+"' and Sevaarth_Id='"+lStrSevaarthId+"' and IS_NEW='Y' order by updated_date DESC FETCH FIRST ROWS ONLY ");
			//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
		lQuery.setParameter("FinyrId", FinyrId);

			 */
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				sixpay = lQuery.list();
			}
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}
	public Long getFinYearCode(Long finYearId){

		List sev = null;
		Long FinYearId = 0l;

		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append( " SELECT SFYM.FIN_YEAR_CODE FROM SGVC_FIN_YEAR_MST SFYM " ); 
			lSBQuery.append( " WHERE SFYM.FIN_YEAR_ID =:finYearId " );

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("finYearId",finYearId);		


			sev = lQuery.list();

			if(!sev.isEmpty() && sev.size()>0 && sev.get(0)!=null)
			{
				FinYearId = Long.parseLong(sev.get(0).toString());

			}

		}
		catch(Exception e){
			logger.error("Exception in getFinYearId of : " , e);
		}
		return FinYearId;

	}
	public Double getInterestAmount(Long month,Long year,String gpfAccNo){

		List sev = null;
		Double InterestAmount = 0d;

		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append( " SELECT sum(MONTHLY_INTEREST) FROM MST_GPF_INTEREST_DTLS " ); 
			lSBQuery.append( " where gpf_acc_no ='"+gpfAccNo+"' and month is not null  and fin_year_id ='"+year+"' " );
					lSBQuery.append( " and STATUS IS NULL " );
			if(month >=4)
			{
				lSBQuery.append( " and month <= '"+month+"' and month not in (1,2,3)" );
			}
			else if(month ==1)
			{
				lSBQuery.append( " and month <= 12 and month  in (1)" );
			}
			else if(month ==2)
			{
				lSBQuery.append( " and month <= 12 and month  in (1,2)" );
			}
			else if(month ==3)
			{
				lSBQuery.append( " and month <= 12 and month  in (1,2,3)" );
			}
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//	lQuery.setParameter("gpfAccNo",gpfAccNo);		
			//lQuery.setParameter("year",year);	


			sev = lQuery.list();

			if(!sev.isEmpty() && sev.size()>0 && sev.get(0)!=null)
			{
				InterestAmount = Double.parseDouble(sev.get(0).toString());

			}

		}
		catch(Exception e){
			logger.error("Exception in getInterestAmount : " , e);
		}
		return InterestAmount;

	}

	//public Double getNetBalance(Long month,Long year,String gpfAccNo){
	//swt 10/07/2020
	public Double getNetBalance(Long month,Long year,String gpfAccNo,String lStrSevaarthId,String isSevenpc){

		List sev = null;
		Double InterestAmount = 0d;

		try{
			StringBuilder lSBQuery = new StringBuilder();
			if (isSevenpc.equalsIgnoreCase("Y")) {
				lSBQuery.append( " SELECT NET_BALANCE FROM MST_GPF_7PC_INTEREST_DTLS " ); 
				lSBQuery.append( " where gpf_acc_no =:gpfAccNo and sevaarth_Id='"+lStrSevaarthId+"' and month is not null  and fin_year_id =:year  and month =:month ");
				lSBQuery.append( "and STATUS IS NULL " );

			}else {
				lSBQuery.append( " SELECT NET_BALANCE FROM MST_GPF_INTEREST_DTLS " ); 
				lSBQuery.append( " where gpf_acc_no =:gpfAccNo and sevaarth_Id='"+lStrSevaarthId+"' and month is not null  and fin_year_id =:year  and month =:month ");
				lSBQuery.append( "and STATUS IS NULL " );

			}
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo",gpfAccNo);		
			lQuery.setParameter("year",year);	
			lQuery.setParameter("month",month);	
			sev = lQuery.list();

			if(!sev.isEmpty() && sev.size()>0 && sev.get(0)!=null)
			{
				InterestAmount = Double.parseDouble(sev.get(0).toString());
			}
		}
		catch(Exception e){
			logger.error("Exception in getInterestAmount : " , e);
		}
		return InterestAmount;
	}

	public List getMonths() {

		String query = "select monthId,monthName from SgvaMonthMst where monthId < 13";
		List<Object> lLstReturnList = null;
		StringBuilder sb = new StringBuilder();
		sb.append(query);
		Query selectQuery = ghibSession.createQuery(sb.toString());
		List lLstResult = selectQuery.list();
		ComboValuesVO lObjComboValuesVO = null;

		if (lLstResult != null && lLstResult.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			for (int liCtr = 0; liCtr < lLstResult.size(); liCtr++) {
				obj = (Object[]) lLstResult.get(liCtr);
				lObjComboValuesVO = new ComboValuesVO();
				lObjComboValuesVO.setId(obj[0].toString());
				lObjComboValuesVO.setDesc(obj[1].toString());
				lLstReturnList.add(lObjComboValuesVO);
			}
		} else {
			lLstReturnList = new ArrayList<Object>();
			lObjComboValuesVO = new ComboValuesVO();
			lObjComboValuesVO.setId("-1");
			lObjComboValuesVO.setDesc("--Select--");
			lLstReturnList.add(lObjComboValuesVO);
		}
		return lLstReturnList;
	}
	public List getYears() {

		//String query = "select finYearId,finYearDesc from SgvcFinYearMst where finYearCode between '2007' and '2017'";
		String query = "select finYearId,finYearDesc from SgvcFinYearMst where finYearCode between '2007' and '2022'";
		List<Object> lLstReturnList = null;
		StringBuilder sb = new StringBuilder();
		sb.append(query);
		Query selectQuery = ghibSession.createQuery(sb.toString());
		List lLstResult = selectQuery.list();
		ComboValuesVO lObjComboValuesVO = null;

		if (lLstResult != null && lLstResult.size() != 0) {
			lLstReturnList = new ArrayList<Object>();
			Object obj[];
			for (int liCtr = 0; liCtr < lLstResult.size(); liCtr++) {
				obj = (Object[]) lLstResult.get(liCtr);
				lObjComboValuesVO = new ComboValuesVO();
				lObjComboValuesVO.setId(obj[0].toString());
				lObjComboValuesVO.setDesc(obj[1].toString());
				lLstReturnList.add(lObjComboValuesVO);
			}
		} else {
			lLstReturnList = new ArrayList<Object>();
			lObjComboValuesVO = new ComboValuesVO();
			lObjComboValuesVO.setId("-1");
			lObjComboValuesVO.setDesc("--Select--");
			lLstReturnList.add(lObjComboValuesVO);
		}
		return lLstReturnList;
	}
	//Added by Akshay Kumar
	public List getPanNumber(String lStrSevaarthId) {

		List sixpay = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();

		try {

			/*lSBQuery.append("SELECT nvl(PAN_NO,0),BANK_NAME,BRANCH_NAME FROM  mst_dcps_emp where SEVARTH_ID='"+lStrSevaarthId+"'");*/
			lSBQuery.append(" SELECT nvl(MDE.PAN_NO,0),MBP.bank_name,RBBP.BRANCH_NAME,MDE.BANK_ACNT_NO from  mst_dcps_emp MDE ");
			lSBQuery.append(" inner JOIN MST_BANK_PAY MBP on MBP.BANK_CODE=MDE.BANK_NAME ");
			lSBQuery.append(" inner join RLT_BANK_BRANCH_PAY RBBP on RBBP.BRANCH_ID=MDE.BRANCH_NAME ");
			lSBQuery.append(" where SEVARTH_ID='"+lStrSevaarthId+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("lSBQuery:::::::::::"+lSBQuery.toString());
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				sixpay = lQuery.list();
			}
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}
	public Double getSanctionedAmount(String trasanId,String lStrreqType){
		Double amount = null;
		logger.info("lStrreqType::::::"+lStrreqType);
		try{
			StringBuilder lSBQuery = new StringBuilder();
			Query lQuery=null;
			if(lStrreqType.equalsIgnoreCase("RA"))
			{
				lSBQuery.append("SELECT PAYABLE_AMOUNT FROM MST_GPF_ADVANCE where TRANSACTION_ID='"+trasanId+"'");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					amount = Double.parseDouble((lQuery.list().get(0).toString()));
				}
			}
			else if(lStrreqType.equalsIgnoreCase("Final"))
			{
				lSBQuery.append("SELECT AMOUNT_SANCTIONED FROM TRN_GPF_FINAL_WITHDRAWAL where TRANSACTION_ID='"+trasanId+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					amount = Double.parseDouble((lQuery.list().get(0).toString()));
				}
			}
			// CS
			else if(lStrreqType.equalsIgnoreCase("CS"))
			{
				lSBQuery.append("SELECT AMOUNT_SANCTIONED FROM MST_GPF_ADVANCE where TRANSACTION_ID='"+trasanId+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					amount = Double.parseDouble((lQuery.list().get(0).toString()));
				}
			}
			else if(lStrreqType.equalsIgnoreCase("NRA"))
			{
				lSBQuery.append(" SELECT nvl(CURRENT_DIS_AMOUNT,0) FROM MST_GPF_ADVANCE where TRANSACTION_ID='"+trasanId+"' ");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					amount = Double.parseDouble((lQuery.list().get(0).toString()));
				}
				//Commented by Akshay
				/*
				//find out how many disbursements are done
				lSBQuery.append(" SELECT COUNT(1) as cnt FROM MST_GPF_ADVANCE  ADV");
				lSBQuery.append(" INNER JOIN MST_GPF_BILL_DTLS BILL ON  ADV.ORDER_NO= BILL.ORDER_NO AND BILL.STATUS_FLAG in (1,0) ");
				lSBQuery.append(" WHERE  ADV.STATUS_FLAG= 'A' and ADV.ADVANCE_TYPE= 'NRA' and  ADV.PURPOSE_CATEGORY = (select PURPOSE_CATEGORY from MST_GPF_ADVANCE where TRANSACTION_ID='"+trasanId+"') ");

				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());	


				logger.info("Querty one:::"+lSBQuery.toString());

				if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
					amount = Double.parseDouble((lQuery.list().get(0).toString()));
				}



				Double count1=amount+1;
				logger.info("count1:::"+count1);
				//


				//
				StringBuilder lSBQuery1 = new StringBuilder();
				Query lQuery1=null;	


				//lSBQuery1.append(" SELECT NVL(DIS_AMOUNT,0) FROM IFMS.MST_GPF_DISBURSE_DETAILS where MST_GPF_ADV_ID = (select min(MST_GPF_ADVANCE_ID) from MST_GPF_ADVANCE where  GPF_ACC_NO='Peon/56' AND PURPOSE_CATEGORY=(select PURPOSE_CATEGORY from MST_GPF_ADVANCE where TRANSACTION_ID = '"+trasanId+"') AND STATUS_FLAG='A' ) and DIS_NUMBER='"+count1+"' ");
				//lSBQuery1.append(" SELECT NVL(DIS_AMOUNT,0) FROM IFMS.MST_GPF_DISBURSE_DETAILS where MST_GPF_ADV_ID = (select min(MST_GPF_ADVANCE_ID) from MST_GPF_ADVANCE where PURPOSE_CATEGORY=(select PURPOSE_CATEGORY from MST_GPF_ADVANCE where TRANSACTION_ID = '"+trasanId+"') AND STATUS_FLAG='A' ) and DIS_NUMBER='"+count1+"' ");  //added by Vivek 08 June 2017

				lSBQuery1.append(" SELECT NVL(DIS_AMOUNT,0) FROM IFMS.MST_GPF_DISBURSE_DETAILS where MST_GPF_ADV_ID = ");  //added by Vivek 08 June 2017
				lSBQuery1.append(" (select MST_GPF_ADVANCE_ID from MST_GPF_ADVANCE where TRANSACTION_ID = '"+trasanId+"' AND STATUS_FLAG='A' ) and DIS_NUMBER='"+count1+"'  ");
				lQuery1 = ghibSession.createSQLQuery(lSBQuery1.toString());	
				logger.info("Querty Two:::"+lSBQuery1.toString());
				if(lQuery1 != null && lQuery1.list() != null && lQuery1.list().size() > 0){	
					amount = Double.parseDouble((lQuery1.list().get(0).toString()));
				}
				else
				{
					StringBuilder lSBQuery2 = new StringBuilder();
					Query lQuery2=null;	


					//lSBQuery2.append(" SELECT NVL(DIS_AMOUNT,0) FROM IFMS.MST_GPF_DISBURSE_DETAILS where MST_GPF_ADV_ID = (select PURPOSE_CATEGORY from MST_GPF_ADVANCE where TRANSACTION_ID = '"+trasanId+"' AND STATUS_FLAG='A' ) and (DIS_NUMBER='"+count1+"' or DIS_NUMBER='"+amount+"')  ");
					lSBQuery2.append(" SELECT NVL(DIS_AMOUNT,0) FROM IFMS.MST_GPF_DISBURSE_DETAILS where MST_GPF_ADV_ID = (select MST_GPF_ADVANCE_ID from MST_GPF_ADVANCE where TRANSACTION_ID = '"+trasanId+"' AND STATUS_FLAG='A' ) and (DIS_NUMBER='"+count1+"' or DIS_NUMBER='"+amount+"')  ");  //Changed By Vivek 12 June 2017
					lQuery2 = ghibSession.createSQLQuery(lSBQuery2.toString());
					if(lQuery2 != null && lQuery2.list() != null && lQuery2.list().size() > 0)
						amount = Double.parseDouble((lQuery2.list().get(0).toString()));
					logger.info("Querty Two:::"+lSBQuery2.toString());
				}
				logger.info("amount::::::"+amount);

				 */
			}



		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return amount; 
	}

	public List getNameAndDesignation(String lStrSevaarthId){
		List sixpay = null;
		StringBuilder lSBQuery = new StringBuilder();

		try {

			/*lSBQuery.append("SELECT emp_name,FIRST_designation FROM mst_dcps_emp where SEVARTH_ID='"+lStrSevaarthId+"'");*//* comment by brijoy 16012019*/
			lSBQuery.append("SELECT a.emp_name,b.DSGN_NAME FROM mst_dcps_emp a  inner join ORG_DESIGNATION_MST b on a.designation = b.DSGN_CODE where a.SEVARTH_ID='"+lStrSevaarthId+"'");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				sixpay = lQuery.list();
			}

		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return sixpay;
	}

	public String getBillGenerationDate(String trasanId){
		String billDate = null;
		try{
			StringBuilder lSBQuery = new StringBuilder();			
			lSBQuery.append(" SELECT TO_CHAR(bill_generated_date,'dd/MM/yyyy') from MST_GPF_BILL_DTLS  where   TRANSACTION_ID='"+trasanId+"' ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());		

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){				
				billDate = lQuery.list().get(0).toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return billDate; 
	}

	public String getOrderNumber(String trasanId){
		String lStrOrderNo = null;

		try {
			StringBuilder lSBQuery = new StringBuilder();

			lSBQuery.append("SELECT nvl(ORDER_NO,0) FROM MST_GPF_BILL_DTLS where TRANSACTION_id ='"+trasanId+"'");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	

				lStrOrderNo = lQuery.list().get(0).toString();
				logger.error("lStrOrderNo:::::::"+lStrOrderNo);
			}

		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrOrderNo;
	}
	public String getDDOLocName(String trasanId){
		String lStrLocName = null;
		logger.info("getDDOLocName:::::"+trasanId);
		try {
			StringBuilder lSBQuery = new StringBuilder();

			lSBQuery.append(" SELECT nvl(cmn.LOC_NAME,'') FROM MST_GPF_BILL_DTLS bill inner join CMN_Location_MST cmn on cmn.LOC_ID=bill.LOCATION_CODE where TRANSACTION_id = '"+trasanId+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	

				lStrLocName = lQuery.list().get(0).toString();
				logger.info("lSBQuery:::::"+lSBQuery.toString()+"loc is "+lStrLocName);
			}

		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrLocName;
	}

	@Override
	public String getGenderOfEmployee(String strSevaarthId) {
		String lStrGender = null;
		logger.info("getGenderOfEmployee:::::"+strSevaarthId);
		try {
			StringBuilder lSBQuery = new StringBuilder();

			lSBQuery.append(" SELECT nvl(GENDER,0) FROM mst_dcps_emp where SEVARTH_ID ='"+strSevaarthId+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			List size1=lQuery.list();

			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
				lStrGender = lQuery.list().get(0).toString();
				logger.info("lSBQuery:::::"+lSBQuery.toString()+"GENDER "+lStrGender);
			}

		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lStrGender;
	}
	//Added by Akshay to ckeck a draft final withdrawal request on 15/06/2017
	public int CheckFinalWithdrawnLoanDraft(String lStrSevarthid)
	{
		StringBuilder lSBQuery = new StringBuilder();

		logger.info("Inside the CheckFinalWithdrawnLoanDraft");
		int size=0;
		try {
			lSBQuery.append(" SELECT NVL(TRN.TRN_GPF_FINAL_WITHDRAWAL_ID,0) FROM TRN_GPF_FINAL_WITHDRAWAL TRN ");
			//lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR','F','F1','F2','F3','F4','A') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )");
			//lSBQuery.append(" where TRN.REQ_STATUS in ('F','F1','F2','F3','F4','A') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )");
			lSBQuery.append(" where TRN.REQ_STATUS in ('F','F1','F2','F3','F4','A') AND TRN.SEVAARTH_ID  = :lStrSevarthid ");

			
			//lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )"); // commented by brijoy 10082018
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.info("The Query for Given function is"+lQuery.toString());
			List FinalWithdrawnId = null;
			FinalWithdrawnId = lQuery.list();
			size= FinalWithdrawnId.size();
			logger.info("The size of FinalWithdrawnId :"+size);

		}


		catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return size;

	}

	//Methods added by vivek
    //swt 01/06/2020 (for SevaarthId)
	//public List getPrvsPurpzRst(String gpfAccNo,String advanceType,String strPurpzId){
	public List getPrvsPurpzRst(String gpfAccNo,String advanceType,String strPurpzId,String lStrSevaarthId){
		List lstData = null;
		StringBuilder lSBQuery = new StringBuilder();

		try {

			lSBQuery.append(" SELECT MGA.PURPOSE_CATEGORY,MGBD.ORDER_NO,TO_CHAR(MGBD.ORDER_DATE,'dd/MM/yyyy'),MGA.ADVANCE_TYPE,MGA.AMOUNT_SANCTIONED,MGBD.VOUCHER_NO,MGBD.STATUS_FLAG ");
			lSBQuery.append(" FROM MST_GPF_ADVANCE MGA INNER JOIN MST_GPF_BILL_DTLS MGBD ON MGBD.TRANSACTION_ID = MGA.TRANSACTION_ID ");
			lSBQuery.append(" WHERE MGA.GPF_ACC_NO = :gpfAccNo AND MGA.sevaarth_Id = :sevaarthId AND MGA.ADVANCE_TYPE = :advanceType AND MGA.PURPOSE_CATEGORY = :strPurpzId AND MGA.STATUS_FLAG IN ('A') AND MGBD.STATUS_FLAG = 1 ORDER BY MGBD.BILL_DTLS_ID DESC ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("gpfAccNo", gpfAccNo);
			lQuery.setParameter("advanceType", advanceType);
			lQuery.setParameter("strPurpzId", strPurpzId);
			lQuery.setParameter("sevaarthId", lStrSevaarthId);
			lstData = lQuery.list();
		} catch (Exception e) {
			logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e); 
		}
		return lstData;
	}

	public String getPrpsIdFrmTransactionId(String transactionId)
	{
		String lStrPurposeId = "";
		try{
			StringBuilder lSBQuery = new StringBuilder(); 
			lSBQuery.append(" SELECT look.LOOKUP_ID FROM IFMS.MST_GPF_ADVANCE gpf inner join CMN_LOOKUP_MST look ");
			lSBQuery.append(" on gpf.PURPOSE_CATEGORY = look.LOOKUP_ID where TRANSACTION_ID =  :transactionId ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("transactionId", transactionId);
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){
				lStrPurposeId = lQuery.list().get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getPrpsFrmTransactionId of GPFRequestProcessDAOImpl  : ", e); 
		}
		return lStrPurposeId;
	}
	//Methods ended by vivek
	//ADDED METHOD BY VIVEK 24 JULY 2017
	public Double getArrearsTotalAmt(String strSevId, Long CurrFinYearId, String strGpfAccNo)
	{
		Double strTotalAmtArrears = 0d;
		logger.info("CurrFinYearId the CurrFinYearId"+CurrFinYearId);
		logger.info("strGpfAccNo the strGpfAccNo"+strGpfAccNo);
		try{
			StringBuilder lSBQuery = new StringBuilder(); 
			lSBQuery.append(" SELECT NVL(SUM(gpf.AMOUNT),0) FROM HR_PAY_PAYBILL pay inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = pay.PAYBILL_GRP_ID ");
			lSBQuery.append(" inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID ");
			lSBQuery.append(" inner join MST_DCPS_EMP emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID ");
			lSBQuery.append(" inner join HR_GPF_MULTIPLE_ARREAR_DTLS gpf on gpf.EMP_ID = pay.EMP_ID and  pay.PAYBILL_MONTH = gpf.PAYBILL_MONTH  ");
			lSBQuery.append(" and pay.PAYBILL_YEAR = gpf.PAYBILL_YEAR  where emp.SEVARTH_ID = :strSevId  and head.APPROVE_FLAG = 1 and  GPF_D_ARR_MR > 0 ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("strSevId", strSevId);
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){
				strTotalAmtArrears = Double.valueOf(lQuery.list().get(0).toString());
			}
			StringBuilder lSBQuery2 = new StringBuilder();
			CurrFinYearId=CurrFinYearId-1;
			lSBQuery2.append(" SELECT NVL(ARR_TOTAL_INT_AMOUNT,0) FROM MST_GPF_INTEREST_DTLS WHERE gpf_Acc_No = :strGpfAccNo ");
			lSBQuery2.append(" and CLOSING_BALANCE is not null and fin_Year_Id = :CurrFinYearId ");// AND STATUS IS NULL
			lSBQuery2.append(" AND STATUS IS NULL  ");
			Query lQuery2 = ghibSession.createSQLQuery(lSBQuery2.toString());
			lQuery2.setParameter("CurrFinYearId", CurrFinYearId);
			lQuery2.setParameter("strGpfAccNo", strGpfAccNo);
			if(lQuery2 != null && lQuery2.list() != null && lQuery2.list().size() > 0){
				strTotalAmtArrears = strTotalAmtArrears+ Double.valueOf(lQuery2.list().get(0).toString()) ;
			}
		}catch (Exception e) {
			logger.error("Exception in getPrpsFrmTransactionId of GPFRequestProcessDAOImpl  : ", e); 
		}
		return strTotalAmtArrears;
	}
	//ENDED METHOD BY VIVEK 24 JULY 2017
	//qUERY TO GET COUNT FOR 
	//List Data=null;  if (Data != null && Data.size() > 0) {
	//ADDED METHOD BY VIVEK 14 Sep 2017
	public String getEmpSevarthIdNew(String strEmpName)
	{
		String lStrEmpSevid = "";
		try{
			StringBuilder lSBQuery = new StringBuilder(); 
			lSBQuery.append(" SELECT SEVARTH_ID FROM MST_DCPS_EMP where EMP_NAME = :strEmpName ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("strEmpName", strEmpName);
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){
				lStrEmpSevid = lQuery.list().get(0).toString();
			}
		}catch (Exception e) {
			logger.error("Exception in getPrpsFrmTransactionId of GPFRequestProcessDAOImpl  : ", e); 
		}
		return lStrEmpSevid;
	}
	//ENDED METHOD BY VIVEK 14 Sep 2017
	public List getYears(Integer finYearId)
	{
		List FinYears = null;
		try{
			StringBuilder lSBQuery = new StringBuilder(); 
			lSBQuery.append("select YEAR(FROM_DATE),year(TO_DATE) from SGVC_FIN_YEAR_MST where FIN_YEAR_ID ='"+finYearId+"' ");
			
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			
			FinYears = lQuery.list();
		}catch (Exception e) {
			logger.error("Exception in getYears of GPFRequestProcessDAOImpl  : ", e); 
		}
		return 	FinYears;
	}
	
	//public List getEmployeeInterestDtls(String gpfAccNo,Integer finYearId)
	//swt 19/06/2020 (SevaarthId added)
	public List getEmployeeInterestDtls(String gpfAccNo,Integer finYearId,String lStrSevaarthId,String isSevenpc)
	{
		List EmployeeDtls = null;
		try{
			StringBuilder lSBQuery = new StringBuilder(); 
			if(isSevenpc.equalsIgnoreCase("Y")){
				lSBQuery.append("SELECT MONTH,NET_BALANCE FROM MST_GPF_7PC_INTEREST_DTLS where GPF_ACC_NO = '"+gpfAccNo+"' and Sevaarth_Id = '"+lStrSevaarthId+"' and FIN_YEAR_ID ='"+finYearId+"' and OPENING_BALANCE <>0 AND STATUS IS NULL order by MST_GPF_7PC_INTEREST_ID ");
				
			}else {
				lSBQuery.append("SELECT MONTH,NET_BALANCE FROM MST_GPF_INTEREST_DTLS where GPF_ACC_NO = '"+gpfAccNo+"' and Sevaarth_Id = '"+lStrSevaarthId+"' and FIN_YEAR_ID ='"+finYearId+"' and OPENING_BALANCE <>0 AND STATUS IS NULL order by MST_GPF_INTEREST_ID ");
				
			}
			//lSBQuery.append("SELECT MONTH,NET_BALANCE FROM MST_GPF_INTEREST_DTLS where GPF_ACC_NO = '"+gpfAccNo+"' and FIN_YEAR_ID ='"+finYearId+"' and OPENING_BALANCE <>0 order by MST_GPF_INTEREST_ID ");
			
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			
			EmployeeDtls = lQuery.list();
		}catch (Exception e) {
			logger.error("Exception in getYears of GPFRequestProcessDAOImpl  : ", e); 
		}
		return EmployeeDtls;
		
	}
	//swt 01/06/2020 (SevaarthId added)
	//public List getOpeningBalanceApril(String strGpfAccNo,Integer lIntCurrFinYearId)
	public List getOpeningBalanceApril(String strGpfAccNo,Integer lIntCurrFinYearId,String lStrSevaarthId,String isSevenpc)
	{
		List OpenBal = null;
		logger.info(" lIntCurrFinYearId"+lIntCurrFinYearId);
		try
		{
			StringBuilder lSBQuery = new StringBuilder();
			//lSBQuery.append(" SELECT OPENING_BALANCE,OUTSTANDING_AMOUNT as diff FROM MST_GPF_YEARLY where GPF_ACC_NO = '"+strGpfAccNo+"' and FIN_YEAR_ID = '"+lIntCurrFinYearId+"' ");
			if(isSevenpc.equalsIgnoreCase("Y")){
			lSBQuery.append(" SELECT OPENING_BALANCE,OUTSTANDING_AMOUNT FROM MST_GPF_YEARLY where GPF_ACC_NO = '"+strGpfAccNo+"' and Sevaarth_Id = '"+lStrSevaarthId+"' and FIN_YEAR_ID = '"+lIntCurrFinYearId+"' and IS_NEW='Y' AND IS_SEVENPC = 'Y' ");
			}
			else{
				lSBQuery.append(" SELECT OPENING_BALANCE,OUTSTANDING_AMOUNT FROM MST_GPF_YEARLY where GPF_ACC_NO = '"+strGpfAccNo+"' and Sevaarth_Id = '"+lStrSevaarthId+"' and FIN_YEAR_ID = '"+lIntCurrFinYearId+"' and IS_NEW='Y' AND (IS_SEVENPC='N' OR IS_SEVENPC IS NULL) ");
				
			}
			logger.info(" lSBQuery.toString() is "+lSBQuery.toString());
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			OpenBal = lQuery.list();
		}
		catch (Exception e) {
			logger.info("Exception in getOpeningBalanceApril");
		}
		return OpenBal;
	}
	public Double getInterestRate(String intDate)
	{
		Double intRate  = 0d;
		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append(" select interest from MST_GPF_INTEREST_RATE where '"+intDate+"' BETWEEN EFFECTIVE_FROM_DATE and APPLICABLE_TO_DATE ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){
				intRate = Double.parseDouble(lQuery.list().get(0).toString());
				
			}
		}catch (Exception e) {
			logger.error("Exception in getPrpsFrmTransactionId of GPFRequestProcessDAOImpl  : ", e); 
		}
		return intRate;
	} 
	public String getSuperAnDate(String strGpfAccNo)
	{
		String supDate = "";
		List supD = null;
		StringBuilder sb = new StringBuilder();
		try
		{
		sb.append(" SELECT oem.EMP_SRVC_EXP FROM MST_EMP_GPF_ACC mega ");
		sb.append(" inner join MST_DCPS_EMP mde on mde.SEVARTH_ID = mega.SEVAARTH_ID ");
		sb.append(" inner join ORG_EMP_MST oem on oem.EMP_ID = mde.ORG_EMP_MST_ID ");
		sb.append(" where mega.GPF_ACC_NO = '"+strGpfAccNo+"' ");
		Query lQuery = ghibSession.createSQLQuery(sb.toString());
		supD = lQuery.list();
		if(supD != null && supD.size() > 0 && supD.get(0)!= null)
		{
			supDate = supD.get(0).toString();
		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return supDate;
	}
	

	  public String getVRSDate(String gpFaccNo)
	  {
	    List lEList = null;
	    List lEndDate = null;
	    String SerEndDate = "";
	    StringBuilder lSBQuery = new StringBuilder();
	    try
	    {
	      lSBQuery.append("SELECT to_char(heed.END_DATE,'dd/MM/yyyy') FROM mst_dcps_emp mde  ");
	      lSBQuery.append(" inner join ORG_EMP_MST oem on oem.EMP_ID = mde.ORG_EMP_MST_ID ");
	      lSBQuery.append(" inner join HR_EIS_EMP_MST hiem on hiem.EMP_MPG_ID = mde.ORG_EMP_MST_ID ");
	      lSBQuery.append(" inner join MST_EMP_GPF_ACC  mega on mega.SEVAARTH_ID= mde.SEVARTH_ID ");
	      lSBQuery.append(" inner join HR_EIS_EMP_END_DATE heed on heed.EMP_ID = hiem.EMP_ID where GPF_ACC_NO = '" + gpFaccNo + "'");
	      
	      Query lSBQuery2 = this.ghibSession.createSQLQuery(lSBQuery.toString());
	      this.logger.info("========Query=========" + lSBQuery.toString());
	      lEndDate = lSBQuery2.list();
	      
	      this.logger.info("========O/P of Query=========" + lEndDate.toString());
	      if ((lSBQuery2 != null) && (lSBQuery2.list() != null) && (lSBQuery2.list().size() > 0))
	      {
	        SerEndDate = lSBQuery2.list().get(0).toString();
	        this.logger.info("Enddate" + SerEndDate);
	      }
	    }
	    catch (Exception e)
	    {
	      this.logger.error("Exception in getDdoCode of GPFRequestProcessDAOImpl  : ", e);
	    }
	    return SerEndDate;
	  }
	  
	  
	  //added by brijoy 06022019
	  public List getNomineeDetailsForPartiSevaa(String sevaarth)
		{
			List NomineDetails = new ArrayList();
			List flagList = new ArrayList();
			
			StringBuilder lSBQuery = new StringBuilder();
			
			List lLstReturnList = new ArrayList<Object>();
			try {
				lSBQuery.append(" SELECT  nvl(b.DCPS_NMN_NAME,'NA'),nvl(to_char(b.DCPS_NMN_DOB,'dd/MM/yyyy'),'NA'),nvl(b.DCPS_NMN_RLT,'NA'),nvl(b.DCPS_NMN_SHARE,0),nvl(b.DCPS_NMN_ADDRESS1,'NA'),nvl(b.DCPS_NMN_GUARDIAN,'NA') FROM MST_DCPS_EMP a  ");
				lSBQuery.append(" inner join MST_DCPS_EMP_NMN b  on a.DCPS_EMP_ID = b.DCPS_EMP_ID where  a.SEVARTH_ID =  '"+sevaarth+"'");
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				logger.error("**** EMployeeNomineeDetails *******"+lQuery);
				NomineDetails = lQuery.list();
				if (NomineDetails.size() > 0) {
					//int gpfAccCount = Integer.parseInt(GPFAcc.get(0).toString());
					
					//String gpfAccCount = obj[0].toString();
					
					return NomineDetails;
					
					
				}
			} 
			catch (Exception e) {
				logger.error("Exception in GPFAccountNumber of GPFAdvanceProcessDAOImpl  : ", e);
			}
			return NomineDetails;
		}
	  //added by brijoy14022019
	  //public String getServiceEnd(String gpfAccNo,Integer finYearId){
	 
	  //swt 19/06/2020 (SevaarthId added)
	  public String getServiceEnd(String gpfAccNo,Integer finYearId,String lStrSevaarthId){
String serviEnd ="";
			logger.error("***********gpfAccNo**"+gpfAccNo);
			logger.error("***********lStrSevaarthId**"+lStrSevaarthId);
			List findetails = null;
			String FinancialYear = "";
			StringBuilder sb = new StringBuilder();

			try{

				/*sb.append( "SELECT to_char(heeem.END_DATE,'dd/mm/yyyy') FROM MST_EMP_GPF_ACC mega inner join  MST_DCPS_EMP mde  on mega.SEVAARTH_ID = mde.SEVARTH_ID");
				sb.append( " inner join HR_EIS_EMP_MST heem on mde.ORG_EMP_MST_ID = heem.EMP_MPG_ID ");
				sb.append( " inner join HR_EIS_EMP_END_DATE heeem on heeem.EMP_ID = heem.EMP_ID inner join ");
				sb.append( " CMN_LOOKUP_MST clm on clm.LOOKUP_ID = heeem.REASON and clm.LOOKUP_ID = 250007 ");
				sb.append(" where mega.gpf_Acc_No ='"+gpfAccNo+"' and mega.Sevaarth_Id ='"+lStrSevaarthId+"' ");
				*/
				sb.append( " SELECT to_char(heem.EMP_SRVC_EXP,'dd/mm/yyyy') FROM MST_DCPS_EMP mde  ");
				sb.append( "	 inner join org_emp_mst heem on mde.ORG_EMP_MST_ID = heem.emp_id  ");
				sb.append(" where mde.SEVARTH_ID ='"+lStrSevaarthId+"'");
				

				Session ghibSession = ServiceLocator.getServiceLocator()
				.getSessionFactorySlave().getCurrentSession();

				Query lQuery = ghibSession.createSQLQuery(sb.toString());
				logger.error("Query is ::::::::::::::"+sb.toString());	



				findetails = lQuery.list();
				logger.error("Query is ::::Service End::::::::::"+findetails.size());	
				//gLogger.info("hii the query for getFinancialYearDescription ****strGpfAccNo is *+"+strGpfAccNo+"+***month*****"+month+"***********year*****"+year+"***********"+sb.toString());
				if(!findetails.isEmpty() && findetails != null && findetails.get(0) != null)
				{
					serviEnd = findetails.get(0).toString();
					
					logger.info(" serviEnd "+serviEnd);
					
				}
				logger.info(" serviEnd "+serviEnd);
	}
			catch(Exception e)
			{
				logger.error("Exception in getFinancialYearDescription of GPFLedgerReportQueryDAOImpl  : ", e);	
			}
			
			
			return serviEnd;
	  }

	/*@Override
	public List getEmployeePay(String empName) {
		List NomineDetails = new ArrayList();
		List flagList = new ArrayList();
		
		StringBuilder lSBQuery = new StringBuilder();
		
		List lLstReturnList = new ArrayList<Object>();
		try {
			lSBQuery.append(" SELECT ME.SEVEN_PC_BASIC,LVL.ID FROM Mst_dcps_Emp ME,ifms.RLT_PAYBAND_GP_STATE_7PC LVL WHERE ME.SEVEN_PC_LEVEL = LVL.LEVEL and  ");
			lSBQuery.append(" ME.emp_name = '"+empName+"'");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**** EMployeeNomineeDetails *******"+lQuery);
			NomineDetails = lQuery.list();
			if (NomineDetails.size() > 0) {	
				return NomineDetails;
				}
		} 
		catch (Exception e) {
			logger.error("Exception in GPFAccountNumber of GPFAdvanceProcessDAOImpl  : ", e);
		}
		return NomineDetails;
	}*/

	/*@Override
	public String getbilldata(String lStrEmpName, String lStrDdoCode) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public Integer getbilldata(String sevaarthID, String lStrDdoCode) {
		List lLstBillList = null;
		List lLstBillList1 = null;
		Integer lStrCount;
		logger.error("lStrLocationCode  : "+lStrDdoCode);
		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where adv.SEVAARTH_ID='"+sevaarthID+"'  AND bill.LOCATION_CODE='"+lStrDdoCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("sevaarthID", sevaarthID);
			//lQuery.setParameter("locCode", lStrDdoCode);
			lLstBillList = lQuery.list();
			
			StringBuilder lSBQueryY = new StringBuilder();
			lSBQueryY.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQueryY.append(" where adv.SEVAARTH_ID='"+sevaarthID+"'  AND bill.LOCATION_CODE='"+lStrDdoCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery1 = ghibSession.createSQLQuery(lSBQueryY.toString());
			//lQuery1.setParameter("sevaarthID", sevaarthID);
			//lQuery1.setParameter("locCode", lStrDdoCode);
			lLstBillList1 = lQuery1.list();
			
			logger.error("lStrCount  : "+lLstBillList.size()+lLstBillList1.size());
			
			lStrCount = lLstBillList.size()+lLstBillList1.size();
			
			
			
			
			
		} catch (Exception e) {
			logger.error("Exception in getbilldata of GPFREQUESTPROCESSDAOIMLP  : ", e);
			throw e;
		}

		return lStrCount;

}

	@Override
	public List getbilldetails(String sevaarthID, String gStrLocationCode) {
		List lLstBillList = null;
		Integer lStrCount;
		logger.error("lStrLocationCode  : "+gStrLocationCode);
		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where adv.SEVAARTH_ID='"+sevaarthID+"'  AND bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			lSBQuery.append(" UNION ALL ");
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where adv.SEVAARTH_ID='"+sevaarthID+"'  AND bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery1 = ghibSession.createSQLQuery(lSBQuery.toString());
			lLstBillList = lQuery1.list();
			
			logger.error("lStrCount  : "+lLstBillList.size());
					
		} catch (Exception e) {
			logger.error("Exception in getbilldetails of getbilldetails  : ", e);
			throw e;
		}

		return lLstBillList;
	}

	@Override
	public Integer getbilldataBS(String gStrLocationCode) {
		List lLstBillList = null;
		List lLstBillList1 = null;
		Integer lStrCount;
		logger.error("lStrLocationCode  : "+gStrLocationCode);
		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where  bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			//lQuery.setParameter("sevaarthID", sevaarthID);
			//lQuery.setParameter("locCode", lStrDdoCode);
			lLstBillList = lQuery.list();
			
			StringBuilder lSBQueryY = new StringBuilder();
			lSBQueryY.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQueryY.append(" where bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery1 = ghibSession.createSQLQuery(lSBQueryY.toString());
			//lQuery1.setParameter("sevaarthID", sevaarthID);
			//lQuery1.setParameter("locCode", lStrDdoCode);
			lLstBillList1 = lQuery1.list();
			
			logger.error("lStrCount  : "+lLstBillList.size()+lLstBillList1.size());
			
			lStrCount = lLstBillList.size()+lLstBillList1.size();
			
			
			
			
			
		} catch (Exception e) {
			logger.error("Exception in getbilldata of GPFREQUESTPROCESSDAOIMLP  : ", e);
			throw e;
		}

		return lStrCount;
	}

	@Override
	public List getbilldetailsBS(String gStrLocationCode) {
		List lLstBillList = null;
		Integer lStrCount;
		logger.error("lStrLocationCode  : "+gStrLocationCode);
		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			lSBQuery.append(" UNION ALL ");
			lSBQuery.append("  SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID,bill.ADVANCE_TYPE FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append(" where bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0 ");
			Query lQuery1 = ghibSession.createSQLQuery(lSBQuery.toString());
			lLstBillList = lQuery1.list();
			
			logger.error("lStrCount  : "+lLstBillList.size());
					
		} catch (Exception e) {
			logger.error("Exception in getbilldetails of getbilldetails  : ", e);
			throw e;
		}

		return lLstBillList;
	}

	@Override
	public List getbillnotLockdetailsBS(String gStrLocationCode) {
		List EmpGPFAcc = new ArrayList();
		List flagList = new ArrayList();
		
		StringBuilder lSBQuery = new StringBuilder();
		
		List lLstReturnList = new ArrayList<Object>();
		try {
				
			lSBQuery.append("  SELECT 'Sevaarth_ID'||':'||adv.SEVAARTH_ID ||'  '|| 'Transaction_ID'||':'||adv.TRANSACTION_ID || '  '||'Advance_Type'||':'||  bill.ADVANCE_TYPE FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID  ");
			lSBQuery.append("  where bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0  ");
			lSBQuery.append(" UNION ALL ");
			lSBQuery.append("  SELECT  'Sevaarth_ID'||':'||adv.SEVAARTH_ID ||'  '|| 'Transaction_ID'||':'||adv.TRANSACTION_ID || '  '||'Advance_Type'||':'|| bill.ADVANCE_TYPE  FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
			lSBQuery.append("  where bill.LOCATION_CODE='"+gStrLocationCode+"' AND bill.STATUS_FLAG = 0  ");
			
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			logger.error("**** EmployeeGPFACCNoExists *******"+lQuery);
			EmpGPFAcc = lQuery.list();
			if (EmpGPFAcc.size() > 0) {
				//int gpfAccCount = Integer.parseInt(GPFAcc.get(0).toString());
				
				//String gpfAccCount = obj[0].toString();
				
				return EmpGPFAcc;
				
				
			}
		} 
		catch (Exception e) {
			logger.error("Exception in GPFAccountNumber of GPFAdvanceProcessDAOImpl  : ", e);
		}
		return EmpGPFAcc;
	}
	public String getUserType(Long lLngUserId){
		
		String type = "";
		List tempList = null;
		StringBuilder lSBQuery = new StringBuilder();
		
		lSBQuery.append( " SELECT detail.ROLE_NAME FROM ORG_USER_MST user, ORG_USERPOST_RLT post, ACL_POSTROLE_RLT role, ACL_ROLE_DETAILS_RLT detail " );
		lSBQuery.append( " where user.USER_ID=post.USER_ID and post.POST_ID=role.POST_ID and role.ROLE_ID=detail.ROLE_ID " );
		lSBQuery.append( " and user.USER_ID=:lLngUserId  and detail.ROLE_NAME in ('DDO','HO') " );
		
		
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lQuery.setParameter("lLngUserId", lLngUserId);
		tempList = lQuery.list();

		if (tempList != null && tempList.size() > 0 && tempList.get(0) != null) {
			
			type = tempList.get(0).toString();
		}

		return type;
		
	}
	//swt 24/06/2020
	public String getEmpName(String lStrSevaarthId,String lStrDdoCode){
		logger.error("in getEmployeeName"+lStrSevaarthId);
		List names = null;
		String EmpName = "";
		StringBuilder SBQuery = new StringBuilder();

		try{
			SBQuery.append( " SELECT ME.EMP_NAME" );
			SBQuery.append( " FROM MST_DCPS_EMP ME " );
			SBQuery.append( " WHERE ME.SEVARTH_ID='"+lStrSevaarthId+"' and ME.DDO_CODE='"+lStrDdoCode+"' " );	

			Query selectQuery = ghibSession.createSQLQuery(SBQuery.toString());
			names = selectQuery.list();
			

			if (names != null && names.size() > 0) {
				EmpName = (String)names.get(0);
				logger.error("in EmpName"+EmpName);
			}

		}
		catch(Exception e){
			logger.error("Exception in getEmployeeName of GPFDataEntryFormDAOImpl  : ", e);	

		}

		return EmpName;
	}

	/*@Override
	public List<Object[]> getAdvanceDetail1(String gpfAccNo, String reqType,String lStrSevaarthId) {
		// TODO Auto-generated method stub
		return null;
	}*/


public List getInitialSixSevenPay(String lStrSevaarthId,String fy) {

	List sixpay = new ArrayList();
	StringBuilder lSBQuery = new StringBuilder();

	try {

		lSBQuery.append("SELECT SIXPAYAMOUNT_09_10,SIXPAYAMOUNT_10_11,SIXPAYAMOUNT_11_12,SIXPAYAMOUNT_12_13,SIXPAYAMOUNT_13_14 ,SEVENPAYAMOUNT_16_17,SEVENPAYAMOUNT_17_18,SEVENPAYAMOUNT_18_19,SEVENPAYAMOUNT_19_20,SEVENPAYAMOUNT_20_21,IS_SEVENPC from MST_GPF_YEARLY  where FIN_YEAR_ID='"+fy+"' and Sevaarth_Id='"+lStrSevaarthId+"' and IS_NEW='Y' order by updated_date DESC fetch first rows only ");
		//lSBQuery.append(" FROM MstGpfYearly gpf where gpf.gpfAccNo=:gpfAccNo and gpf.finYearId=:FinyrId ");

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
	lQuery.setParameter("FinyrId", FinyrId);

		 */
		if(lQuery != null && lQuery.list() != null && lQuery.list().size() > 0){	
			sixpay = lQuery.list();
		}
	} catch (Exception e) {
		logger.error("Exception in getSixPayAmounts of GPFRequestProcessDAOImpl  : ", e);			
	}
	return sixpay;
}



public String getEmployeePaymatri(String level)
{
  List lEList = null;
  List lEndDate = null;
  String SerEndDate = "";
  StringBuilder lSBQuery = new StringBuilder();
  try
  {
    lSBQuery.append(" SELECT (SELECT min("+level+") FROM MST_state_MATRIX_7THPAY where ("+level+") >0 )||'-'||max("+level+") FROM MST_state_MATRIX_7THPAY ");
   
    Query lSBQuery2 = this.ghibSession.createSQLQuery(lSBQuery.toString());
    //lSBQuery2.setParameter("level",level);
    this.logger.info("========Query=========" + lSBQuery.toString());
    lEndDate = lSBQuery2.list();
    
    this.logger.info("========O/P of Query=========" + lEndDate.toString());
    if ((lSBQuery2 != null) && (lSBQuery2.list() != null) && (lSBQuery2.list().size() > 0))
    {
      SerEndDate = lSBQuery2.list().get(0).toString();
      this.logger.info("Enddate" + SerEndDate);
    }
  }
  catch (Exception e)
  {
    this.logger.error("Exception in getDdoCode of GPFRequestProcessDAOImpl  : ", e);
  }
  return SerEndDate;
}

@Override
public int getmonthlyInt(String gpfAccNo, String lStrSevaarthId,
		Integer finYearId) {
	List amount=null;
	int  intAmt=0;
	 StringBuilder sbb = new StringBuilder();
	 sbb.append(" SELECT sum(MONTHLY_INTEREST) FROM MST_GPF_7PC_INTEREST_DTLS where GPF_ACC_NO = '"+gpfAccNo+"' and sevaarth_Id= '"+lStrSevaarthId+"' and FIN_YEAR_ID= "+finYearId+" and STATUS is null and MONTH IS NOT NULL ");
		//sb.append(" AND STATUS IS NULL ");
	 Query lQuery = ghibSession.createSQLQuery(sbb.toString());
		amount = lQuery.list();
		if(amount.size()>0 && amount.get(0)!= null)
		{
			intAmt = Integer.parseInt(amount.get(0).toString());
		}
		return intAmt;
}

@Override
public int getmonthlyIntsixpc(String gpfAccNo, String lStrSevaarthId,
		Integer finYearId) {
	List amount=null;
	int  intAmt=0;
	 StringBuilder sbb = new StringBuilder();
	 sbb.append(" SELECT sum(MONTHLY_INTEREST) FROM MST_GPF_INTEREST_DTLS where GPF_ACC_NO = '"+gpfAccNo+"' and sevaarth_Id= '"+lStrSevaarthId+"' and FIN_YEAR_ID= "+finYearId+" and STATUS is null and MONTH IS NOT NULL ");
		//sb.append(" AND STATUS IS NULL ");
	 Query lQuery = ghibSession.createSQLQuery(sbb.toString());
		amount = lQuery.list();
		if(amount.size()>0 && amount.get(0)!= null)
		{
			intAmt = Integer.parseInt(amount.get(0).toString());
		}
		return intAmt;
}
//swt 24/06/2020
	public String requestSTATUSISr(String lStrSevaarthId,String lStrDdoCode){
		logger.error("in getEmployeeName"+lStrSevaarthId);
		List names = null;
		String EmpName = "";
		StringBuilder SBQuery = new StringBuilder();
		
		try{
			SBQuery.append( "  select MGA.REQ_STATUS " );
			SBQuery.append( "  FROM TRN_GPF_FINAL_WITHDRAWAL MGA " );
			SBQuery.append( " WHERE MGA.Sevaarth_Id='"+lStrDdoCode+"' and MGA.gpf_Acc_No='"+lStrSevaarthId+"' AND MGA.REQ_STATUS in ('R') " );	
			Query selectQuery = ghibSession.createSQLQuery(SBQuery.toString());
			names = selectQuery.list();
			

			if (names != null && names.size() > 0) {
				EmpName = (String)names.get(0);
				logger.error("in EmpName"+EmpName);
			}

		}
		catch(Exception e){
			logger.error("Exception in getEmployeeName of GPFDataEntryFormDAOImpl  : ", e);	

		}

		return EmpName;
	}

	public List getUserPendingNameAdvance(String lStrSevaarthId,String userIdHO) {

		//String disbursementTypeOfDBandAdvSubType = "";
		List lLstData = null;
		try{
			Session session = getSession();
			StringBuilder lSBQuery = new StringBuilder();
			
			lSBQuery.append("  SELECT  Distinct case when adv.STATUS_FLAG='F1' then 'Verifier' when adv.STATUS_FLAG='F2' then 'HO' when adv.STATUS_FLAG='F3' then 'RHO Asst'  when adv.STATUS_FLAG='F4' then 'RHO' end,user.user_name,ADV.ADVANCE_TYPE,ADV.TRANSACTION_ID FROM MST_DCPS_EMP dcps  ");
    lSBQuery.append("   inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join wf_job_mst job on adv.MST_GPF_ADVANCE_ID=job.JOB_REF_ID and doc_id=800002   ");
    lSBQuery.append("   inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID  and post.ACTIVATE_FLAG = 1  inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID  ");
    lSBQuery.append("   inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1  inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID  ");
    lSBQuery.append("  where dcps.SEVARTH_ID='" + lStrSevaarthId + "' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.status_Flag in ('F','F1','F2','F3','F4') ");
    lSBQuery.append(" union  ");
    
    lSBQuery.append(" SELECT  Distinct case  when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then 'HO' when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then 'RHO' end,case  when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then (SELECT USER_NAME FROM org_user_mst where USER_ID='" + userIdHO + "') when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then (SELECT usr.USER_NAME FROM org_user_mst usr inner join ORG_USERPOST_RLT org on usr.user_id=org.USER_ID inner join ACL_POSTROLE_RLT rlt on org.POST_ID=rlt.POST_ID and rlt.ROLE_ID=800002 and rlt.ACTIVATE_FLAG=1 where org.POST_ID=adv.FRWDRD_TO_RHO_POST_ID) end,ADV.ADVANCE_TYPE,ADV.TRANSACTION_ID FROM MST_DCPS_EMP dcps   ");
    lSBQuery.append(" inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID    ");
    lSBQuery.append(" where dcps.SEVARTH_ID='" + lStrSevaarthId + "' and adv.status_Flag='A'    ");
    lSBQuery.append(" and adv.TRANSACTION_ID not in (SELECT  adv.TRANSACTION_ID FROM MST_DCPS_EMP dcps     ");
    lSBQuery.append(" inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID   ");
    lSBQuery.append(" where dcps.SEVARTH_ID='" + lStrSevaarthId + "')   ");
    lSBQuery.append(" union   ");
    lSBQuery.append(" SELECT Distinct case when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then 'HO' when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then 'RHO' end,   ");
    lSBQuery.append(" case  when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then (SELECT USER_NAME FROM org_user_mst where USER_ID='" + userIdHO + "') when (adv.STATUS_FLAG='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then (SELECT usr.USER_NAME FROM org_user_mst usr inner join ORG_USERPOST_RLT org on usr.user_id=org.USER_ID inner join ACL_POSTROLE_RLT rlt on org.POST_ID=rlt.POST_ID and rlt.ROLE_ID=800002 and rlt.ACTIVATE_FLAG=1 where org.POST_ID=adv.FRWDRD_TO_RHO_POST_ID) end,ADV.ADVANCE_TYPE,ADV.TRANSACTION_ID FROM MST_DCPS_EMP dcps      ");
    lSBQuery.append(" inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID    ");
    lSBQuery.append(" where dcps.SEVARTH_ID='" + lStrSevaarthId + "'   ");
    lSBQuery.append(" and adv.status_Flag='A' and bill.STATUS_FLAG=2    ");
    
			/*if(chkBillEntry.equalsIgnoreCase("N")){
			 lSBQuery.append(" SELECT  case when adv.STATUS_FLAG='F1' then 'Verifier' when adv.STATUS_FLAG in ('F2','A') then 'HO' when adv.STATUS_FLAG='F3' then 'RHO Asst'  when adv.STATUS_FLAG='F4' then 'RHO' end,user.user_name,ADV.ADVANCE_TYPE FROM MST_DCPS_EMP dcps  "); 
             lSBQuery.append(" inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  ");
             lSBQuery.append(" inner join wf_job_mst job on adv.MST_GPF_ADVANCE_ID=job.JOB_REF_ID and doc_id=800002  ");
   lSBQuery.append(" inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID  and post.ACTIVATE_FLAG = 1  ");
   lSBQuery.append(" inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID  ");  
   lSBQuery.append(" inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1  ");
   lSBQuery.append(" inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID  ");
  lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.status_Flag in ('A','F','F1','F2','F3','F4') ORDER BY adv.CREATED_DATE desc fetch first row only  ");
			}else {
  lSBQuery.append(" SELECT  case when adv.STATUS_FLAG='F1' then 'Verifier' when adv.STATUS_FLAG in ('F2','A') then 'HO' when adv.STATUS_FLAG='F3' then 'RHO Asst'  when adv.STATUS_FLAG='F4' then 'RHO' end,user.user_name,ADV.ADVANCE_TYPE FROM MST_DCPS_EMP dcps ");  
  lSBQuery.append(" inner join MST_GPF_ADVANCE  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID ");
  lSBQuery.append(" inner join wf_job_mst job on adv.MST_GPF_ADVANCE_ID=job.JOB_REF_ID and doc_id=800002   inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID  ");
  lSBQuery.append(" and post.ACTIVATE_FLAG = 1   inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID   inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1   inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID ");   
  lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.status_Flag in ('A','F','F1','F2','F3','F4') and bill.STATUS_FLAG=2 ORDER BY adv.CREATED_DATE desc fetch first row only ");
			}*/
		 
			

			Query query = session.createSQLQuery(lSBQuery.toString());

			if (query != null)
			{
				lLstData = query.list();
			}

			this.logger.info("list size is..." + lLstData.size());


		}catch (Exception e) {
			logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
		}
		return lLstData;
	}
	
	public List getUserPendingNameFW(String lStrSevaarthId, String userIdHO) {

		//String disbursementTypeOfDBandAdvSubType = "";
		List lLstData = null;
		try{
			Session session = getSession();
			StringBuilder lSBQuery = new StringBuilder();	
			
			/*if(chkBillEntry.equalsIgnoreCase("N")){
				 lSBQuery.append(" SELECT Distinct case when adv.REQ_STATUS='F1' then 'Verifier' when adv.REQ_STATUS in ('F2','A') then 'HO' when adv.REQ_STATUS='F3' then 'RHO Asst'  when adv.REQ_STATUS='F4' then 'RHO' end,user.user_name,'Final Withdrawal' as Advance_type FROM MST_DCPS_EMP dcps ");   
		 lSBQuery.append(" inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID   inner join wf_job_mst job on adv.TRN_GPF_FINAL_WITHDRAWAL_ID=job.JOB_REF_ID and doc_id=800003   inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID "); 
		 lSBQuery.append(" and post.ACTIVATE_FLAG = 1   inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID   inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1   inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID ");  
		 lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.REQ_STATUS in ('A','F','F1','F2','F3','F4') ");
		
			}else {
				 lSBQuery.append("  SELECT Distinct case when adv.REQ_STATUS='F1' then 'Verifier' when adv.REQ_STATUS in ('F2','A') then 'HO' when adv.REQ_STATUS='F3' then 'RHO Asst'  when adv.REQ_STATUS='F4' then 'RHO' end,user.user_name,'Final Withdrawal' as Advance_type FROM MST_DCPS_EMP dcps ");  
		  lSBQuery.append(" inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID ");
		  lSBQuery.append(" inner join wf_job_mst job on adv.TRN_GPF_FINAL_WITHDRAWAL_ID=job.JOB_REF_ID and doc_id=800003   inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID  ");
		  lSBQuery.append(" and post.ACTIVATE_FLAG = 1   inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID   inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1   inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID  ");  
		  lSBQuery.append(" where dcps.SEVARTH_ID='"+lStrSevaarthId+"' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.REQ_STATUS in ('A','F','F1','F2','F3','F4') and (adv.ORDER_no is null or bill.STATUS_FLAG=2) ");
					
			}*/
			 lSBQuery.append(" SELECT  Distinct case when adv.REQ_STATUS='F1' then 'Verifier' when adv.REQ_STATUS='F2' then 'HO' when adv.REQ_STATUS='F3' then 'RHO Asst'  when adv.REQ_STATUS='F4' then 'RHO' end,user.user_name,'Final Withdrawal' AS ADVANCE_TYPE,adv.TRANSACTION_ID FROM MST_DCPS_EMP dcps  ");
    lSBQuery.append(" inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join wf_job_mst job on adv.TRN_GPF_FINAL_WITHDRAWAL_ID=job.JOB_REF_ID and doc_id=800003  ");
    lSBQuery.append(" inner join ORG_USERPOST_RLT post on post.POST_ID=job.LST_ACT_POST_ID  and post.ACTIVATE_FLAG = 1  inner join ORG_USER_MST user on user.USER_ID=POST.USER_ID   ");
    lSBQuery.append(" inner join ACL_POSTROLE_RLT role on role.POST_ID=post.POST_ID and ROLE.ACTIVATE_FLAG = 1  inner join ACL_ROLE_DETAILS_RLT detail on detail.ROLE_ID=role.ROLE_ID   ");
    lSBQuery.append(" where dcps.SEVARTH_ID='" + lStrSevaarthId + "' and role.ROLE_ID in (700001,700002,800002,800004,8000016,8000020,800001,800005) and adv.REQ_STATUS in ('F','F1','F2','F3','F4')  ");
    lSBQuery.append(" union   ");
    lSBQuery.append(" SELECT  Distinct case  when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then 'HO' when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then 'RHO' end, ");
    lSBQuery.append(" case  when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then (SELECT USER_NAME FROM org_user_mst where USER_ID='" + userIdHO + "') when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then (SELECT usr.USER_NAME FROM org_user_mst usr inner join ORG_USERPOST_RLT org on usr.user_id=org.USER_ID inner join ACL_POSTROLE_RLT rlt on org.POST_ID=rlt.POST_ID and rlt.ROLE_ID=800002 and rlt.ACTIVATE_FLAG=1 where org.POST_ID=adv.FRWDRD_TO_RHO_POST_ID) end,'Final Withdrawal' AS ADVANCE_TYPE,adv.TRANSACTION_ID FROM MST_DCPS_EMP dcps     ");
    lSBQuery.append(" inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID   where dcps.SEVARTH_ID='" + lStrSevaarthId + "'  and adv.REQ_STATUS='A'  ");
    lSBQuery.append(" and adv.TRANSACTION_ID  not in (SELECT  adv.TRANSACTION_ID FROM MST_DCPS_EMP dcps    ");
    lSBQuery.append(" inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID   ");
    lSBQuery.append(" where dcps.SEVARTH_ID='" + lStrSevaarthId + "' and adv.REQ_STATUS='A' )  ");
    lSBQuery.append("  union  ");
    lSBQuery.append(" SELECT Distinct case when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then 'HO' when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then 'RHO' end, ");
    lSBQuery.append("  case  when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is null) then (SELECT USER_NAME FROM org_user_mst where USER_ID='" + userIdHO + "') when (adv.REQ_STATUS='A' and adv.FRWDRD_TO_RHO_POST_ID is not null) then (SELECT usr.USER_NAME FROM org_user_mst usr inner join ORG_USERPOST_RLT org on usr.user_id=org.USER_ID inner join ACL_POSTROLE_RLT rlt on org.POST_ID=rlt.POST_ID and rlt.ROLE_ID=800002 and rlt.ACTIVATE_FLAG=1 where org.POST_ID=adv.FRWDRD_TO_RHO_POST_ID) end,'Final Withdrawal' AS ADVANCE_TYPE,adv.TRANSACTION_ID FROM MST_DCPS_EMP dcps   ");
    lSBQuery.append("  inner join TRN_GPF_FINAL_WITHDRAWAL  adv on dcps.SEVARTH_ID=adv.SEVAARTH_ID  inner join MST_gpf_BILL_DTLS bill on bill.transaction_id=adv.TRANSACTION_ID   ");
    lSBQuery.append("  where dcps.SEVARTH_ID='" + lStrSevaarthId + "' and adv.REQ_STATUS='A' and bill.STATUS_FLAG=2  ");
  
  Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			

			Query query = session.createSQLQuery(lSBQuery.toString());

			if (query != null)
			{
				lLstData = query.list();
			}

			this.logger.info("list size is..." + lLstData.size());


		}catch (Exception e) {
			logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
		}
		return lLstData;
	}
	
	public int CheckFinalWithdrawnLoanDeoDraft(String lStrSevarthid)
	{
		StringBuilder lSBQuery = new StringBuilder();

		logger.info("Inside the CheckFinalWithdrawnLoanDraft");
		int size=0;
		try {
			lSBQuery.append(" SELECT NVL(TRN.TRN_GPF_FINAL_WITHDRAWAL_ID,0) FROM TRN_GPF_FINAL_WITHDRAWAL TRN ");
			lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR') AND TRN.SEVAARTH_ID = :lStrSevarthid ");
			//lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )"); // commented by brijoy 10082018
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.info("The Query for Given function is"+lQuery.toString());
			List FinalWithdrawnId = null;
			FinalWithdrawnId = lQuery.list();
			size= FinalWithdrawnId.size();
			logger.info("The size of FinalWithdrawnId :"+size);

		}


		catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return size;

	}
	
	public int CheckFinalWithdrawnEntry(String lStrSevarthid)
	{
		StringBuilder lSBQuery = new StringBuilder();

		logger.info("Inside the CheckFinalWithdrawnLoanDraft");
		int size=0;
		try {
			lSBQuery.append(" SELECT NVL(TRN.TRN_GPF_FINAL_WITHDRAWAL_ID,0) FROM TRN_GPF_FINAL_WITHDRAWAL TRN ");
			//lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR','F','F1','F2','F3','F4','A') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )");
			lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR','F','F1','F2','F3','F4','A') AND TRN.SEVAARTH_ID = :lStrSevarthid ");
			//lSBQuery.append(" where TRN.REQ_STATUS in ('F','F1','F2','F3','F4','A') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )");

			
			//lSBQuery.append(" where TRN.REQ_STATUS in ('D','R','DR') AND TRN.GPF_ACC_NO in (SELECT MGA.GPF_ACC_NO FROM MST_EMP_GPF_ACC MGA WHERE MGA.SEVAARTH_ID = :lStrSevarthid )"); // commented by brijoy 10082018
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("lStrSevarthid", lStrSevarthid);
			logger.info("The Query for Given function is"+lQuery.toString());
			List FinalWithdrawnId = null;
			FinalWithdrawnId = lQuery.list();
			size= FinalWithdrawnId.size();
			logger.info("The size of FinalWithdrawnId :"+size);

		}


		catch (Exception e) {
			logger.error("Exception in getPostId of GPFRequestProcessDAOImpl  : ", e);			
		}
		return size;

	}
	/* public String checkBillEntryForFW(String strSevaarthId) {
			logger.error("chkRoleHoEntry");
			
			List lLstResData = null;
			String chkEntryflag = "";
				try{
				StringBuilder lSBQuery = new StringBuilder();
				//lSBQuery.append(" SELECT * FROM mst_lna_house_advance ");
				//lSBQuery.append(" where SEVAARTH_ID='"+strSevaarthId+"' and status_flag = 'A1' ");
				
				lSBQuery.append(" SELECT fw.transaction_id FROM trn_gpf_final_withdrawal fw inner join mst_gpf_bill_dtls bill on fw.transaction_id=bill.transaction_id ");
				 lSBQuery.append(" where fw.sevaarth_id='"+strSevaarthId+"' and fw.REQ_STATUS='A' ");
				
    
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lLstResData = lQuery.list();
				
				if(lLstResData != null && lLstResData.size() > 0){
					chkEntryflag = "Y";
				}else{
					chkEntryflag = "N";
				}
			}catch(Exception e){
				 logger.error(" Error is : " + e, e);
		throw e; 
			}
			
			return chkEntryflag;
		}*/
	 public String checkBillEntryForFinal(String strSevaarthId) {
			logger.error("chkRoleHoEntry");
			
			List lLstResData = null;
			String chkEntryflag = "";
				try{
				StringBuilder lSBQuery = new StringBuilder();
				//lSBQuery.append(" SELECT * FROM mst_lna_house_advance ");
				//lSBQuery.append(" where SEVAARTH_ID='"+strSevaarthId+"' and status_flag = 'A1' ");
				
				lSBQuery.append(" SELECT fw.transaction_id FROM trn_gpf_final_withdrawal fw inner join mst_gpf_bill_dtls bill on fw.transaction_id=bill.transaction_id ");
				 lSBQuery.append(" where fw.sevaarth_id='"+strSevaarthId+"' and fw.REQ_STATUS='A' and bill.STATUS_FLAG=2 ");
				
    
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lLstResData = lQuery.list();
				
				if (lQuery != null)
				{
					lLstResData = lQuery.list();
				}
			}catch(Exception e){
				 logger.error(" Error is : " + e, e);
		throw e; 
			}
			
			return chkEntryflag;
		}
	 
	 /*public String checkBillEntryForAdv(String strSevaarthId) {
			logger.error("chkRoleHoEntry");
			
			List lLstResData = null;
			String chkEntryflag = "";
				try{
				StringBuilder lSBQuery = new StringBuilder();
				//lSBQuery.append(" SELECT * FROM mst_lna_house_advance ");
				//lSBQuery.append(" where SEVAARTH_ID='"+strSevaarthId+"' and status_flag = 'A1' ");
				
				lSBQuery.append(" SELECT adv.transaction_id FROM MST_GPF_ADVANCE adv inner join mst_gpf_bill_dtls bill on adv.transaction_id=bill.transaction_id ");
				 lSBQuery.append(" where adv.sevaarth_id='"+strSevaarthId+"' and adv.STATUS_FLAG='A' ");
				
    
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lLstResData = lQuery.list();
				
				if(lLstResData != null && lLstResData.size() > 0){
					chkEntryflag = "Y";
				}else{
					chkEntryflag = "N";
				}
			}catch(Exception e){
				 logger.error(" Error is : " + e, e);
		throw e; 
			}
			
			return chkEntryflag;
		}*/
	 public String checkBillEntryForAdvance(String strSevaarthId) {
			logger.error("chkRoleHoEntry");
			
			List lLstResData = null;
			String chkEntryflag = "";
				try{
				StringBuilder lSBQuery = new StringBuilder();
				//lSBQuery.append(" SELECT * FROM mst_lna_house_advance ");
				//lSBQuery.append(" where SEVAARTH_ID='"+strSevaarthId+"' and status_flag = 'A1' ");
				
				lSBQuery.append(" SELECT adv.transaction_id FROM MST_GPF_ADVANCE adv inner join mst_gpf_bill_dtls bill on adv.transaction_id=bill.transaction_id ");
				 lSBQuery.append(" where adv.sevaarth_id='"+strSevaarthId+"' and adv.STATUS_FLAG='A' and bill.STATUS_FLAG=2 ");
				
    
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lLstResData = lQuery.list();
				
				if (lQuery != null)
				{
					lLstResData = lQuery.list();
				}
			}catch(Exception e){
				 logger.error(" Error is : " + e, e);
		throw e; 
			}
			
			return chkEntryflag;
		}
	 
	 
	 public String checkPreBilldata(String strSevaarthId) {
			logger.error("chkRoleHoEntry");
			
			List lLstResData = null;
			String chkEntryflag = "";
				try{
				StringBuilder lSBQuery = new StringBuilder();
				//lSBQuery.append(" SELECT * FROM mst_lna_house_advance ");
				//lSBQuery.append(" where SEVAARTH_ID='"+strSevaarthId+"' and status_flag = 'A1' ");
				
				lSBQuery.append("SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
				lSBQuery.append(" where adv.SEVAARTH_ID='"+strSevaarthId+"' AND bill.STATUS_FLAG = 0 AND  adv.status_flag='A' ");
				lSBQuery.append(" union ");
				lSBQuery.append("SELECT adv.SEVAARTH_ID,adv.TRANSACTION_ID FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
				lSBQuery.append(" where adv.SEVAARTH_ID='"+strSevaarthId+"' AND bill.STATUS_FLAG = 0 and adv.req_status='A' ");
				//Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				
    
				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				lLstResData = lQuery.list();
				
				if(lLstResData != null && lLstResData.size() > 0){
					chkEntryflag = "Y";
				}else{
					chkEntryflag = "N";
				}
			}catch(Exception e){
				 logger.error(" Error is : " + e, e);
		throw e; 
			}
			
			return chkEntryflag;
		}
	 
	 public Boolean requestDataAlreadyExistsDraft(String lStrSevaarthId) {
			List savedRA = new ArrayList();
			StringBuilder lSBQuery = new StringBuilder();
			try {
				
				//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('F','F1','F2','F3','F4')");
				//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('F','F1','F2','F3','F4','A')");
				
				
				lSBQuery.append(" SELECT MST_GPF_ADVANCE_ID FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('F','F1','F2','F3','F4') ");
                lSBQuery.append(" union ");
   lSBQuery.append(" SELECT MST_GPF_ADVANCE_ID FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG='A' ");
   lSBQuery.append(" and TRANSACTION_ID not in (SELECT adv.TRANSACTION_ID FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on adv.TRANSACTION_ID=bill.TRANSACTION_ID ");
  lSBQuery.append("  where adv.SEVAARTH_ID='"+lStrSevaarthId+"' and adv.STATUS_FLAG='A') AND ADVANCE_TYPE<>'CS' ");
                lSBQuery.append(" union ");
   lSBQuery.append(" SELECT adv.MST_GPF_ADVANCE_ID FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on adv.TRANSACTION_ID=bill.TRANSACTION_ID ");
   lSBQuery.append(" and SEVAARTH_ID='"+lStrSevaarthId+"' and adv.STATUS_FLAG='A' and bill.STATUS_FLAG=2 ");



				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				savedRA = lQuery.list();
				logger.error("**** requestDataAlreadyExistsDraft *******"+lSBQuery.toString());


			} catch (Exception e) {
				logger.error("Exception in requestDataAlreadyExistsDraft of GPFAdvanceProcessDAOImpl  : ", e);
			}
			if (savedRA.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
	 
	//swt 24/04/2021
		public Boolean requestDataAlreadyExistsDeoDraft(String lStrSevaarthId) {
			List savedRA = new ArrayList();
			StringBuilder lSBQuery = new StringBuilder();
			try {
				/*lSBQuery.append("select MGA.transactionId");
				lSBQuery.append(" FROM MstGpfAdvance MGA");
				lSBQuery.append(" WHERE MGA.gpfAccNo = :gpfAccNo AND MGA.statusFlag LIKE 'F%'");*/

				lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','R','DR')");
				//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','F','F1','F2','F3','F4','R','DR')");



				Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
				savedRA = lQuery.list();
				logger.error("**** requestDataAlreadyExistsDraft *******"+lSBQuery.toString());


			} catch (Exception e) {
				logger.error("Exception in requestDataAlreadyExistsDraft of GPFAdvanceProcessDAOImpl  : ", e);
			}
			if (savedRA.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		
		/*public List getReportType()
{
  String query = " SELECT lookupId,lookupName FROM CmnLookupMst where parentLookupId = 10001198435 ";
  List<Object> lLstReturnList = null;
  StringBuilder sb = new StringBuilder();
  sb.append(query);
  Query selectQuery = this.ghibSession.createQuery(sb.toString());
  List lLstResult = selectQuery.list();
  ComboValuesVO lObjComboValuesVO = null;
  if ((lLstResult != null) && (lLstResult.size() != 0))
  {
    lLstReturnList = new ArrayList();
    for (int liCtr = 0; liCtr < lLstResult.size(); liCtr++)
    {
      Object[] obj = (Object[])lLstResult.get(liCtr);
      lObjComboValuesVO = new ComboValuesVO();
      lObjComboValuesVO.setId(obj[0].toString());
      lObjComboValuesVO.setDesc(obj[1].toString());
      lLstReturnList.add(lObjComboValuesVO);
    }
  }
  else
  {
    lLstReturnList = new ArrayList();
    lObjComboValuesVO = new ComboValuesVO();
    lObjComboValuesVO.setId("-1");
    lObjComboValuesVO.setDesc("--Select--");
    lLstReturnList.add(lObjComboValuesVO);
  }
  return lLstReturnList;
}

public List getYearsAG()
{
  List<String> lLstResult = null;
  ComboValuesVO lObjComboValueVO = null;
  List<ComboValuesVO> lLstYears = new ArrayList();
  try
  {
    this.ghibSession = ServiceLocator.getServiceLocator().getSessionFactory().getCurrentSession();
    StringBuffer lSBQuery = new StringBuffer("select finYearCode from SgvcFinYearMst where finYearCode between '2012' and '2021' order by finYearCode");
    Query lQuery = this.ghibSession.createQuery(lSBQuery.toString());
    
    lLstResult = lQuery.list();
    if ((lLstResult != null) && (!lLstResult.isEmpty())) {
      for (String lStrYearCode : lLstResult) {
        if (lStrYearCode != null)
        {
          lObjComboValueVO = new ComboValuesVO();
          lObjComboValueVO.setId(lStrYearCode);
          lObjComboValueVO.setDesc(lStrYearCode);
          lLstYears.add(lObjComboValueVO);
        }
      }
    }
  }
  catch (Exception e)
  {
    throw e;
  }
  return lLstYears;
}*/

public List getUserNameDDO(String lStrSevaarthId) {
				logger.error("chkRoleHoEntry");
				
				List lLstResData = null;
				
					try{
					StringBuilder lSBQuery = new StringBuilder();
				
					 lSBQuery.append(" SELECT  mst.USER_NAME,bill.transaction_id,adv.advance_type FROM MST_GPF_ADVANCE adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
					 lSBQuery.append("  inner join org_ddo_mst ddo on ddo.LOCATION_CODE=bill.LOCATION_CODE inner join ORG_USERPOST_RLT usr on usr.POST_ID=ddo.POST_ID inner join ORG_USER_MST mst on mst.USER_ID=usr.user_id ");
					 lSBQuery.append(" where adv.SEVAARTH_ID='"+lStrSevaarthId+"' AND bill.STATUS_FLAG = 0 ");
					 lSBQuery.append(" union ");
					 lSBQuery.append(" SELECT  mst.USER_NAME,bill.transaction_id,'Final withdrawal' as advance_type FROM TRN_GPF_FINAL_WITHDRAWAL adv inner join MST_GPF_BILL_DTLS bill on bill.TRANSACTION_ID=adv.TRANSACTION_ID ");
					 lSBQuery.append(" inner join org_ddo_mst ddo on ddo.LOCATION_CODE=bill.LOCATION_CODE inner join ORG_USERPOST_RLT usr on usr.POST_ID=ddo.POST_ID inner join ORG_USER_MST mst on mst.USER_ID=usr.user_id ");
					 lSBQuery.append("  where adv.SEVAARTH_ID='"+lStrSevaarthId+"' AND bill.STATUS_FLAG = 0 "); 
	    
					Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
					lLstResData = lQuery.list();
					
					if (lQuery != null)
					{
						lLstResData = lQuery.list();
					}
				}catch(Exception e){
					 logger.error(" Error is : " + e, e);
			throw e; 
				}
				
				return lLstResData;
			}  

public List getUserPendingNameAdvanceAndFw(String lStrSevaarthId) {

				//String disbursementTypeOfDBandAdvSubType = "";
				List lLstData = null;
				try{
					Session session = getSession();
					StringBuilder lSBQuery = new StringBuilder();
					
		   
		   lSBQuery.append(" SELECT ADVANCE_TYPE,TRANSACTION_ID FROM mst_gpf_Advance where STATUS_FLAG in ('F1','F2','F3','F4','A') AND sevaarth_id='"+lStrSevaarthId+"' ");
		   lSBQuery.append(" UNION ");
		   lSBQuery.append(" SELECT 'Final Withdrawal' as ADVANCE_TYPE,TRANSACTION_ID FROM TRN_GPF_FINAL_WITHDRAWAL where REQ_STATUS in ('F1','F2','F3','F4','A') AND sevaarth_id='"+lStrSevaarthId+"' ");
				 
					

					Query query = session.createSQLQuery(lSBQuery.toString());

					if (query != null)
					{
						lLstData = query.list();
					}

					this.logger.info("list size is..." + lLstData.size());


				}catch (Exception e) {
					logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
				}
				return lLstData;
			}
public String getActiveHO(String locCode)
{
  List lLstActiveHO = null;
  String lStrActiveHO = "";
  try
  {
    StringBuilder lSBQuery = new StringBuilder();
    lSBQuery.append(" SELECT usr.USER_ID FROM ACL_POSTROLE_RLT role inner join ORG_POST_DETAILS_RLT post ");
    lSBQuery.append(" on post.POST_ID = role.POST_ID  ");
    lSBQuery.append(" inner join ORG_USERPOST_RLT usr on usr.post_id = post.POST_ID and usr.ACTIVATE_FLAG = 1 ");
    lSBQuery.append(" inner join org_emp_mst emp on emp.user_id = usr.user_id ");
    lSBQuery.append(" inner join mst_dcps_emp dcps on dcps.ORG_EMP_MST_ID = emp.EMP_ID ");
    lSBQuery.append(" where post.LOC_ID ='" + locCode + "' and role.ROLE_ID = 800002 and role.ACTIVATE_FLAG =1 ");
    Query lQuery = this.ghibSession.createSQLQuery(lSBQuery.toString());
    
    lLstActiveHO = lQuery.list();
    if ((lLstActiveHO != null) && (lLstActiveHO.size() > 0)) {
      lStrActiveHO = lLstActiveHO.get(0).toString();
    }
  }
  catch (Exception e)
  {
    this.logger.error("Exception in getFinYearCodeForFinYearId of ScheduleGenerationDAOImpl  : ", e);
  }
  return lStrActiveHO;
}
public List getSevenPcArrDetails(String lStrSevaarthId, String empName) {
		List lLstData = null;
		try{
		Session session = getSession();
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(" SELECT Y.SEVENPAYAMOUNT_16_17,Y.SEVENPAYAMOUNT_17_18,Y.SEVENPAYAMOUNT_18_19,Y.SEVENPAYAMOUNT_19_20,Y.SEVENPAYAMOUNT_20_21,Y.SEVAARTH_ID,EMP.EMP_NAME FROM MST_GPF_YEARLY y ");
		lSBQuery.append(" INNER JOIN MST_DCPS_EMP emp on emp.SEVARTH_ID=y.sevaarth_id and y.IS_SEVENPC='Y' ");
		lSBQuery.append(" where (EMP.SEVARTH_ID='"+lStrSevaarthId+"' OR EMP.EMP_NAME='"+empName+"') ");
		Query query = session.createSQLQuery(lSBQuery.toString());
		if (query != null)
	    {
			lLstData = query.list();
		}
		this.logger.info("list size is..." + lLstData.size());
		}catch (Exception e) {
		logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
		}
				return lLstData;
	}

public Long getpkInitialDataEntry(String sevaarthid){
	List advanceHistoryList = new ArrayList();
	Long amount = 0l;
	StringBuilder lSBQuery = new StringBuilder();
	try {
		//lSBQuery.append("SELECT NVL(mst.CurrentDisamount,0) FROM MstGpfAdvance mst where mst.sevaarthId = :sevaarthid and mst.statusFlag in ('D','F1','R','F2','F3','DR','RD') and mst.advanceType='NRA' ");
		lSBQuery.append(" SELECT y.MST_GPF_YEARLY_ID FROM MST_GPF_YEARLY y INNER JOIN TRN_EMP_GPF_ACC trn on trn.SEVAARTH_ID=y.sevaarth_id and trn.PC_FLAG='Y' where y.SEVAARTH_ID='"+sevaarthid+"' AND y.IS_SEVENPC='Y' ");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		advanceHistoryList = lQuery.list();
		if (advanceHistoryList.size() != 0) {
			amount = Long.valueOf(advanceHistoryList.get(0).toString());
		}
	} catch (Exception e) {
		logger.error("Exception in getAdvanceHistory of GPFRequestProcessDAOImpl  : ", e);			
	}
	logger.info("amount"+amount);
	return amount;
}
public List getInterestCalculationFlag(String lStrSevaarthId) {
	List lLstData = null;
	try{
	Session session = getSession();
	StringBuilder lSBQuery = new StringBuilder();
  lSBQuery.append("SELECT distinct SEVENPAYINTAMNT_16_17,SEVENPAYINTAMNT_17_18,SEVENPAYINTAMNT_18_19,SEVENPAYINTAMNT_19_20,SEVENPAYINTAMNT_20_21 from MST_GPF_7PC_INTEREST_DTLS  where Sevaarth_Id='"+lStrSevaarthId+"' and month is not null ");
  lSBQuery.append(" and STATUS IS NULL ");
	Query query = session.createSQLQuery(lSBQuery.toString());
	if (query != null)
    {
		lLstData = query.list();
	}
	this.logger.info("list size is..." + lLstData.size());
	}catch (Exception e) {
	logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
	}
			return lLstData;
}

public boolean isSecondIsEdit(String lStrSevaarthId, String EMPname) {
	List savedRA = new ArrayList();
	StringBuilder lSBQuery = new StringBuilder();
	try {
		/*lSBQuery.append("select MGA.transactionId");
		lSBQuery.append(" FROM MstGpfAdvance MGA");
		lSBQuery.append(" WHERE MGA.gpfAccNo = :gpfAccNo AND MGA.statusFlag LIKE 'F%'");*/

		//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','R','DR')");
		//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','F','F1','F2','F3','F4','R','DR')");
		lSBQuery.append(" SELECT * FROM TRN_EMP_GPF_ACC tr INNER JOIN MST_GPF_YEARLY yr on yr.SEVAARTH_ID = tr.SEVAARTH_ID ");
		lSBQuery.append("where tr.STATUS_FLAG='A' AND TR.PC_FLAG='Y' AND EDIT_FLAG in ('SE','TH') AND YR.SEVAARTH_ID='"+lStrSevaarthId+"' ");


		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		savedRA = lQuery.list();
		logger.error("**** requestDataAlreadyExistsDraft *******"+lSBQuery.toString());


	} catch (Exception e) {
		logger.error("Exception in requestDataAlreadyExistsDraft of GPFAdvanceProcessDAOImpl  : ", e);
	}
	if (savedRA.size() > 0) {
		return true;
	} else {
		return false;
	}
}

public boolean isSecondIserviceEnd(String lStrSevaarthId, String EMPname) {
	List savedRA = new ArrayList();
	StringBuilder lSBQuery = new StringBuilder();
	try {
		/*lSBQuery.append("select MGA.transactionId");
		lSBQuery.append(" FROM MstGpfAdvance MGA");
		lSBQuery.append(" WHERE MGA.gpfAccNo = :gpfAccNo AND MGA.statusFlag LIKE 'F%'");*/

		//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','R','DR')");
		//lSBQuery.append(" SELECT * FROM MST_GPF_ADVANCE where SEVAARTH_ID='"+lStrSevaarthId+"' and STATUS_FLAG in ('D','F','F1','F2','F3','F4','R','DR')");
		/*lSBQuery.append(" SELECT * FROM TRN_EMP_GPF_ACC tr INNER JOIN MST_GPF_YEARLY yr on yr.SEVAARTH_ID = tr.SEVAARTH_ID ");
		lSBQuery.append("where tr.STATUS_FLAG='A' AND TR.PC_FLAG='Y' AND EDIT_FLAG='SE' AND YR.SEVAARTH_ID='"+lStrSevaarthId+"' ");
*/
		lSBQuery.append(" SELECT org.EMP_SRVC_EXP FROM MST_DCPS_EMP emp ");
		lSBQuery.append(" INNER JOIN ORG_EMP_MST org on org.EMP_ID=emp.ORG_EMP_MST_ID ");
		lSBQuery.append(" INNER JOIN TRN_EMP_GPF_ACC tr on tr.SEVAARTH_ID=EMP.SEVARTH_ID AND tr.STATUS_FLAG='A' AND TR.PC_FLAG='Y' ");
		lSBQuery.append(" where SEVARTH_ID='"+lStrSevaarthId+"' and year(org.EMP_SRVC_EXP) - YEAR(sysdate) in (0,1,-2,-1,-3) ");
		
		
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		savedRA = lQuery.list();
		logger.error("**** requestDataAlreadyExistsDraft *******"+lSBQuery.toString());


	} catch (Exception e) {
		logger.error("Exception in requestDataAlreadyExistsDraft of GPFAdvanceProcessDAOImpl  : ", e);
	}
	if (savedRA.size() > 0  && !savedRA.isEmpty()) {
		return true;
	} else {
		return false;
	}
}

public Long getCountThirdIntPaidOrNot(String strSevaarthId) {
	List location = null;
	Long SEVENPC_GPF_ARR_3RD_GRP_D = 0l;

	StringBuilder lSBQuery = new StringBuilder();

	try {
		lSBQuery.append(" SELECT nvl(paybill.SEVENPC_GPF_ARR_3RD_GRP_D,0) "); 
		lSBQuery.append(" FROM MST_EMP_GPF_ACC emp inner join mst_dcps_emp mst on emp.MST_GPF_EMP_ID = mst.DCPS_EMP_ID 	inner join hr_eis_emp_mst eis on eis.EMP_MPG_ID = mst.ORG_EMP_MST_ID  "); 
		lSBQuery.append(" inner join HR_PAY_PAYBILL paybill on paybill.EMP_ID = eis.EMP_ID inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID = paybill.PAYBILL_GRP_ID and head.APPROVE_FLAG = 1    "); 
		lSBQuery.append(" where emp.SEVAARTH_ID ='"+strSevaarthId+"' and  paybill.SEVENPC_GPF_ARR_3RD_GRP_D>0 "); 

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		location = lQuery.list();
		if (location.size() != 0) {
			SEVENPC_GPF_ARR_3RD_GRP_D = Long.valueOf(location.get(0).toString());
		}
	} catch (Exception e) {
		logger.error("Exception in getLocationCode : ", e);
	}
	return SEVENPC_GPF_ARR_3RD_GRP_D;

}

	 /*public List getEmployeeDetailes(String lStrSevaarthId,String gStrLocationCode) {

				//String disbursementTypeOfDBandAdvSubType = "";
				List lLstData = null;
				try{
					Session session = getSession();
					StringBuilder lSBQuery = new StringBuilder();
					lSBQuery.append("SELECT emp.sevarth_id,emp.emp_name,emp.ddo_code,ddo.DDO_NAME,gpf.GPF_ACC_NO,desg.DSGN_NAME FROM MST_DCPS_EMP emp ");
					lSBQuery.append(" INNER JOIN MST_EMP_GPF_ACC gpf on gpf.SEVAARTH_ID=emp.sevarth_id ");
					lSBQuery.append(" inner join ORG_DDO_MST ddo on ddo.ddo_code=emp.DDO_CODE ");
					lSBQuery.append(" inner join ORG_DESIGNATION_MST desg on desg.DSGN_ID=emp.designation ");
					lSBQuery.append(" where substr(ddo.DDO_CODE,1,4)='"+gStrLocationCode+"' and emp.SEVARTH_ID='"+lStrSevaarthId+"' ");

					Query query = session.createSQLQuery(lSBQuery.toString());

					if (query != null)
					{
						lLstData = query.list();
					}

					this.logger.info("list size is..." + lLstData.size());


				}catch (Exception e) {
					logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
				}
				return lLstData;
			}

public List getEmployeeDetailesLNA(String lStrSevaarthId,String gStrLocationCode) {

				//String disbursementTypeOfDBandAdvSubType = "";
				List lLstData = null;
				try{
					Session session = getSession();
					StringBuilder lSBQuery = new StringBuilder();
					lSBQuery.append("SELECT emp.sevarth_id,emp.emp_name,emp.ddo_code,ddo.DDO_NAME,desg.DSGN_NAME FROM MST_DCPS_EMP emp ");
					lSBQuery.append(" inner join ORG_DDO_MST ddo on ddo.ddo_code=emp.DDO_CODE ");
					lSBQuery.append(" inner join ORG_DESIGNATION_MST desg on desg.DSGN_ID=emp.designation ");
					lSBQuery.append(" where substr(ddo.DDO_CODE,1,4)='"+gStrLocationCode+"' and emp.SEVARTH_ID='"+lStrSevaarthId+"' ");

					Query query = session.createSQLQuery(lSBQuery.toString());

					if (query != null)
					{
						lLstData = query.list();
					}

					this.logger.info("list size is..." + lLstData.size());


				}catch (Exception e) {
					logger.error("Exception in getDisbursementTypeOfDB  : ", e);			
				}
				return lLstData;
			}	  */
 

/*		 public List getReportType()
{
  String query = " SELECT lookupId,lookupName FROM CmnLookupMst where parentLookupId = 10001198435 ";
  List<Object> lLstReturnList = null;
  StringBuilder sb = new StringBuilder();
  sb.append(query);
  Query selectQuery = this.ghibSession.createQuery(sb.toString());
  List lLstResult = selectQuery.list();
  ComboValuesVO lObjComboValuesVO = null;
  if ((lLstResult != null) && (lLstResult.size() != 0))
  {
    lLstReturnList = new ArrayList();
    for (int liCtr = 0; liCtr < lLstResult.size(); liCtr++)
    {
      Object[] obj = (Object[])lLstResult.get(liCtr);
      lObjComboValuesVO = new ComboValuesVO();
      lObjComboValuesVO.setId(obj[0].toString());
      lObjComboValuesVO.setDesc(obj[1].toString());
      lLstReturnList.add(lObjComboValuesVO);
    }
  }
  else
  {
    lLstReturnList = new ArrayList();
    lObjComboValuesVO = new ComboValuesVO();
    lObjComboValuesVO.setId("-1");
    lObjComboValuesVO.setDesc("--Select--");
    lLstReturnList.add(lObjComboValuesVO);
  }
  return lLstReturnList;
}

public List getYearsAG()
{
  List<String> lLstResult = null;
  ComboValuesVO lObjComboValueVO = null;
  List<ComboValuesVO> lLstYears = new ArrayList();
  try
  {
    this.ghibSession = ServiceLocator.getServiceLocator().getSessionFactory().getCurrentSession();
    StringBuffer lSBQuery = new StringBuffer("select finYearCode from SgvcFinYearMst where finYearCode between '2012' and '2021' order by finYearCode");
    Query lQuery = this.ghibSession.createQuery(lSBQuery.toString());
    
    lLstResult = lQuery.list();
    if ((lLstResult != null) && (!lLstResult.isEmpty())) {
      for (String lStrYearCode : lLstResult) {
        if (lStrYearCode != null)
        {
          lObjComboValueVO = new ComboValuesVO();
          lObjComboValueVO.setId(lStrYearCode);
          lObjComboValueVO.setDesc(lStrYearCode);
          lLstYears.add(lObjComboValueVO);
        }
      }
    }
  }
  catch (Exception e)
  {
    throw e;
  }
  return lLstYears;
}*/
}
