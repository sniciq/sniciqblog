package com.mini.cms.admin.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class ExceptionUtil {
	
	private static Logger logger = Logger.getLogger(ExceptionUtil.class);
	
	public static String getExceptionMessage(Throwable throwable) {
		StringWriter writer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(writer, true));
		return writer.toString();
	}
 
	/**
	 * 将错误信息输出到页面
	 * 
	 * @param response
	 * @param info 信息
	 * @param cl 错误类
	 * @param throwable
	 */
	public static void responseException(HttpServletResponse response, String info, Class cl, Throwable throwable) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			StringBuffer errorBuf = new StringBuffer();
			errorBuf.append("<table>");
			errorBuf.append("<tr>");
			errorBuf.append("<td>");
			errorBuf.append(info + ": " + cl);
			errorBuf.append("</td>");
			errorBuf.append("</tr>");

			errorBuf.append("<tr>");
			errorBuf.append("<td>");
			errorBuf.append(throwable.getClass() + ": " + throwable.getMessage());
			errorBuf.append("</td>");
			errorBuf.append("</tr>");

			StackTraceElement[] trace = throwable.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				errorBuf.append("<tr>");

				if (trace[i].toString().contains(cl.getName()))
					errorBuf.append("<td bgcolor='#FF0000'>");
				else
					errorBuf.append("<td>");

				errorBuf.append("&nbsp;&nbsp;at " + trace[i]);
				errorBuf.append("</td>");
				errorBuf.append("</tr>");
			}

			Throwable ourCause = throwable.getCause();
			if (ourCause != null) {
				errorBuf.append("<tr>");
				errorBuf.append("<td>");
				errorBuf.append(ourCause);
				errorBuf.append("</td>");
				errorBuf.append("</tr>");
			}
			errorBuf.append("</table>");

			PrintWriter out = response.getWriter();
			out.print(errorBuf);

			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
