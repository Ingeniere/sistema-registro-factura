package com.prueba.datos.app.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {

    public Resource load(String filename) throws MalformedURLException;

    public String copy(MultipartFile file) throws IOException;

    public boolean delete(String filename);

    public void deleteAll();//ESTE METODO ES PARA BORRAR TODA EL DIRECTORIO "subidas-imagenes" DONDE SE ALAMACENA LAS IMAGENES

    public void init() throws IOException;//ESTE METODO ES PARA CREAR EL DIRECTORIO "uploads" NUEVAMENTE
}
