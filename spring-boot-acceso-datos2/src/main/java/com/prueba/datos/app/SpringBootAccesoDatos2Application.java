package com.prueba.datos.app;

import com.prueba.datos.app.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAccesoDatos2Application {//implements CommandLineRunner {este CommandLineRunner se esta usando para inicializar direcotorio "uploads" automaticamente

	@Autowired
	IUploadFileService uploadFileService;// esto igual

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAccesoDatos2Application.class, args);
	}

	/*ESTE METODO TAMBIEN
	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}*/
}
