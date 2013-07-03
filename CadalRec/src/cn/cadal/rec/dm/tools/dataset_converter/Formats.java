package cn.cadal.rec.dm.tools.dataset_converter;

/**
 * Enumeration of the data formats that can be read by the database
 * converters. All output are to the SPMF format.
 * 
 * For a sequence database, the following format can be converted to SPMF:
 * CSV, IBMGenerator, Kosarak, Snake and BMS (see documentation for a description
 * of these formats).
 * 
 * For a transaction database, the following format can be converted to SPMF:
 * CSV, ARF
 * 
 * @author Philippe Fournier-Viger
 */
public enum Formats {
	SPMF, CSV_INTEGER, IBMGenerator, Kosarak, Snake, BMS, 
	ARFF, ARFF_WITH_MISSING_VALUES 
}
