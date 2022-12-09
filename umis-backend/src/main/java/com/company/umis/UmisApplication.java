package com.company.umis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class UmisApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmisApplication.class, args);
	}

}
