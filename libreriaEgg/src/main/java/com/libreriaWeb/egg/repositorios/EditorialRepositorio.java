
package com.libreriaWeb.egg.repositorios;

import com.libreriaWeb.egg.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT c FROM Editorial c WHERE c.alta = true ")
    public List<Editorial> buscarActivos();
    
}
