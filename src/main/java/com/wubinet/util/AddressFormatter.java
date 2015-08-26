package com.wubinet.util;

import com.rapplogic.xbee.api.XBeeAddress64;

public class AddressFormatter {

	public static XBeeAddress64 format(String commaSeparatedAddress) {
		String address = commaSeparatedAddress.replaceAll(",", " ");
		address = address.replaceAll("0x", "");
		return new XBeeAddress64(address);
	}
}
