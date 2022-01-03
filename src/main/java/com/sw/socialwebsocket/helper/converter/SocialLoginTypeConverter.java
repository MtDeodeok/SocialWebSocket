package com.sw.socialwebsocket.helper.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import com.sw.socialwebsocket.helper.constants.SocialLoginType;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
	@Override
	public SocialLoginType convert(String s) {
		return SocialLoginType.valueOf(s.toUpperCase());
	}
}