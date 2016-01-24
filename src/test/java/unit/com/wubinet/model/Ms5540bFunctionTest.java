package unit.com.wubinet.model;

import com.wubinet.model.Ms5540bFunction;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class Ms5540bFunctionTest {

	@Test
	public void calculateTemperature() {
		int[] coeffs = {23470, 1324, 737, 393, 628, 25};
		int temperature = 27856;
		Ms5540bFunction function = new Ms5540bFunction(coeffs);
		BigDecimal calculatedTemperature = function.calculateTemperature(BigDecimal.valueOf(temperature));
		BigDecimal expectedTemperature = BigDecimal.valueOf(39.1);
		assertEquals(expectedTemperature, calculatedTemperature);
	}

	@Test
	public void calculatePressure() {
		int[] coeffs = {23470, 1324, 737, 393, 628, 25};
		int temperature = 27856;
		int pressure = 16460;
		Ms5540bFunction function = new Ms5540bFunction(coeffs);
		BigDecimal calculatedPressure = function.calculatePressure(BigDecimal.valueOf(pressure), BigDecimal.valueOf(temperature));
		BigDecimal expectedPressure = BigDecimal.valueOf(971.7);
		assertEquals(expectedPressure, calculatedPressure);
	}

}