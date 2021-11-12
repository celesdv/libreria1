
package com.libreriaWeb.egg.servicios;

import com.libreriaWeb.egg.entidades.Autor;
import com.libreriaWeb.egg.errores.ErrorServicio;
import com.libreriaWeb.egg.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void guardar(String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        autorRepositorio.save(autor);
        
    }
    
    @Transactional
    public void modificar(String id, String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado");
        }
        
    }
    
    @Transactional
    public void darDeBaja(String id) throws ErrorServicio{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(false);
            
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado");
        }
        
    }
    
    @Transactional
    public void darDeAlta(String id) throws ErrorServicio{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(true);
            
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado");
        }
        
    }
    
    @Transactional(readOnly = true)
    public Autor buscarId(String id) {
        return autorRepositorio.getOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarActivos() {
        return autorRepositorio.buscarActivos();
    }
    
    private void validar(String nombre) throws ErrorServicio{  
        
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("No se puede guardar un autor sin nombre");
        } 
        
    }    
    
}
