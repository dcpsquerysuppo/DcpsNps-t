package com.tcs.sgv.dcps.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.tool.hbm2x.StringUtils;

import com.tcs.sgv.common.helper.SessionHelper;
import com.tcs.sgv.common.service.IFMSCommonServiceImpl;
import com.tcs.sgv.common.util.ExcelParser;
import com.tcs.sgv.common.utils.StringUtility;
import com.tcs.sgv.common.utils.fileupload.dao.CmnAttachmentMstDAOImpl;
import com.tcs.sgv.common.valueobject.CmnAttachmentMpg;
import com.tcs.sgv.common.valueobject.CmnAttachmentMst;
import com.tcs.sgv.common.valueobject.CmnAttdocMst;
import com.tcs.sgv.core.constant.ErrorConstants;
import com.tcs.sgv.core.service.ServiceImpl;
import com.tcs.sgv.core.service.ServiceLocator;
import com.tcs.sgv.core.valueobject.ResultObject;
import com.tcs.sgv.dcps.dao.UploadPranDAO;
import com.tcs.sgv.dcps.dao.UploadPranDaoImpl;
import com.tcs.sgv.dcps.valueobject.UploadPranNo;


public class ExcelFileData extends ServiceImpl
{
	
	Long gLngUserId = null;
	Long gLngPostId = null;
	private Long gLngLangId = null; /* LANG ID */
	private HttpServletRequest request = null; /* REQUEST OBJECT */
	private ServiceLocator serv = null; /* SERVICE LOCATOR */
	private Date gDtCurDate = null; /* CURRENT DATE */
	private final String gStrPostId = null; /* STRING POST ID */
	Log logger = LogFactory.getLog(this.getClass());

public ResultObject importExcel(Map inputMap) throws Exception {
			ResultObject objRes = new ResultObject(ErrorConstants.SUCCESS, "FAIL");
			logger.info("Inside file read ");

			String fileName = null;
			  Integer result=null;	
			  String status="NO";
			  
			  
			try {
				setSessionInfo(inputMap);
				   Integer lIntRowSize = 0;
				   Object[][] xlsData = null;
				   UploadPranDAO lObjUploadPranNo = new UploadPranDaoImpl(UploadPranNo.class, serv.getSessionFactory());
				//DeleteCsrfFormDaoImpl objUpdateDao = new DeleteCsrfFormDaoImpl(null, serv.getSessionFactory());
				fileName = StringUtility.getParameter("filename", request).trim().toUpperCase();
				if (StringUtility.getParameter("filename", request).trim() != null) {
					fileName = StringUtility.getParameter("filename", request).trim().toUpperCase();
					
	                  File inputStream = new File(fileName);	                  
		              List lObjSheetLst = ExcelParser.parseExcel(inputStream);
		             // List lObjSheetLst1=ExcelFileParserPran.parseExcel(fileName);
		             if (lObjSheetLst != null && !lObjSheetLst.isEmpty()) {
                         List lObjRowLst = (List) lObjSheetLst.get(0);
                         System.out.println("list of data" +lObjRowLst);
                     }
		             if (lObjSheetLst != null && !lObjSheetLst.isEmpty()) {
                         List lObjRowLst = (List) lObjSheetLst.get(0);
                         if (lObjRowLst != null && !lObjRowLst.isEmpty()) {
                                 lIntRowSize = lObjRowLst.size();
                                 xlsData = new Object[lObjRowLst.size()][];
                                 for (int i = 1; i < lObjRowLst.size(); ++i) {
                                         xlsData[i] = ((List) lObjRowLst.get(i)).toArray();
                                         System.out.println("list of data" +xlsData[i].toString()+"data:-"+ lObjRowLst.get(i));
                                         int l=xlsData[i].length;
                                        System.out.println("data array"+xlsData[i][2].toString().trim());
                                     //  emplList.add(xlsData[i][0].toString().trim());
                                   //    boolean flag=lObjUploadPranNo.checkIfPranUploaded(xlsData[i][1].toString().trim());
                                        result=lObjUploadPranNo.activePranNOUpdate(xlsData[i][0].toString().trim());                                        
                                 }      
                                 if(result!=null || result!=0){
                                	 
                                	 status="YES";
                                 }
                         }
                     }
				}
				   
				inputMap.put("reload", "Yes");
				     inputMap.put("isExecl", status);
					objRes.setResultValue(inputMap);
					objRes.setViewName("ExcelFileUpload");		
					
			}
				catch (Exception e) {
					logger.info("Exception is " + e);
					objRes.setResultValue(null);
					objRes.setThrowable(e);
					objRes.setResultCode(ErrorConstants.ERROR);
					objRes.setViewName("errorPage");
				}
				return objRes;
		}


		private StringBuilder getResponseXMLDoc(String flag,String pranNo) {
		
			StringBuilder lStrBldXML = new StringBuilder();

			lStrBldXML.append("<XMLDOC>");
			lStrBldXML.append("<Flag>");
			lStrBldXML.append(flag);
			lStrBldXML.append("</Flag>");
			lStrBldXML.append("<pranNo>");
			lStrBldXML.append(pranNo);
			lStrBldXML.append("</pranNo>");
			lStrBldXML.append("</XMLDOC>");

			return lStrBldXML;
		}
	

	

	@SuppressWarnings("unchecked")
	


	private void setSessionInfo(final Map inputMap) throws Exception
	{

		try
		{
			this.request = (HttpServletRequest) inputMap.get("requestObj");
			this.serv = (ServiceLocator) inputMap.get("serviceLocator");
			this.gLngPostId = SessionHelper.getPostId(inputMap);
			this.gLngUserId = SessionHelper.getUserId(inputMap);
			this.gDtCurDate = SessionHelper.getCurDate();
			this.gLngLangId = SessionHelper.getLangId(inputMap);

		} catch (final Exception e)
		{
			this.logger.error("Error in setSessionInfo of changeNameServiceImpl ", e);
			throw e;
		}
	}



	
	
 /*   public ResultObject uploadExcel(Map objectArgs)
    {
        logger.info("Inside Get uploadGr");
        ResultObject resultObject = new ResultObject(ErrorConstants.SUCCESS);
        ServiceLocator serv = (ServiceLocator)objectArgs.get("serviceLocator");
        HttpServletRequest request = (HttpServletRequest) objectArgs.get("requestObj");
        Long attachment_Id_order=0l;
        Object[][] xlsData = null;
        try{
            setSessionInfo(objectArgs);
            PRTrackingDAO lobjPRTrackingDAOImpl = new PRTrackingDAOImpl(PRTrackingDAOImpl.class, this.serv.getSessionFactory());
            String currRowNum="1";
            objectArgs.put("rowNumber",currRowNum);
            objectArgs.put("attachmentName","orderId");
            resultObject = serv.executeService("FILE_UPLOAD_VOGEN",objectArgs);
            Map resultMap=(Map)resultObject.getResultValue();
            resultObject = serv.executeService("FILE_UPLOAD_SRVC", objectArgs);
            resultMap = (Map) resultObject.getResultValue();
            if(resultMap.get("AttachmentId_orderId")!=null)
                attachment_Id_order = (Long) resultMap.get("AttachmentId_orderId"); 
            logger.info("attachment_Id_order is "+attachment_Id_order);

            
            if (attachment_Id_order != null) {
             
                logger.info("attachment_Id_order inside ******************* " );
                
                CmnAttachmentMstDAOImpl mnAttachmentMstDAO = new CmnAttachmentMstDAOImpl(CmnAttachmentMst.class, serv.getSessionFactory());
                CmnAttachmentMst cmnAttachmentMst = mnAttachmentMstDAO.findByAttachmentId(attachment_Id_order);
                Iterator lObjIterator = cmnAttachmentMst.getCmnAttachmentMpgs().iterator();
                Integer lIntRowSize = 0;
                logger.info("attachment_Id_order inside **********2********* " );
                while (lObjIterator != null && lObjIterator.hasNext()) {
                    CmnAttachmentMpg cmnAttachmentMpg = (CmnAttachmentMpg) lObjIterator.next();
                    CmnAttdocMst cmnAttDocMst = (CmnAttdocMst) cmnAttachmentMpg.getCmnAttdocMsts().iterator().next();
                    logger.info("attachment_Id_order inside **********3******** " );
                    String lStrFileName = cmnAttachmentMpg.getOrgFileName().trim();
                    int lIntDotPos = lStrFileName.lastIndexOf(".");
                    String lStrExtension = lStrFileName.substring(lIntDotPos);
                 
                    if (cmnAttDocMst != null) {
                        List lObjSheetLst = ExcelParser.parseExcel(new ByteArrayInputStream(cmnAttDocMst.getFinalAttachment()));
                        logger.info("attachment_Id_order inside **********4******* " );
                        if (lObjSheetLst != null && !lObjSheetLst.isEmpty()) {
                                List lObjRowLst = (List) lObjSheetLst.get(0);////here to get the first sheet of excel
                                if (lObjRowLst != null && !lObjRowLst.isEmpty()) {
                                        lIntRowSize = lObjRowLst.size(); /// Number of rows will be this 
                                        xlsData = new Object[lObjRowLst.size()][];
                                        for (int i = 0; i < lObjRowLst.size(); ++i) {
                                                xlsData[i] = ((List) lObjRowLst.get(i)).toArray();
                                        }
                                }
                        }
                        
                    }
       
                    }
                
                final Map loginMap = (Map) objectArgs.get("baseLoginMap");
            	final PayBillDAOImpl payDAO = new PayBillDAOImpl(HrPayPaybill.class, this.serv.getSessionFactory());
            	final long locId = StringUtility.convertToLong(loginMap.get("locationId").toString()).longValue();
            	
                final List<OrgDdoMst> ddoList = payDAO.getDDOCodeByLoggedInlocId(locId);
        		OrgDdoMst ddoMst = null;
        		String ddoCode="";
        		
        		if (ddoList != null && ddoList.size() > 0)
        		{
        			ddoMst = ddoList.get(0);
        			this.logger.info("hiii i m finding ddo code");
        		}

        		if (ddoMst != null)
        		{
        			ddoCode = ddoMst.getDdoCode();
        		}
                
                
                Long id=0l;
                logger.info("lIntRowSize inside ***************** "+lIntRowSize );
              // List<OrgTicketMst> lLstTicket = new ArrayList<OrgTicketMst>();
                lobjPRTrackingDAOImpl.insertExecelData(xlsData,objectArgs,lIntRowSize,ddoCode,gLngUserId,gLngPostId);
               
                
            }
            resultObject.setResultCode(ErrorConstants.SUCCESS);
            resultObject.setResultValue(objectArgs);//put in result object
            resultObject.setViewName("LoadEmpTickets");//set view name

        }catch(Exception e){
            resultObject = new ResultObject(ErrorConstants.ERROR);
            resultObject.setResultCode(-1);
            resultObject.setViewName("errorPage");
            logger.error("Error in mapDDOCodeList "+ e);
        }
        return resultObject;
    }*/


	

}
