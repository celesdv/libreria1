
package com.libreriaWeb.egg.servicios;

import com.libreriaWeb.egg.entidades.Autor;
import com.libreriaWeb.egg.entidades.Editorial;
import com.libreriaWeb.egg.entidades.Libro;
import com.libreriaWeb.egg.errores.ErrorServicio;
import com.libreriaWeb.egg.repositorios.AutorRepositorio;
import com.libreriaWeb.egg.repositorios.EditorialRepositorio;
import com.libreriaWeb.egg.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void guardar(long isnb, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio{
        
        validar(isnb, titulo, anio, ejemplares, idAutor, idEditorial);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
        Libro libro = new Libro();
        libro.setIsnb(isnb);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestante(restantes(libro.getEjemplares(), libro.getEjemplaresPrestados()));
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAlta(true);
        
        libroRepositorio.save(libro);        
    }
    
    @Transactional
    public void modificar(String idLibro, long isnb, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio{
        
        validar(isnb, titulo, anio, ejemplares, idAutor, idEditorial);
                
        Autor autor = autorRepositorio.findById(idAutor).get();
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            
            int prestados; 
            
            if(libro.getEjemplaresPrestados() == null){
                prestados = 0;
            } else {
                prestados = libro.getEjemplaresPrestados();
            }
            
            libro.setIsnb(isnb);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(prestados);
            libro.setEjemplaresRestante(restantes(libro.getEjemplares(), libro.getEjemplaresPrestados()));
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libroRepositorio.save(libro);
            
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado");
        }
    }
    
    @Transactional
    public void darDeBaja(String id) throws ErrorServicio{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setAlta(false);
            
            libroRepositorio.save(libro);
            
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado");
        }
    }
    
     @Transactional
    public void darDeAlta(String id) throws ErrorServicio{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setAlta(true);
            
            libroRepositorio.save(libro);
            
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado");
        }
    }
    
    @Transactional(readOnly = true)
    public Libro buscarId(String id){
        return libroRepositorio.getOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> listarTodos(){
        return libroRepositorio.findAll();
    }
    
    private void validar(long isnb, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio{
        
        if(titulo == null || titulo.isEmpty()){
            throw new ErrorServicio("Debe ingresar un nombre para el libro");
        }
        if(isnb <= 0){
            throw new ErrorServicio("Debe ingresar un ISNB para el libro");
        }
        if(anio <= 0){
            throw new ErrorServicio("Debe ingresar un año de publicación para el libro");
        }
        if(ejemplares <= 0){
            throw new ErrorServicio("Debe ingresar una cantidad de dejemplares del libro");
        }
        if(idEditorial == null || idEditorial.isEmpty()){
            throw new ErrorServicio("Debe ingresar una identificacion para la editorial");
        }
        if(idAutor == null || idAutor.isEmpty()){
            throw new ErrorServicio("Debe ingresar una identificacion para el autor");
        }
    }
    
    private Integer restantes(Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio{
        
        if(ejemplares <= 0){
            throw new ErrorServicio("Debe ingresar una cantidad de dejemplares del libro");
        } else {
            return ejemplares - ejemplaresPrestados;
        }        
        
    }
    
}
