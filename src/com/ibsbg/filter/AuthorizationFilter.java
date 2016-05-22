package com.ibsbg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="AuthFilter", urlPatterns={ "*.xhtml" })
public class AuthorizationFilter implements Filter{

	public AuthorizationFilter() {
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest reqt = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession ses = reqt.getSession(false);
		
		String reqURL = reqt.getRequestURI();
		
		if (reqURL.indexOf("/login.xhtml") >= 0 
				|| (ses != null && ses.getAttribute("user") != null)) {
			chain.doFilter(request, response);
		}
		else{
			resp.sendRedirect(reqt.getContextPath()+"/login.xhtml");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}