package ru.job4j.dream.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class redirects unauthorized users except for ajax-requests with a valid token
 * to login.jsp and passes all users to authorization|registration servlets.
 *
 * Method req.setCharacterEncoding() needs to be placed in the filter
 * for further correct encoding of the requests.
 *
 *@author AndrewMs
 *@version 1.0
 */
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class.getName());
    private String key;

    @Override
    public void init(FilterConfig filterConfig) {
        this.key  = filterConfig.getServletContext().getInitParameter("key");
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        String token = req.getParameter("tk");
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do") || validateToken(token)) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }

    private boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("dreamjob")
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return false;
    }
}