package com.prueba.datos.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.prueba.datos.app.models.entity.Cliente;
import com.prueba.datos.app.models.service.IClienteService;
import com.prueba.datos.app.models.service.IUploadFileService;
import com.prueba.datos.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@SessionAttributes("cliente")//con esta anotacion controlamos el registro y editar para guardar su ID
public class ClienteController {

    @Autowired
    //private IClienteDao clienteDao;//esto es para inyectar al IClienteDao
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    /*ESTE METODO ES PARA CARGAR O VER IMAGEN DE FORMA PROGRAMATICA*/
    @GetMapping(value="/subidas-imagenes/{filename:.+}") //esta expresion .+ regular no permite que spring borre la extension del archivo
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){

        Resource recurso = null;
        try{
            recurso = uploadFileService.load(filename);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() +"\"")
                .body(recurso);
    }
    /*HASTA AQUI*/

    /*ESTE METODO ES OTRA FORMA PARA CARGAR IMAGEN EN DETALLE PARA VER*/
    @GetMapping(value="/ver/{id}")
    public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash){

          Cliente cliente = clienteService.fetchByIdWithFacturas(id);
        //Cliente cliente = clienteService.unCliente(id);de esta forma solo cuando la consulta es a una tabla o 2
        if(cliente==null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente:" + cliente.getNombre());
        return "ver";
    }

    @RequestMapping(value="listar", method=RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest =  PageRequest.of(page, 5);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @RequestMapping(value="/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de CLiente");
        return "form";
    }

    @RequestMapping(value="/form/{id}")//metodo editar
    public String editar(@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = null;
        if(id>0) {
            cliente = clienteService.unCliente(id);
            if (cliente==null){
                flash.addFlashAttribute("success", "El Id del cliente en la BD");
                return "redirect:/listar";
            }
        }else {
            flash.addFlashAttribute("error", "EL Id del cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    /*ESTE METODO PARA SUBIR IMAGEN Y REGISTRAR CLIENTE*/
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
                          RedirectAttributes flash, SessionStatus status) {
        if(result.hasErrors()){
            model.addAttribute("titulo", "Formulario de cliente");
            return "form";
        }

        if(!foto.isEmpty()){
            /*ESTA PARTE ES PARA ELIMINAR LA FOTO*/
            if(cliente.getId() !=null
                    && cliente.getId() > 0
                    && cliente.getFoto()!=null
                    && cliente.getFoto().length() > 0){

                uploadFileService.delete(cliente.getFoto());//aqui el ID de la foto seria seria "cliente.getFoto()"
                /*HASTA AQUI PARA ELIMINAR LA FOTO*/
            }

            String uniqueFilename = null;
            try{
                //UNA VEZ QUE ELIMINAMOS LA FOTO AQUI ESTAMOS MOVIENDO LA IAMGEN
                uniqueFilename = uploadFileService.copy(foto);
            }catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info","Has subido correctamente '" + uniqueFilename+ "'");
            cliente.setFoto(uniqueFilename);
            //Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
            //String rootPath = directorioRecursos.toFile().getAbsolutePath();
            //String rootPath = "C://Temp//uploads"; AQUI ESTAMOS CREANDO UNA RUTA EXTERNA AL PROYECTO ESTO FUNCIONA CON EL MvcConfig.java

        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito";

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:listar";
    }
    /*AQUI TERMINA METODO GUARDARCLIENTE*/

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
        if(id>0){
            Cliente cliente = clienteService.unCliente(id);
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");

            if(uploadFileService.delete(cliente.getFoto())){
                flash.addFlashAttribute("info", "Foto" + cliente.getFoto() + "eliminado con exito");
            }
        }
        return "redirect:/listar";
    }
}
