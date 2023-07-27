package com.tcs.sgv.lna.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tcs.sgv.core.dao.GenericDaoHibernateImpl;

public class LNAHealthInsuranceDAOImpl extends GenericDaoHibernateImpl implements LNAHealthInsuranceDAO {
	Session ghibSession = null;

	public LNAHealthInsuranceDAOImpl(Class type, SessionFactory sessionFactory) {
		super(type);
		ghibSession = sessionFactory.getCurrentSession();
		setSessionFactory(sessionFactory);
	}

	public List getHealthInsurance(String lStrSevaarthId, Long lLngRequestType) {
		
		List healthinsurancelist = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		
		lSBQuery.append("select computerAdvanceId");
		lSBQuery.append(" FROM MstLnaHealthInsurance");
		lSBQuery.append(" WHERE sevaarthId = :sevaarthId AND statusFlag = 'D' AND advanceType = :RequestType");
		
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("sevaarthId", lStrSevaarthId);
		lQuery.setParameter("RequestType", lLngRequestType);
		
		healthinsurancelist = lQuery.list();
		return healthinsurancelist;
	}

	public List getHealthInsuranceToDEOApprover(Long lLngComAdvnId) {
		
		List gpfEmpApproverlist = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append("select computerAdvanceId");
		lSBQuery.append(" FROM MstLnaHealthInsurance");
		lSBQuery.append(" WHERE computerAdvanceId = :ComAdvnId");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("ComAdvnId", lLngComAdvnId);
		gpfEmpApproverlist = lQuery.list();
		return gpfEmpApproverlist;
	}

	public Boolean requestDataAlreadyExists(String lStrSevaarthId) {
		List CARequest = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append("select MCA.computerAdvanceId ");
		lSBQuery.append(" FROM MstLnaHealthInsurance MCA");
		lSBQuery.append(" WHERE MCA.sevaarthId = :SevaarthId AND MCA.statusFlag = 'A' ");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("SevaarthId", lStrSevaarthId);
		CARequest = lQuery.list();
		if (CARequest.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public Boolean requestPendingStatus(String lStrSevaarthId) {
		
		
		List CARequest = new ArrayList();
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append("select MCA.computerAdvanceId");
		lSBQuery.append(" FROM MstLnaHealthInsurance MCA");
		lSBQuery.append(" WHERE MCA.sevaarthId = :SevaarthId AND MCA.statusFlag = 'F'");
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("SevaarthId", lStrSevaarthId);
		CARequest = lQuery.list();
		if (CARequest.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<Long> getAllHierarchyRefIdsForLocation(Long LocationCode) {

		List<Long> listHierarchyRefIds = null;

		StringBuilder lSBQuery = new StringBuilder();

		Query lQuery = null;

		lSBQuery.append(" SELECT DISTINCT WFP.HIERACHY_REF_ID FROM WF_HIERACHY_POST_MPG WFP");
		lSBQuery.append(" JOIN WF_HIERARCHY_REFERENCE_MST WFR ON WFP.HIERACHY_REF_ID = WFR.HIERACHY_REF_ID ");
		lSBQuery.append(" JOIN WF_DOC_MST WFD ON WFR.DOC_ID = WFD.DOC_ID");
		lSBQuery.append(" WHERE WFD.DOC_ID in (800007,800008,800009) and ");
		lSBQuery.append(" WFP.LOC_ID = " + LocationCode );

		lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		listHierarchyRefIds = lQuery.list();

		return listHierarchyRefIds;

	}
	
	public Boolean checkEntryInWfHierachyPostMpg(Long lLongHierarchyRefId,Long lLongPostId) {

		StringBuilder lSBQuery = new StringBuilder();
		List<Long> tempList = new ArrayList();
		Boolean flag = true;

		lSBQuery.append(" SELECT * FROM WF_HIERACHY_POST_MPG WFP where WFP.HIERACHY_REF_ID = " + lLongHierarchyRefId + " and WFP.POST_ID = '"+ lLongPostId  +"' and WFP.LEVEL_ID = 20 and WFP.activate_flag = 1 ");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		tempList = lQuery.list();
		if (tempList.size() == 0) {
			flag = false;
		}
		return flag;

	}
	
	public void insertWfHierachyPostMpg(Long lLongHierarchySeqId ,Long lLongHierarchyRefId ,Long lLongPostId,
			Long lLongCreatedByUserId,Date gDtCurDate,Long LocId ) throws Exception {

		StringBuilder lSBQuery = new StringBuilder();
		try {
			lSBQuery.append("INSERT INTO WF_HIERACHY_POST_MPG VALUES \n");
			lSBQuery.append("(:hierachySeqId,:parentHierachy,:postId,:levelId,:hierachyRefId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:locId,:langId,:dueDays) \n"); 

			Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
			lQuery.setParameter("hierachySeqId", lLongHierarchySeqId);
			lQuery.setParameter("parentHierachy", null );
			lQuery.setParameter("postId", lLongPostId);
			lQuery.setParameter("levelId", 20);
			lQuery.setParameter("hierachyRefId", lLongHierarchyRefId);
			lQuery.setParameter("crtUser",lLongCreatedByUserId );
			lQuery.setParameter("createdDate",gDtCurDate );
			lQuery.setParameter("lstUpdUser",null );
			lQuery.setParameter("lstUpdDate",null );
			lQuery.setParameter("startDate",gDtCurDate );
			lQuery.setParameter("endDate",null );
			lQuery.setParameter("activeFlag", 1);
			lQuery.setParameter("locId", LocId  );
			lQuery.setParameter("langId",1 );
			lQuery.setParameter("dueDays",null );

			lQuery.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
	}
	
	
	public String getiNewOrUpdate(String lStrSevaarthId) {
		
		List HIRequest = new ArrayList();
		String iNewOrUpdate = "";
		
		StringBuilder lSBQuery = new StringBuilder();
		
		lSBQuery.append( " select MHI.computerAdvanceId FROM MstLnaHealthInsurance MHI " );
		lSBQuery.append( " where MHI.sevaarthId=:lStrSevaarthId and MHI.statusFlag='S' ");
		
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		
		lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
		
		HIRequest = lQuery.list();
		if (HIRequest.isEmpty()) {
			iNewOrUpdate = "1";		
		} 
		
		else {
			iNewOrUpdate = "2";
		}
		return iNewOrUpdate;
	}
	
	
public List getHealthApprovedRequestList(Long PostId) {
		
		logger.info("getHealthApprovedRequestList dao method");
		logger.info("PostId"+PostId);

		List empListForHealthInsurance = new ArrayList();
			
		StringBuilder lSBQuery = new StringBuilder();
		
		String lStrPostId = PostId.toString();
		
		try {
		

			lSBQuery.append( " select health.transactionId,health.applicationDate,health.sevaarthId,me.name,health.insuranceScheme,health.amountRequested " );
			lSBQuery.append( " from MstLnaHealthInsurance health,MstEmp me " );
			lSBQuery.append( " where health.statusFlag='A' " );
			lSBQuery.append( " and me.sevarthId=health.sevaarthId and health.toPostID=:lStrPostId " );
			
			
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			
		
			lQuery.setParameter("lStrPostId", lStrPostId);
			
			empListForHealthInsurance = lQuery.list();
			
			

		} catch (Exception e) {
			logger.error("Exception in getHealthApprovedRequestList of LNAHealthInsuranceDAOImpl  : ", e);
		}
		return empListForHealthInsurance;
	}


public String getGroupDflag(String lStrSevaarthId){
	
	logger.info("getGroupDflag called");
	
	String grpDflag = "";
	
	StringBuilder sb = new StringBuilder();
	List flag = null;
	
	try{
	
		
	sb.append( " SELECT dcps.dcpsId FROM MstEmp dcps " );
	sb.append( " where dcps.group != 'D' " );
	sb.append( " and dcps.sevarthId =:lStrSevaarthId " );
	
	Query selectQuery = ghibSession.createQuery(sb.toString());
	selectQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
	
	flag = selectQuery.list();
	
	if (flag != null && flag.size() > 0) {
		grpDflag = "1";
	}
	
	else {
		grpDflag = "0";
	}
	
	}
	catch(Exception e){
		logger.error("Exception in getGroupDflag of LNAHealthInsuranceDAOImpl  : ", e);	
		
	}
	
	return grpDflag;
	
}	


public String getEmpGrp(String lStrSevaarthId){
	
	logger.info("getEmpGrp called");
	
	String empgrp = "";
	
	StringBuilder sb = new StringBuilder();
	List flag = null;
	
	try{
	
		
	sb.append( " SELECT dcps.group FROM MstEmp dcps where dcps.sevarthId=:lStrSevaarthId " );

	
	Query selectQuery = ghibSession.createQuery(sb.toString());
	selectQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
	
	flag = selectQuery.list();
	
	if (flag != null && flag.size() > 0) {
		empgrp = flag.get(0).toString();
	}
	

	}
	catch(Exception e){
		logger.error("Exception in getEmpGrp of LNAHealthInsuranceDAOImpl  : ", e);	
		
	}
	
	return empgrp;
	
}


public String getEmpNameforBill(String lStrSevaarthId){
	
	logger.info("getEmpNameforBill called");
	
	String empname = "";
	
	StringBuilder sb = new StringBuilder();
	List flag = null;
	
	try{
	
		
	sb.append( " SELECT dcps.name FROM MstEmp dcps where dcps.sevarthId=:lStrSevaarthId " );

	
	Query selectQuery = ghibSession.createQuery(sb.toString());
	selectQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
	
	flag = selectQuery.list();
	
	if (flag != null && flag.size() > 0) {
		empname = flag.get(0).toString();
	}
	

	}
	catch(Exception e){
		logger.error("Exception in getEmpNameforBill of LNAHealthInsuranceDAOImpl  : ", e);	
		
	}
	
	return empname;
	
}

public Long getHealthSancAmtforBill(String lStrSevaarthId){
	
	logger.info("lStrSevaarthId"+lStrSevaarthId);
	Long sancamt = 0l;
	List temp = null;
	StringBuilder SBQuery = new StringBuilder();
	
	try{
		
	SBQuery.append( " SELECT health.amountRequested FROM MstLnaHealthInsurance health " );
	SBQuery.append( " where health.sevaarthId=:lStrSevaarthId " );

	
	Query selectQuery = ghibSession.createQuery(SBQuery.toString());
	selectQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
	
	temp = selectQuery.list();
	
	if (temp != null && temp.size() > 0) {
		sancamt = (Long) temp.get(0);
	}
	
	
}
catch(Exception e){
	logger.error("Exception in getHealthSancAmtforBill of LNAHealthInsuranceDAOImpl  : ", e);	
	
}

return sancamt;
}


public String getEmpsevaarthId(String lStrGpfAccNo){
	
	logger.info("getEmpsevaarthId called");
	
	String sevaid = "";
	
	StringBuilder sb = new StringBuilder();
	List flag = null;
	
	try{
	
		
	sb.append( " SELECT emp.sevaarthId FROM MstEmpGpfAcc emp where trim(emp.gpfAccNo)=:lStrGpfAccNo " );


	
	Query selectQuery = ghibSession.createQuery(sb.toString());
	selectQuery.setParameter("lStrGpfAccNo", lStrGpfAccNo);
	
	flag = selectQuery.list();
	
	if (flag != null && flag.size() > 0) {
		sevaid = flag.get(0).toString();
	}
	

	}
	catch(Exception e){
		logger.error("Exception in getEmpsevaarthId of LNAHealthInsuranceDAOImpl  : ", e);	
		
	}
	
	return sevaid;
	
}

public String getEmpBillGrpId(String lStrSevaarthId) {
	
	List bill = new ArrayList();
	String lStrBillGrpId = "";
	
	StringBuilder lSBQuery = new StringBuilder();
	
	lSBQuery.append( " select MI.billGroupId FROM MstEmp MI " );
	lSBQuery.append( " where MI.sevarthId=:lStrSevaarthId ");
	
	Query lQuery = ghibSession.createQuery(lSBQuery.toString());
	
	lQuery.setParameter("lStrSevaarthId", lStrSevaarthId);
	
	bill = lQuery.list();
	
	if (bill != null && bill.size() > 0) {
		lStrBillGrpId = bill.get(0).toString();
	}
	

	return lStrBillGrpId;
}


public List getBeamsDetails(String lStrSevaarthId){
	List Beams = new ArrayList();
	StringBuilder lSBQuery = new StringBuilder();
	
	lSBQuery.append(" SELECT motor.SchemeCode,motor.SchemeName,motor.SubMajorHead,motor.MinorHead,motor.GroupHead,motor.SubHead ");
	lSBQuery.append(" FROM MstLnaHealthInsurance motor ");
	lSBQuery.append(" where motor.sevaarthId=:sevaarthId ");
	
	Query lQuery = ghibSession.createQuery(lSBQuery.toString());
	
	lQuery.setParameter("sevaarthId", lStrSevaarthId);

	
	Beams = lQuery.list();
	return Beams;
}

public String getParentDept(String lStrSevaarthid) {

	StringBuilder lSBQuery = new StringBuilder();
	List<String> parent = new ArrayList<String>();
	String lStrParentDept = "0";
	
	logger.error("getParentDept"+lStrSevaarthid);
	
	try {
		lSBQuery.append(" SELECT dcps.parentDept FROM MstEmp dcps ");
		lSBQuery.append(" where dcps.sevarthId=:lStrSevaarthid ");
	
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("lStrSevaarthid", lStrSevaarthid);

		parent = lQuery.list();
		logger.info("parentsize"+parent.isEmpty());
		
		if(!parent.isEmpty()){
			logger.info("false");
			if(parent.get(0) != null){
				lStrParentDept = parent.get(0);
			}
		}
		
		
		logger.error("lStrParentDept from query"+lStrParentDept);
	} catch (Exception e) {
		logger.error("Exception in getParentDept of GPFRequestProcessDAOImpl  : ", e);			
	}
	return lStrParentDept;
}

public String getSchemeCode(String SchemeCode) {

	StringBuilder lSBQuery = new StringBuilder();
	List<String> parent = new ArrayList<String>();
	String lStrParentDept = "0";
	
	logger.error("getSchemeCode"+SchemeCode);
	
	try {
		lSBQuery.append(" SELECT beams.UniqCd FROM MstLnaBeamsMapping beams ");
		lSBQuery.append(" where beams.SchemeCode=:SchemeCode ");
	
		Query lQuery = ghibSession.createQuery(lSBQuery.toString());
		lQuery.setParameter("SchemeCode", SchemeCode);

		parent = lQuery.list();
		logger.info("parentsize"+parent.isEmpty());
		
		if(!parent.isEmpty()){
			logger.info("false");
			if(parent.get(0) != null){
				lStrParentDept = parent.get(0);
			}
		}
		
		
		logger.error("lStrParentDept from query"+lStrParentDept);
	} catch (Exception e) {
		logger.error("Exception in getParentDept of GPFRequestProcessDAOImpl  : ", e);			
	}
	return lStrParentDept;
}

}
