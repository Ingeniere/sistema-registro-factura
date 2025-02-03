package com.prueba.datos.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
//AQUI CONFIGURAMOS LA RUTA EXTERNA PARA CARGAR  LAS IMAGEN DE UN CLIENTE
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //ESTO ES UN FORMA DE IMPLEMENTAR LA CARGA DE IMAGEN EL EN DETALLE
    private final Logger log = LoggerFactory.getLogger(getClass());
/*
    //CON ESTE METODO ESTAMOS ESPECIFICANDO LA RUTA DE DONDE SE CARGA LA IMAGEN
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        //el toAbsolutePath() quiere decir que sera una ruta absoluta y toUri() agrega el esquema file: y ultimo convertimos a string
        String resourcePath = Paths.get("subidas-imagenes").toAbsolutePath().toUri().toString();
        log.info(resourcePath);

        registry.addResourceHandler("/subidas-imagenes/**")
                .addResourceLocations(resourcePath);
        //OTRA FORMA DE LOCALIZAR
        //.addResourceLotacions("file:/C:/Temp/Uploads");
    }*/
}
