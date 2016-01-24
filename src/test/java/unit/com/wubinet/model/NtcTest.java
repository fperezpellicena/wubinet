package unit.com.wubinet.model;

import com.wubinet.model.Ntc;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class NtcTest {

	@Test
	public void twentyFiveDegrees() {
		Ntc ntc = new Ntc(378);
		BigDecimal expectedTemperature = BigDecimal.valueOf(25.05);
		assertEquals(expectedTemperature, ntc.calculateTemperature());
	}
}