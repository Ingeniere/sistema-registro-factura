package com.prueba.datos.app.models.dao;

import com.prueba.datos.app.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

    //esta consulta se usa cuando hay muchas relaciones entre tablas, en este caso tenemos: factura que se relaciona con cliente
    //con producto e itemFactura
    @Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);
}
