package com.mini.cms.admin.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AjaxOut {
	
	private static Logger logger = Logger.getLogger(AjaxOut.class);

	/**
	 * AJAX输出页面
	 * 
	 * @param response
	 * @param s
	 */
	public static void responseText(HttpServletResponse response, String s) {
		// 指定内容类型
		response.setContentType("text/html;charset=utf-8");
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.print(s);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
