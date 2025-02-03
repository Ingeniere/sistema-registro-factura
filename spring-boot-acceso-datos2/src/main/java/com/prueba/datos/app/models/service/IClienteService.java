package com.prueba.datos.app.models.service;

import com.prueba.datos.app.models.entity.Cliente;
import com.prueba.datos.app.models.entity.Factura;
import com.prueba.datos.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public Page<Cliente> findAll(Pageable pageable);

    public List<Cliente> findAll();//metodo para listar

    public void save(Cliente cliente);//metodo para guardar

    public Cliente unCliente(Long Id);//metodo para editar

    public Cliente fetchByIdWithFacturas(Long id);

    public void delete(Long id);//metodo para eliminar

    public List<Producto> findByNombre(String term);

    public void guardarFactura(Factura factura);

    public Producto buscarProductoPorId(Long id);

    public Factura findFacturaById(Long id);

    public void eliminarFactura(Long id);

    //este metodo es para la consulta de varias tablas
    public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id);
}
