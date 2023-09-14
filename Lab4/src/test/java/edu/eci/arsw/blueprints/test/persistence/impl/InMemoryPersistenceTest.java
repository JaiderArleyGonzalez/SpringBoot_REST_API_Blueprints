/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }


    @Test
    public void addNewBlueprintTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices servicio = ac.getBean(BlueprintsServices.class);
        Blueprint bp=new Blueprint("Miguel", "parque");
        try {
            servicio.addNewBlueprint(bp);
            assertEquals(servicio.getAllBlueprints().size(), 1);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBlueprintTest()  {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices servicio = ac.getBean(BlueprintsServices.class);
        Blueprint bp=new Blueprint("Jaider", "Hospital");
        try {
            servicio.addNewBlueprint(bp);
            assertEquals(servicio.getBlueprint("Jaider", "Hospital"), bp);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getBlueprintsByAuthorTest(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices servicio = ac.getBean(BlueprintsServices.class);
        Blueprint bp=new Blueprint("Jaider", "Hospital");
        Blueprint bc=new Blueprint("Jaider", "Bloque F");
        Blueprint ba=new Blueprint("Jaider", "Casa Jaider");
        
        try {
            servicio.addNewBlueprint(bp);
            servicio.addNewBlueprint(bc);
            servicio.addNewBlueprint(ba);
            assertEquals(servicio.getBlueprintsByAuthor("Jaider").size(), 3);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllBlueprintsTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices servicio = ac.getBean(BlueprintsServices.class);
        Blueprint bp=new Blueprint("Jaider", "Hospital");
        Blueprint bc=new Blueprint("Jaider", "Bloque F");
        Blueprint ba=new Blueprint("Jaider", "Casa Jaider");
        Blueprint bo=new Blueprint("Jaider", "Casa Miguel");
        
        try {
            servicio.addNewBlueprint(bp);
            servicio.addNewBlueprint(bc);
            servicio.addNewBlueprint(ba);
            servicio.addNewBlueprint(bo);
            assertEquals(servicio.getAllBlueprints().size(), 4);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void aplicarFiltroTest(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices ibpp = ac.getBean(BlueprintsServices.class);
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "parque",pts2);

        try{
            ibpp.addNewBlueprint(bp2);
            
        }
        catch (BlueprintNotFoundException ex){
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        Set<Blueprint> blueprints;
        
        blueprints = ibpp.aplicarFiltro();
        
        for (Blueprint blueprint : blueprints) {
            //Redundancia
            //assertEquals(2,blueprint.getPoints().size());
            
            //Submuestreo
            assertEquals(2,blueprint.getPoints().size());
            
        }
       
    }

    
}
