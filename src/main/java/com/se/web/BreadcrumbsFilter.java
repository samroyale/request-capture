package com.se.web;

import static com.se.web.Breadcrumbs.parseBreadcrumbs;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A servlet filter that processes and applies breadcrumbs outside the servlets/pages that need it.
 * 
 * @author seldred
 */
public class BreadcrumbsFilter implements Filter {

    private FilterConfig filterConfig;
    
    private static final Logger log = Logger.getLogger(BreadcrumbsFilter.class.getName());

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
		String breadcrumbsFormat = request.getParameter("bc");
        log.info("BreadcrumbsFilter invoked: " + breadcrumbsFormat);
		Breadcrumbs breadcrumbs = parseBreadcrumbs(breadcrumbsFormat);

		request.setAttribute("breadcrumbs", breadcrumbs);

        filterChain.doFilter(request, response);
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {}
}
