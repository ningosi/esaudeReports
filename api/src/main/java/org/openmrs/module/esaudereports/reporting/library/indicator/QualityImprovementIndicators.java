package org.openmrs.module.esaudereports.reporting.library.indicator;

import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.esaudereports.reporting.utils.EmrReportingUtils.cohortIndicator;

import org.openmrs.module.esaudereports.reporting.library.cohort.QualityImprovementCohort;
import org.openmrs.module.esaudereports.reporting.utils.ReportUtils;

@Component
public class QualityImprovementIndicators {
	
	@Autowired
	private QualityImprovementCohort cohorts;
	
	/**
	 * Clinical consultation numerator
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator clinicalConsulationsNumerator() {
		return cohortIndicator(
		    "Initial clinical consultations numerator",
		    ReportUtils.map(cohorts.allPatients(),
		        "startDate=${startDate},endDate=${endDate},location=${location},revisionEndDate=${revisionEndDate},testStart=${testStart}"));
	}
	
	/**
	 * Clinical consultation denominator
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator clinicalConsulationsDenominator() {
		return cohortIndicator(
		    "Initial clinical consultations denominator",
		    ReportUtils.map(cohorts.allPatients(),
		        "startDate=${startDate},endDate=${endDate},location=${location},revisionEndDate=${revisionEndDate},testStart=${testStart}"));
	}
	
}
