package com.eventLedger.accountService.filter;

import com.eventLedger.accountService.trace.TraceContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TraceFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String traceId =
                httpRequest.getHeader("X-Trace-Id");

        TraceContext.setTraceId(traceId);

        try {

            chain.doFilter(request, response);

        } finally {

            TraceContext.clear();
        }
    }
}
