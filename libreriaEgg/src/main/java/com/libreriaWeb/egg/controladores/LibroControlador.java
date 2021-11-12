package com.libreriaWeb.egg.controladores;

import com.libreriaWeb.egg.entidades.Autor;
import com.libreriaWeb.egg.entidades.Editorial;
import com.libreriaWeb.egg.entidades.Libro;
import com.libreriaWeb.egg.servicios.AutorServicio;
import com.libreriaWeb.egg.servicios.EditorialServicio;
import com.libreriaWeb.egg.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libros")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/cargar")
    public String formulario(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);

        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);

        return "cargarLibros";
    }

    @PostMapping("/cargar")
    public String guardar(ModelMap modelo, @RequestParam long isnb, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) {

        try {
            libroServicio.guardar(isnb, titulo, anio, ejemplares, idAutor, idEditorial);

            modelo.put("exito", "Registro exitoso");
            return "redirect:/libros/lista";

        } catch (Exception e) {

            modelo.put("error", "Falto algun dato");
            return "cargarLibros";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Libro> librosLista = libroServicio.listarTodos();

        modelo.addAttribute("libros", librosLista);

        return "listarLibros";
    }

    @GetMapping("/modificar/{id}") //PATHVARIABLE
    public String modificar(@PathVariable String id, ModelMap modelo) {
        
        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);

        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);

        modelo.put("libro", libroServicio.buscarId(id));

        return "modificarLibros";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam long isnb, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) {

        try {
            
            libroServicio.modificar(id, isnb, titulo, anio, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "Modificacion exitosa");

            return "redirect:/libros/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "redirect:/libros/lista";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            libroServicio.darDeBaja(id);
            return "redirect:/libros/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            libroServicio.darDeAlta(id);
            return "redirect:/libros/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
