package com.tablabs.hrms.errors;

import java.net.URI;

public class CompanyRegisteredIdAlreadyUsedException extends BadRequestAlertException{

	public CompanyRegisteredIdAlreadyUsedException() {
		super(ErrorConstants.COMPANY_ID_ALREADY_USED_TYPE, "Company ID is already in user", "companies", "idexists");
	}
	
	

}
