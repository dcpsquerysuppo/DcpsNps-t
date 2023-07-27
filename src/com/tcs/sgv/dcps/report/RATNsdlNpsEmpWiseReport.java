package com.tcs.sgv.dcps.report;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tcs.sgv.common.business.reports.DefaultReportDataFinder;
import com.tcs.sgv.common.exception.reports.ReportException;
import com.tcs.sgv.common.util.EnglishDecimalFormat;
import com.tcs.sgv.common.util.reports.IReportConstants;
import com.tcs.sgv.common.valuebeans.reports.ReportVO;
import com.tcs.sgv.common.valuebeans.reports.StyleVO;
import com.tcs.sgv.common.valuebeans.reports.StyledData;
import com.tcs.sgv.common.valuebeans.reports.TabularData;
import com.tcs.sgv.common.valuebeans.reports.URLData;
import com.tcs.sgv.core.service.ServiceLocator;
import com.tcs.sgv.dcps.dao.MatchContriEntryDAOImpl;
import com.tcs.sgv.dcps.dao.NsdlNpsDAOImpl;
import com.tcs.sgv.dcps.service.DcpsCommonDAO;
import com.tcs.sgv.dcps.service.DcpsCommonDAOImpl;
import com.tcs.sgv.gpf.dao.GPFRequestProcessDAO;
import com.tcs.sgv.gpf.dao.GPFRequestProcessDAOImpl;
import com.tcs.sgv.lna.dao.LNAHealthInsuranceDAO;
import com.tcs.sgv.lna.dao.LNAHealthInsuranceDAOImpl;

import java.util.ResourceBundle;

public class RATNsdlNpsEmpWiseReport extends DefaultReportDataFinder {

private static final Logger gLogger = Logger.getLogger(RATNsdlNpsEmpWiseReport.class);
public static String newline = System.getProperty("line.separator");

// private final Logger gGLogger = Logger.getLogger(getClass());
String Lang_Id = "en_US";
String Loc_Id = "LC1";

Map requestAttributes = null;

// $t 2020 9-1
/*SessionFactory sessionFactory1 = null; 
Session neardrSession = null;*/
SessionFactory lObjSessionFactory = null;

ServiceLocator serviceLocator = null;

public Collection findReportData(ReportVO report, Object criteria)
		throws ReportException {

	String locId = report.getLocId();

	Connection con = null;

	criteria.getClass();

	Statement smt = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ArrayList dataList = new ArrayList();
	ArrayList dataList1 = new ArrayList();
	ArrayList dataList43 = new ArrayList();
	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	// for Center Alignment format
	StyleVO[] CenterAlignVO = new StyleVO[2];
	CenterAlignVO[0] = new StyleVO();
	CenterAlignVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	CenterAlignVO[0]
			.setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
	CenterAlignVO[1] = new StyleVO();
	CenterAlignVO[1].setStyleId(IReportConstants.BORDER);
	CenterAlignVO[1]
			.setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);

	StyleVO[] noBorder = new StyleVO[1];
	noBorder[0] = new StyleVO();
	noBorder[0].setStyleId(IReportConstants.BORDER);
	noBorder[0].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);

	StyleVO[] leftboldStyleVO = new StyleVO[3];
	leftboldStyleVO[0] = new StyleVO();
	leftboldStyleVO[0].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
	leftboldStyleVO[0]
			.setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_NORMAL);
	leftboldStyleVO[1] = new StyleVO();
	leftboldStyleVO[1].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	leftboldStyleVO[1].setStyleValue("Left");
	leftboldStyleVO[2] = new StyleVO();
	leftboldStyleVO[2].setStyleId(IReportConstants.BORDER);
	leftboldStyleVO[2]
			.setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
	StyleVO[] rowsFontsVO = new StyleVO[5];		
	rowsFontsVO[0] = new StyleVO();
	rowsFontsVO[0].setStyleId(IReportConstants.BACKGROUNDCOLOR);
	rowsFontsVO[0].setStyleValue("white");
	rowsFontsVO[1] = new StyleVO();
	rowsFontsVO[1].setStyleId(IReportConstants.BORDER);
	rowsFontsVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
	rowsFontsVO[2] = new StyleVO();
	rowsFontsVO[2].setStyleId(26);
	rowsFontsVO[2].setStyleValue("JavaScript:self.close()");
	rowsFontsVO[3] = new StyleVO();
	rowsFontsVO[3].setStyleId(IReportConstants.REPORT_PAGINATION);
	rowsFontsVO[3].setStyleValue("NO");
	rowsFontsVO[4] = new StyleVO();
	rowsFontsVO[4].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	rowsFontsVO[4].setStyleValue(IReportConstants.VALUE_FONT_SIZE_MEDIUM);
	
	StyleVO[] rightAlign = new StyleVO[2];
	rightAlign[0] = new StyleVO();
	rightAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	rightAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_RIGHT);
	rightAlign[1] = new StyleVO();
	rightAlign[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	rightAlign[1].setStyleValue(IReportConstants.VALUE_FONT_SIZE_MEDIUM);
	
	StyleVO[] centerAlign = new StyleVO[3];
	centerAlign[0] = new StyleVO();
	centerAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	centerAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
	centerAlign[1] = new StyleVO();
	centerAlign[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	centerAlign[1].setStyleValue(IReportConstants.VALUE_FONT_SIZE_MEDIUM);
	centerAlign[2] = new StyleVO();
	centerAlign[2].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
	centerAlign[2].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
	
	StyleVO[] centerAlign1 = new StyleVO[2];
	centerAlign1[0] = new StyleVO();
	centerAlign1[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	centerAlign1[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
	centerAlign1[1] = new StyleVO();
	centerAlign1[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	centerAlign1[1].setStyleValue("14");
	
	StyleVO[] CenterBoldAlignVO = new StyleVO[4];
	CenterBoldAlignVO[0] = new StyleVO();
	CenterBoldAlignVO[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	CenterBoldAlignVO[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
	CenterBoldAlignVO[1] = new StyleVO();
	CenterBoldAlignVO[1].setStyleId(IReportConstants.BORDER);
	CenterBoldAlignVO[1].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_NONE);
	CenterBoldAlignVO[2] = new StyleVO();
	//CenterBoldAlignVO[2].setStyleValue(IReportConstants.VALUE_FONT_SIZE_LARGE);
	//	CenterBoldAlignVO[2].setStyleValue("12");
	CenterBoldAlignVO[2].setStyleValue(IReportConstants.VALUE_FONT_WEIGHT_BOLD);
	CenterBoldAlignVO[2].setStyleId(IReportConstants.STYLE_FONT_WEIGHT);
	CenterBoldAlignVO[3] = new StyleVO();
	CenterBoldAlignVO[3].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	CenterBoldAlignVO[3].setStyleValue(IReportConstants.VALUE_FONT_SIZE_LARGE);
	
	StyleVO[] leftAlign = new StyleVO[2];
	leftAlign[0] = new StyleVO();
	leftAlign[0].setStyleId(IReportConstants.STYLE_FONT_ALIGNMENT);
	leftAlign[0].setStyleValue(IReportConstants.VALUE_FONT_ALIGNMENT_LEFT);
	leftAlign[1] = new StyleVO();
	leftAlign[1].setStyleId(IReportConstants.STYLE_FONT_SIZE);
	leftAlign[1].setStyleValue("14");
	
	
	
	try {

		requestAttributes = (Map) ((Map) criteria)
				.get(IReportConstants.REQUEST_ATTRIBUTES);
		serviceLocator = (ServiceLocator) requestAttributes
				.get("serviceLocator");
		lObjSessionFactory = serviceLocator.getSessionFactorySlave();
		// $t 2020 9-1
		/*sessionFactory1 = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session neardrSession = sessionFactory1.openSession();*/
        //
		Map requestAttributes = (Map) ((Map) criteria)
				.get(IReportConstants.REQUEST_ATTRIBUTES);

		ServiceLocator serviceLocator = (ServiceLocator) requestAttributes
				.get("serviceLocator");

		ResourceBundle gObjRsrcBndle = ResourceBundle.getBundle("resources/dcps/dcpsLabels");

		SessionFactory lObjSessionFactory = serviceLocator.getSessionFactorySlave();
		con = lObjSessionFactory.getCurrentSession().connection();
		smt = con.createStatement();
		Map sessionKeys = (Map) ((Map) criteria)
				.get(IReportConstants.SESSION_KEYS);
		Map loginDetail = (HashMap) sessionKeys.get("loginDetailsMap");

		Long locationId = (Long) loginDetail.get("locationId");

		String StrSqlQuery = "";

		if (report.getReportCode().equals("800009605")) {

			// report.setStyleList(noBorder);

			ArrayList rowList = new ArrayList();

			Long ddoCode = Long.valueOf((String) report
					.getParameterValue("ddoCode"));
			String year = ((String) report.getParameterValue("year"));
			String month = ((String) report.getParameterValue("month"));
			String cmbTr = ((String) report.getParameterValue("cmbTr"));
			String ifDeputation = ((String) report.getParameterValue("ifDeputation"));
			String billNo = ((String) report.getParameterValue("billNo"));
			
			gLogger.info("treasuryCode****" + cmbTr);

			String lStrDDOCode = null;
			String lStrDDOName = "";

			DcpsCommonDAO lObjDcpsCommonDAO = new DcpsCommonDAOImpl(null,
					serviceLocator.getSessionFactory());
			String url = "";

            if(ifDeputation.equals("1"))
			url = "ifms.htm?actionFlag=loadNPSNSDLFormRAT&elementId=1900001549&yearId="+ year + "&monthId=" + month + "&cmbTr=" + cmbTr+"&ifDeputation=1";
            else
			url = "ifms.htm?actionFlag=loadNPSNSDLFormRAT&elementId=1900001549&yearId="+ year + "&monthId=" + month + "&cmbTr=" + cmbTr+"&ifDeputation=2";

			StyleVO[] lObjStyleVO = new StyleVO[report.getStyleList().length];
			lObjStyleVO = report.getStyleList();
			for (Integer lInt = 0; lInt < report.getStyleList().length; lInt++) {
				if (lObjStyleVO[lInt].getStyleId() == 26) {
					lObjStyleVO[lInt].setStyleValue(url);
				}
			}

			StringBuilder SBQuery = new StringBuilder();

			if (ifDeputation != null && !"".equalsIgnoreCase(ifDeputation)
					&& "2".equalsIgnoreCase(ifDeputation)) {
				List TableData = null;
				List TableData1 = null;
				List TableData2 = null;

				SBQuery.append(" select a.EMP_NAME,a.SEVARTH_ID,a.DCPS_ID,a.PRAN_NO,a.ddo_code,cast(a.emp_amt-nvl(b.sd_amnt,0) as double) as employye ,cast(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0) as double) as emplr_amount,a.loc_name,a.dto_reg_no,a.ddo_reg_no,b.sd_amnt from ");
				SBQuery.append(" (SELECT emp.EMP_NAME,emp.SEVARTH_ID,emp.DCPS_ID,emp.PRAN_NO,sum(paybill.DCPS)+sum(paybill.DCPS_PAY)+sum(paybill.DCPS_DELAY)+sum(paybill.DCPS_DA)+sum(paybill.DCPS_PAY_DIFF) as emp_amt,");////PayArrearDiff $t 23-2-2021
				SBQuery.append(" sum(paybill.DED_ADJUST) as DED_ADJUST,loc.loc_name,dto.dto_reg_no,reg.ddo_reg_no,ddo.ddo_code FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on eis.EMP_MPG_ID=emp.ORG_EMP_MST_ID ");
				SBQuery.append(" inner join NSDL_PAYBILL_DATA paybill on paybill.EMP_ID=eis.EMP_ID ");
				SBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID=paybill.PAYBILL_GRP_ID ");
				SBQuery.append(" and paybill.DCPS+paybill.DCPS_PAY+paybill.DCPS_DELAY+paybill.DCPS_DA+paybill.DCPS_PAY_DIFF <=paybill.DED_ADJUST  ");////PayArrearDiff $t 23-2-2021
				SBQuery.append(" inner join ORG_DDO_MST ddo on ddo.LOCATION_CODE=paybill.LOC_ID ");
				SBQuery.append(" inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code  ");
				SBQuery.append(" inner join MST_DTO_REG dto on substr(dto.LOC_ID,1,2)=substr(ddo.ddo_code,1,2)  ");
				SBQuery.append(" inner join CMN_LOCATION_MST loc on loc.LOC_ID=substr(ddo.DDO_CODE,1,4)  ");
				SBQuery.append(" where head.PAYBILL_YEAR=" + year
						+ " and head.PAYBILL_MONTH=" + month
						+ "  and  ddo.ddo_code='" + ddoCode + "' and head.bill_no='"+billNo+"' and ");
				SBQuery.append(" emp.PRAN_NO is not null and PRAN_ACTIVE=1 and emp.REG_STATUS=1 and head.APPROVE_FLAG=1 and head.RAT_FLAG=1 ");
				SBQuery.append(" group by  emp.EMP_NAME,emp.SEVARTH_ID,emp.DCPS_ID,emp.PRAN_NO,loc.loc_name,dto.dto_reg_no,reg.ddo_reg_no,ddo.ddo_code ) a left outer join ");
				SBQuery.append(" (SELECT sd.SD_PRAN_NO,cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt ,cast(sum(nvl(sd.SD_EMPLR_AMOUNT,0)) as double) as sd_amnt_emplr,bh.YEAR,bh.MONTH ,sd.ddo_reg_no FROM RAT_NSDL_SD_DTLS sd ");
				SBQuery.append(" inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.RAT_STATUS <>-1 and bh.file_name not like '%D' and sd.SD_REMARK like 'Contribution for%' and cast(sd.SD_EMP_AMOUNT as bigint) <= cast(sd.SD_EMPlr_AMOUNT as bigint) ");////$t 6-11-2020 emplr 4%  /////$t  25-3-2021
				//SBQuery.append(" inner join NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.STATUS <>-1 ");////$t 6-11-2020 emplr 4%
				SBQuery.append(" and bh.YEAR="
						+ year
						+ " and bh.MONTH="
						+ month
						+ " and bh.file_name like '"
						+ cmbTr
						+ "%' group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ,sd.ddo_reg_no ) b on b.SD_PRAN_NO=a.PRAN_NO and b.ddo_reg_no=a.ddo_reg_no ");
				SBQuery.append(" where cast(a.emp_amt-nvl(b.sd_amnt,0) as double) > 0 or cast(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0) as double) > 0 order by  a.ddo_reg_no ");

				 Session ghibSession =ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
				 Query lQuery =ghibSession.createSQLQuery(SBQuery.toString());
				// $t 2020 9-1
				//SessionFactory sessionFactory1 = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				//Session neardrSession = sessionFactory1.openSession();
				//Query lQuery = neardrSession.createSQLQuery(SBQuery.toString());
				//

				gLogger.info("script for all Rregular employee ---------"
						+ SBQuery.toString());

				TableData1 = lQuery.list();

				gLogger.info("script for all Rregular employee ---------"
						+ SBQuery.toString());

				StringBuilder SBQuery1 = new StringBuilder();

				SBQuery1.append(" select a.EMP_NAME,a.SEVARTH_ID,a.DCPS_ID,a.PRAN_NO,a.ddo_code,cast(a.emp_amt-nvl(b.sd_amnt,0) as double) as employye ,cast(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0) as double) as emplr_amount,a.loc_name,a.dto_reg_no,a.ddo_reg_no,b.sd_amnt from ");
				SBQuery1.append(" (SELECT emp.EMP_NAME,emp.SEVARTH_ID,emp.DCPS_ID,emp.PRAN_NO,ddo.ddo_code, '0' as emp_amt,");
				SBQuery1.append(" sum(paybill.emplr_contri_arrears)+SUM(nvl(paybill.NPS_EMPLR_DIFFERENCE_ADJ,0)) as DED_ADJUST,loc.loc_name,dto.dto_reg_no,reg.ddo_reg_no FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on eis.EMP_MPG_ID=emp.ORG_EMP_MST_ID ");
				SBQuery1.append(" inner join NSDL_PAYBILL_DATA paybill on paybill.EMP_ID=eis.EMP_ID ");
				SBQuery1.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID=paybill.PAYBILL_GRP_ID ");
				SBQuery1.append(" inner join ORG_DDO_MST ddo on ddo.LOCATION_CODE=paybill.LOC_ID ");
				SBQuery1.append(" inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code  ");
				SBQuery1.append(" inner join MST_DTO_REG dto on substr(dto.LOC_ID,1,2)=substr(ddo.ddo_code,1,2)  ");
				SBQuery1.append(" inner join CMN_LOCATION_MST loc on loc.LOC_ID=substr(ddo.DDO_CODE,1,4)  ");
				SBQuery1.append(" where head.PAYBILL_YEAR=" + year
						+ " and head.PAYBILL_MONTH=" + month
						+ "  and  ddo.ddo_code='" + ddoCode + "'  and head.bill_no='"+billNo+"' and  ");
				SBQuery1.append(" emp.PRAN_NO is not null and PRAN_ACTIVE=1 and emp.REG_STATUS=1 and head.APPROVE_FLAG=1 and head.RAT_FLAG=1 AND (cast((nvl(paybill.emplr_contri_arrears,0)) as double) <> 0  or cast((nvl(paybill.NPS_EMPLR_DIFFERENCE_ADJ,0)) as double) <> 0)  ");
				SBQuery1.append(" group by  emp.EMP_NAME,emp.SEVARTH_ID,emp.DCPS_ID,emp.PRAN_NO,loc.loc_name,dto.dto_reg_no,reg.ddo_reg_no,ddo.ddo_code ) a left outer join ");
				SBQuery1.append(" (SELECT sd.SD_PRAN_NO,cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt ,cast(sum(nvl(sd.SD_EMPLR_AMOUNT,0)) as double) as sd_amnt_emplr,bh.YEAR,bh.MONTH ,sd.ddo_reg_no FROM RAT_NSDL_SD_DTLS sd ");
				SBQuery1.append(" inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.RAT_STATUS <>-1 and sd.SD_REMARK like '4 Per Contribution%' ");////$t 6-11-2020 emplr 4%
				//SBQuery1.append(" inner join NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.STATUS <>-1 ");////$t 6-11-2020 emplr 4%
				SBQuery1.append(" and bh.YEAR="
						+ year
						+ " and bh.MONTH="
						+ month
						+ " and bh.file_name like '"
						+ cmbTr
						+ "%' group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ,sd.ddo_reg_no ) b on b.SD_PRAN_NO=a.PRAN_NO and b.ddo_reg_no=a.ddo_reg_no ");
				SBQuery1.append(" where cast(a.emp_amt-nvl(b.sd_amnt,0) as double) >= 0 or cast(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0) as double) > 0 order by  a.ddo_reg_no ");

				//Session ghibSession =ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
				Query lQuery1 =ghibSession.createSQLQuery(SBQuery1.toString());

				// $t 2020 9-1
				//Query lQuery1 = neardrSession.createSQLQuery(SBQuery1.toString());
				//
				gLogger.info("script for all Rregular employee ---------"
						+ SBQuery1.toString());

				TableData2 = lQuery1.list();

				if ((TableData1 != null && TableData1.size() > 0)
						|| (TableData2 != null && TableData2.size() > 0)) {
					TableData = new ArrayList();
					if (TableData1 != null && TableData1.size() > 0) {
						TableData.addAll(TableData1);

					}
					if (TableData2 != null && TableData2.size() > 0) {
						TableData.addAll(TableData2);
					}
				}

				gLogger.info("script for all Rregular employee ---------"
						+ SBQuery1.toString());

				Integer counter = 1;
				String amount = null;
				Double lLongEmployeeAmt = 0d;
				Double lLongEmployerAmt = 0d;
				Double TotalAmt = 0d;
				Double EmployeeAmt = 0.0;

				if (TableData != null && TableData.size() > 0) {
					for (int i = 0; i < TableData.size(); i++) {
						Object[] tupleSub = (Object[]) TableData.get(i);
						rowList = new ArrayList();

						lLongEmployeeAmt = Double.parseDouble(tupleSub[5]
								.toString());

						lLongEmployeeAmt = Double.parseDouble(tupleSub[5]
								.toString());

						lLongEmployerAmt = Double.parseDouble(tupleSub[6]
								.toString());
						TotalAmt = lLongEmployeeAmt + lLongEmployerAmt;
						amount = TotalAmt.toString();
						rowList.add(new StyledData(counter, CenterAlignVO));
						if (tupleSub[0] != null) {
							rowList.add(new StyledData(tupleSub[0],CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[1] != null) {
							rowList.add(new StyledData(tupleSub[1],CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[2] != null) {
							rowList.add(new StyledData(tupleSub[2],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[3] != null) {
							rowList.add(new StyledData(tupleSub[3],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[4] != null) {
							rowList.add(new StyledData(tupleSub[4],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[5] != null) {
							rowList.add(new StyledData(tupleSub[5],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[6] != null) {
							rowList.add(new StyledData(tupleSub[6],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}

						rowList.add(new StyledData(amount, CenterAlignVO));
						/*
						 * if (tupleSub[6] != null) { rowList.add(new
						 * StyledData(tupleSub[6], CenterAlignVO)); }
						 */

						// rowList.add(new StyledData("-", CenterAlignVO));

						/*
						 * if (tupleSub[7] != null) { rowList.add(new
						 * StyledData(tupleSub[7], CenterAlignVO)); }
						 */

						// rowList.add(new StyledData("-", CenterAlignVO));

						dataList.add(rowList);

						counter = counter + 1;
					}
				}
			} else {
				Long y = Long.parseLong(year);
				if (Integer.parseInt(month) < 4) {
					y = Long.parseLong(year) - 1;
				}
				Long finYearId = getFinYearId(y.toString());
				List TableData = null;
				List TableData1 = null;
				List TableData2 = null;
				List TableData3 = null;

				//Session ghibSession = ServiceLocator.getServiceLocator()
				//		.getSessionFactorySlave().getCurrentSession();

				String s = ddoCode.toString();
				String s1 = s.substring(4, 8);
				String s2 = s.substring(8, 10);

				NsdlNpsDAOImpl lObjNsdlDAO = new NsdlNpsDAOImpl(null,
						this.serviceLocator.getSessionFactory());

				List l = lObjNsdlDAO.cehckIfTreasuryDdoCode(ddoCode);

				if (l != null && l.size() > 0 && l.get(0) != null) {
					StringBuilder sb1 = new StringBuilder();

					sb1.append(" SELECT abc.EMP_NAME,abc.sevarth_id, abc.DCPS_ID, abc.PRAN_NO, cast(abc.c-nvl(a.sd_amnt,0) as double), cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) from ");
					sb1.append(" (select mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO, cast(sum(nvl(trn.CONTRIBUTION,0) ) as double) as c, cast(sum(nvl(trn.CONTRIBUTION_EMPLR,0) ) as double) as d ");
					sb1.append(" FROM MST_DCPS_EMP mstemp inner join TRN_DCPS_CONTRIBUTION trn on trn.DCPS_EMP_ID=mstemp.DCPS_EMP_ID  inner join ORG_DDO_MST ddo on ddo.DDO_CODE='"+ ddoCode+ "' inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code ");
					sb1.append(" where trn.FIN_YEAR_ID="+ finYearId+ " and trn.MONTH_ID="+ month+ "   and substr(trn.TREASURY_CODE,1,2)='"+ cmbTr.substring(0, 2)+ "' " ////$t 8-4-2021 deputation KR one file
							+ "and (trn.IS_CHALLAN='Y' or trn.IS_ARREARS='Y') and trn.STATUS='H' and trn.IS_DEPUTATION='Y' and mstemp.PRAN_NO is not null and PRAN_ACTIVE=1  ");
					sb1.append(" and mstemp.DDO_CODE is null and mstemp.DEPT_DDO_CODE is null and mstemp.DCPS_OR_GPF='Y' ");
					sb1.append(" group by mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO ) abc ");
					sb1.append(" left outer join (    SELECT    sd.SD_PRAN_NO,    cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt , cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,    bh.YEAR,    bh.MONTH    FROM RAT_NSDL_SD_DTLS sd    inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME ");
					sb1.append(" and bh.RAT_STATUS <>-1 and bh.YEAR=" + year+ " and bh.MONTH=" + month + " and ");
					sb1.append(" bh.file_name like '"+ cmbTr.substring(0, 2)+ "%D'    group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.sd_pran_no=abc.pran_no ");////$t 8-4-2021 deputation KR one file
					sb1.append(" where  cast(abc.c-nvl(a.sd_amnt,0) as double) >0  or cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) >0 ");
					Session ghibSession =ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
					SQLQuery lQuery3 = ghibSession.createSQLQuery(sb1.toString());
					// $t 2020 9-1
					//Query lQuery3 = neardrSession.createSQLQuery(sb1.toString());
					//
					
					gLogger.info("script for all dept employee ---------"
							+ sb1.toString());

					TableData3 = lQuery3.list();

					StringBuilder SBQuery1 = new StringBuilder();

					SBQuery1.append(" SELECT abc.EMP_NAME,abc.sevarth_id, abc.DCPS_ID, abc.PRAN_NO, cast(abc.c-nvl(a.sd_amnt,0) as double), cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) from ");
					SBQuery1.append(" (select mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO, cast(sum(nvl(trn.CONTRIBUTION,0) ) as double) as c, cast(sum(nvl(trn.CONTRIBUTION_EMPLR,0) ) as double) as d  ");
					SBQuery1.append(" FROM MST_DCPS_EMP mstemp inner join TRN_DCPS_CONTRIBUTION trn on trn.DCPS_EMP_ID=mstemp.DCPS_EMP_ID  inner join ORG_DDO_MST ddo on ddo.DDO_CODE=mstemp.DDO_CODE inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code ");
					SBQuery1.append(" where trn.FIN_YEAR_ID="+ finYearId+ " and trn.MONTH_ID="+ month+ "  and substr(trn.TREASURY_CODE,1,2)='"+ cmbTr.substring(0, 2)+ "' " ////$t 8-4-2021 deputation KR one file
							+ "and (trn.IS_CHALLAN='Y' or trn.IS_ARREARS='Y') and trn.STATUS='H' and trn.IS_DEPUTATION='Y' and mstemp.PRAN_NO is not null and PRAN_ACTIVE=1  ");
					SBQuery1.append(" and  mstemp.DDO_CODE ='" + ddoCode+ "' and mstemp.DCPS_OR_GPF='Y' ");
					SBQuery1.append(" group by mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO ) abc ");
					SBQuery1.append(" left outer join (    SELECT    sd.SD_PRAN_NO,    cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt , cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,   bh.YEAR,    bh.MONTH    FROM RAT_NSDL_SD_DTLS sd    inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME ");
					SBQuery1.append(" and bh.RAT_STATUS <>-1  and bh.YEAR="+ year + " and bh.MONTH=" + month + " and ");
					SBQuery1.append(" bh.file_name like '"+ cmbTr.substring(0, 2)+ "%D'    group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.sd_pran_no=abc.pran_no "); ////$t 8-4-2021 deputation KR one file
					SBQuery1.append(" where  cast(abc.c-nvl(a.sd_amnt,0) as double) >0 or cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) >0 ");

					Query lQuery1 = ghibSession.createSQLQuery(SBQuery1.toString());
					
					// $t 2020 9-1
					//Query lQuery1 = neardrSession.createSQLQuery(SBQuery1.toString());
					//
					
					gLogger.info("script for all dept employee ---------"
							+ lQuery1.toString());
					TableData1 = lQuery1.list();

					StringBuilder SBQuery2 = new StringBuilder();

					SBQuery2.append(" SELECT abc.EMP_NAME,abc.sevarth_id, abc.DCPS_ID, abc.PRAN_NO, cast(abc.c-nvl(a.sd_amnt,0) as double), cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) from  ");
					SBQuery2.append(" (select mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO, cast(sum(nvl(trn.CONTRIBUTION,0) ) as double) as c, cast(sum(nvl(trn.CONTRIBUTION_EMPLR,0) ) as double) as d ");
					SBQuery2.append(" FROM MST_DCPS_EMP mstemp inner join TRN_DCPS_CONTRIBUTION trn on trn.DCPS_EMP_ID=mstemp.DCPS_EMP_ID  inner join ORG_DDO_MST ddo on ddo.DDO_CODE= mstemp.DEPT_DDO_CODE inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code ");
					SBQuery2.append(" where trn.FIN_YEAR_ID="+ finYearId+ " and trn.MONTH_ID="+ month+ " and substr(trn.TREASURY_CODE,1,2)='"+ cmbTr.substring(0, 2)+ "' " ////$t 8-4-2021 deputation KR one file
							+ "and (trn.IS_CHALLAN='Y' or trn.IS_ARREARS='Y') and trn.STATUS='H' and trn.IS_DEPUTATION='Y' and mstemp.PRAN_NO is not null and PRAN_ACTIVE=1  ");
					SBQuery2.append(" and mstemp.DDO_CODE is null and mstemp.DEPT_DDO_CODE ='"+ ddoCode + "' and mstemp.DCPS_OR_GPF='Y' ");
					SBQuery2.append(" group by mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO ) abc ");
					SBQuery2.append(" left outer join (    SELECT    sd.SD_PRAN_NO,    cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt , cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,   bh.YEAR,    bh.MONTH    FROM RAT_NSDL_SD_DTLS sd    inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME ");
					SBQuery2.append(" and bh.RAT_STATUS <>-1 and bh.YEAR="+ year + " and bh.MONTH=" + month + " and ");
					SBQuery2.append(" bh.file_name like '"+ cmbTr.substring(0, 2)+ "%D'    group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.sd_pran_no=abc.pran_no ");////$t 8-4-2021 deputation KR one file
					SBQuery2.append(" where  cast(abc.c-nvl(a.sd_amnt,0) as double) >0 or cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) >0 ");

					SQLQuery lQuery21 = ghibSession.createSQLQuery(SBQuery2.toString());
					
					// $t 2020 9-1
					//SQLQuery lQuery21 = neardrSession.createSQLQuery(SBQuery1.toString());
					//
					
					gLogger.info("script for all dept employee ---------"
							+ SBQuery2.toString());
					TableData2 = lQuery21.list();
				}else {
					SBQuery.append(" SELECT abc.EMP_NAME,abc.sevarth_id, abc.DCPS_ID, abc.PRAN_NO, cast(abc.c-nvl(a.sd_amnt,0) as double), cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) from ");
					SBQuery.append(" (select mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO, cast(sum(nvl(trn.CONTRIBUTION,0) ) as double) as c, cast(sum(nvl(trn.CONTRIBUTION_EMPLR,0) ) as double) as d  ");
					SBQuery.append(" FROM MST_DCPS_EMP mstemp inner join TRN_DCPS_CONTRIBUTION trn on trn.DCPS_EMP_ID=mstemp.DCPS_EMP_ID  inner join ORG_DDO_MST ddo on ddo.DDO_CODE=mstemp.DDO_CODE inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code ");
					SBQuery.append(" where trn.FIN_YEAR_ID="+ finYearId+ " and trn.MONTH_ID="+ month+ "  and substr(trn.TREASURY_CODE,1,2)='"+ cmbTr.substring(0, 2)+ "' " ////$t 8-4-2021 deputation KR one file
							+ "and (trn.IS_CHALLAN='Y' or trn.IS_ARREARS='Y') and trn.STATUS='H' and trn.IS_DEPUTATION='Y' and mstemp.PRAN_NO is not null and PRAN_ACTIVE=1  ");
					SBQuery.append(" and  mstemp.DDO_CODE ='" + ddoCode+ "' and mstemp.DCPS_OR_GPF='Y' ");
					SBQuery.append(" group by mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO) abc ");
					SBQuery.append(" left outer join (    SELECT    sd.SD_PRAN_NO,    cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt , cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,   bh.YEAR,    bh.MONTH    FROM RAT_NSDL_SD_DTLS sd    inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME ");
					SBQuery.append(" and bh.RAT_STATUS <>-1    and bh.YEAR="+ year + " and bh.MONTH=" + month + " and ");
					SBQuery.append(" bh.file_name like '"+ cmbTr.substring(0, 2)+ "%D'    group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.sd_pran_no=abc.pran_no ");////$t 8-4-2021 deputation KR one file
					SBQuery.append(" where  cast(abc.c-nvl(a.sd_amnt,0) as double) >0 or cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) >0 ");

					StringBuilder sb2 = new StringBuilder();

					sb2.append(" SELECT abc.EMP_NAME,abc.sevarth_id, abc.DCPS_ID, abc.PRAN_NO, cast(abc.c-nvl(a.sd_amnt,0) as double), cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) from  ");
					sb2.append(" (select mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO, cast(sum(nvl(trn.CONTRIBUTION,0) ) as double) as c, cast(sum(nvl(trn.CONTRIBUTION_EMPLR,0) ) as double) as d ");
					sb2.append(" FROM MST_DCPS_EMP mstemp inner join TRN_DCPS_CONTRIBUTION trn on trn.DCPS_EMP_ID=mstemp.DCPS_EMP_ID inner join ORG_DDO_MST ddo on ddo.DDO_CODE= mstemp.DEPT_DDO_CODE inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code ");
					sb2.append(" where trn.FIN_YEAR_ID="+ finYearId+ " and trn.MONTH_ID="+ month+ "  and substr(trn.TREASURY_CODE,1,2)='"+ cmbTr.substring(0, 2)+ "' " ////$t 8-4-2021 deputation KR one file
							+ "and (trn.IS_CHALLAN='Y' or trn.IS_ARREARS='Y') and trn.STATUS='H' and trn.IS_DEPUTATION='Y' and mstemp.PRAN_NO is not null and PRAN_ACTIVE=1  ");
					sb2.append(" and mstemp.DDO_CODE is null and mstemp.DEPT_DDO_CODE ='"+ ddoCode + "' and mstemp.DCPS_OR_GPF='Y' ");
					sb2.append(" group by mstemp.EMP_NAME,mstemp.sevarth_id, mstemp.DCPS_ID, mstemp.PRAN_NO ) abc ");
					sb2.append(" left outer join (    SELECT    sd.SD_PRAN_NO,    cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt , cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,   bh.YEAR,    bh.MONTH    FROM RAT_NSDL_SD_DTLS sd    inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME ");
					sb2.append(" and bh.RAT_STATUS <>-1    and bh.YEAR=" + year+ " and bh.MONTH=" + month + " and ");
					sb2.append(" bh.file_name like '"+ cmbTr.substring(0, 2)+ "%D'    group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.sd_pran_no=abc.pran_no ");////$t 8-4-2021 deputation KR one file
					sb2.append(" where  cast(abc.c-nvl(a.sd_amnt,0) as double) >0 or cast(abc.d-nvl(a.sd_emplr_amnt,0) as double) >0  ");

					Session ghibSession =ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
					Query lQuery = ghibSession.createSQLQuery(SBQuery.toString());
					
					// $t 2020 9-1
					//SQLQuery lQuery = neardrSession.createSQLQuery(sb2.toString());
					//
					
					//SQLQuery lQuery2 = ghibSession.createSQLQuery(sb2
					//		.toString());
					
					// $t 2020 9-1
					SQLQuery lQuery2 = ghibSession.createSQLQuery(sb2.toString());
					//

					gLogger.info("script for all dept employee ---------"
							+ SBQuery.toString());
					gLogger.info("script for all dept employee ---------"
							+ sb2.toString());
					TableData1 = lQuery.list();
					TableData2 = lQuery2.list();
				}

				if ((TableData1 != null && TableData1.size() > 0)
						|| (TableData2 != null && TableData2.size() > 0)
						|| (TableData3 != null && TableData3.size() > 0)) {
					TableData = new ArrayList();
					if (TableData1 != null && TableData1.size() > 0) {
						TableData.addAll(TableData1);

					}
					if (TableData2 != null && TableData2.size() > 0) {
						TableData.addAll(TableData2);
					}
					if (TableData3 != null && TableData3.size() > 0) {
						TableData.addAll(TableData3);
					}
				}
				Integer counter = 1;
				String amount = null;
				Double lLongEmployeeAmt = 0d;
				Double lLongEmployerAmt = 0d;
				Double TotalAmt = 0d;

				if (TableData != null && TableData.size() > 0) {
					for (int i = 0; i < TableData.size(); i++) {
						Object[] tupleSub = (Object[]) TableData.get(i);
						rowList = new ArrayList();

						lLongEmployeeAmt = Double.parseDouble(tupleSub[4]
								.toString());

						lLongEmployerAmt = Double.parseDouble(tupleSub[5]
								.toString());
						TotalAmt = lLongEmployeeAmt + lLongEmployerAmt;
						amount = TotalAmt.toString();
						rowList.add(new StyledData(counter, CenterAlignVO));
						if (tupleSub[0] != null) {
							rowList.add(new StyledData(tupleSub[0],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[1] != null) {
							rowList.add(new StyledData(tupleSub[1],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[2] != null) {
							rowList.add(new StyledData(tupleSub[2],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[3] != null) {
							rowList.add(new StyledData(tupleSub[3],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[4] != null) {
							rowList.add(new StyledData(tupleSub[4],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}
						if (tupleSub[5] != null) {
							rowList.add(new StyledData(tupleSub[5],
									CenterAlignVO));
						} else {
							rowList.add(new StyledData("-", CenterAlignVO));
						}

						rowList.add(new StyledData(amount, CenterAlignVO));
						/*
						 * if (tupleSub[6] != null) { rowList.add(new
						 * StyledData(tupleSub[6], CenterAlignVO)); }
						 */

						// rowList.add(new StyledData("-", CenterAlignVO));

						/*
						 * if (tupleSub[7] != null) { rowList.add(new
						 * StyledData(tupleSub[7], CenterAlignVO)); }
						 */

						// rowList.add(new StyledData("-", CenterAlignVO));

						dataList.add(rowList);

						counter = counter + 1;

					}
				}
			}

			gLogger.info("StrSqlQueryTEST#$#$#$***********"
					+ SBQuery.toString());

		}
		if (report.getReportCode().equals("800009606")) {

            // report.setStyleList(noBorder);
            ArrayList rowList = new ArrayList();

            String fileNo = report.getParameterValue("fileNo").toString();
            String year = ((String) report.getParameterValue("year"));
            String month = ((String) report.getParameterValue("month"));
            String url = "";
            
           //$t 5-11 2019  
            String lastChar = (fileNo.charAt(fileNo.length() -1))+"";
            lastChar.trim();
            gLogger.info("check file deputation is *********"+lastChar);
            // 

            if(lastChar.equals("D"))
            url = "ifms.htm?actionFlag=loadNPSNSDLGenFilesRAT&billFlag=Y&cmbYear="+year+"&cmbMonth="+month+"&cmbDep=1";
            else
            url = "ifms.htm?actionFlag=loadNPSNSDLGenFilesRAT&billFlag=Y&cmbYear="+year+"&cmbMonth="+month+"&cmbDep=2";

            StyleVO[] lObjStyleVO = new StyleVO[report.getStyleList().length];
            lObjStyleVO = report.getStyleList();
            for (Integer lInt = 0; lInt < report.getStyleList().length; lInt++) {
                if (lObjStyleVO[lInt].getStyleId() == 26) {
                    lObjStyleVO[lInt].setStyleValue(url);
                }
            }


            StringBuilder SBQuery = new StringBuilder();


            /*SBQuery.append(" SELECT distinct sd.SD_PRAN_NO,sd.DDO_REG_NO,sd.SD_EMP_AMOUNT,sd.SD_EMPLR_AMOUNT,sd.SD_TOTAL_AMT FROM NSDL_SD_DTLS sd ");
            SBQuery.append(" inner join NSDL_BH_DTLS bh on bh.FILE_NAME = sd.FILE_NAME and bh.STATUS <> '-1' and bh.FILE_NAME ='"+fileNo+"'");*/
            
            SBQuery.append(" SELECT distinct sd.SD_PRAN_NO,m.ddo_code,sd.DDO_REG_NO,sd.SD_EMP_AMOUNT,sd.SD_EMPLR_AMOUNT,sd.SD_TOTAL_AMT,emp.SEVARTH_ID,emp.EMP_NAME FROM RAT_NSDL_SD_DTLS sd ");////m.ddo_code,
            SBQuery.append(" inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME = sd.FILE_NAME inner join MST_DDO_REG  m on sd.DDO_REG_NO = m.DDO_REG_NO inner join mst_Dcps_emp emp on sd.sd_pran_no = emp.pran_no ");
            SBQuery.append(" and bh.RAT_STATUS <> '-1' and bh.FILE_NAME ='"+fileNo+"' order by m.ddo_code ");/////$t KR 
         
            gLogger.info("StrSqlQuery***********"+SBQuery.toString());

            StrSqlQuery = SBQuery.toString();
            rs = smt.executeQuery(StrSqlQuery);
            Integer counter = 1;
          
            while (rs.next()) {

                rowList = new ArrayList();
                
                rowList.add(new StyledData(counter, CenterAlignVO));
                rowList.add(new StyledData(rs.getString(1), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(7), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(8), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(2), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(3), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(4), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(5), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(6), CenterAlignVO));
                
         
                dataList.add(rowList);

                counter = counter + 1;
            }
        }
		
        if (report.getReportCode().equals("800009609")) {

            // report.setStyleList(noBorder);

            ArrayList rowList = new ArrayList();

            String fileNo = report.getParameterValue("fileNo").toString();
            String year = ((String) report.getParameterValue("year"));
            String month = ((String) report.getParameterValue("month"));

            String lStrDDOCode = null;
            String lStrDDOName = "";


            DcpsCommonDAO lObjDcpsCommonDAO = new DcpsCommonDAOImpl(null,serviceLocator.getSessionFactory());
            String url = "";

          //$t 5-11 2019  
            String lastChar = (fileNo.charAt(fileNo.length() -1))+"";
            lastChar.trim();
            gLogger.info("check file deputation is *********"+lastChar);
            // 

            if(lastChar.equals("D"))
            url = "ifms.htm?actionFlag=loadNPSNSDLGenFilesRATF&billFlag=Y&cmbYear="+year+"&cmbMonth="+month+"&cmbDep=1";
            else
            url = "ifms.htm?actionFlag=loadNPSNSDLGenFilesRATF&billFlag=Y&cmbYear="+year+"&cmbMonth="+month+"&cmbDep=2";

            StyleVO[] lObjStyleVO = new StyleVO[report.getStyleList().length];
            lObjStyleVO = report.getStyleList();
            for (Integer lInt = 0; lInt < report.getStyleList().length; lInt++) {
                if (lObjStyleVO[lInt].getStyleId() == 26) {
                    lObjStyleVO[lInt].setStyleValue(url);
                }
            }


            StringBuilder SBQuery = new StringBuilder();


            /*SBQuery.append(" SELECT distinct sd.SD_PRAN_NO,sd.DDO_REG_NO,sd.SD_EMP_AMOUNT,sd.SD_EMPLR_AMOUNT,sd.SD_TOTAL_AMT FROM NSDL_SD_DTLS sd ");
            SBQuery.append(" inner join NSDL_BH_DTLS bh on bh.FILE_NAME = sd.FILE_NAME and bh.STATUS <> '-1' and bh.FILE_NAME ='"+fileNo+"'");*/
            
            SBQuery.append(" SELECT distinct sd.SD_PRAN_NO,m.ddo_code,sd.DDO_REG_NO,sd.SD_EMP_AMOUNT,sd.SD_EMPLR_AMOUNT,sd.SD_TOTAL_AMT,emp.SEVARTH_ID,emp.EMP_NAME FROM RAT_NSDL_SD_DTLS sd ");////m.ddo_code,
            SBQuery.append(" inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME = sd.FILE_NAME inner join MST_DDO_REG  m  on sd.DDO_REG_NO = m.DDO_REG_NO inner join mst_Dcps_emp emp on sd.sd_pran_no = emp.pran_no ");
            SBQuery.append(" and bh.STATUS <> '-1' and bh.FILE_NAME ='"+fileNo+"' order by m.ddo_code ");/////$t KR 
         
          
            gLogger.info("StrSqlQuery***********"+SBQuery.toString());

            StrSqlQuery = SBQuery.toString();
            rs = smt.executeQuery(StrSqlQuery);
            Integer counter = 1;
          
            while (rs.next()) {

                rowList = new ArrayList();
                
                rowList.add(new StyledData(counter, CenterAlignVO));
                rowList.add(new StyledData(rs.getString(1), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(7), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(8), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(2), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(3), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(4), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(5), CenterAlignVO));
                rowList.add(new StyledData(rs.getString(6), CenterAlignVO));
         
                dataList.add(rowList);

                counter = counter + 1;
            }
        }
        if (report.getReportCode().equals("800009613")) {
        	 ArrayList td = new ArrayList();
             String ddoCode = ((String) report.getParameterValue("ddoCode")).trim();
             String year = ((String) report.getParameterValue("year")).trim();
             String month = ((String) report.getParameterValue("month")).trim();
             String cmbTr = ((String) report.getParameterValue("cmbTr")).trim();
             String ifDeputation = ((String) report.getParameterValue("ifDeputation")).trim();
             List TableData1 = null;
			 List TableData2 = null;
			 List TableData = null;
			 Collection l = null;
			 
			 String url = "";

	            if(ifDeputation.equals("1"))
				url = "ifms.htm?actionFlag=loadNPSNSDLFormRAT&elementId=1900001549&yearId="+ year + "&monthId=" + month + "&cmbTr=" + cmbTr+"&ifDeputation=1";
	            else
				url = "ifms.htm?actionFlag=loadNPSNSDLFormRAT&elementId=1900001549&yearId="+ year + "&monthId=" + month + "&cmbTr=" + cmbTr+"&ifDeputation=2";

				StyleVO[] lObjStyleVO = new StyleVO[report.getStyleList().length];
				lObjStyleVO = report.getStyleList();
				for (Integer lInt = 0; lInt < report.getStyleList().length; lInt++) {
					if (lObjStyleVO[lInt].getStyleId() == 26) {
						lObjStyleVO[lInt].setStyleValue(url);
					}
				}
             
             StringBuilder SBQuery = new StringBuilder();
             SBQuery.append(" select a.bill_no,a.VOUCHER_NO,a.v_date,sum(a.grs_amt) as gross_amt,sum(a.net_amt) as net_total,cast(sum(a.emp_amt-nvl(b.sd_amnt,0)) as double) as employye,cast(sum(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0)) as double) as emplr_amount, ");////m.ddo_code,
             SBQuery.append(" cast(sum(a.emp_amt-nvl(b.sd_amnt,0))+sum(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0))as double)as total,a.ddo_reg_no,a.ddo_code ");
             SBQuery.append(" from(SELECT head.BILL_NO,head.VOUCHER_NO,VARCHAR_FORMAT(head.VOUCHER_DATE,'DD/MM/YYYY')as v_date,sum(paybill.GROSS_AMT) as grs_amt,sum(paybill.NET_TOTAL) as net_amt, ");
             SBQuery.append(" sum(paybill.DCPS)+sum(paybill.DCPS_PAY)+sum(paybill.DCPS_DELAY)+sum(paybill.DCPS_DA)+sum(paybill.DCPS_PAY_DIFF) as emp_amt,sum(paybill.DED_ADJUST) as DED_ADJUST, "); 
             SBQuery.append(" emp.pran_no,reg.ddo_reg_no,ddo.ddo_code FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on eis.EMP_MPG_ID=emp.ORG_EMP_MST_ID inner join NSDL_PAYBILL_DATA paybill on paybill.EMP_ID=eis.EMP_ID "); 
             SBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID=paybill.PAYBILL_GRP_ID and paybill.DCPS+paybill.DCPS_PAY+paybill.DCPS_DELAY+paybill.DCPS_DA+paybill.DCPS_PAY_DIFF <=paybill.DED_ADJUST "); 
             SBQuery.append(" inner join ORG_DDO_MST ddo on ddo.LOCATION_CODE=paybill.LOC_ID inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code "); 
             SBQuery.append(" inner join MST_DTO_REG dto on substr(dto.LOC_ID,1,2)=substr(ddo.ddo_code,1,2) inner join CMN_LOCATION_MST loc on loc.LOC_ID=substr(ddo.DDO_CODE,1,4) "); 
             SBQuery.append(" where head.PAYBILL_YEAR='"+year+"' and head.PAYBILL_MONTH='"+month+"' and ddo.ddo_code='"+ddoCode+"' and emp.PRAN_NO is not null and PRAN_ACTIVE=1 and emp.REG_STATUS=1 and head.RAT_FLAG=1 "); 
             SBQuery.append(" and head.APPROVE_FLAG=1 group by head.BILL_NO,head.VOUCHER_NO,VARCHAR_FORMAT(head.VOUCHER_DATE,'DD/MM/YYYY'),emp.pran_no,reg.ddo_reg_no,ddo.ddo_code) a left outer join "); 
             SBQuery.append(" (SELECT sd.SD_PRAN_NO,cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt,cast(sum(nvl(sd.SD_EMPLR_AMOUNT,0)) as double) as sd_amnt_emplr,bh.YEAR, "); 
             SBQuery.append(" bh.MONTH,sd.ddo_reg_no FROM RAT_NSDL_SD_DTLS sd inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.RAT_STATUS <>-1 and bh.file_name not like '"+cmbTr+"%D' "); 
             SBQuery.append(" and sd.SD_REMARK like 'Contribution for%' and cast(sd.SD_EMP_AMOUNT as bigint) <= cast(sd.SD_EMPlr_AMOUNT as bigint) and bh.YEAR='"+year+"' and bh.MONTH='"+month+"' and bh.file_name like '"+cmbTr+"%' "); 
             SBQuery.append(" group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH,sd.ddo_reg_no) b on b.SD_PRAN_NO=a.PRAN_NO and b.ddo_reg_no=a.ddo_reg_no "); 
             SBQuery.append(" where cast(a.emp_amt-nvl(b.sd_amnt,0) as double)> 0 or cast(a.DED_ADJUST-nvl(b.sd_amnt_emplr,0) as double) > 0 group by a.BILL_NO,a.VOUCHER_NO,a.v_date,a.ddo_reg_no,a.ddo_code "); 
             Session ghibSession = ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
 			 Query lQuery = ghibSession.createSQLQuery( SBQuery.toString());
 			 TableData1= lQuery.list();
 			
 			SBQuery = new StringBuilder();
 			SBQuery.append(" select a.bill_no,a.VOUCHER_NO,a.v_date,sum(a.grs_amt) as gross_amt,sum(a.net_amt) as net_total,'0' as employye,cast(sum(a.DED_ADJUST-nvl(b.sd_emplr_amnt,0)) as double) as emplr_amount,cast(sum(a.DED_ADJUST-nvl(b.sd_emplr_amnt,0)) as double) as total, ");
            SBQuery.append(" a.ddo_reg_no,a.ddo_code from( SELECT head.BILL_NO,head.VOUCHER_NO,VARCHAR_FORMAT(head.VOUCHER_DATE,'DD/MM/YYYY')as v_date,sum(paybill.GROSS_AMT) as grs_amt,sum(paybill.NET_TOTAL) as net_amt,'0' as emp_amt,SUM(nvl(paybill.emplr_contri_arrears,0))+SUM(nvl(paybill.NPS_EMPLR_DIFFERENCE_ADJ,0)) as DED_ADJUST, ");
            SBQuery.append(" emp.pran_no,reg.ddo_reg_no,ddo.ddo_code FROM mst_dcps_emp emp inner join HR_EIS_EMP_MST eis on eis.EMP_MPG_ID=emp.ORG_EMP_MST_ID inner join NSDL_PAYBILL_DATA paybill on paybill.EMP_ID=eis.EMP_ID ");
            SBQuery.append(" inner join PAYBILL_HEAD_MPG head on head.PAYBILL_ID=paybill.PAYBILL_GRP_ID inner join ORG_DDO_MST ddo on ddo.LOCATION_CODE=paybill.LOC_ID inner join mst_ddo_reg reg on reg.ddo_code = ddo.ddo_code "); 
            SBQuery.append(" inner join MST_DTO_REG dto on substr(dto.LOC_ID,1,2)=substr(ddo.ddo_code,1,2) inner join CMN_LOCATION_MST loc on loc.LOC_ID=substr(ddo.DDO_CODE,1,4) "); 
            SBQuery.append(" where head.PAYBILL_YEAR='"+year+"' and head.PAYBILL_MONTH='"+month+"' and ddo.ddo_code='"+ddoCode+"' and emp.PRAN_NO is not null and emp.PRAN_ACTIVE=1 and emp.REG_STATUS=1 and head.RAT_FLAG=1 and head.APPROVE_FLAG=1 ");
            SBQuery.append(" and(cast((nvl(paybill.emplr_contri_arrears,0)) as double) <> 0 or cast((nvl(paybill.NPS_EMPLR_DIFFERENCE_ADJ,0)) as double) <> 0) group by head.BILL_NO,head.VOUCHER_NO,VARCHAR_FORMAT(head.VOUCHER_DATE,'DD/MM/YYYY'),emp.pran_no,reg.ddo_reg_no,ddo.ddo_code) a left outer join( ");
            SBQuery.append(" SELECT sd.SD_PRAN_NO,cast(sum(nvl(sd.SD_EMP_AMOUNT,0)) as double) as sd_amnt,cast(sum(nvl(sd.SD_EMPlr_AMOUNT,0)) as double) as sd_emplr_amnt,bh.YEAR,bh.MONTH,sd.ddo_reg_no ");
            SBQuery.append(" FROM RAT_NSDL_SD_DTLS sd inner join RAT_NSDL_BH_DTLS bh on bh.FILE_NAME=sd.FILE_NAME and bh.RAT_STATUS <>-1 and bh.YEAR='"+year+"' and bh.MONTH='"+month+"' and bh.file_name like '"+cmbTr+"%' ");
            SBQuery.append(" and(sd.SD_REMARK like '4 Per Contribution%' or sd.SD_REMARK like 'Less Employer Contribution%') group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH,sd.ddo_reg_no) ");
            SBQuery.append(" b on b.SD_PRAN_NO=a.PRAN_NO and b.ddo_reg_no=a.ddo_reg_no "); 
            SBQuery.append(" where cast(a.emp_amt-nvl(b.sd_amnt,0) as double) >= 0 and cast(a.DED_ADJUST-nvl(b.sd_emplr_amnt,0) as double) > 0 group by a.BILL_NO,a.VOUCHER_NO,a.v_date,a.ddo_reg_no,a.ddo_code "); 
            ghibSession = ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
			lQuery = ghibSession.createSQLQuery( SBQuery.toString());
			TableData2= lQuery.list();
			
			if ((TableData1 != null && TableData1.size() > 0)
					|| (TableData2 != null && TableData2.size() > 0)) {
				TableData = new ArrayList();
				if (TableData1 != null && TableData1.size() > 0) {
					TableData.addAll(TableData1);

				}
				if (TableData2 != null && TableData2.size() > 0) {
					TableData.addAll(TableData2);
				}
			}
			
			HashMap m = new HashMap();
			if ((TableData != null) && (TableData.size() > 0)) {
				for (int i = 0; i < TableData.size(); i++) {
					Object[] objI = (Object[]) TableData.get(i);
					if ((objI != null) && (m.containsKey(objI[0]))) {
						Object[] objNew = new Object[8];
						objNew[0] = objI[0];
						objNew[1] = objI[1];
						objNew[2] = objI[2];
						Object[] objNew1 = (Object[]) m.get(objI[0]);
						objNew[3] = Double.valueOf(Double.parseDouble(objI[3].toString())+ Double.parseDouble(objNew1[3].toString()));
						objNew[4] = Double.valueOf(Double.parseDouble(objI[4].toString())+ Double.parseDouble(objNew1[4].toString()));
						objNew[5] = Double.valueOf(Double.parseDouble(objI[5].toString())+ Double.parseDouble(objNew1[5].toString()));
						objNew[6] = objI[6];
						objNew[7] = objI[7];
						m.remove(objI[0]);
						m.put(objI[0], objNew);
					} else if (objI != null) {
						m.put(objI[0], objI);
					}
				}
			}
			if ((m != null) && (!m.isEmpty())) {
				l = m.values();
				Object[] obj1 = l.toArray();
				if ((obj1 != null) && (obj1.length > 0)) {
					TableData = new ArrayList();
					for (int k = 0; k < obj1.length; k++) {
						TableData.add(obj1[k]);
					}
				}
			}
			
			for (int i=0;i<TableData.size();i++)
			{
				int countApprove = 0;
				Object[] tupleSub = (Object[]) TableData.get(i);
				td = new ArrayList();
				td.add(i+1);
				if (tupleSub[0] != null) 
				{	    
					    //td.add(new URLData(tupleSub[0] ,"hrms.htm?actionFlag=reportService&reportCode=800009605&action=generateReport&DirectReport=TRUE&displayOK=FALSE&finYear="+year+"&treasuryCode="+cmbTr+"&finMonth="+month));
					    td.add(new URLData(tupleSub[0].toString() ,"hrms.htm?actionFlag=reportService&reportCode=800009605&action=generateReport&DirectReport=TRUE&displayOK=FALSE&ddoCode="+ddoCode+"&year="+year+"&month="+month+"&cmbTr="+cmbTr+"&ifDeputation="+ifDeputation+"&billNo="+tupleSub[0].toString()));
					    td.add(tupleSub[1].toString());
					    td.add(tupleSub[2].toString());
					    td.add(tupleSub[9].toString());
						td.add(tupleSub[3]);
						td.add(tupleSub[4]);
						td.add(tupleSub[5]);
						td.add(tupleSub[6]);
						td.add(tupleSub[7]);
				}
				
							
				dataList.add(td);
			}
			
         }// if report
        
		if(report.getReportCode().equals("800009614"))
		{
			report.setStyleList(rowsFontsVO);
			/*String lStrGpfAccNo = (String) report.getParameterValue("gpfAccNo");
			String lStrMonth = (String) report.getParameterValue("month");
			String lStrYear = (String) report.getParameterValue("year");
			String lStrTransactionId = (String) report.getParameterValue("transactionId");*/
			
			String strTrsryCode="7101";
			String strTrsryName="PAO Treasury";
			String strDdoCode="7101003272";
			Double lStrSanctionedAmount =0.0;
			Double lStrSanctionedAmountCount =0.0;
			
			ArrayList<Object> rowList = new ArrayList<Object>();
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.A1"),CenterBoldAlignVO));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.A2"),CenterBoldAlignVO));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.A3"),leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.A4")+space(50)+gObjRsrcBndle.getString("RAT.A5"),leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(".........................................................................................................................................................................................................................................",leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(" [ "+strTrsryCode+" "+strTrsryName+" ]"+space(18)+""+gObjRsrcBndle.getString("RAT.B1b")+strDdoCode+" ]",leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B2a")+space(2)+"["+space(35)+"]"+space(23)+""+gObjRsrcBndle.getString("RAT.B2b")+space(10)+"["+space(40)+"]",leftAlign));
			dataList43.add(rowList);

			/*rowList.add(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE1") + space(200) + gObjRsrcBndle.getString("GPF.PAYBILLGENLINE2"));
                dataList.add(rowList);*/

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B3a")+space(23)+"["+space(36)+"]"+space(24)+""+gObjRsrcBndle.getString("RAT.B3b")+space(18)+"["+space(40)+"]",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B4a")+space(23)+"["+space(36)+"]"+space(24)+""+gObjRsrcBndle.getString("RAT.B4b")+space(34)+"["+space(40)+"]",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B5a")+space(14)+"["+space(36)+"]"+space(24)+""+gObjRsrcBndle.getString("RAT.B5b")+space(6)+"["+space(41)+"]",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B6")+space(18)+"["+space(36)+"]",leftAlign));
			dataList43.add(rowList);


			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(".........................................................................................................................................................................................................................................",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.B7a")+space(105)+gObjRsrcBndle.getString("RAT.B7b")+space(40)+gObjRsrcBndle.getString("RAT.B7c"),leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(".........................................................................................................................................................................................................................................",leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.C1"),centerAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.C2"),centerAlign1));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.C3"),centerAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE15A"),""+space(40)+"[Code]"+space(40)+"[description]"),leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D1"),""+space(40)+" "+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE16A"),""+space(49)+"[Code]"+space(40)+"[description]"),leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D2"),""+space(49)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE17A"),""+space(51)+"[Code]"+space(40)+"[description]"),leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D3"),""+space(51)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE18A"),""+space(52)+"[Code]"+space(40)+"[description]"),
					leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D4"),""+space(52)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE19A"),""+space(56)+"[Code]"+space(40)+"[description]"),
					leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D5"),""+space(56)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE20A"),""+space(43)+"[Code]"+space(40)+"[description]"),
					leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D6"),""+space(43)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE21A"),""+space(31)+"[Code]"+space(40)+"[description]"),
					leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D7"),""+space(31)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			/*rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("GPF.PAYBILLGENLINE22A"),""+space(39)+"[Code]"+space(40)+"[description]"),
					leftAlign));*/
			rowList.add(new StyledData( MessageFormat.format(gObjRsrcBndle.getString("RAT.D8"),""+space(39)+""+space(40)+""),leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("......................................................................................................................................................................................................................................",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.E1a")+"["+space(7)+lStrSanctionedAmount+space(27)+"]"+space(29)+""+gObjRsrcBndle.getString("RAT.E1b")+space(21)+"["+space(14)+"NA"+space(14)+"]",leftAlign)); 
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.E2a")+space(8)+"["+space(39)+"]"+space(28)+""+gObjRsrcBndle.getString("RAT.E2b")+space(5)+"["+space(32)+"]",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.E3a")+space(11)+"["+space(7)+lStrSanctionedAmount+space(27)+"]"+space(29)+""+gObjRsrcBndle.getString("RAT.E3b")+space(29)+"["+space(14)+"NA"+space(15)+"]",leftAlign));
			dataList43.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(".........................................................................................................................................................................................................................................",leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.F1a"),leftAlign));
			dataList43.add(rowList);
			
			rowList = new ArrayList<Object>();
			rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.F2"),leftAlign));
			dataList43.add(rowList);
		
			ArrayList styleList = new ArrayList();
			TabularData tData  = new TabularData(dataList43);				
			tData.addStyle(IReportConstants.STYLE_FONT_ALIGNMENT, IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			tData.addStyle(IReportConstants.BORDER, "No");
			tData.addStyle(IReportConstants.SHOW_REPORT_NAME, IReportConstants.VALUE_NO);
			report.setAdditionalHeader(tData);
			
			ArrayList td1 = new ArrayList();
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");	
			td1.add("");
			td1.add("");
			dataList.add(td1);
			
			ArrayList td2 = new ArrayList();
			td2.add("");
			td2.add("");
			td2.add("");
			td2.add("");
			td2.add("");	
			td2.add("");
			td2.add("");
			dataList.add(td2);
	    }
		if(report.getReportCode().equals("800009616"))
		{
			report.setStyleList(rowsFontsVO);
			
			ArrayList<Object> rowList = new ArrayList<Object>();
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G1"),leftAlign));
            dataList43.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G2"),leftAlign));
            dataList43.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G3"),leftAlign));
            dataList43.add(rowList);
            
			rowList = new ArrayList<Object>();
            //rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G4"),leftAlign));
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G4a")+space(55)+gObjRsrcBndle.getString("RAT.G4b"),leftAlign));
            dataList43.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.G5"),leftAlign));
            dataList43.add(rowList);
            
            ArrayList styleList = new ArrayList();
			TabularData tData  = new TabularData(dataList43);				
			tData.addStyle(IReportConstants.STYLE_FONT_ALIGNMENT, IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			tData.addStyle(IReportConstants.BORDER, "No");
			tData.addStyle(IReportConstants.SHOW_REPORT_NAME, IReportConstants.VALUE_NO);
			report.setAdditionalHeader(tData);
			
			ArrayList td1 = new ArrayList();
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");	
			td1.add("");
			td1.add("");
			dataList.add(td1);
			ArrayList td2 = new ArrayList();
			 td2.add("");
			 td2.add("");
			 td2.add("");
			 td2.add("");
			 td2.add("");
			 td2.add("");
			 td2.add("");
			dataList.add(td2);
		}
		if(report.getReportCode().equals("800009617"))
		{
		    report.setStyleList(rowsFontsVO);
			
			ArrayList<Object> rowList = new ArrayList<Object>();
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.H1"),leftAlign));
            dataList.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.H2"),leftAlign));
            dataList.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.H3"),leftAlign));
            dataList.add(rowList);
            
			rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.H4"),leftAlign));
            dataList.add(rowList);
            
            rowList = new ArrayList<Object>();
            rowList.add(new StyledData(gObjRsrcBndle.getString("RAT.H5a")+space(30)+gObjRsrcBndle.getString("RAT.H5b"),leftAlign));
            dataList.add(rowList);
            
            rowList = new ArrayList<Object>();
			rowList.add(new StyledData("...............................................................................................................................................................................",leftAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("For use of Treasury/Sub Treasury/Pay & Accounts Office",centerAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(("Pay Rs.  ------------------------/- (In words) Rupees -----------------------------------------------------------------------------------------------------"));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("Pay by Transfer credit Rs. ------------------/-"+space(50)+"to (Scheme Code)",leftAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("Auditor ---------------"+space(50)+"Supervisor ----------------"+space(50)+"STO/ATO/TO/APAO ------------------");
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("................................................................................................................................................................................",leftAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("Cheque/e-Payment"+space(40)+"Date-------- "+space(50)+" Cheque/e-Payment Advise Slip ----------------------------");
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("Payment Advise No.--------------------"+space(120)+"Delivered on date ----------------");
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("STO/ATO/TO/APAO -----------------"+space(130)+"Delivery Clerk -----------------");
			dataList.add(rowList);


			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("....................................................................................................................................................................................",leftAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("For Audit use only ",centerAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("Admitted for Rs. ------------/-"  +space(180) +"Objected for Rs. -------------/-");
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add(new StyledData("Reason for objection",leftAlign));
			dataList.add(rowList);

			rowList = new ArrayList<Object>();
			rowList.add("Auditor"+space(75) + "Section Officer"   +space(75) +  "Accounts Officer");
			dataList.add(rowList);
            
            ArrayList styleList = new ArrayList();
			TabularData tData  = new TabularData(dataList43);				
			tData.addStyle(IReportConstants.STYLE_FONT_ALIGNMENT, IReportConstants.VALUE_FONT_ALIGNMENT_CENTER);
			tData.addStyle(IReportConstants.BORDER, "No");
			tData.addStyle(IReportConstants.SHOW_REPORT_NAME, IReportConstants.VALUE_NO);
			report.setAdditionalHeader(tData);
			
			/*ArrayList td1 = new ArrayList();
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");
			td1.add("");	
			td1.add("");
			td1.add("");
			dataList.add(td1);*/		
		}

	} catch (Exception e) {
		e.printStackTrace();
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
			//neardrSession.close();
			//gLogger.error("neardrSession.close() ");
			
		} catch (Exception e) {
			e.printStackTrace();
			gLogger.error("Exception :" + e, e);
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

public Long getFinYearId(String finYearCode) {
	List sev = null;
	Long FinYearId = 0l;

	try {
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(" SELECT SFYM.FIN_YEAR_ID FROM SGVC_FIN_YEAR_MST SFYM ");
		lSBQuery.append(" WHERE SFYM.FIN_YEAR_CODE =:finYearCode ");
		Session ghibSession = ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lQuery.setParameter("finYearCode", finYearCode);

		sev = lQuery.list();

		if (!sev.isEmpty() && sev.size() > 0 && sev.get(0) != null)
			FinYearId = Long.parseLong(sev.get(0).toString());
	} catch (Exception e) {
		gLogger.error(
				"Exception in getFinYearId of NsdlNpsEmpWiseReport: ", e);
	}
	return FinYearId;
}

public List getTreasuryNameandCode(String lStrDDOCode) {
	List<String> lLstTreasury = null;
	Session ghibSession = ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
	try {
		StringBuilder lSBQuery = new StringBuilder();			
		lSBQuery.append(" SELECT * FROM CMN_LOCATION_MST where LOC_ID like '22__' and DEPARTMENT_ID in(100003,100006) ");			
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lLstTreasury = lQuery.list();
		
	} catch (Exception e) {
		gLogger.error("Exception in getTreasuryNameOfEmp of GPFRequestProcessDAOImpl  : ", e);			
	}
	return lLstTreasury;
}

}//main class