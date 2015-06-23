import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphFactory;
import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ayeshadastagiri on 6/18/15.
 */
public class GlobalVars {

    public enum nodeType{
        PEOPLE,
        RESTAURANT
    }
    static Graph graph = GraphFactory.open("src/main/resources/graph.properties");

    static HashMap<String,HashMap<String, List<Vertex>>> People = new HashMap<String,HashMap<String, List<Vertex>> >();
    static HashMap<String,HashMap<String, List<Vertex>>> Restaurants = new HashMap<String,HashMap<String, List<Vertex>>>();

}
