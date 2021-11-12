
package com.libreriaWeb.egg.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/")
public class PortalControlador {
    
    @GetMapping("/") 
    public String index(){ 
        return "index.html"; 
    } 
    
    @GetMapping("/autores") 
    public String autores(){ 
        return "autores"; 
    }
    
    @GetMapping("/editoriales") 
    public String editoriales(){ 
        return "editoriales"; 
    }
    
    @GetMapping("/libros") 
    public String libros(){ 
        return "libros"; 
    }
     
}
