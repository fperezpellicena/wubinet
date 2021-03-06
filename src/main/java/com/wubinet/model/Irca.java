package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.log;
import static java.math.BigDecimal.ONE;

public class Irca implements Sensor {

	// Valores de calibración
	private static final BigDecimal CALIBRATION_CONCENTRATION = BigDecimal.valueOf(17.2);
	private static final BigDecimal CALIBRATION_TEMPERATURE = BigDecimal.valueOf(295);
	private static final BigDecimal CALIBRATION_ACTIVE = BigDecimal.valueOf(0.52799);
	private static final BigDecimal CALIBRATION_REFERENCE = BigDecimal.valueOf(1.0198);
	private static final BigDecimal CALIBRATION_ABSORBANCE = BigDecimal.valueOf(0.46723);
	private static final BigDecimal CALIBRATION_SPAN = BigDecimal.valueOf(0.51427);

	// Valores de referencia en ausencia de CO2
	private static final BigDecimal ZERO = BigDecimal.valueOf(0.97);

	// Parámetros de cálculo
	private static final BigDecimal B = BigDecimal.valueOf(0.520);
	private static final BigDecimal C = BigDecimal.valueOf(0.680);
	private static final BigDecimal BETA0 = BigDecimal.valueOf(0.0021);
	private static final BigDecimal ALPHA = BigDecimal.valueOf(0.0009);
	private static final BigDecimal BETAA = BigDecimal.valueOf(0.0014);

	private final BigDecimal active;

	private final BigDecimal reference;

	private final BigDecimal temperature;

	public Irca(int active, int reference, int temperature) {
		this.active = BigDecimal.valueOf(active);
		this.reference = BigDecimal.valueOf(reference);
		this.temperature = BigDecimal.valueOf(temperature);
	}

	@Override
	public Map<MeasureType, BigDecimal> calculate() {
		Map<MeasureType, BigDecimal> measures = new HashMap();
		measures.put(MeasureType.CO2_PERCENTAGE, calculateConcentration());
		return measures;
	}

	private BigDecimal calculateAbsorbance() {
		BigDecimal divisor = reference.multiply(ZERO);
		BigDecimal actRefRatio = active.divide(divisor, 2, RoundingMode.HALF_UP);
		return ONE.subtract(actRefRatio);
	}

	private BigDecimal calculateConcentration() {
		BigDecimal absorbance = calculateAbsorbance();
		BigDecimal absorbanceSpanRatio = absorbance.divide(CALIBRATION_SPAN, 2, RoundingMode.HALF_UP);
		BigDecimal numerator;
		if (absorbance.signum() == 1) {
			numerator = BigDecimal.valueOf(log(ONE.subtract(absorbanceSpanRatio).doubleValue()));
		} else {
			numerator = BigDecimal.valueOf(log(ONE.add(absorbanceSpanRatio).doubleValue()));
		}
		double base = numerator.divide(B.negate(), 2, RoundingMode.HALF_UP).doubleValue();
		double exponent = ONE.divide(C, 2, RoundingMode.HALF_UP).doubleValue();
		double concentration = Math.exp(exponent * Math.log(base));
		BigDecimal concentrationPercentage = BigDecimal.valueOf(concentration);
		if (absorbance.signum() == -1) {
			concentrationPercentage = concentrationPercentage.negate();
		}
		return concentrationPercentage.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public SensorType getType() {
		return SensorType.IRCA1;
	}
}
