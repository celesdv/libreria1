package com.libreriaWeb.egg.controladores;

import com.libreriaWeb.egg.entidades.Autor;
import com.libreriaWeb.egg.servicios.AutorServicio;
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
@RequestMapping("/autores")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/cargar")
    public String formulario() {
        return "cargarAutores.html";
    }

    @PostMapping("/cargar")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {

        try {
            autorServicio.guardar(nombre);

            modelo.put("exito", "Registro exitoso");
            return "redirect:/autores/lista";

        } catch (Exception e) {

            modelo.put("error", "Falto algun dato");
            return "cargarAutores.html";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Autor> autorLista = autorServicio.listarTodos();

        modelo.addAttribute("autores", autorLista);

        return "listarAutores";
    }
    
    @GetMapping("/modificar/{id}") //PATHVARIABLE
	public String modificar(@PathVariable String id, ModelMap modelo ) {
            
		modelo.put("autor", autorServicio.buscarId(id));
            
		return "modificarAutores";
	}

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {

        try {
            autorServicio.modificar(id, nombre);
            modelo.put("exito", "Modificacion exitosa");

            return "redirect:/autores/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "redirect:/autores/lista";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            autorServicio.darDeBaja(id);
            return "redirect:/autores/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            autorServicio.darDeAlta(id);
            return "redirect:/autores/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
