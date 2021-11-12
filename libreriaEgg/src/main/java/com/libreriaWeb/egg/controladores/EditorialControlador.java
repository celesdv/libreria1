package com.libreriaWeb.egg.controladores;

import com.libreriaWeb.egg.entidades.Editorial;
import com.libreriaWeb.egg.servicios.EditorialServicio;
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
@RequestMapping("/editoriales")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/cargar")
    public String formulario() {
        return "cargarEditoriales";
    }
    
    @PostMapping("/cargar")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {

        try {
            editorialServicio.guardar(nombre);

            modelo.put("exito", "Registro exitoso");
            return "redirect:/editoriales/lista";

        } catch (Exception e) {

            modelo.put("error", "Falto algun dato");
            return "cargarEditoriales";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Editorial> editorialLista = editorialServicio.listarTodos();

        modelo.addAttribute("editoriales", editorialLista);

        return "listarEditoriales";
    }
    
     @GetMapping("/modificar/{id}") //PATHVARIABLE
	public String modificar(@PathVariable String id, ModelMap modelo ) {
            
		modelo.put("editorial", editorialServicio.buscarId(id));
            
		return "modificarEditoriales";
	}

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {

        try {
            editorialServicio.modificar(id, nombre);
            modelo.put("exito", "Modificacion exitosa");

            return "redirect:/editoriales/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "redirect:/edutoriales/lista";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            editorialServicio.darDeBaja(id);
            return "redirect:/editoriales/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            editorialServicio.darDeAlta(id);
            return "redirect:/editoriales/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
