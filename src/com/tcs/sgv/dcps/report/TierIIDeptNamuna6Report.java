package com.tcs.sgv.dcps.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.*;
import java.math.BigDecimal;
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

public class TierIIDeptNamuna6Report extends DefaultReportDataFinder implements ReportDataFinder {

	private static final Logger gLogger = Logger.getLogger(TierIIDeptNamuna6Report.class);
	public static String newline = System.getProperty("line.separator");
	String Lang_Id = "en_US";
	String Loc_Id = "LC1";

	Map requestAttributes = null;
	Map lMapSessionAttributes = null;
	ServiceLocator serviceLocator = null;
	SessionFactory lObjSessionFactory = null;
	Session ghibSession = null;
	LoginDetails lObjLoginVO = null;


	/*private ResourceBundle gObjRsrcBndleForRA = ResourceBundle.getBundle("resources/gpf/RefundableOrderMarathi");


	private ResourceBundle gObjRsrcBndleForNRA = ResourceBundle.getBundle("resources/gpf/NonRefundableOrderMarathi");

	private ResourceBundle gObjRsrcBndleForSPL90 = ResourceBundle.getBundle("resources/gpf/90PercentWithdrawalOrderMarathi");

	private ResourceBundle gObjRsrcBndleForFW = ResourceBundle.getBundle("resources/gpf/FinalPayOrderMarathi");*/

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
			noBorder[0].setStyleValue(IReportConstants.VALUE_STYLE_BORDER_THICK);

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
			if (report.getReportCode().equals("800009592")) {
				 final ResourceBundle gObjRsrcBndleForSPL6= ResourceBundle.getBundle("resources/dcps/dcpsLabels");
				 final StringBuilder SBQuery3 = new StringBuilder();
                 final String DdoCOde2 = (String)report.getParameterValue("ddoCode");
                 final String orderId = (String)report.getParameterValue("billId");
                 final String year2 = (String)report.getParameterValue("year");
                 final String month2 = (String)report.getParameterValue("month");
                 final String Date = (String)report.getParameterValue("Date");
                 final Double PayableTotal1 = Double.parseDouble((String)report.getParameterValue("amount"));
                 final String billId = (String)report.getParameterValue("orderId");
                 final String Type = (String)report.getParameterValue("type");
                 final String deputation = (String)report.getParameterValue("deputation");
                 final String amountWord = EnglishDecimalFormat.convertWithSpace(new BigDecimal(PayableTotal1));
                 String LocationName = "NA";
                 String DdoOfficeName = "NA";
                 String DdoAddress = "NA";
                 String DesignationName = "NA";
                 final List lstDDoDetails = this.getDDODetails(DdoCOde2);
                 for (int J2 = 0; J2 < lstDDoDetails.size(); ++J2) {
                     final Object[] tupleSub2 = (Object[]) lstDDoDetails.get(J2);
                     if (tupleSub2[0] != null) {
                         DdoOfficeName = tupleSub2[0].toString();
                     }
                     if (tupleSub2[1] != null) {
                         DdoAddress = tupleSub2[1].toString();
                     }
                     if (tupleSub2[2] != null) {
                         LocationName = tupleSub2[2].toString();
                     }
                     if (tupleSub2[3] != null) {
                         DesignationName = tupleSub2[3].toString();
                     }
                 }
                 final Date date = new Date();
                 final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                 final String strDate = formatter.format(date);
				 
					report.setStyleList(noBorder);
					report.setStyleList(rowsFontsVO);
					ArrayList<Object> rowList = new ArrayList<Object>();

					rowList.add("" + space(1));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForSPL6.getString("RH.HEADER1") ,CenterBoldAlignVO));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForSPL6.getString("DRH.HEADER1.1") ,CenterBoldAlignVO));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(gObjRsrcBndleForSPL6.getString("DRH.HEADER2")+""+billId, rightAlign));
					dataList.add(rowList);

					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForSPL6.getString("DRH.HEADER2.1")+space(20),""), rightAlign));
					dataList.add(rowList);
					
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(MessageFormat.format(gObjRsrcBndleForSPL6.getString("DRH.HEADER2.2")+space(30),""), rightAlign));
					dataList.add(rowList);
					
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(LocationName, rightAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(DdoAddress, rightAlign));
					dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.HEADER3")+""+ Date, rightAlign));
                    dataList.add(rowList);
                    
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.vacha1"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha2"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha3"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha4"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha5"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha6"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.vacha7"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH"), CenterBoldAlignVO));
                    dataList.add(rowList);
					
					rowList = new ArrayList<Object>();
					rowList.add(new StyledData(MessageFormat.format(space(20)+gObjRsrcBndleForSPL6.getString("DRH.ADESH1"),"")+ new BigDecimal(PayableTotal1)+gObjRsrcBndleForSPL6.getString("RH.ADESH5")+" "+amountWord+" "+gObjRsrcBndleForSPL6.getString("DRH.ADESH6"), leftAlign));
					dataList.add(rowList);
					rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH7"), leftAlign));
                    dataList.add(rowList);
					
					ArrayList<Object> sixRowsLeft = new ArrayList<Object>();
					rowList = new ArrayList<Object>();
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL1"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL2"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL3"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL4"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL5"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL6"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL7"));
					rowList.add(gObjRsrcBndleForSPL6.getString("RH.COL8"));
					sixRowsLeft.add(rowList);
					
					final List lstDetailsG2 = this.getDetailsForNamunaSix(orderId,"table",deputation);
                    int counter4 = 1;
                    String billDate="";
                    int total1=0;
                    int total2=0;
                    int total3=0;
                    for (int J3 = 0; J3 < lstDetailsG2.size(); ++J3) {
                        final Object[] tupleSub3 = (Object[]) lstDetailsG2.get(J3);
                        if (tupleSub3[0] != null) {
                            DdoOfficeName = tupleSub3[0].toString();
                        }
                        rowList = new ArrayList<Object>();
                        rowList.add(counter4);
                        rowList.add(tupleSub3[0].toString());
                        rowList.add(tupleSub3[1].toString());
                        rowList.add(tupleSub3[2].toString());
                        if (tupleSub3[3] != null)
                        rowList.add(tupleSub3[3].toString());
                        else
                        rowList.add("NA");
                        
                        rowList.add(tupleSub3[4].toString());
                        rowList.add(tupleSub3[5].toString());
                        rowList.add(tupleSub3[6].toString());
                        total1=total1+(int) tupleSub3[4];
                        total2=total2+(int) tupleSub3[5];
                        total3=total3+(int) tupleSub3[6];
                        sixRowsLeft.add(rowList);
                        ++counter4;
                    }
                    if(total1 > '0'){
                    rowList = new ArrayList<Object>();
                    rowList.add("");
                    rowList.add("");
                    rowList.add("");
                    rowList.add("");
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.tableTotal"), leftAlign));
                    rowList.add(total1);                   	
                    rowList.add(total2);                   	
                    rowList.add(total3);                   	
                    sixRowsLeft.add(rowList);
                    }

                    System.out.println("-1->"+total1);
                    System.out.println("-2->"+total2);
                    System.out.println("-3->"+total3);
					td = new TabularData(sixRowsLeft);
					RptVo = reportsDao.getReport("800009593", report.getLangId(), report.getLocId());
					ReportColumnVO[] lArrReportColumnVO = RptVo.getReportColumns();
					lArrReportColumnVO[0].setColumnWidth(5);
					lArrReportColumnVO[1].setColumnWidth(10);
					lArrReportColumnVO[2].setColumnWidth(20);
					lArrReportColumnVO[3].setColumnWidth(25);
					lArrReportColumnVO[4].setColumnWidth(20);
					lArrReportColumnVO[5].setColumnWidth(25);
					lArrReportColumnVO[6].setColumnWidth(20);
					lArrReportColumnVO[7].setColumnWidth(20);
					(td).setRelatedReport(RptVo);
					//(td).setStyles(noBorder);
					rowList = new ArrayList<Object>();
					rowList.add(td);
					dataList.add(rowList);
					
				    String url4 = "";
                    url4 = "ifms.htm?actionFlag=getNamunaF";
                    if (Type.equalsIgnoreCase("700004")){
                    	url4 = "ifms.htm?actionFlag=ViewSrkaGrantApprove&deputation=Y&locID="+DdoCOde2.substring(0, 4);
                    }else{
                    	if (deputation.equals("Y"))                    
                    	url4 = "ifms.htm?actionFlag=getEmpViewBillDept&DddoCode=" + DdoCOde2;
                    }
                    
                    StyleVO[] lObjStyleVO4 = new StyleVO[report.getStyleList().length];
                    lObjStyleVO4 = report.getStyleList();
                    for (Integer lInt4 = 0; lInt4 < report.getStyleList().length; ++lInt4) {
                        if (lObjStyleVO4[lInt4].getStyleId() == 26) {
                            lObjStyleVO4[lInt4].setStyleValue(url4);
                        }
                    }
                    rowList = new ArrayList<Object>();
                    rowList.add(newline);
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH8"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(newline);
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH9"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH10"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH11"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH12"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.ADESH13"), leftAlign));
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
                    rowList.add(newline);
                    dataList.add(rowList);
                    /*rowList = new ArrayList<Object>();
                    rowList.add(newline);
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(newline);
                    dataList.add(rowList);*/
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.FOOTER1"), rightAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.FOOTER2")+space(30), rightAlign));
                    dataList.add(rowList);
                    /*rowList = new ArrayList<Object>();
                    rowList.add(new StyledData(space(15)+(Object)gObjRsrcBndleForSPL6.getString("RH.PRATI"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI2")+space(10), CenterAlignVO));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI3"), CenterAlignVO));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData(space(13)+(Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI4"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData(space(13)+(Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI5"), leftAlign));
                    dataList.add(rowList);*/
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("RH.PRATI"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI2"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI3"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI4"), leftAlign));
                    dataList.add(rowList);
                    rowList = new ArrayList<Object>();
                    rowList.add(new StyledData((Object)gObjRsrcBndleForSPL6.getString("DRH.PRATI5"), leftAlign));
                    dataList.add(rowList);
                    
				}		
			}catch (Exception e) {
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
	public List getDDODetails(final String DDOCode) {
        List lLstEmpFiveInst = null;
        StringBuilder sb = null;
        try {
            final Session ghibSession = (Session)ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
            sb = new StringBuilder();
            sb.append("select loc.loc_name,case when off.address1 is null then off.ADDRESS1_NEW||','|| off.ADDRESS2_NEW||','|| off.ADDRESS3_NEW else address1 end ,off.OFF_name ,design.DSGN_NAME ");
            sb.append("from MST_DCPS_DDO_OFFICE off ");
            sb.append("join cmn_location_mst loc on substr(loc.location_code,0,2)=substr(off.ddo_code,0,2) and loc.department_id=100003 ");
            sb.append("join ORG_DDO_MST ddo on ddo.ddo_code=off.ddo_code ");
            sb.append("join ORG_POST_DETAILS_RLT post on post.POST_ID=ddo.post_id ");
            sb.append("join ORG_DESIGNATION_MST design on post.DSGN_ID=design.DSGN_ID ");
            sb.append("where off.DDO_CODE = '" + DDOCode + "' and off.ddo_office='Yes' ");
            final Query stQuery = (Query)ghibSession.createSQLQuery(sb.toString());
            lLstEmpFiveInst = stQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lLstEmpFiveInst;
    }
	public List getDetailsForNamunaSix(final String BillId, String type, String Deputation) {
        List lLstEmpFiveInst = null;
        StringBuilder sb = null;
        String check;
        try {
            final Session ghibSession = (Session)ServiceLocator.getServiceLocator().getSessionFactorySlave().getCurrentSession();
            sb = new StringBuilder();
            sb.append("\t select distinct em.emp_name,em.sevarth_id,em.dcps_id,em.pran_no,cast(f.INST_AMOUNT as int),cast(int_amount as int),cast(f.INST_AMOUNT+int_amount as int),VARCHAR_FORMAT(bill.BILL_GENERATION_DATE,'DD/MM/YYYY')  from TIERII_EMP_DETAILS f ");
            //sb.append("\t join  MST_DCPS_EMP em on em.DCPS_EMP_ID=f.DCPS_ID join TIERTWO_BILL_DTLS bill on f.BILLL_ID=bill.BILL_ID join RLT_DCPS_SIXPC_YEARLY rlt on f.BILLL_ID=rlt.BILL_no where f.BILLL_ID='" + BillId + "' and bill.BILL_STATUS in('0','2','4','5','6') ");////$t
            
            if(Deputation.equals("Y"))
            sb.append("\t join  MST_DCPS_EMP em on em.DCPS_EMP_ID=f.DCPS_ID join TIERTWO_BILL_DTLS bill on f.BILLL_ID=bill.BILL_ID where f.BILLL_ID='" + BillId + "' and bill.BILL_STATUS in('10','12','14','15','16','17') ");////$t
            else
            sb.append("\t join  MST_DCPS_EMP em on em.DCPS_EMP_ID=f.DCPS_ID join TIERTWO_BILL_DTLS bill on f.BILLL_ID=bill.BILL_ID where f.BILLL_ID='" + BillId + "' and bill.BILL_STATUS in('0','2','4','5','6','7') ");/////$t1Sept2022             	
                           
            Query stQuery = (Query)ghibSession.createSQLQuery(sb.toString());
            lLstEmpFiveInst = stQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lLstEmpFiveInst;
    }

}

