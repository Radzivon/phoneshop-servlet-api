package com.es.phoneshop.web.filter;

import com.es.phoneshop.model.dos.DosProtectionService;
import com.es.phoneshop.model.dos.DosProtectionServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosProtectionService = DosProtectionServiceImpl.getInstance();
        ((DosProtectionServiceImpl) dosProtectionService).setStartTime(new Date(System.currentTimeMillis()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosProtectionService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
