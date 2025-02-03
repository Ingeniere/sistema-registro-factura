package com.prueba.datos.app.controllers;

import com.prueba.datos.app.models.entity.Cliente;
import com.prueba.datos.app.models.entity.Factura;
import com.prueba.datos.app.models.entity.ItemFactura;
import com.prueba.datos.app.models.entity.Producto;
import com.prueba.datos.app.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
    Model model,
    RedirectAttributes flash){

          Factura factura = clienteService.fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(id);// esta forma cuando son muchas tablas
        //Factura factura = clienteService.findFacturaById(id);de esta forma solo cuando la consulta es a una tabla o 2

        if (factura==null){
            flash.addFlashAttribute("error","La factura bi existe en la BD!");
            return "redirect:/listar";
        }
        model.addAttribute("factura",factura);
        model.addAttribute("titulo","Factura :".concat(factura.getDescripcion()));
        return "factura/ver";
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId,
                        Map<String, Object> model,
                        RedirectAttributes flash){
        Cliente cliente = clienteService.unCliente(clienteId);
        if(cliente==null){
            flash.addFlashAttribute("error","El cliente no existe en la BD de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura",factura);
        model.put("titulo", "Crear factura");

        return "factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return clienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardarFactura(@Valid Factura factura,
             BindingResult result,
             Model model,
             @RequestParam(name = "item_id[]", required = false) Long[] itemId,
             @RequestParam(name = "cantidad[]",required = false) Integer[] cantidad,
             RedirectAttributes flash,
             SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Crear Factura");
            return "factura/form";
        }

        if(itemId == null || itemId.length==0){
            model.addAttribute("titulo","Crear Factura");
            model.addAttribute("error","Error: La factura no puede tener lineas");
            return "factura/form";
        }

        for(int i=0; i< itemId.length; i++){
            Producto producto = clienteService.buscarProductoPorId(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.adicionarItemFactura(linea);

            log.info("ID:" + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }
       clienteService.guardarFactura(factura);

        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con exito");
        return "redirect:/ver/" + factura.getCliente().getId();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        Factura factura = clienteService.findFacturaById(id);

        if(factura !=null){
            clienteService.eliminarFactura(id);
            flash.addFlashAttribute("success","Factura eliminada con exito!");
            return "redirect:/ver/" + factura.getCliente().getId();
        }
        flash.addFlashAttribute("error","La factura no existe en la base de datos, nose pudo eliminar");
        return "redirect:/listar";
    }
}
