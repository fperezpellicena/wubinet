package unit.com.wubinet.model;

import com.wubinet.model.H25K5AFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class H25K5AFunctionTest {

	private H25K5AFunction function = new H25K5AFunction();

	@Test
	public void zeroDegrees() {
		int relativeHumidity = function.getRelativeHumidity(0, 725);
		assertEquals(45, relativeHumidity);
	}

	@Test
	public void fiftyDegrees() {
		int relativeHumidity = function.getRelativeHumidity(50, 18);
		assertEquals(55, relativeHumidity);
	}

	@Test
	public void twentyFiveDegrees() {
		int relativeHumidity = function.getRelativeHumidity(25, 5.0);
		assertEquals(80, relativeHumidity);
	}

	@Test
	public void subzeroDegrees() {
		int relativeHumidity = function.getRelativeHumidity(-10, 5.0);
		assertEquals(0, relativeHumidity);
	}

	@Test
	public void overFiftyDegrees() {
		int relativeHumidity = function.getRelativeHumidity(92, 5.0);
		assertEquals(100, relativeHumidity);
	}

}