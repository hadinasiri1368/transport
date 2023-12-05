package org.transport.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.transport.common.CommonUtils;

import java.io.IOException;

public class CheckPermission extends OncePerRequestFilter implements Filter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = CommonUtils.getToken(request);
        if (!CommonUtils.isNull(token)) {
            String returnValue = CommonUtils.checkValidation(token, request.getRequestURI());
            if (CommonUtils.isNull(returnValue)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write(returnValue);
            }
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("token is null");
        }
    }
}
