package com.joaolive.javanews.auth.web;

import java.security.Principal;
import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.joaolive.javanews.auth.CurrentUser;
import com.joaolive.javanews.auth.UserContext;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentUser.class) != null
				&& parameter.getParameterType().equals(UserContext.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Principal principal = webRequest.getUserPrincipal();
		if (principal instanceof Authentication authentication && authentication.getPrincipal() instanceof Jwt jwt) {
			String userId = jwt.getClaimAsString("user_id");
			String username = jwt.getClaimAsString("username");
			if (userId != null && username != null) {
				return new UserContext(UUID.fromString(userId), username);
			}
		}
		throw new InvalidUserIdentityException("Invalid or missing user identity in request context");
	}
}