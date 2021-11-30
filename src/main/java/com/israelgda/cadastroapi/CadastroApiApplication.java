package com.israelgda.cadastroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class CadastroApiApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(CadastroApiApplication.class, args);
	}

}
