package com.example.jugarenbolsa.data;

import com.example.jugarenbolsa.helpers.db.IdGenerator;

public class Accion {
	private static IdGenerator idGenerator = new IdGenerator();

	public static final double INITIAL_MAX_VALUE_ACCION = 300;
	public static final int MIN_RISK = 1;
	public static final int MAX_RISK = 4;

	private long id;
	private String companyName;
	private double value = 0;	// de la acción
	private double trend = 0;	// tendencia: si en los últimos movimientos ha subido o bajado
	private double maxReached = Double.MIN_VALUE;
	private double minReached = Double.MAX_VALUE;
	private int risk = 1;		// nivel de riesgo: de 1 a 4, siendo 1 el riesgo mínimo y 4 el riesgo máximo
								// el riesgo será bajo si la accion NO varía mucho

	/**
	 * Generate a new Accion type
	 * @param companyName
	 */
	public Accion( String companyName ) {
		this.id = idGenerator.getNewId();
		this.companyName = companyName;
	}

	/**
	 * Restore the Accion type
	 * @param id
	 * @param companyName
	 */
	public Accion( long id, String companyName ) {
		this.id = id;	// Set its id
		this.companyName = companyName;
	}

	public long getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void addValue(double value) {
		this.value += value;
	}

	public double getTrend() {
		return trend;
	}

	public void setTrend(double trend) {
		this.trend = trend;
	}

	public double getMaxReached() {
		return maxReached;
	}

	public void setMaxReached(double maxReached) {
		this.maxReached = maxReached;
	}

	public double getMinReached() {
		return minReached;
	}

	public void setMinReached(double minReached) {
		this.minReached = minReached;
	}

	public int getRisk() {
		return risk;
	}

	public void setRisk(int risk) {
		this.risk = risk;
	}

	@Override
	public String toString() {
		return "id=" + id +
				", '" + companyName + '\'';
	}
	public String toStringAll() {
		return "Accion{" +
				"id=" + id +
				", companyName='" + companyName + '\'' +
				", value=" + value +
				", trend=" + trend +
				", maxReached=" + maxReached +
				", minReached=" + minReached +
				", risk=" + risk +
				'}';
	}
}
