package com.ab.eventmanager;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CtxConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.ab.eventmanager" })
public class Application {

	  @PostConstruct
	  void started() {
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	  }
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
