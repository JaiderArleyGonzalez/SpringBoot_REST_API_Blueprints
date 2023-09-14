package edu.eci.arsw.blueprints.persistence.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.Filtro;

@Component("Redundancia")
public class Redundancia implements Filtro{
    public Redundancia(){

    }
    @Override
    public Set<Blueprint> aplicarFiltro(Set<Blueprint> blueprints){
        
        for (Blueprint blueprint : blueprints) {
           List<Point> points = blueprint.getPoints();
           List<Point> newPoints = new ArrayList<>();
           for (int i = 0; i < points.size() - 1; i++) {
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);
            if(currentPoint.getX()!=nextPoint.getX() || currentPoint.getY()!=nextPoint.getY()){
                newPoints.add(currentPoint);
            }
            if(i==points.size()-2){
                newPoints.add(points.get(points.size()-1));
            }
            
        }
           
           blueprint.setPoints(newPoints);
        }
        
        return blueprints;
    }
}
