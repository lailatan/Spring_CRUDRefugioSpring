package com.example.spring_crudrefugiospring.controllers;

import com.example.spring_crudrefugiospring.entities.Animal;
import com.example.spring_crudrefugiospring.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("animales")
public class AnimalController {
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //PathVariable /buscar/2 (GET)
    //RequestParamen buscar?id=2 (GET)
    //RequestParamen (POST)

    @GetMapping(value={"animales","animales/guardar"})
    public String getAnimales(Model modelo){
        modelo.addAttribute("animales", animalService.getAnimales());
        modelo.addAttribute("colores", animalService.getColores());
        return "ver-animales";
    }

    @GetMapping("animales/nombre/{nombre}")
    public String getAnimalesByNombreContaining(Model modelo, @PathVariable String nombre){
        modelo.addAttribute("animales", animalService.getAnimalesByNombreContaining(nombre));
        modelo.addAttribute("colores", animalService.getColores());
        return "ver-animales";
    }

    @GetMapping("animales/color/{color}")
    public String getAnimalesByColorEquals(Model modelo, @PathVariable String color){
        modelo.addAttribute("animales", animalService.getAnimalesByColorEquals(color));
        modelo.addAttribute("colores", animalService.getColores());
        return "ver-animales";
    }

    @GetMapping("animales/edad/{edadDesde}/{edadHasta}")
    public String getAnimalesByEdadBetween(Model modelo, @PathVariable String edadDesde, @PathVariable String edadHasta){
        modelo.addAttribute("animales", animalService.getAnimalesByEdadBetween(edadDesde,edadHasta));
        modelo.addAttribute("colores", animalService.getColores());
        return "ver-animales";

    }

    @GetMapping("animales/id/{id}")
    public String getAnimalById(Model modelo, @PathVariable String id){
        modelo.addAttribute("animales", (animalService.getAnimalById(id)));
        modelo.addAttribute("colores", animalService.getColores());
        return "ver-animales";
    }

    @GetMapping("animales/nuevo")
    public String nuevoAnimal(Model modelo){
        modelo.addAttribute("animal", new Animal());
        return "guardar-animal";
    }

    @GetMapping("animales/editar/{id}")
    public String editarAnimal(Model modelo, @PathVariable String id){
        modelo.addAttribute("animal", animalService.getAnimalById(id));
        return "guardar-animal";
    }

    @PostMapping("animales/guardar")
    public String guardarAnimal(@ModelAttribute Animal animal, Model modelo){
        //modelo.addAttribute("animal",animal);
        animalService.guardarAnimal(animal);
        return "redirect:/animales";
    }


    @DeleteMapping("animales/borrar/{id}")
    public String eliminarAnimal(Model modelo, @PathVariable String id){
        animalService.eliminarAnimalById(id);
        return "success";
    }


    @GetMapping("animales/buscar")
    public String buscar(Model modelo, @RequestParam Optional<String> nombreORid, @RequestParam Optional<String> color, @RequestParam Optional<Integer> edad){
        if(color.isPresent() && !color.get().equals(("0")))
            return "redirect:/animales/color/"+color.get();
        if(edad.isPresent() && edad.get()>0)
            return "redirect:/animales/edad/0/"+edad.get();
        if(nombreORid.isPresent() && !nombreORid.get().isEmpty())
            try {
                Integer id = Integer.parseInt(nombreORid.get());
                return "redirect:/animales/id/" + id;
            }catch (NumberFormatException e){

                return "redirect:/animales/nombre/" + nombreORid.get();
            }
        return "redirect:/animales";

    }
}

