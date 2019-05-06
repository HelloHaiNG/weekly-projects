package com.ucarweekly.usercenterservice.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Jackson属性配置
 * @author MaChuang
 * @since 2018/6/27
 */
@Configuration
public class MappingJacksonConfig {
	/**
	 * jsons输出配置
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter messageConverter(){
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
