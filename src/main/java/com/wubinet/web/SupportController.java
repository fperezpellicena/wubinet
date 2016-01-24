package com.wubinet.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SupportController {

	@RequestMapping(value = "/support.pdf", method = RequestMethod.GET)
	@ResponseBody
	public ClassPathResource downloadSupportDocument(HttpServletResponse response) throws IOException {
		ClassPathResource resource = new ClassPathResource("/static/docs/support.pdf");
		response.setContentType("application/pdf");
		response.setContentLength((int) resource.contentLength());
		response.addHeader("Content-Type","application/pdf");
		response.addHeader("Content-Disposition","attachment");
		response.addHeader("filename", "support.pdf");
		return resource;
	}
}
