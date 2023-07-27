package com.tcs.sgv.gpf.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.kohsuke.rngom.parse.Parseable;

import java.util.Iterator;


//import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.tcs.sgv.acl.login.valueobject.LoginDetails;
import com.tcs.sgv.common.business.reports.ReportDataFinder;
import com.tcs.sgv.common.dao.FinancialYearDAO;
import com.tcs.sgv.common.dao.FinancialYearDAOImpl;
import com.tcs.sgv.common.dao.reports.DefaultReportDataFinder;
import com.tcs.sgv.common.dao.reports.ReportsDAO;
import com.tcs.sgv.common.dao.reports.ReportsDAOImpl;
import com.tcs.sgv.common.exception.reports.ReportException;
import com.tcs.sgv.common.util.EnglishDecimalFormat;
import com.tcs.sgv.common.util.reports.IReportConstants;
import com.tcs.sgv.common.valuebeans.reports.ReportColumnVO;
import com.tcs.sgv.common.valuebeans.reports.ReportVO;
import com.tcs.sgv.common.valuebeans.reports.StyleVO;
import com.tcs.sgv.common.valuebeans.reports.StyledData;
import com.tcs.sgv.common.valuebeans.reports.TabularData;
import com.tcs.sgv.core.constant.ErrorConstants;
import com.tcs.sgv.core.service.ServiceLocator;
import com.tcs.sgv.core.valueobject.ResultObject;
import com.tcs.sgv.ess.valueobject.OrgPostMst;
import com.tcs.sgv.gpf.dao.GPFRequestProcessDAO;
import com.tcs.sgv.gpf.dao.GPFRequestProcessDAOImpl;
import com.tcs.sgv.gpf.dao.ScheduleGenerationDAO;
import com.tcs.sgv.gpf.dao.ScheduleGenerationDAOImpl;

import java.text.MessageFormat;
/*import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;*/
public class GPFOrderReport extends DefaultReportDataFinder implements ReportDataFinder {

	private static final Logger gLogger = Logger.getLogger(GPFOrderReport.class);
	public static String newline = System.getProperty("line.separator");
	String Lang_Id = "en_US";
	String Loc_Id = "LC1";

	Map requestAttributes = null;
	Map lMapSessionAttributes = null;
	ServiceLocator serviceLocator = null;
	SessionFactory lObjSessionFactory = null;
	Session ghibSession = null;
	LoginDetails lObjLoginVO = null;


	private ResourceBundle gObjRsrcBndleForRA = ResourceBundle.getBundle("resources/gpf/RefundableOrderMarathi");


	private ResourceBundle gObjRsrcBndleForNRA = ResourceBundle.getBundle("resources/gpf/NonRefundableOrderMarathi");

	private ResourceBundle gObjRsrcBndleForSPL90 = ResourceBundle.getBundle("resources/gpf/90PercentWithdrawalOrderMarathi");

	private ResourceBundle gObjRsrcBndleForFW = ResourceBundle.getBundle("resources/gpf/FinalPayOrderMarathi");

	@SuppressWarnings("unchecked")
	public Collection findReportData(ReportVO report, Object criteria) throws ReportException {

		Connection con = null;

		requestAttributes = (Map) ((Map) criteria).get(IReportConstants.REQUEST_ATTRIBUTES);
		lMapSessionAttributes = (Map) ((Map) criteria).get(IReportConstants.SESSION_KEYS);
		serviceLocator = (ServiceLocator) requestAttributes.get("serviceLocator");
		lObjSessionFactory = serviceLocator.getSessionFactorySlave();
		lObjLoginVO = (LoginDetails) lMapSessionAttributes.get("loginDetails");
		Map serviceMap = (Map)requestAttributes.get("serviceMap");
		HttpServletRequest request = (HttpServletRequest) serviceMap.get("requestObj");
		Map sessionKeys = (Map) ((Map) criteria).get(IReportConstants.SESSION_KEYS);
		Map loginDetail = (HashMap) sessionKeys.get("loginDetailsMap");
		Map inputMap = new HashMap();

		List<OrgPostMst> lLngPostIdList = (List) loginDetail.get("postIdList");
		OrgPostMst lObjPostMst = lLngPostIdList.get(0);
		Long lLngPostId = lObjPostMst.getPostId();

		Statement smt = null;
		ResultSet rs = null;
		TabularData td = null;
		ReportVO RptVo = null;
		MessageFormat msgFormatter = null;
		ReportsDAO reportsDao = new ReportsDAOImpl();

		ArrayList<Object> dataList = new ArrayList<Object>();
		try {

			con = lObjSessionFactory.getCurrentSession().connection();
			ghibSession = lObjSessionFactory.getCurrentSession();
			smt = con.createStatement();





			StyleVO[] rowsFontsVO = new StyleVO[5];
			rowsFontsVO[0] = new StyleVO();
			rowsFontsVO[0].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			rowsFontsVO[0].setStyleValue("12");
			rowsFontsVO[1] = new StyleVO();
			rowsFontsVO[1].setStyleId(IReportConstants.BACKGROUNDCOLOR);
			rowsFontsVO[1].setStyleValue("white");
			rowsFontsVO[2] = new StyleVO();
			rowsFontsVO[2].setStyleId(IReportConstants.BORDER);
			rowsFontsVO[2].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
			rowsFontsVO[3] = new StyleVO();
			rowsFontsVO[3].setStyleId(26);
			rowsFontsVO[3].setStyleValue("JavaScript:self.close()");
			rowsFontsVO[4] = new StyleVO();
			rowsFontsVO[4].setStyleId(IReportConstants.REPORT_PAGINATION);
			rowsFontsVO[4].setStyleValue("NO");

			StyleVO[] noBorder = new StyleVO[1];
			noBorder[0] = new StyleVO();
			noBorder[0].setStyleId(IReportConstants.BORDER);
			noBorder[0].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);

			report.setStyleList(rowsFontsVO);
			report.initializeDynamicTreeModel();
			report.initializeTreeModel();
			report.setStyleList(rowsFontsVO);

			StyleVO[] centerUnderlineBold = new StyleVO[4];
			centerUnderlineBold[0] = new StyleVO();
			centerUnderlineBold[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			centerUnderlineBold[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			centerUnderlineBold[1] = new StyleVO();
			centerUnderlineBold[1].setStyleId(IReportConstants.STYLE_TEXT_DECORATION);
			centerUnderlineBold[1].setStyleValue(IReportConstants.VALUE_STYLE_TEXT_DECORATION_UNDERLINE);
			centerUnderlineBold[2] = new StyleVO();
			centerUnderlineBold[2].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			centerUnderlineBold[2].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
			centerUnderlineBold[3] = new StyleVO();
			centerUnderlineBold[3].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			centerUnderlineBold[3].setStyleValue("14");

			StyleVO[] rightAlign = new StyleVO[2];
			rightAlign[0] = new StyleVO();
			rightAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			rightAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_RIGHT);
			rightAlign[1] = new StyleVO();
			rightAlign[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			rightAlign[1].setStyleValue("12");

			StyleVO[] leftAlign = new StyleVO[2];
			leftAlign[0] = new StyleVO();
			leftAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			leftAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_LEFT);
			leftAlign[1] = new StyleVO();
			leftAlign[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			leftAlign[1].setStyleValue("12");

			StyleVO[] boldVO = new StyleVO[2];
			boldVO[0] = new StyleVO();
			boldVO[0].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			boldVO[0].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
			boldVO[1] = new StyleVO();
			boldVO[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			boldVO[1].setStyleValue("14");

			// for Center Alignment format
			StyleVO[] CenterAlignVO = new StyleVO[3];
			CenterAlignVO[0] = new StyleVO();
			CenterAlignVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			CenterAlignVO[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			CenterAlignVO[1] = new StyleVO();
			CenterAlignVO[1].setStyleId(IReportConstants.BORDER);
			CenterAlignVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
			CenterAlignVO[2] = new StyleVO();
			CenterAlignVO[2].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			CenterAlignVO[2].setStyleValue("12");

			StyleVO[] CenterBoldAlignVO = new StyleVO[4];
			CenterBoldAlignVO[0] = new StyleVO();
			CenterBoldAlignVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			CenterBoldAlignVO[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			CenterBoldAlignVO[1] = new StyleVO();
			CenterBoldAlignVO[1].setStyleId(IReportConstants.BORDER);
			CenterBoldAlignVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
			CenterBoldAlignVO[2] = new StyleVO();
			//CenterBoldAlignVO[2].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			//	CenterBoldAlignVO[2].setStyleValue("12");
			CenterBoldAlignVO[2].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
			CenterBoldAlignVO[2].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			CenterBoldAlignVO[3] = new StyleVO();
			CenterBoldAlignVO[3].setStyleId(IReportConstants.STYLE_FONT_SIZE);
			CenterBoldAlignVO[3].setStyleValue("14");


			//ADDED BY Kiranvir
			StyleVO[] AadeshVO = new StyleVO[4];
			AadeshVO[0] = new StyleVO();
			AadeshVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
			AadeshVO[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			AadeshVO[1] = new StyleVO();
			AadeshVO[1].setStyleId(IReportConstants.BORDER);
			AadeshVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
			AadeshVO[3] = new StyleVO();
			AadeshVO[3].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
			AadeshVO[3].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			AadeshVO[2] = new StyleVO();
			AadeshVO[2].setStyleId(IReportConstants.FONT_SIZE);
			AadeshVO[2].setStyleValue("20");



			StyleVO[] SixMonthsVO = new StyleVO[4];
			SixMonthsVO[0] = new StyleVO();
			SixMonthsVO[0].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
			SixMonthsVO[0].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);




			if (report.getReportCode().equals("800007")) {

				String reqType = (String) report.getParameterValue("reqType");
				gLogger.error("inside 800007"+reqType);
				String lStrDisbursementNo = "1";
				if (reqType.startsWith("Non-Refundable")) {
					lStrDisbursementNo = reqType.substring(3);
					reqType = "NRA";
				}
				if (reqType.equalsIgnoreCase("Refundable")) {

					reqType = "RA";
				}

				gLogger.error("reqType***************"+reqType);

				gLogger.error("inside 800007"+reqType);

				String gpfAccNo = (String) report.getParameterValue("gpfAccNo");
				String lStrOrderId = (String) report.getParameterValue("orderNo");
				gLogger.error("lStrOrderId"+lStrOrderId);
				gLogger.error("gpfAccNo"+gpfAccNo);


				String lStrSevaarthId = (String) report.getParameterValue("sevaarthId");
				String lStrTransactionId = (String) report.getParameterValue("transactionId");
				String lStrDDOCODE123 = (String) report.getParameterValue("ddocode");
				String empName = (String) report.getParameterValue("empName");
				if(empName.equalsIgnoreCase(""))
				{
					empName = getEmployeeName(lStrSevaarthId);
				}
				//Added By Vivek Sharma 09 June 2017
				List lstRetirementDateandDOJ = getRetireMentDateandDOJ(lStrSevaarthId);
				String strSuperAnndate = "";
				String strDOj = "";
				String strSysdate = "";
				String strAge = "";
				int strRDay = 0;
				int strRMonth = 0;
				int strRYear = 0;
				int strJoinDay = 0; 
				int strJoinMonth = 0; 
				int strJoinYear = 0;
				int strCurrDay =   0;
				int strCurrMonth =  0;
				int strCurrYear =  0;

				Iterator it = lstRetirementDateandDOJ.iterator();
				while (it.hasNext()) {
					Object[] tuple = (Object[]) it.next();
					if(!tuple[0].toString().equalsIgnoreCase(null)){
						strSuperAnndate = tuple[0].toString();
						gLogger.error("strSuperAnndate***************** "+strSuperAnndate);
						String[] strRDate = strSuperAnndate.split("/");
						strRDay = Integer.parseInt(strRDate[0]);
						strRMonth = Integer.parseInt(strRDate[1]); 
						strRYear = Integer.parseInt(strRDate[2]);
					}
					if(!tuple[1].toString().equalsIgnoreCase(null)){
						strDOj = tuple[1].toString();
						gLogger.error("strDOj***************** "+strDOj);
						String[] strJoinDate = strDOj.split("/");
						strJoinDay = Integer.parseInt(strJoinDate[0]);
						strJoinMonth = Integer.parseInt(strJoinDate[1]);
						strJoinYear = Integer.parseInt(strJoinDate[2]);

					}
					if(!tuple[2].toString().equalsIgnoreCase(null)){
						strSysdate = tuple[2].toString();
						gLogger.error("strSysdate***************** "+strSysdate);
						String[] strCurrDate = strSysdate.split("/");
						strCurrDay = Integer.parseInt(strCurrDate[0]);
						strCurrMonth = Integer.parseInt(strCurrDate[1]);
						strCurrYear = Integer.parseInt(strCurrDate[2]);

					}




				}

				gLogger.error("strRDay***************** "+strRDay);
				gLogger.error("strRMonth***************** "+strRMonth);
				gLogger.error("strRYear***************** "+strRYear);
				gLogger.error("strJoinDay***************** "+strJoinDay);
				gLogger.error("strJoinMonth***************** "+strJoinMonth);
				gLogger.error("strJoinYear***************** "+strJoinYear);
				gLogger.error("strCurrDay***************** "+strCurrDay);
				gLogger.error("strCurrMonth***************** "+strCurrMonth);
				gLogger.error("strCurrYear***************** "+strCurrYear);
				int ServiceMonth = 0;
				int ServiceYear = 0;
				int remainMonth = 0;
				int remainYear = 0;
				if(strJoinMonth < strCurrMonth){
					ServiceMonth = strCurrMonth - strJoinMonth;
					ServiceYear = strCurrYear - strJoinYear;
				}
				else if(strJoinMonth > strCurrMonth){
					ServiceMonth = (strCurrMonth+12) - strJoinMonth;
					ServiceYear = (strCurrYear-1) - strJoinYear;
				}

				if(strRMonth > strCurrMonth ){
					remainMonth = strRMonth - strCurrMonth;
					remainYear = strRYear - strCurrYear;
				}
				else if(strRMonth < strCurrMonth){
					remainMonth = (strRMonth+12) - strCurrMonth;
					remainYear = (strRYear-1) - strCurrYear;
				}

				gLogger.error("ServiceMonth*****************"+ServiceMonth);
				gLogger.error("ServiceYear*****************"+ServiceYear);
				gLogger.error("remainMonth*****************"+remainMonth);
				gLogger.error("remainYear*****************"+remainYear);



				//Ended By Vivek Sharma 09 June 2017

				gLogger.error("lStrDDOCODE*****************"+lStrDDOCODE123);


				String lStrPurposeOfNra = (String) request.getParameter("purposeOfNra");

				gLogger.error("lStrTransactionId"+lStrTransactionId);

				Long sancAmount = 0L;
				if (report.getParameterValue("sancAmount") != "" && report.getParameterValue("sancAmount") != null) {
					sancAmount = Math.round(Double.parseDouble((String) report.getParameterValue("sancAmount")));
					gLogger.error("sancAmount1"+sancAmount);



				}

				GPFRequestProcessDAO lObjGPFRequestProcess = new GPFRequestProcessDAOImpl(null, serviceLocator.getSessionFactory());				

				Double d = Double.parseDouble((String) report.getParameterValue("sancAmount"));
				int sm = (int)d.doubleValue();

				gLogger.error("sm"+sm);
				

				/*StringBuffer strEmpIntls = null;
				if(empName!=null && !empName.isEmpty()){
					String[] strNameAry = empName.split(" ");
					strEmpIntls = new StringBuffer();
					for(int i=0;i<strNameAry.length;i++)
						strEmpIntls.append(strNameAry[i].charAt(0));
					}*/

				//code added for employee initials by kiranvir on may 21



				String strEmpIntls = "";
				String lStrFirstname = "";
				String lStrMiddlename = "";
				String lStrLastname = "";

				List initials = lObjGPFRequestProcess.getInitials(lStrSevaarthId);

				if (initials != null && initials.size() > 0) {

					Object[] lArrObj = (Object[]) initials.get(0);

					if(lArrObj[0] != null){
						lStrFirstname = lArrObj[0].toString();
					}
					if(lArrObj[1] != null){
						lStrMiddlename = lArrObj[1].toString();
					}
					if(lArrObj[2] != null){
						lStrLastname = lArrObj[2].toString();
					}
				}

				if(lStrFirstname != "" && lStrMiddlename != "" && lStrLastname != ""){
					strEmpIntls = lStrFirstname.substring(0,1) + lStrMiddlename.substring(0,1) + lStrLastname.substring(0,1);
				}
				if(lStrFirstname != "" && lStrMiddlename == "" && lStrLastname != ""){
					strEmpIntls = lStrFirstname.substring(0,1) + lStrLastname.substring(0,1);
				}
				if(lStrFirstname != "" && lStrMiddlename != "" && lStrLastname == ""){
					strEmpIntls = lStrFirstname.substring(0,1) + lStrMiddlename.substring(0,1);
				}
				if(lStrFirstname != "" && lStrMiddlename == "" && lStrLastname == ""){
					strEmpIntls = lStrFirstname.substring(0,1);
				}
				if(lStrFirstname == "" && lStrMiddlename != "" && lStrLastname != ""){
					strEmpIntls =  lStrMiddlename.substring(0,1) + lStrLastname.substring(0,1);
				}
				if(lStrFirstname == "" && lStrMiddlename == "" && lStrLastname == ""){
					strEmpIntls = "";
				}


				gLogger.error("lStrFirstname"+lStrFirstname);
				gLogger.error("lStrMiddlename"+lStrMiddlename);
				gLogger.error("lStrLastname"+lStrLastname);
				gLogger.error("strEmpIntls"+strEmpIntls);

				SimpleDateFormat lObjDateFormate = new SimpleDateFormat("dd/MM/yyyy");
				Date today = new Date();



				String lStrJoiningDate = (String)report.getParameterValue("joiningDate");
				String lStrOrderDate = (String) report.getParameterValue("orderDate");
				String lStrRetirementDate = (String) report.getParameterValue("superAnnDate");


				Integer retirementMonths = null ;
				Integer serviceMonths = null;

				gLogger.info("lStrRetirementDate********"+lStrRetirementDate);
				gLogger.info("retirementMonths********"+retirementMonths);
				gLogger.info("serviceMonths********"+serviceMonths);
				gLogger.info("lStrJoiningDate********"+lStrJoiningDate);
				gLogger.info("lStrOrderDate********"+lStrOrderDate);

				Date joiningDate = lObjDateFormate.parse(lStrJoiningDate);
				Date retirementDate = lObjDateFormate.parse(lStrRetirementDate);

				gLogger.error("A retirementDate 374***** "+retirementDate);

				//getDateDifference(joiningDate, retirementDate);
				int a = Math.abs(retirementDate.getMonth());
				gLogger.error("A retirementDate**********  "+a);

				int b = today.getMonth();
				gLogger.error("B retirementDate **** "+b);

				int noOfMonthLeft = 0;

				if(a > b)
				{
					gLogger.error("noOfMonthLeft 387");
					noOfMonthLeft = ((12 - b) - (12 - a));
					gLogger.error("noOfMonthLeft"+noOfMonthLeft);
				}
				else if(b > a)
				{ 
					gLogger.error("noOfMonthLeft 393");
					noOfMonthLeft = ((12 - a) - (12 - b));
					gLogger.error("noOfMonthLeft"+noOfMonthLeft);
				}

				//	int days= retirementDate.getDate() 
				//gokarna
				int noOfYearsLeft=0;
				gLogger.error("noOfMonthLeft 401"+noOfMonthLeft);

				if(noOfMonthLeft<12)
				{
					gLogger.error("noOfYearsLeft 405");
					noOfYearsLeft=0;


				}

				else if( noOfMonthLeft>12 )

				{
					gLogger.error("noOfMonthLeft414****"+retirementDate);
					gLogger.error("today418*****"+today);
					noOfYearsLeft = retirementDate.getYear() - today.getYear();


				}
				else if(noOfMonthLeft==12 )

				{
					gLogger.error("noOfMonthLeft414****"+retirementDate);
					noOfYearsLeft=1;


				}
				//if()

				gLogger.error("noOfYearsLeft"+noOfYearsLeft);

				int noOfMonthServed = Math.abs(today.getMonth() - joiningDate.getMonth());
				gLogger.error("noOfMonthServed"+noOfMonthServed);

				int noOfYearsServed = today.getYear() - joiningDate.getYear();
				gLogger.error("noOfYearsServed"+noOfYearsServed);


				Long lLongnoOfYearsServed = Long.parseLong(String.valueOf(noOfYearsServed));
				Long lLongnoOfMonthServed = Long.parseLong(String.valueOf(noOfMonthServed));


				FinancialYearDAO lObjFinancialYearDAO = new FinancialYearDAOImpl(null, serviceLocator.getSessionFactory());

				//Integer lIntCurrFinYearId = lObjFinancialYearDAO.getFinYearIdByCurDate();
				//gLogger.info("lIntCurrFinYearId"+lIntCurrFinYearId);



				Integer lIntFinYrId = lObjFinancialYearDAO.getFinYearIdByCurDate();


				Long lLngPreFinYearId = lIntFinYrId.longValue() - 1;
				Long lLngCurrFinYearId = lIntFinYrId.longValue();

				Integer PayableYrSixPay0910 = 27;
				Integer PayableYrSixPay1011 = 28;
				Integer PayableYrSixPay1112 = 29;
				Integer PayableYrSixPay1213 = 30;
				Integer PayableYrSixPay1314 = 31;

				Integer PayableYrSPay0910 = 34;
				Integer PayableYrSPay1011 = 35;
				Integer PayableYrSPay1112 = 36;
				Integer PayableYrSPay1213 = 37;
				Integer PayableYrSPay1314 = 38;

				String lStrSixPay0910 = "";
				String lStrSixPay1011 = "";
				String lStrSixPay1112 = "";
				String lStrSixPay1213 = "";
				String lStrSixPay1314 = "";

				String lStrIntSixPay0910 = "0";
				String lStrIntSixPay1011 = "0";
				String lStrIntSixPay1112 = "0";
				String lStrIntSixPay1213 = "0";
				String lStrIntSixPay1314 = "0";

			String isSevenpc="";

				//List SixPayAmounts = lObjGPFRequestProcess.getSixPayAmounts(gpfAccNo,lIntFinYrId.longValue());
				List SixPayAmounts = lObjGPFRequestProcess.getSixPayAmounts(gpfAccNo,lIntFinYrId.longValue(),lStrSevaarthId); //swt 01/06/2020
				int size = SixPayAmounts.size();
				gLogger.info("size"+size);
				if (SixPayAmounts != null && SixPayAmounts.size() > 0) {

					Object[] lArrObj = (Object[]) SixPayAmounts.get(0);

					if (lArrObj[10] != null) {
						isSevenpc = lArrObj[10].toString();
						gLogger.info("isSevenpc:::::::::"
										+ isSevenpc);
					
						inputMap.put("isSevenpc", isSevenpc);
					}
				}
if(isSevenpc.equalsIgnoreCase("Y")){
				if (SixPayAmounts != null && SixPayAmounts.size() > 0) {

					Object[] lArrObj = (Object[]) SixPayAmounts.get(0);

					if(lArrObj[5] != null){
						lStrSixPay0910 = lArrObj[5].toString();
						gLogger.info("lStrSixPay0910:::::::::"+lStrSixPay0910);
					}

					if(lArrObj[6] != null){
						lStrSixPay1011 = lArrObj[6].toString();
						gLogger.info("lStrSixPay1011:::::::::"+lStrSixPay1011);
					}

					if(lArrObj[7] != null){
						lStrSixPay1112 = lArrObj[7].toString();
						gLogger.info("lStrSixPay1112:::::::::"+lStrSixPay1112);
					}

					if(lArrObj[8] != null){
						lStrSixPay1213 = lArrObj[8].toString();
						gLogger.info("lStrSixPay1213:::::::::"+lStrSixPay1213);
					}

					if(lArrObj[9] != null){
						lStrSixPay1314 = lArrObj[9].toString();
						gLogger.info("lStrSixPay1314:::::::::"+lStrSixPay1314);
					}

				}

}else {

				if (SixPayAmounts != null && SixPayAmounts.size() > 0) {

					Object[] lArrObj = (Object[]) SixPayAmounts.get(0);

					if(lArrObj[0] != null){
						lStrSixPay0910 = lArrObj[0].toString();
						gLogger.info("lStrSixPay0910:::::::::"+lStrSixPay0910);
					}

					if(lArrObj[1] != null){
						lStrSixPay1011 = lArrObj[1].toString();
						gLogger.info("lStrSixPay1011:::::::::"+lStrSixPay1011);
					}

					if(lArrObj[2] != null){
						lStrSixPay1112 = lArrObj[2].toString();
						gLogger.info("lStrSixPay1112:::::::::"+lStrSixPay1112);
					}

					if(lArrObj[3] != null){
						lStrSixPay1213 = lArrObj[3].toString();
						gLogger.info("lStrSixPay1213:::::::::"+lStrSixPay1213);
					}

					if(lArrObj[4] != null){
						lStrSixPay1314 = lArrObj[4].toString();
						gLogger.info("lStrSixPay1314:::::::::"+lStrSixPay1314);
					}

				}

}			
//List SixPayIntAmounts = lObjGPFRequestProcess.getSixPayAmountsNew(gpfAccNo,lIntFinYrId.longValue());
				List SixPayIntAmounts = lObjGPFRequestProcess.getSixPayAmountsNew(gpfAccNo,lIntFinYrId.longValue(),lStrSevaarthId,isSevenpc);  //swt 01/06/2020
				int s = SixPayAmounts.size();
				gLogger.info("size"+size);


				if (SixPayIntAmounts != null && SixPayIntAmounts.size() > 0) {

					Object[] lArrIntObj = (Object[]) SixPayIntAmounts.get(0);

					if(lArrIntObj[0] != null){
						lStrIntSixPay0910 = lArrIntObj[0].toString();
						gLogger.info("lStrIntSixPay0910:::::::::"+lStrIntSixPay0910);
					}

					if(lArrIntObj[1] != null){
						lStrIntSixPay1011 = lArrIntObj[1].toString();
						gLogger.info("lStrIntSixPay1011:::::::::"+lStrIntSixPay1011);
					}

					if(lArrIntObj[2] != null){
						lStrIntSixPay1112 = lArrIntObj[2].toString();
						gLogger.info("lStrIntSixPay1112:::::::::"+lStrIntSixPay1112);
					}

					if(lArrIntObj[3] != null){
						lStrIntSixPay1213 = lArrIntObj[3].toString();
						gLogger.info("lStrIntSixPay1213:::::::::"+lStrIntSixPay1213);
					}

					if(lArrIntObj[4] != null){
						lStrIntSixPay1314 = lArrIntObj[4].toString();
						gLogger.info("lStrIntSirxPay1314:::::::::"+lStrIntSixPay1314);
					}

				}

				//Double lDblOpeningBal = lObjGPFRequestProcess.getOpeningBalForCurrYearNew(gpfAccNo, lIntFinYrId.longValue());
				Double lDblOpeningBal = lObjGPFRequestProcess.getOpeningBalForCurrYearNew(gpfAccNo, lIntFinYrId.longValue(),lStrSevaarthId,isSevenpc); //swt 01/06/2020
				//Sooraj 16/04/2018
				
				List OpeningBalanceAndOutstanding = null;
				Double openingBalanceApril = 0d;
				Double OutStandingAmount = 0d;
				Double AprilOpeningBalance =0d;
				Date date1 = new Date();
				int monthId = date1.getMonth();
				gLogger.info("monthId"+monthId);
				if(lDblOpeningBal == 0 && monthId == 3)
				{	
					gLogger.info("aaaaa2");
				
					//OpeningBalanceAndOutstanding = lObjGPFRequestProcess.getOpeningBalanceApril(gpfAccNo,lIntFinYrId);
					OpeningBalanceAndOutstanding = lObjGPFRequestProcess.getOpeningBalanceApril(gpfAccNo,lIntFinYrId,lStrSevaarthId,isSevenpc); //swt 01/06/2020
					if(OpeningBalanceAndOutstanding != null)
					{
						
						gLogger.info("aaaaa3");
					if(OpeningBalanceAndOutstanding.size()>0)
					{
						
						gLogger.info("aaaaa2");
					Object[] outStand  = null;
					outStand = (Object[]) OpeningBalanceAndOutstanding.get(0);
					openingBalanceApril = Double.parseDouble(outStand[0].toString());
					//OutStandingAmount   = Double.parseDouble(outStand[1].toString());
					AprilOpeningBalance = openingBalanceApril;
					lDblOpeningBal = AprilOpeningBalance ; 
					gLogger.info("openingBalanceApril is"+lDblOpeningBal);
					}
				}
				}
				
				
				//Added By Vivek Sharma 31 July 2017
			      gLogger.info("lDblOpeningBal::::::::::" + lDblOpeningBal);
			      if (lDblOpeningBal.doubleValue() == 0D)
			      {
			        gLogger.info("lDblOpeningBal::::zero::::::" + lDblOpeningBal);
			        //lDblOpeningBal = lObjGPFRequestProcess.getOpeningBalance(gpfAccNo, Long.valueOf(lIntFinYrId.longValue()));
			        lDblOpeningBal = lObjGPFRequestProcess.getOpeningBalance(gpfAccNo, Long.valueOf(lIntFinYrId.longValue()),lStrSevaarthId,isSevenpc); //swt 01/06/2020
			       if (isSevenpc.equalsIgnoreCase("Y")) {
			    	   if ((lIntFinYrId == PayableYrSPay0910) && 
						          (lStrIntSixPay0910 != null) && (lStrIntSixPay0910 != "")) {
						          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay0910).doubleValue());
						        }

						        if ((lIntFinYrId == PayableYrSPay1011) && 
						          (lStrIntSixPay1011 != null) && (lStrIntSixPay1011 != "")) {
						          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1011).doubleValue());
						        }

						        if ((lIntFinYrId == PayableYrSPay1112) && 
						          (lStrIntSixPay1112 != null) && (lStrIntSixPay1112 != "")) {
						          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1112).doubleValue());
						        }

						        if ((lIntFinYrId == PayableYrSPay1213) && 
						          (lStrIntSixPay1213 != null) && (lStrIntSixPay1213 != "")) {
						          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1213).doubleValue());
						        }

						        if ((lIntFinYrId == PayableYrSPay1314) && 
						          (lStrIntSixPay1314 != null) && (lStrIntSixPay1314 != "")){
						          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1314).doubleValue());
						        }
				}else {
					if ((lIntFinYrId == PayableYrSixPay0910) && 
					          (lStrIntSixPay0910 != null) && (lStrIntSixPay0910 != "")) {
					          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay0910).doubleValue());
					        }

					        if ((lIntFinYrId == PayableYrSixPay1011) && 
					          (lStrIntSixPay1011 != null) && (lStrIntSixPay1011 != "")) {
					          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1011).doubleValue());
					        }

					        if ((lIntFinYrId == PayableYrSixPay1112) && 
					          (lStrIntSixPay1112 != null) && (lStrIntSixPay1112 != "")) {
					          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1112).doubleValue());
					        }

					        if ((lIntFinYrId == PayableYrSixPay1213) && 
					          (lStrIntSixPay1213 != null) && (lStrIntSixPay1213 != "")) {
					          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1213).doubleValue());
					        }

					        if ((lIntFinYrId == PayableYrSixPay1314) && 
					          (lStrIntSixPay1314 != null) && (lStrIntSixPay1314 != ""))
					          lDblOpeningBal = Double.valueOf(lDblOpeningBal.doubleValue() + Double.valueOf(lStrIntSixPay1314).doubleValue());

				}
			        
			      }
				gLogger.info("lDblOpeningBal::ww::zero::::::"+lDblOpeningBal);
			//	gLogger.info("lDblIntSixPayArr::::::::"+lDblIntSixPayArr);
				if(reqType.equalsIgnoreCase("FW")){

					lDblOpeningBal = lDblOpeningBal + Double.valueOf(lStrSixPay1314) + Double.valueOf(lStrSixPay1213) + Double.valueOf(lStrSixPay1112) + Double.valueOf(lStrSixPay1011) + Double.valueOf(lStrSixPay0910);
				}


				String advancedSanctioned="0";
				String withdrawlSanctioned="0";
				int advancedSanctionedN=0;
				//gpfAccNo, lIntCurrFinYearId.longValue()
				//List lstAdvWithSanction=lObjGPFRequestProcess.getAdvWithSanctionedAmnt(gpfAccNo, lIntFinYrId.longValue());
				List lstAdvWithSanction=lObjGPFRequestProcess.getAdvWithSanctionedAmnt(gpfAccNo, lIntFinYrId.longValue(),lStrSevaarthId,isSevenpc); //swt 01/06/2020
				if(lstAdvWithSanction!=null && lstAdvWithSanction.size()>0)
				{
					Object[] advWithObje=(Object[]) lstAdvWithSanction.get(0);
					if(advWithObje!=null && advWithObje[0]!=null)
					{
						advancedSanctioned=advWithObje[0].toString();
					}
					if(advWithObje!=null && advWithObje[1]!=null)
					{
						withdrawlSanctioned=advWithObje[1].toString();
					}
				}

				gLogger.info("Advanced SAnctioned From Initial data Entry screen is ::::::::"+advancedSanctioned);
				gLogger.info("withdrawlSanctioned From Initial data Entry screen is ::::::::"+withdrawlSanctioned);



				String lDblWithdrawalSanc = "";



				//uncommented By Gokarna for correct Advances Sanctioned and withdrawal sanctioned amount


				//List advanceHistoryDtls = lObjGPFRequestProcess.getAdvanceHistory(gpfAccNo, lIntFinYrId.longValue());
				
				List advanceHistoryDtls = lObjGPFRequestProcess.getAdvanceHistory(gpfAccNo, lIntFinYrId.longValue(),lStrSevaarthId); //swt 01/06/2020


				if (advanceHistoryDtls != null && advanceHistoryDtls.size() > 0) {
					Object[] historyObj = (Object[]) advanceHistoryDtls.get(0);
					if (historyObj[0].equals("RA")) {
						advancedSanctionedN = Integer.parseInt(advancedSanctioned)+Integer.parseInt(historyObj[2].toString());
					} 
					else
					{
						advancedSanctionedN=Integer.parseInt(advancedSanctioned);
					}

					/*else {
						lDblWithdrawalSanc = historyObj[1].toString();
					}*/


					if (advanceHistoryDtls.size() > 1) {
						historyObj = (Object[]) advanceHistoryDtls.get(1);
						if (historyObj[0].equals("RA")) {
							gLogger.error("****************************************");
							advancedSanctionedN = advancedSanctionedN +Integer.parseInt(historyObj[2].toString());

							gLogger.error("advancedSanctionedN:::::::::::"+advancedSanctionedN);
						}
						else
						{
							advancedSanctionedN = advancedSanctionedN +Integer.parseInt(historyObj[1].toString());
						}
					}
				}


				/*if (advanceHistoryDtls != null && advanceHistoryDtls.size() > 0) {
					Object[] historyObj = (Object[]) advanceHistoryDtls.get(0);

					lDblWithdrawalSanc = (Double) historyObj[1];

				}*/

				Double WithdrawalSancDouble=0d;
				//WithdrawalSancDouble = lObjGPFRequestProcess.getWithdrawalSanc(gpfAccNo,lIntFinYrId.longValue());
				WithdrawalSancDouble = lObjGPFRequestProcess.getWithdrawalSanc(gpfAccNo,lIntFinYrId.longValue(),lStrSevaarthId);//swt 01/06/2020
				gLogger.error("lDblWithdrawalSanc"+lDblWithdrawalSanc);


				String WithdrawalSanctioned = "0";
				Double WithdrawalSanctionedN=0d;
				if(WithdrawalSancDouble != null){
					gLogger.error("WithdrawalSancDouble not null::::::::::"+WithdrawalSancDouble);
					WithdrawalSanctionedN = Double.parseDouble(withdrawlSanctioned.toString())+WithdrawalSancDouble;
				}
				else
				{
					WithdrawalSanctionedN=Double.parseDouble(withdrawlSanctioned.toString());
				}


				gLogger.info("Advanced SAnctioned From Currently approved loan is::::::::"+advancedSanctionedN);
				gLogger.info("withdrawlSanctioned From Currently approved loan is ::::::::"+WithdrawalSanctionedN);


				/*if(WithdrawalSanctionedN==0.0){}
				{

				}*/

				if(lstAdvWithSanction!=null && lstAdvWithSanction.size()>0 && advanceHistoryDtls.size()==0)
				{
					gLogger.error("in ifffffffff");
					inputMap.put("WithdrawalSanctioned", withdrawlSanctioned);
					inputMap.put("lStrAmtSanctioned", advancedSanctioned); 

				}
				else 
				{
					gLogger.error("in elseeeee");
					inputMap.put("WithdrawalSanctioned", WithdrawalSanctionedN);
					inputMap.put("lStrAmtSanctioned", advancedSanctionedN);
				}





				Double lDblAmtSanctioned = 0d;
				Date lDtLoanStartDate = null;
				Long lDblLoanPrinAmt = 0l;

				Date today2 = new Date();

				int currmonth = today2.getMonth() + 1;
				int curryear = 1900 + today2.getYear();
				int prevyear = curryear - 1;
				//20/06/2018
				String statusFlag = getStatusFlag(lStrTransactionId);
				if(statusFlag.equalsIgnoreCase("1"))
				{
					String billGenDate = getBillDate(lStrTransactionId);
					// String sDate1="31/12/1998";  
					    Date billDate=new SimpleDateFormat("dd/MM/yyyy").parse(billGenDate); 
					    
					    currmonth = billDate.getMonth()+ 1;
					    
					    
					 
				
				}
				

				Long lLngCurrMonth = Long.parseLong(String.valueOf(currmonth));
				Long lLngCurrYr = Long.parseLong(String.valueOf(curryear)); 
				Long lLngPrevYr = Long.parseLong(String.valueOf(prevyear)); 


				List sanction = lObjGPFRequestProcess.getLoanDetails(lStrSevaarthId,curryear,(curryear+1));

				if (sanction != null && sanction.size() > 0) {

					Object[] lArrObj = (Object[]) sanction.get(0);

					lDtLoanStartDate = (Date) lArrObj[0];
					lDblLoanPrinAmt = Long.parseLong((lArrObj[1]).toString());
					gLogger.error("lDtLoanStartDate"+lDtLoanStartDate);
					gLogger.error("lDblLoanPrinAmt"+lDblLoanPrinAmt);
				}


				/*
				int currmonth = today.getMonth() + 1;
				int curryear = 1900 + today.getYear();
				int prevyear = curryear - 1;

				Long lLngCurrMonth = Long.parseLong(String.valueOf(currmonth));
				Long lLngCurrYr = Long.parseLong(String.valueOf(curryear)); 
				Long lLngPrevYr = Long.parseLong(String.valueOf(prevyear)); 
				 */
				int loanyear = 0;
				if(lDtLoanStartDate != null){
					loanyear = lDtLoanStartDate.getYear() + 1900;
				}




				if(loanyear == curryear){

					lDblAmtSanctioned = Double.valueOf(lDblLoanPrinAmt.toString());
					gLogger.error("lDblAmtSanctioned"+lDblAmtSanctioned);
				}

				/*String temp = lObjGPFRequestProcess.getRegularSubscription(lStrSevaarthId,lLngCurrMonth,lLngCurrYr);

				Double lDblRegSub = 0d;
				if(temp != null && temp != ""){
					lDblRegSub = Double.valueOf(temp);
					gLogger.error("lDblRegSub"+lDblRegSub);
				}
				 */
				//Added By Vivek Sharma 14 july 2017 For Monthly Subscription and Regular Emi
				Long lDblRegSubFrCurrMonth=0l;
				Long monthlyregEmi=0l;
				List lstRegEmiAndMonthSub=null;
				//lDblRegSubFrCurrMonth=lObjGPFRequestProcess.getregsub(lStrSevaarthId,lLngCurrYr,lLngCurrMonth);
				//monthlyregEmi = getregEmi(lStrSevaarthId,lLngCurrYr,lLngCurrMonth);
				lstRegEmiAndMonthSub = getRegEmiAndMonthlySub(lStrSevaarthId);
				if(lstRegEmiAndMonthSub.size()!=0){
					Object[] RegEmiAndMonthSubObj = (Object[]) lstRegEmiAndMonthSub.get(0);
					if(RegEmiAndMonthSubObj[0]!=null)
						monthlyregEmi = Long.parseLong(RegEmiAndMonthSubObj[0].toString());
					if(RegEmiAndMonthSubObj[1]!=null)
						lDblRegSubFrCurrMonth = Long.parseLong(RegEmiAndMonthSubObj[1].toString());
					gLogger.info("lDblRegSubFrCurrMonth"+lDblRegSubFrCurrMonth);
					gLogger.info("monthlyregEmi"+monthlyregEmi);
				}
				//Ended By Vivek Sharma For Monthly Subscription

				String temp = lObjGPFRequestProcess.getRegularSubscription(lStrSevaarthId,lLngCurrMonth,lLngCurrYr);
				gLogger.info("reg sub fetched in service"+temp);
				Double lDblRegSub = 0d;
				if(temp != null && temp != ""){
					lDblRegSub = Double.valueOf(temp);
				}

				Double lDblWithAmt = 0d;
				Integer lIntCurrFinYearId = lObjFinancialYearDAO.getFinYearIdByCurDate();
				//Integer lIntPrevFinYearId = lIntCurrFinYearId - 1;		


				//gokarna for correct aounts in order on 24/09/2015	
				//Double lDbltemp = lObjGPFRequestProcess.getWithdrawalSanc(gpfAccNo, lIntCurrFinYearId.longValue());
				Double lDbltemp = lObjGPFRequestProcess.getWithdrawalSanc(gpfAccNo, lIntCurrFinYearId.longValue(),lStrSevaarthId);//swt 01/06/2020
				gLogger.error("lDbltemp"+lDbltemp);
				if(lDbltemp != null ){
					lDblWithAmt = lDbltemp;
					gLogger.error("lDbltemp"+lDbltemp);
				}
				/*if (advanceHistoryDtls != null && advanceHistoryDtls.size() > 0) {
					Object[] historyObj = (Object[]) advanceHistoryDtls.get(0);

					lDblWithAmt = (Double) historyObj[1];

				}*/

				Long lLngPaybillMonth = 0l;

				Long month = lObjGPFRequestProcess.getPaybillMonth(lStrSevaarthId,lLngCurrYr);

				if(month != 0){
					lLngPaybillMonth = month;

				}
				if((lLngCurrYr==2021)  && month==3 ){
					lLngPaybillMonth = (long) 4;
				}

				if(month==0){
					lLngPaybillMonth = lLngCurrMonth ;
				}
				
				if((lLngCurrYr==2022)  && month<=3 ){
					lLngPaybillMonth = (long) 4;
				}
				

				Double lDblRecAmt = Double.valueOf(lObjGPFRequestProcess.getRecoveryAmt(gpfAccNo,lLngCurrYr,lStrSevaarthId,lLngPaybillMonth));

				gLogger.error("OpeningBalance"+lDblOpeningBal);
				gLogger.error("lDblRegSub"+lDblRegSub);
				gLogger.error("lDblRecAmt"+lDblRecAmt);
				gLogger.error("lDblAmtSanctioned"+lDblAmtSanctioned);
				gLogger.error("lDblWithAmt"+lDblWithAmt);


				Double NetBalance = lDblOpeningBal + lDblRegSub + lDblRecAmt - lDblAmtSanctioned - lDblWithAmt ;
				gLogger.error("NetBalance"+NetBalance);
				
				Double lDblAmtSancGpf = getAmtSanctioned(lStrTransactionId,reqType);
				gLogger.error("lDblAmtSancGpf"+lDblAmtSancGpf);



				ScheduleGenerationDAO lObjScheduleGenerationDAO = new ScheduleGenerationDAOImpl(null, serviceLocator.getSessionFactory());


				String preFinYear = lObjScheduleGenerationDAO.getFinYearCodeForFinYearId(lLngPreFinYearId);
				String CurrFinYear = lObjScheduleGenerationDAO.getFinYearCodeForFinYearId(lLngCurrFinYearId);

				String lStrcurrFinYearCode = lObjScheduleGenerationDAO.getFinYearCodeForFinYearId(lIntFinYrId.longValue());


				lStrcurrFinYearCode = lStrcurrFinYearCode.substring(0, 4);


				Date startDate = lObjScheduleGenerationDAO.getStartDateOfFinancialYear1(lIntFinYrId.longValue());
				gLogger.error("NetBalance"+NetBalance);
				gLogger.error("lDblAmtSancGpf"+lDblAmtSancGpf);
				gLogger.error("preFinYear"+preFinYear);
				gLogger.error("CurrFinYear"+CurrFinYear);
				gLogger.error("lStrcurrFinYearCode"+lStrcurrFinYearCode);
				gLogger.error("startDate"+startDate);

				String strDate = startDate.toString();
				String[] parts = strDate.split("-");
				String strYear = parts[0]; // 004
				String strMonth = parts[1]; // 034556
				String strDt = parts[2]; // 034556
				gLogger.error("strYear"+strYear);
				gLogger.error("strMonth"+strMonth);
				gLogger.error("strDt"+strDt);
				List lLstDsgnAndOfficeName = lObjGPFRequestProcess.getDsgnAndOfficeName(lStrSevaarthId);

				List lstDDODeptDtls = lObjGPFRequestProcess.getDDODeptDetails(lStrSevaarthId);

				String deptName = "";
				String ddoOfcAdrs = "";
				String ddoOfcEmail = "";
				String ddoOfcTelNo = "";

				if(lstDDODeptDtls != null && !lstDDODeptDtls.isEmpty()){
					Object[] obj = (Object[])lstDDODeptDtls.get(0);

					if(obj[1] != null){
						deptName = obj[1].toString();
					}

					if(obj[4] != null){
						ddoOfcAdrs = obj[4].toString();
					}

					if(obj[5]!=null)
						ddoOfcEmail = obj[5].toString();
					if(obj[6]!=null)
						ddoOfcTelNo = obj[6].toString();
				}

				Object[] lObjDsgnAndOfficeName = null;
				String lStrDsgnName = "";
				String lStrOfficeName = "";
				String lStrPayScale = "";
				Long lStrBasicPay = 0l;
				Long  lStrGradePay = 0l;




				if (!lLstDsgnAndOfficeName.isEmpty()) {
					lObjDsgnAndOfficeName = (Object[]) lLstDsgnAndOfficeName.get(0);
					lStrDsgnName = (String) lObjDsgnAndOfficeName[0];
					lStrOfficeName = (String) lObjDsgnAndOfficeName[1];
					lStrPayScale = (String) lObjDsgnAndOfficeName[2];



					Object obj =  lObjDsgnAndOfficeName[3];
					lStrBasicPay = Long.parseLong(obj.toString());


					if(lObjDsgnAndOfficeName[4] != null)
						lStrGradePay = Long.parseLong(obj.toString());


				}




				//List lLstaccountBalance = lObjGPFRequestProcess.getGPFAccountBalance(gpfAccNo, lIntFinYrId.longValue());
				List lLstaccountBalance = lObjGPFRequestProcess.getGPFAccountBalance(gpfAccNo, lIntFinYrId.longValue(),lStrSevaarthId); //swt 01/06/2020
				Object[] lObjGPFAccountBal = null;
				if (lLstaccountBalance != null && lLstaccountBalance.size() > 0) {
					lObjGPFAccountBal = (Object[]) lLstaccountBalance.get(0);
				}

				//Long lDblWithdrawalSanc = 0l;
				Object[] lObjhistory = null;
				//List lLstAdvanceHistoryDtls = lObjGPFRequestProcess.getAdvanceHistory(gpfAccNo, lIntFinYrId.longValue());
				List lLstAdvanceHistoryDtls = lObjGPFRequestProcess.getAdvanceHistory(gpfAccNo, lIntFinYrId.longValue(),lStrSevaarthId);  //swt 01/06/2020
				if (lLstAdvanceHistoryDtls != null && lLstAdvanceHistoryDtls.size() > 0) {
					for (Integer lIntcnt = 0; lIntcnt < lLstAdvanceHistoryDtls.size(); lIntcnt++) {
						lObjhistory = (Object[]) lLstAdvanceHistoryDtls.get(lIntcnt);
						lDblWithdrawalSanc += Long.parseLong(lObjhistory[1].toString());
					}
				}


				//get Refundable amount sanctioned value
				Double lDbWithdrawalSancNew = getAmountSAnctionedRA(lStrTransactionId);
				//


				//Double lDbClosingBalance = lObjGPFRequestProcess.getClosingBalance(gpfAccNo);
				//List<Object[]> advanceList = lObjGPFRequestProcess.getAdvanceDetail(gpfAccNo, reqType);
				
				Double lDbClosingBalance = lObjGPFRequestProcess.getClosingBalance(gpfAccNo,lStrSevaarthId);  //swt 01/06/2020
				List<Object[]> advanceList = lObjGPFRequestProcess.getAdvanceDetail(gpfAccNo,reqType,lStrSevaarthId); //swt 01/06/2020
				
				Object lObjAdvDtls[] = new Object[9];
				if (!advanceList.isEmpty()) {
					lObjAdvDtls = advanceList.get(0);
				}

				String lStrDDOCodeOfLoggedInDDO = lObjGPFRequestProcess.getDdoCodeForDDO(lLngPostId);
				String ddocode = "7101";
				String sub = lStrDDOCodeOfLoggedInDDO.substring(0, 4);
				gLogger.error("lStrDDOCodeOfLoggedInDDO"+lStrDDOCodeOfLoggedInDDO);

				gLogger.error("sub"+sub);


				String lStrEmployerOfficeName = lObjGPFRequestProcess.getEmployerOfficeName(lStrDDOCodeOfLoggedInDDO);

				gLogger.error("lStrEmployerOfficeName"+lStrEmployerOfficeName);
				gLogger.error("lLngPostId******"+lLngPostId);
				String lStrEmployerDsgnName = lObjGPFRequestProcess.getEmployerDsgnName(lLngPostId);
				String emplyeeOfficerName = lObjGPFRequestProcess.getEmployerNameFrmPostId(lLngPostId.toString());

				String lStrTreasuryName = lObjGPFRequestProcess.getTreasuryNameOfEmp(lStrDDOCodeOfLoggedInDDO);
				//String lStrGradePay = lObjGPFRequestProcess.getGradePayFrmSevaarthId(lStrSevaarthId);
				String lStrPurposeOfRequest = lObjGPFRequestProcess.getPrpsFrmTransactionId(lStrTransactionId);
				String lStrBeneficiaryName = lObjGPFRequestProcess.getPrpsFrmTransactionId(lStrTransactionId);
				String lStrOtherPurpose = lObjGPFRequestProcess.getOtherPurpose(lStrTransactionId);
				String lStrPurposeIdOfRequest = lObjGPFRequestProcess.getPrpsIdFrmTransactionId(lStrTransactionId); //Added by Vivek Sharma 06 July 2017
				gLogger.error("lStrOtherPurpose"+lStrOtherPurpose);
				gLogger.error("lStrPurposeOfRequest"+lStrPurposeOfRequest);
				gLogger.error("lStrEmployerDsgnName"+lStrEmployerDsgnName);
				gLogger.error("lStrPurposeIdOfRequest**********"+lStrPurposeIdOfRequest);

				//Added By Vivek Sharma 06 July 2017
				//List lstPreviusData = lObjGPFRequestProcess.getPrvsPurpzRst(gpfAccNo,reqType,lStrPurposeIdOfRequest);
				List lstPreviusData = lObjGPFRequestProcess.getPrvsPurpzRst(gpfAccNo,reqType,lStrPurposeIdOfRequest,lStrSevaarthId); //swt 01/06/2020
				String PrevpurpozId = "";
				String PrevOrderNo = "";
				String PrevOrderDate = "";
				Double PrevSancAmt = 0D;
				gLogger.error("lstPreviusData***"+lstPreviusData.size());
				if (lstPreviusData != null && lstPreviusData.size() > 0) {

					Object[] lArrObj = (Object[]) lstPreviusData.get(0);

					if(lArrObj[0] != null){
						PrevpurpozId = lArrObj[0].toString();
					}
					if(lArrObj[1] != null){
						PrevOrderNo = lArrObj[1].toString();
					}
					if(lArrObj[2] != null){
						PrevOrderDate = lArrObj[2].toString();
					}
					if(lArrObj[4] != null){
						PrevSancAmt = Double.parseDouble(lArrObj[4].toString());
					}
				}

				long lPrevSancAmt = (new Double(PrevSancAmt)).longValue();
				gLogger.error("PrevpurpozId***"+PrevpurpozId);
				gLogger.error("PrevOrderNo***"+PrevOrderNo);
				gLogger.error("lstPreviusData***"+PrevOrderDate);
				gLogger.error("PrevSancAmt***"+PrevSancAmt);
				gLogger.error("lPrevSancAmt***"+lPrevSancAmt);
				gLogger.error("lPrevSancAmt***"+lPrevSancAmt);


				//Ended By Vivek Sharma 06 July 2017


				String strPurpz = "";
				String strPurpzRuleNo = "";
				String[] strPurpzOfRqst = lStrPurposeOfRequest.split("~");

				strPurpz = strPurpzOfRqst[0];
				//strPurpzRuleNo = strPurpzOfRqst[1];
				if(strPurpzOfRqst.length > 1)
				{
					//gLogger.error("Invalid123456789");
					strPurpzRuleNo = strPurpzOfRqst[1];
				}
				gLogger.error("strPurpzOfRqst**********"+strPurpzOfRqst);
				gLogger.error("strPurpz**********"+strPurpz);
				gLogger.error("strPurpzRuleNo**********"+strPurpzRuleNo);
				String lStrPurposeName = "";
				if(lStrPurposeOfNra!= null && !lStrPurposeOfNra.equals(""))
					lStrPurposeName = lObjGPFRequestProcess.getPurposeNameFrmId(lStrPurposeOfNra); 


				startDate.getMonth();
				startDate.getYear();

				Integer currDate = today.getDate();
				Integer currMonth = today.getMonth() + 1;
				Integer currYear = today.getYear() + 1900;
				//20/06/2018
				String statFlag = getStatusFlag(lStrTransactionId);
				if(statFlag.equalsIgnoreCase("1"))
				{
					String billGenDate = getBillDate(lStrTransactionId);
					// String sDate1="31/12/1998";  
					    Date billDate=new SimpleDateFormat("dd/MM/yyyy").parse(billGenDate); 
					    currDate = billDate.getDate();
					    currMonth = billDate.getMonth()+ 1;
					    currYear  =  billDate.getYear() + 1900; 
					    
					    gLogger.info("billDate Date currDate "+currDate);
					    gLogger.info("billDate Date currMonth "+currMonth);
					    gLogger.info("billDate Date currYear "+currYear);
				}
				
				
				
				
				HashMap hm = new HashMap();
				// Put elements to the map
				hm.put("1", "January");
				hm.put("2", "February");
				hm.put("3", "March");
				hm.put("4", "April");
				hm.put("5", "May");
				hm.put("6", "June");
				hm.put("7", "July");
				hm.put("8", "August");
				hm.put("9","September");
				hm.put("10", "October");
				hm.put("11", "November");
				hm.put("12", "December");
				hm.put("13", "January");
				hm.put("14", "February");


				/*String lStrInstAmt = "0";
				String lStrTotalInst = "0";
				String recoverableAmt="0";*/
                Double lStrInstAmt = 0.0;
				
				long lStrInstAmtODD = 1;
				Double lStrInstIntODD = 0.0;
				Integer lStrTotalInst = 0;
				long lStrTotalInst1 = 0;
				String lStrTotalInst2 = "0";
				String recoverableAmt="0";

				if (reqType.equals("RA")) {
					List inst = lObjGPFRequestProcess.getInstallmentvalues(lStrTransactionId);

					if (inst != null && inst.size() > 0) {

						Object[] lArrObj = (Object[]) inst.get(0);

						if(lArrObj[0] != null){
							/*lStrTotalInst = lArrObj[0].toString();
							lStrInstAmt = lArrObj[1].toString();
							recoverableAmt=lArrObj[5] != null ? lArrObj[5].toString() : "0";*/
							lStrTotalInst = (Integer) lArrObj[0];
							lStrInstAmt = (Double) lArrObj[1];
							recoverableAmt=lArrObj[5] != null ? lArrObj[5].toString() : "0";
							lStrInstIntODD = (Double) lArrObj[6];
						}
					}

					gLogger.error("lStrTotalInst"+lStrTotalInst);
					gLogger.error("lStrInstAmt"+lStrInstAmt);
				}
	               if(lStrInstIntODD > 0){
					
					lStrTotalInst=lStrTotalInst-1;
					//lStrTotalInst=lStrTotalInst1;
				}else {
					lStrTotalInst=lStrTotalInst;
				}


				gLogger.error("reqType$$$$$$$$$$"+reqType);


				if (reqType.equals("RA") || reqType.equals("NRA") || reqType.equals("FW") || reqType.equals("Non-Refundable") ){

					//List amnt=lObjGPFRequestProcess.getRecoveredAndOutstandingAmt(gpfAccNo);
					List amnt=lObjGPFRequestProcess.getRecoveredAndOutstandingAmt(gpfAccNo,lStrSevaarthId); //swt 01/06/2020

					String outStanding=null;
					String recoveredAmt=null;

					if (amnt != null && amnt.size() > 0) {

						Object[] lArrObj = (Object[]) amnt.get(0);

						if(lArrObj[0] != null){
							outStanding = lArrObj[0].toString();
							recoveredAmt = lArrObj[1].toString();
						}
					}




					gLogger.info("******RARARARA*******");
					if(lStrDDOCODE123!=null)
					{
						gLogger.info("******5555555555555*******");
						StringBuilder lSBQuery1 = new StringBuilder();
						String ddocode123="";
						List lstrList=null;


						lSBQuery1.append( " SELECT LOCATION_CODE FROM ORG_DDO_MST where DDO_CODE='"+lStrDDOCODE123+"'" ); 


						Query lQuery = ghibSession.createSQLQuery(lSBQuery1.toString());

						//lQuery.setParameter("lStrTransactionId", lStrTransactionId);

						gLogger.info("Query is:::::::::"+lSBQuery1.toString());

						lstrList = lQuery.list();
						if (lstrList.size() != 0) {

							ddocode123 = lstrList.get(0).toString();

							gLogger.info("ddocode123::::::::"+ddocode123);

						}

					}	

					gLogger.error("inside regular RAAAAAAAA");

					Date lDtApplicDate = lObjGPFRequestProcess.getApplicationDateFrmTranId(lStrTransactionId);
					int dt = 0;
					int mn = 0;
					int yr = 0;
					if(lDtApplicDate != null){
						dt = lDtApplicDate.getDate();
						mn = lDtApplicDate.getMonth() + 1;
						yr = lDtApplicDate.getYear() + 1900;
					}
					gLogger.error("inside regular lDtApplicDate"+lDtApplicDate);
					gLogger.error("inside regular lDtApplicDate"+dt);
					gLogger.error("inside regular lDtApplicDate"+mn);
					gLogger.error("inside regular lDtApplicDate"+yr);
					Double total = Math.round(lDblOpeningBal) + Double.parseDouble(lObjGPFAccountBal[0].toString()) + Double.parseDouble(lObjGPFAccountBal[1].toString());


					report.setStyleList(noBorder);
					report.setStyleList(rowsFontsVO);
					ArrayList<Object> rowList = new ArrayList<Object>();

					rowList.add("" + space(1));
					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE1") + " " + lStrOrderId,
							boldVO));
					dataList.add(rowList);
					 */

					//Commented By Vivek Sharma 08 June 2017
					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GFP.ADDRESS1") ,
							CenterBoldAlignVO));
					dataList.add(rowList);*/



					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GFP.ADDRESS1111") ,
							CenterBoldAlignVO));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(deptName, CenterBoldAlignVO));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(lStrOfficeName, CenterBoldAlignVO));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(ddoOfcAdrs, CenterBoldAlignVO));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();

					/*if(ddoOfcEmail != null)
						rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE67")+ ddoOfcEmail, CenterBoldAlignVO) );
					else 
						rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE67")+ "_____________________", CenterBoldAlignVO) );
					dataList.add(rowList);*/
					//Added By vivek Sharma 08 June 2017
					if(ddoOfcEmail != null)
						rowList.add(new StyledData( ddoOfcEmail, CenterBoldAlignVO) );
					else 
						rowList.add(new StyledData( "_____________________", CenterBoldAlignVO) );
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					if(ddoOfcTelNo != null)
						rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE66")+ ddoOfcTelNo, CenterBoldAlignVO));
					else 
						rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE66")+ "____________________", CenterBoldAlignVO));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE67"), CenterBoldAlignVO) );
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					//Sooraj 1/3/2018
					String OrderRefNo ="";
					OrderRefNo = getOrderRefNo(lStrTransactionId);
					
					//GPF.ORDERLINENEW500
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW500")+OrderRefNo+" / "+strCurrYear, CenterBoldAlignVO) );
					dataList.add(rowList);
					//End
					rowList = new ArrayList<Object>();
					/*rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE68"), lStrDDOCodeOfLoggedInDDO,currYear+"",strEmpIntls,
							//lStrTransactionId,currYear+"-"+currMonth+"-"+currDate) ,CenterAlignVO));
							lStrTransactionId," "+currDate+"/"+currMonth+"/"+currYear) ,CenterAlignVO));*/ 
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW68"), lStrDDOCodeOfLoggedInDDO,currYear+"",strEmpIntls,
							lStrTransactionId,dt+"/"+mn+"/"+yr) ,CenterAlignVO));//Added By Vivek Sharma 08 June 2017
					dataList.add(rowList);




					rowList = new ArrayList<Object>();
					rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE2"),empName,lStrDsgnName,gpfAccNo),
							CenterAlignVO));
					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE69"),gpfAccNo),CenterAlignVO));  //Change with GPF acnt Number
					dataList.add(rowList);*/


					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE4"), centerUnderlineBold));
					dataList.add(rowList);*/

					Double lDblTemp = lDblOpeningBal + lDblRegSub + lDblRecAmt;
					gLogger.error("lDblTemp"+lDblTemp);

					Long lLngTemp = lDblTemp.longValue();
					gLogger.error("lLngTemp"+lLngTemp);

					//Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth();
					//	gLogger.error("Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth()"+Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth()));

					/*Long lLngTemp1 = Long.parseLong(lDblTemp.toString());
					gLogger.error("lLngTemp1"+lLngTemp1);

					Long lLngTemp2 = Long.valueOf(lDblTemp.toString());
					gLogger.error("lLngTemp2"+lLngTemp2);

					Long lLngTemp3 = Long.valueOf(String.valueOf(lDblTemp));
					gLogger.error("lLngTemp3"+lLngTemp3);*/

					rowList = new ArrayList();
					gLogger.info("lStrPurposeName $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+lStrPurposeName);
					//gLogger.info("lStrPurposeName $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+lStrPurposeName);
					// if(Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth())==12 || Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth())== 11)
					if(Integer.parseInt(lObjAdvDtls[8].toString())==12 || Integer.parseInt(lObjAdvDtls[8].toString())== 11)
					{

						rowList.add(
								/* MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE70"), new Object[] { 
					          "______", "_____", lStrEmployerDsgnName, empName, lStrDsgnName, lStrBasicPay, gpfAccNo, lStrPurposeName, lDblAmtSancGpf, 
					          Double.valueOf(total.doubleValue() - lDblWithdrawalSanc.doubleValue()), EnglishDecimalFormat.convert(sancAmount.longValue()), lObjAdvDtls[5], lObjAdvDtls[4], Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 2), Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 3) }));
					        dataList.add(rowList);*/


								//	 MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE70"), new Object[] { 
								//		 lStrOrderId, "_____", lStrEmployerDsgnName, empName, lStrDsgnName, lStrBasicPay, gpfAccNo, lStrPurposeName, lDblAmtSancGpf, (OpeningBalance + lDblRegSub + lDblRecAmt), EnglishDecimalFormat.convert(lLngTemp),lStrTotalInst,lStrInstAmt, Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() - 10  ), Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() - 9) }));


								//MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE70"), new Object[] { 
								//lStrEmployerDsgnName, empName,lStrPurposeName,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()) }));
								MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW70"), new Object[] {
									//lStrEmployerDsgnName, empName,lStrOtherPurpose,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()),lStrPurposeOfRequest }));  //Added By Vivek Sharma 09 June 2017
									lStrTreasuryName, empName,strPurpz,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()),strPurpzRuleNo  }));  //Added By Vivek Sharma 04 July 2017

					}

					else
					{
						rowList.add(
								/* MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE70"), new Object[] { 
				          "______", "_____", lStrEmployerDsgnName, empName, lStrDsgnName, lStrBasicPay, gpfAccNo, lStrPurposeName, lDblAmtSancGpf, 
				          Double.valueOf(total.doubleValue() - lDblWithdrawalSanc.doubleValue()), EnglishDecimalFormat.convert(sancAmount.longValue()), lObjAdvDtls[5], lObjAdvDtls[4], Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 2), Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 3) }));
				        dataList.add(rowList);*/


								//MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE70"), new Object[] { 
								// lStrOrderId, "_____", lStrEmployerDsgnName, empName, lStrDsgnName, lStrBasicPay, gpfAccNo, lStrPurposeName, lDblAmtSancGpf, (OpeningBalance + lDblRegSub + lDblRecAmt), EnglishDecimalFormat.convert(lLngTemp),lStrTotalInst,lStrInstAmt, Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 2), Integer.valueOf(((Date)lObjAdvDtls[8]).getMonth() + 3) }));

								//lStrEmployerDsgnName, empName,lStrPurposeName,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()) }));
								MessageFormat.format(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW70"), new Object[] {	
									//lStrEmployerDsgnName, empName,lStrOtherPurpose,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()),lStrPurposeOfRequest })); //Added By Vivek Sharma 09 June 2017
									lStrTreasuryName, empName,strPurpz,gpfAccNo,lDblAmtSancGpf ,EnglishDecimalFormat.convert(lDblAmtSancGpf.longValue()),strPurpzRuleNo  })); //Changed Purpose of request By Vivek Sharma 04 July 2017



					}


					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(space(40) + gObjRsrcBndleForRA.getString("GPF.ORDERLINE5") + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE6") +" "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE48") + " " + lStrDsgnName + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE49") +" "+ lStrOfficeName + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE7") + " "
							+ lStrPayScale + " " 
							//+gObjRsrcBndleForRA.getString("GPF.ORDERLINE8")+" "
							+lStrGradePay+"/- "+  gObjRsrcBndleForRA.getString("GPF.ORDERLINE9") + " " + gpfAccNo
							+ " " + gObjRsrcBndleForRA.getString("GPF.ORDERLINE10") + " " +lStrPurposeOfRequest+" "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE50") +" "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE11") +" "+ lDbClosingBalance + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE12") + " " + sancAmount +"/- "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE51")+" "
							+ EnglishDecimalFormat.convert(sancAmount) +" "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE52")+" "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE13") + " ________ " + gObjRsrcBndleForRA.getString("GPF.ORDERLINE53")+" "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE14") + " " + sancAmount + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE16") ///+" " (sancAmount + ) 
							+" " + gObjRsrcBndleForRA.getString("GPF.ORDERLINE17") + " " + lObjAdvDtls[5] + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE18") + " " + lObjAdvDtls[4] + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE19") + "_________"
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE20") + "___________"
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE21"));
					dataList.add(rowList);*/

					rowList = new ArrayList<Object>();
					/*rowList.add(space(40) + ((lObjAdvDtls[7] == null) ? "" : lObjAdvDtls[7]) + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE22") + " "
							+ lObjDateFormate.format(lObjAdvDtls[8]) + " "
							+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE23"));*/
					//rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE71"),empName , lStrDsgnName,lStrOfficeName,currDate+"/"+currMonth+"/"+currYear));
					//rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE71"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,recoverableAmt));
					//Added By Vivek 12 June 2017
					gLogger.info("empName $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+empName);
					gLogger.info("lStrInstAmt $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+lStrInstAmt);
					gLogger.info("lStrTotalInst $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+lStrTotalInst);
					gLogger.info("recoveredAmt $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+recoveredAmt);
					gLogger.info("outStanding $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+outStanding);
					gLogger.info("recoverableAmt $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+recoverableAmt);
					gLogger.info("PrevOrderNo $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+PrevOrderNo);
					gLogger.info("PrevOrderDate $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+PrevOrderDate);
					gLogger.info("lPrevSancAmt $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+lPrevSancAmt);
					gLogger.info("EnglishDecimalFormat.convert(lPrevSancAmt) $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+EnglishDecimalFormat.convert(lPrevSancAmt));
					//Added Condition By vivek Sharma 06 July 2017
					//Sooraj 3/3/2018
					String CurrentMonth = " ";
					String CurrentMonth2 = " ";
					if(lstPreviusData != null && lstPreviusData.size() > 0){
						gLogger.info("Previousdata$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
						//if((currMonth+2)==1 && (currMonth+1)== 12){ //commented on 05112018
						if((currMonth+2)==13 && (currMonth+1)== 12){
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear+1);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear);
						}
						//else if((currMonth+2)==2 && (currMonth+1)== 1){ //commented on 05112018
						else if((currMonth+2)==14 && (currMonth+1)== 13){
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear+1);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear+1);
						}
						else{
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear);
						}
						//End
						/*rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEWUPDATE71"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,
								recoverableAmt,CurrentMonth,CurrentMonth2,PrevOrderNo,PrevOrderDate,lPrevSancAmt,EnglishDecimalFormat.convert(lPrevSancAmt)));*/
					
						if(lStrInstIntODD > 0){
							rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEWUPDATE711"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,
							recoverableAmt,CurrentMonth,CurrentMonth2,PrevOrderNo,PrevOrderDate,lPrevSancAmt,EnglishDecimalFormat.convert(lPrevSancAmt),lStrInstIntODD,lStrInstAmtODD));
						   
						}
						else {
						rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEWUPDATE71"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,
								recoverableAmt,CurrentMonth,CurrentMonth2,PrevOrderNo,PrevOrderDate,lPrevSancAmt,EnglishDecimalFormat.convert(lPrevSancAmt)));
					      }
					
					
					}
					else{
						gLogger.info("NoPreviousdata$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
						//if((currMonth+2)==1 && (currMonth+1)== 12){  ////commented on 05112018
						if((currMonth+2)==13 && (currMonth+1)== 12){
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear+1);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear);
						}
						/*else if((currMonth+2)==2 && (currMonth+1)== 1){*/ //commented on 05112018
						else if((currMonth+2)==14 && (currMonth+1)== 13){
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear+1);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear+1);
						}
						else{
							CurrentMonth = hm.get(""+(currMonth+2)+"").toString()+" "+(currYear);
							CurrentMonth2 = hm.get(""+(currMonth+1)+"").toString()+" "+(currYear);
						}
						/*rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW71"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,
								recoverableAmt,CurrentMonth,CurrentMonth2));*/
						if(lStrInstIntODD > 0){
							rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW711"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,recoverableAmt,CurrentMonth,CurrentMonth2,lStrInstIntODD,lStrInstAmtODD));
							
						}
						else{
						rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW71"),empName,lStrInstAmt,lStrTotalInst,recoveredAmt,outStanding,recoverableAmt,CurrentMonth,CurrentMonth2));
						}
					}
					dataList.add(rowList);




					/*rowList = new ArrayList<Object>();
					//rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE271"),noOfYearsLeft,noOfYearsServed));
					rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW271"),empName,remainMonth,remainYear,lStrOrderDate,ServiceMonth,ServiceYear));  //Added By Vivek Sharma 08 June 2017
					dataList.add(rowList);*/

					rowList = new ArrayList<Object>();
					//rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE272"),empName,currYear+"-"+currMonth+"-"+currDate ));
					//rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW272"),empName,currDate+"/"+currMonth+"/"+currYear )); //Added By Vivek Sharma 08 June 2017
					rowList.add(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEWONE272"),empName,currDate+"/"+currMonth+"/"+currYear )); //Added By Vivek Sharma 04 July 2017
					dataList.add(rowList);





					/*rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);*/

					/*
					 * rowList = new ArrayList<Object>(); rowList.add(new
					 * StyledData
					 * ("Through amount approved for withdrawal to Mr./Ms. " +
					 * empName +
					 * " is more than their 6 months salary, it is not more " +
					 * "than 90% of amount accumumlated in their A/C. Their Basic Monthly salary is Rs. "
					 * + basicSalary + "/- .", rowsFontsVO));
					 * dataList.add(rowList);
					 */

					// rowList = new ArrayList();
					// rowList.add(new StyledData("This is to certify that " +
					// retirementMonths
					// + " months are there for retirement of Mr./Ms./Mrs. " +
					// empName + " and he/she has "
					// + "completed " + serviceYears + " years  and " +
					// serviceMonths
					// +
					// " months of service. Following is the account balance as on "
					// + today + " in the "
					// + "account of Mr./Ms./Mrs. " + empName, rowsFontsVO));
					// dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add("" + space(1));
					dataList.add(rowList);



					ArrayList<Object> sixRowsLeft = new ArrayList<Object>();

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForNRA.getString("GFP.SrNo"));
					rowList.add(gObjRsrcBndleForNRA.getString("GFP.Particulars"));
					rowList.add(gObjRsrcBndleForNRA.getString("GFP.Amount"));
					sixRowsLeft.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add("1.");
					//rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE24") + preFinYear
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE24") + CurrFinYear   //swt 13/10/2020
							+ " " + gObjRsrcBndleForRA.getString("GPF.ORDERLINE25"), rowsFontsVO));
					//rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + NetBalance+"/-");
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + lDblOpeningBal+"/-");
					sixRowsLeft.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add("2.");
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE26") + " "
							//+ startDate + gObjRsrcBndleForRA.getString("GPF.ORDERLINE430") + " " + currYear+"-"+currMonth+"-"+currDate+" " +gObjRsrcBndleForRA.getString("GPF.ORDERLINE432")+" " + lDblRegSub + ""+gObjRsrcBndleForRA.getString("GPF.ORDERLINE433"),
							//	+ strDt+"/"+strMonth+"/"+strYear+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE430") + " " + currDate+"/"+currMonth+"/"+currYear+" " +gObjRsrcBndleForRA.getString("GPF.ORDERLINE432")+" " + lDblRegSub + ""+gObjRsrcBndleForRA.getString("GPF.ORDERLINE433"),
							+ strDt+"/"+strMonth+"/"+strYear+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE430") + " " + currDate+"/"+currMonth+"/"+currYear+" " +gObjRsrcBndleForRA.getString("GPF.ORDERLINE432")+" " + lDblRegSubFrCurrMonth + " "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE433"),
							rowsFontsVO));
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + lDblRegSub +"/-");
					sixRowsLeft.add(rowList);

					
					//Added by Vivek 31 July 2017
					rowList = new ArrayList<Object>();
					rowList.add("3.");

					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE326") + " "
							//+ startDate + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE435")
							//+ currYear+"-"+currMonth+"-"+currDate + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE436")  + " "
							+ strDt+"/"+strMonth+"/"+strYear + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE435")
							+ currDate+"/"+currMonth+"/"+currYear + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE436")  + " "
							//+ lDblRecAmt + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE437")
							+ monthlyregEmi + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE437")
							+ " ",rowsFontsVO));
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + lDblRecAmt +"/-");
					sixRowsLeft.add(rowList);



					rowList = new ArrayList<Object>();
					rowList.add("4.");
					//rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE30"), rowsFontsVO));
			        rowList.add(new StyledData(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE30"), rowsFontsVO));

			        rowList.add(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + (lDblRecAmt.doubleValue() + lDblRegSub.doubleValue() + lDblOpeningBal.doubleValue()) + "/-");
			        sixRowsLeft.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add("5.");

					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE440") + " "
							//+ startDate + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE441")
							//+ currYear+"-"+currMonth+"-"+currDate + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE442")  + " "
							+ strDt+"/"+strMonth+"/"+strYear+ " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE441")
							+ currDate+"/"+currMonth+"/"+currYear + " "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE442")  + " "
							+ " ",rowsFontsVO));


					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + (advancedSanctionedN + WithdrawalSanctionedN) +"/-");
					//rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + (lDblWithAmt + lDblLoanPrinAmt)   +"/-");
					sixRowsLeft.add(rowList);


					//value="${resValue.OpeningBalance + resValue.regularSubscription - resValue.lStrAmtSanctioned - resValue.WithdrawalSanctioned + resValue.lStrRecAmt}"></c:set>



					rowList = new ArrayList<Object>();
					rowList.add("6.");
					//rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE35"), rowsFontsVO));
			        rowList.add(new StyledData(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE35"), rowsFontsVO));

			        rowList.add(this.gObjRsrcBndleForRA.getString("GPF.ORDERLINE47") + " " + (lDblRecAmt.doubleValue() + lDblRegSub.doubleValue() + lDblOpeningBal.doubleValue() - advancedSanctionedN - WithdrawalSanctionedN.doubleValue()) + "/-");

					sixRowsLeft.add(rowList);

					td = new TabularData(sixRowsLeft);
					RptVo = reportsDao.getReport("800009", report.getLangId(), report.getLocId());
					ReportColumnVO[] lArrReportColumnVO = RptVo.getReportColumns();
					lArrReportColumnVO[0].setColumnWidth(5);
					lArrReportColumnVO[1].setColumnWidth(65);
					lArrReportColumnVO[2].setColumnWidth(35);
					(td).setRelatedReport(RptVo);
					(td).setStyles(noBorder);
					rowList = new ArrayList<Object>();
					rowList.add(td);
					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(space(40) + empName + " , " + lStrDsgnName+ " "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE54") + " "
										+lStrOfficeName+" "+ gObjRsrcBndleForRA.getString("GPF.ORDERLINE55")+" _____________________________________ " 
										 +gObjRsrcBndleForRA.getString("GPF.ORDERLINE57")+" ______________________ " + gObjRsrcBndleForRA.getString("GPF.ORDERLINE58") );
					dataList.add(rowList);*/





					rowList = null;

					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);


					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForNRA.getString("GPF.signature"), rightAlign));
					dataList.add(rowList);*/


					rowList = new ArrayList<Object>();
					/*rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE401"), leftAlign));*/ //commented by brijoy 15012019
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE401chn"), leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE402"), rightAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(emplyeeOfficerName, rightAlign));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(lStrEmployerDsgnName, rightAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForNRA.getString("GPF.MahaState")+lStrEmployerOfficeName, rightAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(ddoOfcAdrs, rightAlign));
					dataList.add(rowList); //commented by brijoy 15012018

					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);

					/*rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);*/


					/*
					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE41"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE42")+"_____________________"+gObjRsrcBndleForRA.getString("GPF.ORDERLINE59")+",__________________");
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE60"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE44")+" ___________________ "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE65"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE45")+" ______________________________________________ ");
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add("_____________________________________________"+gObjRsrcBndleForRA.getString("GPF.ORDERLINE46"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE61")+" __________________________, "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE62")+"__________"
							+gObjRsrcBndleForRA.getString("GPF.ORDERLINE63"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE64"));
					dataList.add(rowList);
					 */
					/*rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE40"));
					dataList.add(rowList);*/
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);




					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE403"), leftAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(lStrTreasuryName, leftAlign));  //Added By Vivek Sharma
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE404"), leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE404"), leftAlign));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();
					// rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE405"),rightAlign));
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE405"), lStrDDOCodeOfLoggedInDDO,currYear+"",strEmpIntls,
							lStrTransactionId) ,rightAlign));


					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					//rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE406"), currYear+"/"+currMonth+"/"+currDate) ,rightAlign));
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForRA.getString("GPF.ORDERLINE406"), currDate+"/"+currMonth+"/"+currYear) ,rightAlign)); //Added By Vivek 08 June 2017
					//  rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE406"),rightAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE407"),leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE408"),leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE409"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					//rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE410"),leftAlign));
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINENEW410")+" "+empName,leftAlign));  //Added By Vivek Sharma 09 June 2017
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE411"),leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE412"),leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE413"),leftAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE414"),rightAlign));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(emplyeeOfficerName, rightAlign));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(lStrEmployerDsgnName, rightAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForNRA.getString("GPF.MahaState")+lStrEmployerOfficeName, rightAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(ddoOfcAdrs, rightAlign));
					dataList.add(rowList); 




					rowList = new ArrayList<Object>();
					rowList.add(newline);
					dataList.add(rowList);

					//Place Changes by Vivek Sharma 04 July 2017
					/*
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE403"), leftAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(lStrTreasuryName, leftAlign));  //Added By Vivek Sharma
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE404"), leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE404"), leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE415"),leftAlign));
					dataList.add(rowList);*/

					//----------------

					//Commented By Vivek 08 June2017

					/*rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE416"),leftAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE417"),leftAlign));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE418"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE419"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE420"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE421"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE422"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE423"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE424"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE425"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE426"),leftAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForRA.getString("GPF.ORDERLINE427"),leftAlign));
					dataList.add(rowList);*/








					/*String lStrEmpAdd = deptName + "," + lStrOfficeName;


					//gLogger.info("cond"+ddocode.equalsIgnoreCase(sub));
					if(ddocode.equalsIgnoreCase(sub)){

					rowList = new ArrayList<Object>();
					rowList.add("1." + lStrEmpAdd+" "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE72"));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE73")+" "+lStrTreasuryName);
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE74")+" "+"7101");
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE75"));
					dataList.add(rowList);



					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE76")+" "+ empName + gObjRsrcBndleForRA.getString("GPF.ORDERLINE761"));
					dataList.add(rowList);


					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE77")+" "+empName);
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE78"));
					dataList.add(rowList);


					}


					gLogger.info("cond"+!ddocode.equalsIgnoreCase(sub));
					if(!ddocode.equalsIgnoreCase(sub)){



						rowList = new ArrayList<Object>();
						rowList.add("1." +lStrEmpAdd+" "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE172"));
						dataList.add(rowList);

						rowList = new ArrayList<Object>();
						rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE173")+" "+" "+lStrTreasuryName);
						dataList.add(rowList);

						rowList = new ArrayList<Object>();
						rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE174"));
						dataList.add(rowList);

						rowList = new ArrayList<Object>();
						rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE176")+" "+empName+" "+gObjRsrcBndleForRA.getString("GPF.ORDERLINE1761"));
						dataList.add(rowList);

						rowList = new ArrayList<Object>();
						rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE177")+" "+empName);
						dataList.add(rowList);
						rowList = new ArrayList<Object>();
						rowList.add(gObjRsrcBndleForRA.getString("GPF.ORDERLINE178"));
						dataList.add(rowList);
					}*/





				}




				gLogger.info("************chkForGeneratedBill*********"+chkForGeneratedBill(gpfAccNo, lStrTransactionId) );

				if(chkForGeneratedBill(gpfAccNo, lStrTransactionId).equals("N"))
				{	


					gLogger.info("************chkForGeneratedBill*********"+chkForGeneratedBill(gpfAccNo, lStrTransactionId) );
					String lStrddocodetowhichbillforward = (String) report.getParameterValue("ddocode");
					gLogger.info("************lStrddocodetowhichbillforward*********"+lStrddocodetowhichbillforward);
					String locationcode = getLocationCode(lStrddocodetowhichbillforward);
					gLogger.info("************locationcode*********"+locationcode);



					inputMap.put("gpfAccNo", gpfAccNo);
					inputMap.put("transactionId", lStrTransactionId);



					inputMap.put("AdvanceType", reqType);
					inputMap.put("orderNo", lStrOrderId);
					inputMap.put("orderDate", lStrOrderDate);
					inputMap.put("openingBalc", lDblOpeningBal);
					inputMap.put("regularSub", Double.parseDouble(lObjGPFAccountBal[0].toString()));
					inputMap.put("advanceRecovery", Double.parseDouble(lObjGPFAccountBal[1].toString()));
					//inputMap.put("advanceSanctioned", lDblWithdrawalSanc);
					//gokarna lDbWithdrawalSancNew
					inputMap.put("advanceSanctioned", lDbWithdrawalSancNew);
					inputMap.put("locationcode", locationcode);

					inputMap.put("lObjLoginVO", lObjLoginVO);

					ResultObject resultObject = new ResultObject(ErrorConstants.SUCCESS);
					inputMap.put("serviceLocator", serviceLocator);

					resultObject = serviceLocator.executeService("GenerateBillDataGPF",inputMap);	

					updateLoanBillID(lStrTransactionId);
				}


			} 
		}
		catch (Exception e) {
			gLogger.error("Exception :" + e, e);
		} finally {
			try {
				if (smt != null) {
					smt.close();
				}

				if (rs != null) {
					rs.close();
				}

				if (con != null) {
					con.close();
				}

				smt = null;
				rs = null;
				con = null;

			} catch (Exception e1) {
				gLogger.error("Exception :" + e1, e1);

			}
		}
		return dataList;
	}

	public String space(int noOfSpace) {
		String blank = "";
		for (int i = 0; i < noOfSpace; i++) {
			blank += "\u00a0";
		}
		return blank;
	}
	public String getLocationCode(String lstrddocode) throws Exception {

		gLogger.info("lstrddocode"+lstrddocode);
		//gLogger.info("lLngYearId"+lLngYearId);

		String lDblOpeningBal = null;
		List lLstOpeningBal = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		try {
			lSBQuery
			.append(" SELECT LOCATION_CODE FROM ORG_DDO_MST where DDO_CODE='"+lstrddocode+"' ");

			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			/*lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			lQuery.setParameter("finYearId", lLngYearId);
			 */
			lLstOpeningBal = lQuery.list();
			if (lLstOpeningBal != null && lLstOpeningBal.size() > 0) {
				lDblOpeningBal =(lLstOpeningBal.get(0).toString());
				gLogger.info("lDblOpeningBal"+lDblOpeningBal);
			} else {
				lDblOpeningBal = "";
			}
		} catch (Exception e) {
			gLogger.error("Exception in getOpeningBalForCurrYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lDblOpeningBal;
	}
	public String chkForGeneratedBill(String lStrGpfAccNo, String lStrTransactionId)
	{
		gLogger.info("chkForGeneratedBill**"+lStrGpfAccNo+lStrTransactionId);
		String lStrChkBill = "";
		List lLstData = null;

		try{
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT BILL_DTLS_ID FROM MST_GPF_BILL_DTLS ");
			lSBQuery.append("WHERE GPF_ACC_NO = '"+lStrGpfAccNo+"' AND STATUS_FLAG <> 2 AND TRANSACTION_ID ='"+lStrTransactionId+"' ");

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			//lQuery.setParameter("gpfAccNo", lStrGpfAccNo);
			//lQuery.setParameter("transactionId", lStrTransactionId);
			lLstData = lQuery.list();
			gLogger.error("chkForGeneratedBill*****111*******"+lSBQuery.toString());
			gLogger.info("lLstData:::::::::::::**"+lLstData+"size::::::::::"+lLstData.size());
			if(lLstData != null && lLstData.size() > 0){
				lStrChkBill = "Y";

				gLogger.info("value is::::::::::"+lLstData.get(0).toString());
			}else{
				lStrChkBill = "N";
			}


		}catch (Exception e) {
			gLogger.error("Exception in chkForGeneratedBill:" + e, e);
		}

		return lStrChkBill;
	}

	public Double getAmtSanctioned(String lStrTransactionId, String reqType){

		gLogger.info("getAmtSanctioned"+lStrTransactionId+"reqType******"+reqType);


		List sanction = null;
		Double lDblSanctionedAmt = 0d;

		StringBuilder lSBQuery = new StringBuilder();
		if(reqType.equalsIgnoreCase("Conversion"))
		{
			reqType="NRA";
		}
		gLogger.info("reqType********"+reqType);
		try {

			if(reqType.equalsIgnoreCase("NRA") || reqType.equalsIgnoreCase("FW") || reqType.equalsIgnoreCase("Non-Refundable Advance")){
				lSBQuery.append( " SELECT nvl(advance.amount_Sanctioned,0) " ); 
				lSBQuery.append( " FROM MST_GPF_ADVANCE advance where advance.TRANSACTION_ID=:lStrTransactionId " );
			}


			if(reqType.equalsIgnoreCase("RA")){
				lSBQuery.append( " SELECT nvl(advance.payable_Amount,0)" ); 
				lSBQuery.append( " FROM MST_GPF_ADVANCE advance where advance.TRANSACTION_ID=:lStrTransactionId " );

			}
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

			lQuery.setParameter("lStrTransactionId", lStrTransactionId);

			gLogger.info("Query is:::::::::"+lSBQuery.toString());

			sanction = lQuery.list();
			if (sanction.size() != 0) {

				lDblSanctionedAmt = (Double)sanction.get(0);
			}

			gLogger.info("lDblSanctionedAmt"+lDblSanctionedAmt+"sanction"+sanction.size());

		} catch (Exception e) {
			gLogger.error("Exception in getAmtSanctioned : ", e);
		}
		return lDblSanctionedAmt;

	}

	public void updateLoanBillID(String trnascationid) {


		gLogger.info("**********in ***********updated*********");
		StringBuilder lSb = new StringBuilder();

		lSb.append("update MST_GPF_ADVANCE set LOAN_BILL_ID  = '"+trnascationid+"' where TRANSACTION_ID='"+trnascationid+"'");

		Query lQuery = ghibSession.createSQLQuery(lSb.toString());

		lQuery.executeUpdate();

		gLogger.info("updated*********");
	}


	public double getAmountSAnctionedRA(String trnascationid) {

		Double lDblAvanceSanced =0d;
		List lLstData=null;
		gLogger.info("**********in ***********updated*********");
		StringBuilder lSb = new StringBuilder();

		lSb.append(" SELECT PAYABLE_AMOUNT FROM MST_GPF_ADVANCE where TRANSACTION_ID='"+trnascationid+"'" );

		Query lQuery = ghibSession.createSQLQuery(lSb.toString());
		lLstData = lQuery.list();

		if(lLstData != null && lLstData.size() > 0){
			if(lLstData.get(0)!= null)
				lDblAvanceSanced = Double.parseDouble(lLstData.get(0).toString());
		}
		gLogger.info("lDblAvanceSanced*********"+lDblAvanceSanced+"Query is:::::::::::::"+lSb.toString());



		return lDblAvanceSanced;
	}

	public List getRetireMentDateandDOJ(String strSevId) throws Exception {
		gLogger.info("lstrddocode"+strSevId);
		List lLstRetireMentDate = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		Query lQuery = null;
		try {
			lSBQuery.append(" SELECT to_char(SUPER_ANN_DATE,'dd/mm/yyyy'),to_char(doj,'dd/mm/yyyy'),to_char(sysdate,'dd/mm/yyyy') ");
			lSBQuery.append(" FROM MST_DCPS_EMP where SEVARTH_ID = '"+strSevId+"' ");
			lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lLstRetireMentDate = lQuery.list();

		} catch (Exception e) {
			gLogger.error("Exception in getOpeningBalForCurrYear of GPFRequestProcessDAOImpl  : ", e);			
		}
		return lLstRetireMentDate;
	}




	/*public Date getDateDifference(Date date1, Date date2){
		Date date = new Date();
		long timeInMillA = 0;
		long timeInMillB = 0;
		Date convertedDateA;
		Date convertedDateB;

		Calendar cal = Calendar.getInstance();		          
		cal.setTime(date1);
		timeInMillA = cal.getTimeInMillis();

		cal.setTime(date);
		timeInMillB = cal.getTimeInMillis();

		 LocalDateTime startA = new LocalDateTime(timeInMillA);
		    LocalDateTime startB = new LocalDateTime(timeInMillB);

		    Period difference = new Period(startA, startB, PeriodType.days());
		    int day = difference.getDays();

		    difference = new Period(startA, startB, PeriodType.months());
		    int month = difference.getMonths();

		    difference = new Period(startA, startB, PeriodType.years());
		    int year = difference.getYears();

		    difference = new Period(startA, startB, PeriodType.weeks());
		    int week = difference.getWeeks();

		    difference = new Period(startA, startB, PeriodType.hours());
		    int hour = difference.getHours();

		    difference = new Period(startA, startB, PeriodType.minutes());
		    long min = difference.getMinutes();

		    difference = new Period(startA, startB, PeriodType.seconds());
		    long sec = difference.getSeconds();

		    //difference = new Period(startA, startB, PeriodType.millis());
		    long mili = timeInMillB - timeInMillA; 
		return date;
	}*/


//Sooraj3/3/2018
	public String getOrderRefNo(String transactionId)
	{
		String OrderRef = "";
		List RefOrder = null;
		StringBuilder lSBQuery = new StringBuilder();
		
		try
		{
			lSBQuery.append( " SELECT ORDERREFERENCENO FROM MST_GPF_ADVANCE where TRANSACTION_ID ='"+transactionId+"'");
			Query iquery = ghibSession.createSQLQuery(lSBQuery.toString());
			RefOrder =iquery.list();
			if(RefOrder!= null && RefOrder.size()>0 && RefOrder.get(0)!=null)
			{
				OrderRef = RefOrder.get(0).toString();
			}
			
		} catch (Exception e) {
			gLogger.error("Exception in getLocationCode : ", e);
		}
		return OrderRef;
	}
	//Added by Sooraj 15/05/2018
	public String getEmployeeName(String SevId)
	{
		List nameList = null;
		String empName = "";
		StringBuilder lSBQuery = new StringBuilder();
		try
		{
			lSBQuery.append("SELECT EMP_NAME FROM MST_DCPS_EMP where SEVARTH_ID ='"+SevId+"'");
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			nameList =lQuery.list();
			if(nameList.size()>0 && nameList != null & nameList.get(0) != null)
			{
				empName = nameList.get(0).toString();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			gLogger.info(" Exception in getEmployeeName, GPFOrderReport");
		}
		return empName;
	}
	//20-06-2018
	public String getStatusFlag(String lStrTransactionId)
	{
		List statusFlag = null;
		StringBuilder sb =new StringBuilder();
		String status = "";
		try
		{
			sb.append("SELECT STATUS_FLAG FROM MST_GPF_BILL_DTLS where TRANSACTION_ID = '"+lStrTransactionId+"' and STATUS_FLAG = 1");
			Query lQuery =ghibSession.createSQLQuery(sb.toString());
			statusFlag =lQuery.list();
			if(statusFlag.size()>0 && statusFlag != null && statusFlag.get(0) != null)
			{
				status = statusFlag.get(0).toString();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}
	public String getBillDate(String lStrTransactionId)
	{
		List statusFlag = null;
		StringBuilder sb =new StringBuilder();
		String status = "";
		try
		{
			sb.append("SELECT to_char(BILL_GENERATED_DATE,'dd/mm/yyyy') FROM MST_GPF_BILL_DTLS where TRANSACTION_ID ='"+lStrTransactionId+"' ");
			Query lQuery =ghibSession.createSQLQuery(sb.toString());
			statusFlag =lQuery.list();
			if(statusFlag.size()>0 && statusFlag != null && statusFlag.get(0) != null)
			{
				status = statusFlag.get(0).toString();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}
	//Created method By Vivek Sharma 14 July 2017
	public List getRegEmiAndMonthlySub(String strSevaarthId) {
		List lstRegEmiAndMonthlySub = null;

		gLogger.info("strSevaarthId"+strSevaarthId);

		StringBuilder lSBQuery = new StringBuilder();

		try {
			lSBQuery.append( "SELECT nvl(pay.GPF_ADV_GRP_D,0),nvl(pay.GPF_GRP_D,0) FROM HR_PAY_PAYBILL pay inner join PAYBILL_HEAD_MPG head on pay.PAYBILL_GRP_ID = head.PAYBILL_ID " ); 
			lSBQuery.append( " inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID " ); 
			lSBQuery.append( " inner join mst_dcps_emp emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID " ); 
			lSBQuery.append( " where emp.SEVARTH_ID = '"+strSevaarthId+"'  and head.APPROVE_FLAG = 1 and head.BILL_CATEGORY <> 3" ); 
			lSBQuery.append( " and head.PAYBILL_MONTH = (SELECT max(head.PAYBILL_MONTH) FROM HR_PAY_PAYBILL pay inner join PAYBILL_HEAD_MPG head " );
			lSBQuery.append( " on pay.PAYBILL_GRP_ID = head.PAYBILL_ID inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID " );
			lSBQuery.append( " inner join mst_dcps_emp emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID " );
			lSBQuery.append( " where emp.SEVARTH_ID = '"+strSevaarthId+"'  and head.APPROVE_FLAG = 1 and head.BILL_CATEGORY <> 3" );
			lSBQuery.append( " and head.PAYBILL_YEAR = (SELECT max(head.PAYBILL_YEAR) FROM HR_PAY_PAYBILL pay " );
			lSBQuery.append( " inner join PAYBILL_HEAD_MPG head on pay.PAYBILL_GRP_ID = head.PAYBILL_ID " );
			lSBQuery.append( " inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID inner join mst_dcps_emp emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID    " );
			lSBQuery.append( " where emp.SEVARTH_ID = '"+strSevaarthId+"'  and head.APPROVE_FLAG = 1 and head.BILL_CATEGORY <> 3) ) " );
			lSBQuery.append( "  and head.PAYBILL_YEAR = (SELECT max(head.PAYBILL_YEAR) FROM HR_PAY_PAYBILL pay " );
			lSBQuery.append( " inner join PAYBILL_HEAD_MPG head on pay.PAYBILL_GRP_ID = head.PAYBILL_ID " );
			lSBQuery.append( " inner join HR_EIS_EMP_MST eis on eis.EMP_ID = pay.EMP_ID  " );
			lSBQuery.append( " inner join mst_dcps_emp emp on emp.ORG_EMP_MST_ID = eis.EMP_MPG_ID   " );
			lSBQuery.append( " where emp.SEVARTH_ID = '"+strSevaarthId+"'  and head.APPROVE_FLAG = 1 and head.BILL_CATEGORY <> 3) " );
			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lstRegEmiAndMonthlySub = lQuery.list();

		} catch (Exception e) {
			gLogger.error("Exception in getLocationCode : ", e);
		}
		return lstRegEmiAndMonthlySub;

	}


}
