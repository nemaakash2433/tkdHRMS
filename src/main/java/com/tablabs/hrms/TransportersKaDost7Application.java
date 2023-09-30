package com.tablabs.hrms;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class TransportersKaDost7Application {

	public static void main(String[] args) {
		SpringApplication.run(TransportersKaDost7Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	@PostConstruct
//	public void init(){
//		// Setting Spring Boot SetTimeZone
//		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//	}

}
