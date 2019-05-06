package com.ucarweekly.usercenterservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 配置mybatis扫描包
 * @author MaChuang
 * @since 2018/6/27
 */
@Configuration
public class GenericMapperScannerConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		scannerConfigurer.setBasePackage("com.ucarweekly.usercenterservice.mapper");
		return scannerConfigurer;
	}

}
