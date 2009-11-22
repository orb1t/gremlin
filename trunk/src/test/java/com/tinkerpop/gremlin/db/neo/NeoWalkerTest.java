package com.tinkerpop.gremlin.db.neo;

import com.tinkerpop.gremlin.model.Vertex;
import com.tinkerpop.gremlin.model.Element;
import junit.framework.TestCase;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Transaction;
import org.neo4j.util.index.IndexService;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @version 0.1
 */
public class NeoWalkerTest extends TestCase {

    public void testJXPath() {
        GratefulNeoGraph gng = new GratefulNeoGraph();
        //gng.loadGratefulDeadGraph();
        IndexService index = gng.getIndex();
        NeoService neo = gng.getNeo();

        Transaction tx = neo.beginTx();
        try {

            Node node = index.getSingleNode(GratefulNeoGraph.NAME, "DARK STAR");
            System.out.println(node);
            Vertex vertex = new NeoVertex(node);
            System.out.println("Outedges size: " + vertex.getEdges(Element.Direction.OUT).size());
            //Evaluator eval = new Evaluator();
            //Iterator itty = eval.evaluate(vertex, "./outEdges[@label = 'SUNG_BY']/outVertex/inEdges[@label='WRITTEN_BY'][g:random(last())]/inVertex/@name");
            //Iterator itty = eval.evaluate(vertex, "./outEdges[@label='FOLLOWED_BY'][g:random(last())]/outVertex/name");

            /*int counter = 0;
            while (itty.hasNext()) {
                Object obj = itty.next();
                System.out.println(obj + "---" + obj.getClass());
                counter++;
            }
            System.out.println("Counter: " + counter);*/


            tx.success();

        } finally {
            tx.finish();
        }
        index.shutdown();
        neo.shutdown();

    }


}