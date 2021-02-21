package com.test.async.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.async.configurations.RequestTraceId;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to create the traceId for every request to the server. The
 * trace id is created using {@link UUID} and it is set to the header.
 */
@Slf4j
@Component
@Order(value = 1)
public class TraceIdFilter extends OncePerRequestFilter {

    /**
     * This is a method which is used to guarantee a single execution per request
     * dispatch.
     * <p>
     * Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones
     * </p>
     * 
     * If the incoming request don't have traceId it will create a id using
     * {@link UUID#randomUUID()} and attach to the request header. This can be use
     * if we have to tack any particular request from the user.
     * 
     * @param request     {@link HttpServletRequest} object.
     * @param response    {@link HttpServletResponse} object.
     * @param filterChain {@link FilterChain} object.
     * 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String traceId = request.getHeader(RequestTraceId.TRACE_ID);
            traceId = StringUtils.hasText(traceId) ? traceId : UUID.randomUUID().toString();

            RequestTraceId.setTraceId(traceId);
            MDC.put(RequestTraceId.TRACE_ID, traceId);

        } catch (Exception ex) {
            log.error("Exception during TraceIdFilter.doFilterInternal()", ex);
        }

        if (StringUtils.hasText(RequestTraceId.getTraceId())) {
            response.setHeader(RequestTraceId.TRACE_ID, RequestTraceId.getTraceId());
        }

        filterChain.doFilter(request, response);
    }
}
