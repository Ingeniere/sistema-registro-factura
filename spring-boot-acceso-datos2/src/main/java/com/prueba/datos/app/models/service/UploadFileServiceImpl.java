package com.prueba.datos.app.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService{

    //con esto podemos mostrar los nombres de directorio en la pantalla
    private final Logger log = LoggerFactory.getLogger(getClass());
    //con esto estamos creando nuestra carpeta
    private final static String UPLOADS_FOLDER = "subidas-imagenes";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathFoto = getPath(filename);// el "getPath" es el metodo donde creamos la ruta de ahi cargamos el archivo
        log.info("pahFoto: " + pathFoto);
        Resource recurso = null;

        recurso = new UrlResource(pathFoto.toUri());
        if(!recurso.exists() || !recurso.isReadable()){
            throw new RuntimeException("Error: nose puede cargar la imagen:" + pathFoto.toString());//CON ESTO LANZAMOS UNA SOLA EXCEPCION
        }
        return recurso;
    }

    //ESTE METODO ES PARA COPIAR LA IMAGEN EN LA RUTA O PARA GUARDAR
    @Override
    public String copy(MultipartFile file) throws IOException {
        //aqui le decimos que vamos a copiar el nombre unico de la imagen para que no se repita
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);

        log.info("rootPath" + rootPath);

        /*ESTE METODO PARA CARGAR IMAGEN UTILIZANDO ARCHIVO MvcConfig.java*/
        /*OTRA FORMA DE GUADAR*/
        //byte[] bytes = foto.getBytes();
        //Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
        //Files.write(rutaCompleta, bytes);

        /*OTRA FORMA DE GUADRAR*/
        Files.copy(file.getInputStream(), rootPath);

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()){
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename){
        //aqui estamos creando la carpeta en la ruta absoluta
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        //este FileSystemUtils es de org.springframework.util
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }
}
