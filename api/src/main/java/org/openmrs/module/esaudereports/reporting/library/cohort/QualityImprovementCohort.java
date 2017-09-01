package org.openmrs.module.esaudereports.reporting.library.cohort;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Nicholas Ingosi on 7/27/17.
 */
@Component
public class QualityImprovementCohort {
	
	public CohortDefinition allPatients() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("all patients");
		cd.addParameter(new Parameter("startDate", "Data Inicial", Date.class));
		cd.addParameter(new Parameter("endDate", "Data Final", Date.class));
		cd.addParameter(new Parameter("revisionEndDate", "Data Final de Revisão", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		cd.addParameter(new Parameter("testStart", "Test Start", Boolean.class));
		cd.setQuery("SELECT patient_id FROM patient");
		return cd;
	}
}
