package com.tcs.sgv.gpf.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import com.ibm.icu.util.Calendar;
import com.tcs.sgv.acl.login.valueobject.LoginDetails;
import com.tcs.sgv.common.business.reports.DefaultReportDataFinder;
import com.tcs.sgv.common.business.reports.ReportDataFinder;
import com.tcs.sgv.common.dao.reports.ReportsDAO;
import com.tcs.sgv.common.dao.reports.ReportsDAOImpl;
import com.tcs.sgv.common.exception.reports.ReportException;
import com.tcs.sgv.common.util.reports.IReportConstants;
import com.tcs.sgv.common.valuebeans.reports.ReportVO;
import com.tcs.sgv.common.valuebeans.reports.StyleVO;
import com.tcs.sgv.common.valuebeans.reports.TabularData;
import com.tcs.sgv.common.valuebeans.reports.URLData;
import com.tcs.sgv.core.service.ServiceLocator;

public class GPFOrderGenReport extends DefaultReportDataFinder implements ReportDataFinder {
			 

	private static final Logger gLogger = Logger.getLogger("GPFOrderGenReport");
	public static String newline = System.getProperty("line.separator");
	ServiceLocator serviceLocator = null;
	SessionFactory gObjSessionFactory = null;
	String gStrLocCode = null;
	Long gLngLangId = null;
	Map lMapSeriesHeadCode = null;
	Session ghibSession = null;
	//private ResourceBundle gObjRsrcBndle = ResourceBundle.getBundle("resources/lna/LNAConstants");
	private ResourceBundle gObjRsrcBndleGpfReports = ResourceBundle.getBundle("resources/gpf/GpfReportsMarathi");
	@SuppressWarnings("unchecked")
	public Collection findReportData(ReportVO lObjReport, Object criteria) throws ReportException {
		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;
		Map lMapRequestAttributes = null;
		Map lMapSessionAttributes = null;
		LoginDetails lObjLoginVO = null;
		ReportVO RptVo = null;  
		ReportsDAO reportsDao = new ReportsDAOImpl();
		ArrayList tr = null;
		ArrayList td = new ArrayList();
		ArrayList rptList1 = null;
		TabularData rptTd = null;
		SimpleDateFormat lObjDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat lObjDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<Object> rowList = null;
		ArrayList<Object> dataList = new ArrayList<Object>();
		List lLstDataList = new ArrayList();
		HashMap map = null;
		try{
			lMapRequestAttributes = (Map) ((Map) criteria).get(IReportConstants.REQUEST_ATTRIBUTES);
			lMapSessionAttributes = (Map) ((Map) criteria).get(IReportConstants.SESSION_KEYS);
			Map sessionKeys = (Map) ((Map) criteria).get(IReportConstants.SESSION_KEYS);
			lObjLoginVO = (LoginDetails) lMapSessionAttributes.get("loginDetails");
			Map loginDetail = (HashMap) sessionKeys.get("loginDetailsMap");
			serviceLocator = (ServiceLocator) lMapRequestAttributes.get("serviceLocator");
			gObjSessionFactory = serviceLocator.getSessionFactorySlave();
			gStrLocCode = lObjLoginVO.getLocation().getLocationCode();
			gLngLangId = lObjLoginVO.getLangId();
			ghibSession = gObjSessionFactory.getCurrentSession();
			Long lLngPostId =  (Long) loginDetail.get("loggedInPost");
			con = gObjSessionFactory.getCurrentSession().connection();
			smt = con.createStatement();
			StyleVO[] noBorder = new StyleVO[2];
			noBorder[0] = new StyleVO();
			noBorder[0].setStyleId(IReportConstants.BORDER);
			noBorder[0].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
			noBorder[1] = new StyleVO();
			noBorder[1].setStyleId(IReportConstants.REPORT_PAGINATION);
			noBorder[1].setStyleValue("NO");
			StyleVO[] RightAlignVO = new StyleVO[2];
			RightAlignVO[0] = new StyleVO();
			RightAlignVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			RightAlignVO[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_RIGHT);
			RightAlignVO[1] = new StyleVO();
			RightAlignVO[1].setStyleId(IReportConstants.BORDER);
			RightAlignVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);

			StyleVO[] boldVO = new StyleVO[2];
			boldVO[0] = new StyleVO();
			boldVO[0].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			boldVO[0].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
			boldVO[1] = new StyleVO();
			boldVO[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			boldVO[1].setStyleValue("10");

			StyleVO[] LeftAlign = new StyleVO[1];
			LeftAlign[0] = new StyleVO();
			LeftAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			LeftAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_LEFT);

			StyleVO[] rightAlign = new StyleVO[1];
			rightAlign[0] = new StyleVO();
			rightAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			rightAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_RIGHT);
			SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar currentDate = Calendar.getInstance();
			int year = currentDate.get(Calendar.YEAR);
			int firstYear = 0;
			int lastYear = 0;
			gLogger.info("Current Year is"+ year);
			int month =currentDate.get(Calendar.MONTH)+1;
			gLogger.info("Current Month"+ month);
			gLogger.info("Current Date is" + Calendar.DATE);
			List startEndDates =null;
			//SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(lObjReport.getReportCode().equalsIgnoreCase("80000926")){
				gLogger.info("Inside report 80000926");
				String strFromDate = lObjReport.getParameterValue("Datefrom").toString().trim();
				String strToDate = lObjReport.getParameterValue("Dateto").toString().trim();
				gLogger.info("Start Date is"+strFromDate);
				gLogger.info("End Date is"+strToDate);
				
				String fromDate = "";
				String toDate = "";
				Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(strFromDate);  
				Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(strToDate);
				fromDate = ymdFormat.format(date1);
				toDate = ymdFormat.format(date2);
				gLogger.info("Start Date is"+fromDate);
				gLogger.info("End Date is"+toDate);
				
				List orderDetails = null;
				String lPostId = lLngPostId.toString();
				gLogger.info(" lPostId"+lPostId );
				orderDetails = getOrderDetails(lPostId,fromDate,toDate);
				Iterator<Object> iter = orderDetails.iterator();
				Object[] tuple = null;
				while(iter.hasNext())
				{
					String AdvType = "";
					String sevId = "";
					String transId = "";
					String appDate = "";
					String strempName = "";
					String gpfAccNo = "";
					String orderNo = "";
					String sancAmount = "";
					String basicPay = "";
					String joinDate = "";
					String superAnDate = "";
					String orderDate = "";
					String ddoCode = "";
					String special90 = "";
					
					tuple = (Object []) iter.next();
					List tdd=new ArrayList();				
					if(tuple[5]!= null)
					{
						AdvType = tuple[5].toString();
					}
					if(AdvType.equalsIgnoreCase("CS"))
					{
						AdvType="Conversion";
					}
					else if(AdvType.equalsIgnoreCase("RA"))
					{
						AdvType="Refundable";

					}
					else if(AdvType.equalsIgnoreCase("NRA"))
					{
						AdvType="Non-Refundable";

					}
					else if(AdvType.equalsIgnoreCase("FW"))
					{
						AdvType="Final";

					}
					
					if(tuple[0]!= null)
					{
						transId = tuple[0].toString(); 
					}					
					if(tuple[1]!= null)
					{
						appDate = tuple[1].toString();
					}					
					if(tuple[2]!= null)
					{
						sevId = tuple[2].toString();
					}					
					if(tuple[3]!= null)
					{
						strempName = tuple[3].toString();
					}				
					if(tuple[4]!= null)
					{
						gpfAccNo = tuple[4].toString();
					}										
					if(tuple[6]!= null)
					{
						sancAmount = tuple[6].toString();
					}					
					if(tuple[7]!= null)
					{
						orderNo = tuple[7].toString();
					}
					if(tuple[8]!= null)
					{
						basicPay = tuple[8].toString();
					}
					if(tuple[9]!= null)
					{
						joinDate = tuple[9].toString();
					}
					if(tuple[10]!= null)
					{
						superAnDate = tuple[10].toString();
					}
					if(tuple[11]!= null)
					{
						orderDate = tuple[11].toString();
					}
					if(tuple[12]!= null)
					{
						ddoCode = tuple[12].toString();
					}
					if(tuple[13]!= null)
					{
						special90 = tuple[13].toString();
					}
					tdd.add(transId);
					tdd.add(appDate);
					tdd.add(sevId);
					tdd.add(strempName);
					tdd.add(gpfAccNo);
					tdd.add(AdvType);
					tdd.add(sancAmount);
					tdd.add(orderNo);
					Double amt = Double.parseDouble(sancAmount);
					if (AdvType.equalsIgnoreCase("Refundable")) {
						if (amt > 0) {
							//gLogger.info("Outside amt amt > 276 0");
							gLogger.info("Outside amt empName"+strempName);
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=800007&action=generateReport&reqType="+ AdvType+ "&gpfAccNo="+gpfAccNo+ "&sancAmount="+ sancAmount+"&basicSalary="+ basicPay+"&orderNo="+orderNo+"&joiningDate="+joinDate+"&superAnnDate="+superAnDate+"&orderDate="+orderDate+"&transactionId="+transId+"&ddocode="+ddoCode+"&sevaarthId="+sevId+ "&asPopup=TRUE" ));
						}
						else{
							gLogger.info("Inside amt");
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=9000353&action=generateReport&reqType="+AdvType+"&gpfAccNo="+gpfAccNo+ "&sancAmount="+ sancAmount+ "&basicSalary="+ basicPay+ "&orderNo="+ orderNo+ "&joiningDate="+ joinDate+ "&superAnnDate="+ superAnDate+ "&orderDate="+ orderDate+ "&transactionId="+ transId+ "&ddocode="+ ddoCode+ "&sevaarthId="+ sevId ));
						}
					}
					else
					{
						if(special90.equalsIgnoreCase("10001198394"))
						{
							gLogger.info("Inside 11");
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=9000354&action=generateReport&reqType="+AdvType+"&gpfAccNo="+gpfAccNo+ "&sancAmount="+ sancAmount+ "&basicSalary="+ basicPay+ "&orderNo="+ orderNo+ "&joiningDate="+ joinDate+ "&superAnnDate="+ superAnDate+ "&orderDate="+ orderDate+ "&transactionId="+ transId+ "&ddocode="+ ddoCode+ "&sevaarthId="+ sevId+ "&specialcase90="+special90));
						}
						else if(AdvType.equalsIgnoreCase("Conversion"))
						{
							gLogger.info("Inside a22");
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=80000655&action=generateReport&reqType="+AdvType+"&gpfAccNo="+gpfAccNo+ "&sancAmount="+ sancAmount+ "&basicSalary="+ basicPay+ "&orderNo="+ orderNo+ "&joiningDate="+ joinDate+ "&superAnnDate="+ superAnDate+ "&orderDate="+ orderDate+ "&transactionId="+ transId+ "&ddocode="+ ddoCode+ "&sevaarthId="+ sevId+ "&specialcase90="+special90 ));	
						}
						else if(special90.equalsIgnoreCase("800064"))
						{
							gLogger.info("Inside 33 "+strempName);
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=80000667&action=generateReport&reqType="+AdvType+"&gpfAccNo="+gpfAccNo+"&sancAmount="+sancAmount+"&basicSalary="+basicPay+"&orderNo="+orderNo+"&joiningDate="+joinDate+"&superAnnDate="+superAnDate+"&orderDate="+orderDate+"&transactionId="+transId+"&ddocode="+ddoCode+"&sevaarthId="+sevId+"&specialcase90="+AdvType  ));	
						}
						else
						{
							gLogger.info("Inside 44");
							tdd.add(new URLData("View order","ifms.htm?actionFlag=reportService&reportCode=9000354&action=generateReport&reqType="+AdvType+ "&gpfAccNo="+gpfAccNo+"&sancAmount="+sancAmount+"&basicSalary="+basicPay+ "&orderNo="+ orderNo+ "&joiningDate="+joinDate+ "&superAnnDate="+superAnDate+ "&orderDate="+orderDate +"&transactionId="+transId+ "&ddocode="+ddoCode+ "&sevaarthId="+sevId+ "&specialcase90=" +special90 ));	
						}
					}
					gLogger.info("Dtata Advance type "+AdvType+"Sevarth Id"+sevId+"Transaction Id"+transId+"Application Date"+appDate+"Name"+strempName+"Gpf Acc No"+gpfAccNo+"Order no"+orderNo+"Sanction amount"+sancAmount);
					gLogger.info("basicPay"+basicPay+"joinDate"+joinDate+"superAnDate"+superAnDate+"orderDate"+orderDate+"ddoCode"+ddoCode+"special90"+special90);
					gLogger.info("Start");
					lLstDataList.add(tdd);
					gLogger.info("End");
				}			
			}		
		}
		catch (Exception e)
		{
			gLogger.error("Error is : " + e, e);
		}
		return lLstDataList;
	}
	public List getOrderDetails(String locId,String fromDate,String toDate)
	{
		List orderDetails = null;
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		gLogger.info("Location code is "+locId);
		try
		{
			lSBQuery.append(" select MGA.transaction_Id,to_char(MGA.application_Date,'dd/MM/yyyy'),MG.sevaarth_Id,MGE.EMP_name,MGA.GPF_ACC_NO,MGA.ADVANCE_TYPE, ");
			lSBQuery.append(" mga.AMOUNT_SANCTIONED,MGA.ORDER_NO,MGE.BASIC_PAY,to_char(MGE.DOJ,'dd/MM/yyyy'),to_char(MGE.SUPER_ANN_DATE,'dd/MM/yyyy'),to_char(MGA.ORDER_DATE,'dd/MM/yyyy'),MG.DDO_CODE,cast(nvl(MGA.SPECIAL_CASE_90_PERCENT,'0')as varchar) ");
			lSBQuery.append(" FROM MST_GPF_ADVANCE MGA ");
			lSBQuery.append(" inner join Mst_Emp_Gpf_Acc MG on MGA.GPF_ACC_NO = MG.GPF_ACC_NO  AND MGA.SEVAARTH_ID = MG.SEVAARTH_ID ");
			lSBQuery.append(" inner join  Mst_DCPS_Emp MGE on MG.MST_GPF_EMP_ID = MGE.DCPS_EMP_ID and MGE.DCPS_OR_GPF ='N' and  MGE.EMP_group ='D' ");
			lSBQuery.append(" inner join Org_Ddo_Mst ODM on MG.ddo_Code=ODM.ddo_Code ");
			lSBQuery.append(" inner join MST_GPF_BILL_DTLS blt on blt.TRANSACTION_ID=MGA.TRANSACTION_ID ");
			lSBQuery.append(" WHERE MGA.status_Flag IN ('A','AC') and blt.STATUS_FLAG in (0,1) AND MGA.ORDER_DATE BETWEEN '"+fromDate+"' and '"+toDate+"' ");
			lSBQuery.append(" AND (MGA.FRWDRD_TO_RHO_POST_ID = '"+locId+"' or  ODM.LOCATION_CODE =(SELECT LOC_ID FROM ORG_POST_DETAILS_RLT where POST_ID= '"+locId+"')) ");
			lSBQuery.append(" AND MGA.data_Entry is null ");
			lSBQuery.append(" union all ");
			lSBQuery.append(" select TGFW.transaction_Id,to_char(TGFW.application_Date,'dd/MM/yyyy'),MG.sevaarth_Id,MGE.EMP_name,TGFW.gpf_Acc_No, ");
			lSBQuery.append(" cast(substr('FW',1,2) as varchar),TGFW.AMOUNT_SANCTIONED,TGFW.ORDER_NO,0,to_char(MGE.DOJ,'dd/MM/yyyy'),to_char(MGE.SUPER_ANN_DATE,'dd/MM/yyyy'),to_char(TGFW.ORDER_DATE,'dd/MM/yyyy'),MG.DDO_CODE,cast(nvl(TGFW.REASON,'0')as varchar) ");
			lSBQuery.append(" FROM TRN_GPF_FINAL_WITHDRAWAL TGFW ");
			lSBQuery.append(" inner join Mst_Emp_Gpf_Acc MG on TGFW.GPF_ACC_NO = MG.GPF_ACC_NO AND TGFW.SEVAARTH_ID = MG.SEVAARTH_ID ");
			lSBQuery.append(" inner join  Mst_DCPS_Emp MGE on MG.MST_GPF_EMP_ID = MGE.DCPS_EMP_ID and MGE.DCPS_OR_GPF ='N' and  MGE.EMP_group ='D' ");
			lSBQuery.append(" inner join Org_Ddo_Mst ODM on MG.ddo_Code=ODM.ddo_Code ");
			lSBQuery.append(" inner join MST_GPF_BILL_DTLS blt on blt.TRANSACTION_ID=TGFW.TRANSACTION_ID AND TGFW.ORDER_DATE BETWEEN '"+fromDate+"' and '"+toDate+"' ");
			lSBQuery.append(" WHERE TGFW.REQ_STATUS = 'A' and blt.STATUS_FLAG in (0,1) ");
			lSBQuery.append(" AND (TGFW.FRWDRD_TO_RHO_POST_ID = '"+locId+"' or  ODM.LOCATION_CODE =(SELECT LOC_ID FROM ORG_POST_DETAILS_RLT where POST_ID= '"+locId+"')) ");
			lSBQuery.append(" union all ");//add by kavita conversion adv type
			lSBQuery.append(" select MGA.transaction_Id,to_char(MGA.application_Date,'dd/MM/yyyy'),MG.sevaarth_Id,MGE.EMP_name,MGA.GPF_ACC_NO,MGA.ADVANCE_TYPE, ");
			lSBQuery.append(" mga.AMOUNT_SANCTIONED,MGA.ORDER_NO,MGE.BASIC_PAY,to_char(MGE.DOJ,'dd/MM/yyyy'),to_char(MGE.SUPER_ANN_DATE,'dd/MM/yyyy'),to_char(MGA.ORDER_DATE,'dd/MM/yyyy'),MG.DDO_CODE,cast(nvl(MGA.SPECIAL_CASE_90_PERCENT,'0')as varchar) ");
			lSBQuery.append(" FROM MST_GPF_ADVANCE MGA ");
			lSBQuery.append(" inner join Mst_Emp_Gpf_Acc MG on MGA.GPF_ACC_NO = MG.GPF_ACC_NO ");
			lSBQuery.append(" inner join  Mst_DCPS_Emp MGE on MG.MST_GPF_EMP_ID = MGE.DCPS_EMP_ID and MGE.DCPS_OR_GPF ='N' and  MGE.EMP_group ='D' ");
			lSBQuery.append(" inner join Org_Ddo_Mst ODM on MG.ddo_Code=ODM.ddo_Code ");
			lSBQuery.append(" WHERE MGA.status_Flag IN ('A','AC') and MGA.ADVANCE_TYPE='CS' AND MGA.ORDER_DATE BETWEEN '"+fromDate+"' and '"+toDate+"' ");
			lSBQuery.append(" AND (MGA.FRWDRD_TO_RHO_POST_ID = '"+locId+"' or  ODM.LOCATION_CODE =(SELECT LOC_ID FROM ORG_POST_DETAILS_RLT where POST_ID= '"+locId+"')) ");
			lSBQuery.append(" AND MGA.data_Entry is null ");
			lQuery =ghibSession.createSQLQuery(lSBQuery.toString());
			gLogger.info("Query is"+lSBQuery.toString());
			orderDetails = lQuery.list();
		}
		catch(Exception e)
		{
			gLogger.info("Inside getOrderDetails ");
		}
		return orderDetails;
	}
}
