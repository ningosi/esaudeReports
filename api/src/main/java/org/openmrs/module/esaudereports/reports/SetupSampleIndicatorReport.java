/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.esaudereports.reports;

import org.openmrs.module.esaudereports.EsaudeDataExportManager;
import org.openmrs.module.esaudereports.reporting.library.dimension.CommonDimension;
import org.openmrs.module.esaudereports.reporting.library.indicator.EsaudeReportIndicators;
import org.openmrs.module.esaudereports.reporting.utils.ColumnParameters;
import org.openmrs.module.esaudereports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.esaudereports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicholas Ingosi on 7/20/17.
 */
@Component
public class SetupSampleIndicatorReport extends EsaudeDataExportManager {
	
	@Autowired
	private CommonDimension commonDimension;
	
	@Autowired
	private EsaudeReportIndicators indicators;
	
	/**
	 * @return the uuid for the report design for exporting to Excel
	 */
	@Override
	public String getExcelDesignUuid() {
		return "c33406d2-6d51-11e7-8db8-507b9dc4c741";
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(buildReportDesign(reportDefinition));
		return l;
	}
	
	/**
	 * Build the report design for the specified report, this allows a user to override the report
	 * design by adding properties and other metadata to the report design
	 * 
	 * @param reportDefinition
	 * @return The report design
	 */
	@Override
	public ReportDesign buildReportDesign(ReportDefinition reportDefinition) {
		return createExcelTemplateDesign(getExcelDesignUuid(), reportDefinition, "sampleIndicator.xls");
	}
	
	@Override
	public String getUuid() {
		return "b1815d72-6d51-11e7-8f41-507b9dc4c741";
	}
	
	@Override
	public String getName() {
		return "Sample Indicator Report";
	}
	
	@Override
	public String getDescription() {
		return "This report contain sample indicators for review";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.addParameters(getParameters());
		rd.addDataSetDefinition("I", Mapped.mapStraightThrough(dataSetDefinition()));
		return rd;
	}
	
	@Override
	public String getVersion() {
		return "0.1";
	}
	
	private DataSetDefinition dataSetDefinition() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setParameters(getParameters());
		dsd.setName("I");
		
		//add dimensions to the dsd
		dsd.addDimension("age", ReportUtils.map(commonDimension.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", ReportUtils.map(commonDimension.gender()));
		
		//define dimension columns
		ColumnParameters colInfants = new ColumnParameters(null, "<1", "age=<1");
		ColumnParameters colMPeds = new ColumnParameters(null, "<15, Male", "gender=M|age=<15");
		ColumnParameters colFPeds = new ColumnParameters(null, "<15, Female", "gender=F|age=<15");
		ColumnParameters colMAdults = new ColumnParameters(null, "15+, Male", "gender=M|age=15+");
		ColumnParameters colFAdults = new ColumnParameters(null, "15+, Female", "gender=F|age=15+");
		ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
		
		//come up with a list that can be passed to a row
		List<ColumnParameters> allColumns = Arrays.asList(colInfants, colMPeds, colFPeds, colMAdults, colFAdults, colTotal);
		String indParams = "startDate=${startDate},endDate=${endDate}";
		
		//build the design here, all patients in the system with different aggregation
		EmrReportingUtils.addRow(dsd, "A", "All Patients", ReportUtils.map(indicators.allPatients(), indParams), allColumns,
		    Arrays.asList("01", "02", "03", "04", "05", "06"));
		return dsd;
	}
	
	@Override
	public List<Parameter> getParameters() {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class));
	}
}
