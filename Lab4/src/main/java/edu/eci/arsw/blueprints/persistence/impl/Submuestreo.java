package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.persistence.Filtro;
@Component("Submuestreo")
public class Submuestreo implements Filtro{
    public Submuestreo(){
        
    }
    @Override
    public Set<Blueprint> aplicarFiltro(Set<Blueprint> blueprints){
        for (Blueprint blueprint : blueprints) {
            List<Point> puntos = blueprint.getPoints();
            List<Point> filtro = new ArrayList<>();
           for(int i = 0; i < puntos.size(); i++){
            if (i%2 == 0){
                filtro.add(puntos.get(i)); 
                
            }
           }
           
           blueprint.setPoints(filtro);
        }
        return blueprints;
    }
}
