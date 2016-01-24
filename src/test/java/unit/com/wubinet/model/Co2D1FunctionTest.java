package unit.com.wubinet.model;

import com.wubinet.model.Co2D1Function;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class Co2D1FunctionTest {

	private static final int DEFAULT_TEMPERATURE = 5960;
	private static final int OFFSET = 0;			// mv
	private static final int REFERENCE = 491;		// LSB

	@Test
	public void zeroMillis() {
		int sense = 491;
		Co2D1Function function = new Co2D1Function(REFERENCE, OFFSET, sense, DEFAULT_TEMPERATURE);
		BigDecimal expected = new BigDecimal(33884.42).setScale(2, RoundingMode.HALF_UP);
		assertEquals(expected, function.calculateCO2());
	}

	@Test
	public void eighteenMillis() {
		int sense = 498;
		Co2D1Function function = new Co2D1Function(REFERENCE, OFFSET, sense, DEFAULT_TEMPERATURE);
		BigDecimal expected = new BigDecimal(316.23).setScale(2, RoundingMode.HALF_UP);
		assertEquals(expected, function.calculateCO2());
	}

}