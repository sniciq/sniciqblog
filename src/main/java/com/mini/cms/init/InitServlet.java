package com.mini.cms.init;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 8082252675110738430L;

	@Override
	public void init() throws ServletException {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		
		String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		getServletContext().setAttribute("SysVersion", version);
		super.init();
	}
}
