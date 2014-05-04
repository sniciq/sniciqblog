package com.mini.cms.admin.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class GzipFilter implements Filter {
	
	Map<String, String> headers = new HashMap<String, String>();

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			doFilter((HttpServletRequest) request, (HttpServletResponse)response, chain);
		}
		else {
			chain.doFilter(request, response);
		}
	}
	
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		Iterator<String> itor = headers.keySet().iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			String value = headers.get(key);
			response.addHeader(key, value);
		}
		
		String reqUri = request.getRequestURI();
		if(reqUri.contains(".js")) {
			response.addHeader("content-type", "text/javascript");
		}
		else if(reqUri.contains(".css")) {
			response.addHeader("content-type", "text/css");
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String headersStr = config.getInitParameter("headers");
		String[] headers = StringUtils.split(headersStr, ",");
		for(int i = 0; i < headers.length; i++) {
			String[] temp = headers[i].split("=");
			this.headers.put(temp[0].trim(), temp[1].trim());
		}
	}
}