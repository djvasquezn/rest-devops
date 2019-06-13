package com.develop.rest.devops.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String API_KEY_HEADER = "X-Parse-REST-API-Key";
	private final String API_KEY = "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (existeApiKey(request, response)) {
				String apiKey = request.getHeader(API_KEY_HEADER);
				
				if (!API_KEY.equals(apiKey)) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Autorizacion denegada");
				} else {
					SecurityContextHolder.getContext().setAuthentication(null);
				}
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Se requiere autorizacion");
			}
			chain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	/**
	 * Valida si se envio una API key
	 * @param request
	 * @param res
	 * @return
	 */
	private boolean existeApiKey(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(API_KEY_HEADER);
		if (authenticationHeader == null)
			return false;
		return true;
	}

}
