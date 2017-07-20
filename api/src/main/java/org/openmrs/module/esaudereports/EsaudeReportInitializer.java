package org.openmrs.module.esaudereports;

/**
 * Created by codehub on 7/19/17.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.module.reporting.report.util.ReportUtil;

/**
 * Initializes reports
 */
public class EsaudeReportInitializer implements Initializer {
	
	protected static final Log log = LogFactory.getLog(EsaudeReportInitializer.class);
	
	/**
	 * @see Initializer#started()
	 */
	@Override
	public synchronized void started() {
		removeOldReports();
		
		ReportManagerUtil.setupAllReports(EsaudeReportManager.class);
		ReportUtil.updateGlobalProperty(ReportingConstants.GLOBAL_PROPERTY_DATA_EVALUATION_BATCH_SIZE, "-1");
	}
	
	/**
	 * @see Initializer#stopped()
	 */
	@Override
	public void stopped() {
	}
	
	private void removeOldReports() {
		AdministrationService as = Context.getAdministrationService();
		log.warn("Removing all reports");
		String report_resource_sample_register_id = "select id from reporting_report_design where uuid='cc0fa186-6c83-11e7-9fd6-507b9dc4c741'";
		
		as.executeSQL("delete from reporting_report_design_resource where report_design_id =("
		        + report_resource_sample_register_id + ");", false);
		as.executeSQL("delete from reporting_report_design where uuid='cc0fa186-6c83-11e7-9fd6-507b9dc4c741';", false);
		as.executeSQL("delete from reporting_report_request;", false);
		as.executeSQL("delete from global_property WHERE property LIKE 'reporting.reportManager%';", false);
		as.executeSQL("delete from serialized_object WHERE uuid = 'bf60e44a-6c83-11e7-9a26-507b9dc4c741';", false);
	}
}
