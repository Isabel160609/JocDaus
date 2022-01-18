package com.init.JocDausS.security.service;

public class JwtSecurityConstants {

	
		public static final String SECRET = "secret";
		public static final long EXPIRATION_TIME = 86_400_000;
		public static final String TOKEN_PREFIX = "Bearer ";
		public static final String HEADER_STRING = "Authorization";
		public static final String SIGN_UP_URL = "/auth/nuevo";
	
}
