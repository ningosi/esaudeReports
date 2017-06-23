package org.openmrs.module.esaudereports.reporting.dataset.definition;

import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas Ingosi on 6/20/17. A data set made by merging other data sets
 */
public class MergingDataSetDefinition extends BaseDataSetDefinition {
	
	/**
	 * Merge order options
	 */
	public enum MergeOrder {
		NONE, NAME, LABEL
	}
	
	@ConfigurationProperty
	private List<DataSetDefinition> dataSetDefinitions;
	
	@ConfigurationProperty
	private MergeOrder mergeOrder;
	
	/**
	 * Gets the contained data set definitions
	 * 
	 * @return the data set definitions
	 */
	public List<DataSetDefinition> getDataSetDefinitions() {
		if (dataSetDefinitions == null) {
			dataSetDefinitions = new ArrayList<DataSetDefinition>();
		}
		return dataSetDefinitions;
	}
	
	/**
	 * Sets the contained data set definitions
	 * 
	 * @param dataSetDefinitions the data set definitions
	 */
	public void setDataSetDefinitions(List<DataSetDefinition> dataSetDefinitions) {
		this.dataSetDefinitions = dataSetDefinitions;
	}
	
	/**
	 * Adds a contained data set definition
	 * 
	 * @param dataSetDefinition the data set definition
	 */
	public void addDataSetDefinition(DataSetDefinition dataSetDefinition) {
		getDataSetDefinitions().add(dataSetDefinition);
	}
	
	/**
	 * Gets the merge order
	 * 
	 * @return the merge order
	 */
	public MergeOrder getMergeOrder() {
		return mergeOrder;
	}
	
	/**
	 * Sets the merge order
	 * 
	 * @param mergeOrder the merge order
	 */
	public void setMergeOrder(MergeOrder mergeOrder) {
		this.mergeOrder = mergeOrder;
	}
}
