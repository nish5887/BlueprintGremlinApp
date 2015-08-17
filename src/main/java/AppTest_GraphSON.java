/**
 * Created by nishitarao on 8/13/15.
 */

    import com.fasterxml.jackson.databind.JsonNode;
    import com.tinkerpop.blueprints.*;
    import com.tinkerpop.blueprints.util.io.graphson.GraphSONMode;
    import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter;
    import org.json.simple.JSONObject;
    import org.json.simple.parser.JSONParser;
    import org.json.simple.JSONArray;
    import org.json.simple.parser.ParseException;

    import java.io.*;
    import java.util.Iterator;

    /**
     * Created by ApigeeCorporation on 6/29/15.
     */
    public class AppTest_GraphSON {


        public static String SLASH = "/";
        public static String filePath = "blueprints-usergrid-graph/src/main/resources/usergrid.properties";

        public static void main(String[] args) throws FileNotFoundException {

            Graph usergrid = GraphFactory.open(filePath);


            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream();
                GraphSONWriter.outputGraph(usergrid, bos, GraphSONMode.NORMAL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] bytes = bos.toByteArray();
            String jsonString = new String(bytes);

            JSONObject json_object = new JSONObject();
            JSONParser parser = new JSONParser();
            BufferedWriter out = null;
            FileWriter fileWrite = null;
            try {
                json_object = (JSONObject) parser.parse(jsonString);
                System.out.println(json_object);
                fileWrite = new FileWriter("blueprints-usergrid-graph/src/main/resources/test2.json");

                JSONArray vertices = (JSONArray) json_object.get("vertices");
                for (int i = 0; i < vertices.size(); i++) {
                    JSONObject vertex = (JSONObject) vertices.get(i);
                    vertex.remove("metadata");
                    vertex.remove("id");
                    if (vertex.get("connecting")!=null){
                        vertex.remove("connecting");
                    }
                    if (vertex.get("connections")!=null){
                        vertex.remove("connections");
                    }
                    vertex.remove("_type");
                    vertex.remove("propertyKeys");
                    String vertexID = vertex.get("_id").toString();
                    String[] parts = null;
                    parts = vertexID.split(SLASH);
                    String VertexType = parts[0];
                    if (VertexType.toLowerCase().equals("person")||VertexType.toLowerCase().equals("people")){
                        vertex.put("group",1);
                    }
                    else if (VertexType.toLowerCase().equals("restaurant")||VertexType.toLowerCase().equals("restaurants")){
                        vertex.put("group",2);
                    }
                    else {vertex.put("group", 0);}
                }

                JSONArray edges = (JSONArray) json_object.get("edges");
                for (int i = 0; i < edges.size(); i++) {
                    JSONObject edge = (JSONObject) edges.get(i);
                    edge.remove("connectionId");
                    edge.remove("_id");
                    edge.remove("label");
                    edge.remove("_type");
                    if (edge.get("_label").toString().toLowerCase().equals("visits")){
                        edge.put("group",0);}
                    else if (edge.get("_label").toString().toLowerCase().equals("follows")){
                        edge.put("group",1);}
                    else {
                        edge.put("group",2);}
                }

                JSONObject finalObject = new JSONObject();
                finalObject.put("nodes",vertices);
                finalObject.put("links", edges);

                out = new BufferedWriter(fileWrite);
                out.write(finalObject.toJSONString().replace("\\/","/"));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }


    }



