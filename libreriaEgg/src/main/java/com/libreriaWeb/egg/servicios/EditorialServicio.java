
package com.libreriaWeb.egg.servicios;

import com.libreriaWeb.egg.entidades.Editorial;
import com.libreriaWeb.egg.errores.ErrorServicio;
import com.libreriaWeb.egg.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void guardar(String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
        
    }
    
    @Transactional
    public void modificar(String id, String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el editorial solicitado");
        }
        
    }
    
    @Transactional
    public void darDeBaja(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el editorial solicitado");
        }
        
    }
    
    @Transactional
    public void darDeAlta(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el editorial solicitado");
        }
        
    }
    
    @Transactional(readOnly = true)
    public Editorial buscarId(String id){
        return editorialRepositorio.getOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarTodos(){
        return editorialRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarActivos(){
        return editorialRepositorio.buscarActivos();
    }
    
    private void validar(String nombre) throws ErrorServicio{  
        
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("No se puede guardar un editorial sin nombre");
        } 
        
    }
}
