import com.tinkerpop.blueprints.*;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.iterators.SingleIterator;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GremlinBlueprint {
    static Vertex person1,person2,person3,person4,person5,person6,restaurant1,restaurant2,restaurant3,restaurant4,restaurant5,restaurant6;
    static Edge link1,link2,link3,link4,link5;


    //Creates a graph with 6 people and 6 restaurants, and creates a few edges
    public static void CreateGraph(){
        person1 = GlobalVars.graph.addVertex(1);
        person2 = GlobalVars.graph.addVertex(2);
        person3 = GlobalVars.graph.addVertex(3);
        person4 = GlobalVars.graph.addVertex(4);
        person5 = GlobalVars.graph.addVertex(5);
        person6 = GlobalVars.graph.addVertex(6);
        restaurant1 = GlobalVars.graph.addVertex(7);
        restaurant2 = GlobalVars.graph.addVertex(8);
        restaurant3 = GlobalVars.graph.addVertex(9);
        restaurant4 = GlobalVars.graph.addVertex(10);
        restaurant5 = GlobalVars.graph.addVertex(11);
        restaurant6 = GlobalVars.graph.addVertex(12);

        person1.setProperty("name","A");
        person2.setProperty("name","B");
        person3.setProperty("name","C");
        person4.setProperty("name","D");
        person5.setProperty("name","E");
        person6.setProperty("name","F");
        restaurant1.setProperty("name","R1");
        restaurant2.setProperty("name","R2");
        restaurant3.setProperty("name","R3");
        restaurant4.setProperty("name","R4");
        restaurant5.setProperty("name","R5");
        restaurant6.setProperty("name","R6");


        link1 = GlobalVars.graph.addEdge(null, person1, restaurant1, "Visits");
        link1 = GlobalVars.graph.addEdge(null, person1, restaurant2, "Visits");
        link2 = GlobalVars.graph.addEdge(null, person2, restaurant1, "Visits");
        link3 = GlobalVars.graph.addEdge(null, person3, restaurant1, "Visits");
        link4 = GlobalVars.graph.addEdge(null, person2, person1, "Follows");
        link5 = GlobalVars.graph.addEdge(null, person3, person2, "FollowedBy");


    }

    public static void TraverseGraph(String namePerson) throws ScriptException {

        //Suggestions of whom to follow are given to the 'namePerson'
        // It depends on 1) People who visit the same restaurant as him/her 2) People who they follow follow someone else

        List<String> AlreadyFollowing = new ArrayList<String>();
        GremlinPipeline GraphForPeopleAlreadyBeingFollowed = new GremlinPipeline(GlobalVars.graph);
        GraphForPeopleAlreadyBeingFollowed.V("name", namePerson).out("Follows");
        for (Object nameFollowed: GraphForPeopleAlreadyBeingFollowed.property("name")){
            AlreadyFollowing.add(nameFollowed.toString());

        }

        GremlinPipeline GraphPipeForRestaurantsVisitors = new GremlinPipeline(GlobalVars.graph);
//        pipe.V("name", "John");
        GraphPipeForRestaurantsVisitors.V("name",namePerson).out("Visits");

        for (Object nameRes :GraphPipeForRestaurantsVisitors.property("name")) {
            System.out.println(nameRes); //prints the restaurant name the person visited
            GremlinPipeline VisitsPipe = new GremlinPipeline(GlobalVars.graph).V("name", (String) nameRes).in("Visits");
            for (Object nameP : VisitsPipe.property("name")) {
                if(!nameP.equals(namePerson) && !AlreadyFollowing.contains(nameP.toString())){
                    System.out.println(namePerson + " should follow " + nameP);}
            }

        }



        GremlinPipeline GraphPipeForFollowed = new GremlinPipeline(GlobalVars.graph);
        GraphPipeForFollowed.V("name", namePerson).out("Follows");

        for (Object nameFollows: GraphPipeForFollowed.property("name")) {
            System.out.println(nameFollows); //prints the person name who is following namePerson
            GremlinPipeline FollowsPipe = new GremlinPipeline(GlobalVars.graph).V("name", (String) nameFollows).in("Follows");
            for (Object nameP : FollowsPipe.property("name")) {
                if(!nameP.equals(namePerson) && !AlreadyFollowing.contains(nameP.toString()) ){
                    System.out.println(namePerson + " should follow " + nameP);}
            }

        }


    }
    public static void main( String[] args ) throws ScriptException {
        String name = "A";
        //CreateGraph();
        TraverseGraph(name);
        GlobalVars.graph.shutdown();

    }

}
