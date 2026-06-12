package com.joaolive.javanews.core.web;

import java.security.Principal;
import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.joaolive.javanews.core.CurrentUser;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentUser.class) != null
				&& parameter.getParameterType().equals(UUID.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		// 1. Pega o Principal direto do Request (Mais elegante que o SecurityContextHolder estático)
		Principal principal = webRequest.getUserPrincipal();

		if (principal instanceof Authentication authentication && authentication.getPrincipal() instanceof Jwt jwt) {
			String userId = jwt.getClaimAsString("user_id");
			if (userId != null) {
				return UUID.fromString(userId); // Caminho Feliz :)
			}
		}

		// 2. FAIL-FAST: Se o Controller pediu o @CurrentUser e algo deu errado (ex: Token corrompido), 
		// corte o mal pela raiz! Não retorne 'null' para a aplicação.
		throw new InvalidUserIdentityException("Invalid or missing user identity in request context");
	}
}