package org.acme.filter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class RestFilter implements ContainerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(RestFilter.class);
	
	@Context
    UriInfo info;

    @Context
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext context) {

        final String method = context.getMethod();
        final String path = info.getPath();
        final String address = request.getRemoteAddr();

        LOG.info("Request {} {} from IP {}", method, path, address);
    }
}
