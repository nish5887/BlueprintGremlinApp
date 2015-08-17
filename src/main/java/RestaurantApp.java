/**
 * Created by nishitarao on 8/14/15.
 */
import com.tinkerpop.blueprints.*;
import org.apache.usergrid.drivers.blueprints.*;

import java.io.FileNotFoundException;

public class RestaurantApp {
    public static String filePath = "/Users/nishitarao/BlueprintGremlinApp/resources/usergrid.properties";

    public static void main(String[] args) throws FileNotFoundException {

        Graph usergrid = GraphFactory.open(filePath);
        Vertex v1 = usergrid.addVertex(null);
        Vertex v2 = usergrid.addVertex(null);
        Edge ev1 = usergrid.addEdge(null, v1, v2, "test1");
    }
}
