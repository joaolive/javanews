package com.joaolive.javanews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic(sharedModules = "core")
public class JavanewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavanewsApplication.class, args);
	}

}
