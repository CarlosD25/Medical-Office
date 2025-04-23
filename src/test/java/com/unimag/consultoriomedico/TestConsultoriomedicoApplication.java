package com.unimag.consultoriomedico;

import org.springframework.boot.SpringApplication;

public class TestConsultoriomedicoApplication {

	public static void main(String[] args) {
		SpringApplication.from(ConsultoriomedicoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
