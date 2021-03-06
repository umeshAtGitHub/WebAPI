package org.ohdsi.webapi.report;

import java.util.Objects;

public class MonthObservationRecord {

	private int monthYear;
	private double percentValue;
	private long countValue;
	
	/**
	 * @return the monthYear
	 */
	public int getMonthYear() {
		return monthYear;
	}
	/**
	 * @param monthYear the monthYear to set
	 */
	public void setMonthYear(int monthYear) {
		this.monthYear = monthYear;
	}
	/**
	 * @return the percentValue
	 */
	public double getPercentValue() {
		return percentValue;
	}
	/**
	 * @param percentValue the percentValue to set
	 */
	public void setPercentValue(double percentValue) {
		this.percentValue = percentValue;
	}
	/**
	 * @return the countValue
	 */
	public long getCountValue() {
		return countValue;
	}
	/**
	 * @param countValue the countValue to set
	 */
	public void setCountValue(long countValue) {
		this.countValue = countValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MonthObservationRecord that = (MonthObservationRecord) o;
		return monthYear == that.monthYear &&
				Double.compare(that.percentValue, percentValue) == 0 &&
				countValue == that.countValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(monthYear, percentValue, countValue);
	}
}
