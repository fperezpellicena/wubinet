package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.log;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

public class Ntc {

	private static final BigDecimal VCC = valueOf(3.3);

	private static final BigDecimal KELVIN_CONVERSION = valueOf(273);
	private static final BigDecimal ADC_RANGE = valueOf(1024);

	private static final BigDecimal B_NTC = valueOf(3964);
	private static final BigDecimal TEMPERATURE_REF = valueOf(298);
	private static final BigDecimal RESISTANCE_REF = valueOf(50000);
	private static final BigDecimal PULL_DOWN_RESISTENCE = valueOf(29200);

	private final BigDecimal temperature;

	public Ntc(int temperature) {
		this.temperature = valueOf(temperature);
	}

	public BigDecimal calculateTemperature() {
		BigDecimal vout = VCC.divide(ADC_RANGE).multiply(temperature);
		//  Rntc=(Vout*Rfija)/(Vin-Vout);
		BigDecimal rntc = ((VCC.divide(vout, 2, RoundingMode.HALF_UP)).subtract(ONE)).multiply(PULL_DOWN_RESISTENCE);
		// TempK = Beta/(log(Rntc/R25)+(Beta/T0));
		BigDecimal b = B_NTC.divide(TEMPERATURE_REF, 2 , RoundingMode.HALF_UP);
		BigDecimal a = valueOf(log(rntc.divide(RESISTANCE_REF, 2, RoundingMode.HALF_UP).doubleValue()));
		BigDecimal tempKelvin = B_NTC.divide(a.add(b), 2, RoundingMode.HALF_UP);
		return tempKelvin.subtract(KELVIN_CONVERSION);
	}
}
