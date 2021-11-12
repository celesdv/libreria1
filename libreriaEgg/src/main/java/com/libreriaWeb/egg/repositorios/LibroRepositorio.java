
package com.libreriaWeb.egg.repositorios;

import com.libreriaWeb.egg.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo")String titulo);
    
    @Query("SELECT c FROM Libro c WHERE c.isnb = :isnb")
    public Libro buscarPorIsnb(@Param("isnb")long isnb);
    
    @Query("SELECT c FROM Libro c WHERE c.autor.id = :id")
    public List<Libro> buscarLibroPorAutor(@Param("id") String id);
    
    @Query("SELECT c FROM Libro c WHERE c.editorial.id = :id")
    public List<Libro> buscarLibroPorEditorial(@Param("id") String id);
}
