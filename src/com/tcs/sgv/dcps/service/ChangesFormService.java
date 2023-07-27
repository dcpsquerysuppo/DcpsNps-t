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
package com.tcs.sgv.dcps.service;

import java.util.Map;

import com.tcs.sgv.core.valueobject.ResultObject;

/**
 * Class Description -
 * 
 * 
 * @author Kapil Devani
 * @version 0.1
 * @since JDK 5.0 Mar 18, 2011
 */
public interface ChangesFormService {
	public ResultObject loadChangesForm(Map inputMap);

	public ResultObject updatePersonalDtls(Map inputMap);

	public ResultObject updateOfficeDtls(Map inputMap);

	public ResultObject updateOtherDtls(Map inputMap);

	public ResultObject updateNomineeDtls(Map inputMap);

	public ResultObject updatePhotoAndSignDtls(Map inputMap);

	public ResultObject forwardChangesToDDO(Map objectArgs);

	public ResultObject loadChangesDrafts(Map inputMap);

	public ResultObject rejectChangesToDDOAsst(Map objectArgs);

	public ResultObject approveChangesByDDO(Map objectArgs);

}
