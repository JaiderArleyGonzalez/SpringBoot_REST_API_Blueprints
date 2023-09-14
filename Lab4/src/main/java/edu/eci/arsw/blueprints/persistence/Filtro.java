package edu.eci.arsw.blueprints.persistence;
import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.Set;


public interface Filtro {
    public Set<Blueprint> aplicarFiltro(Set<Blueprint> blueprints);
}
