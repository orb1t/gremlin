package com.tinkerpop.gremlin.db.sail.functions;

import com.tinkerpop.gremlin.XPathEvaluator;
import com.tinkerpop.gremlin.db.sail.SailGraph;
import com.tinkerpop.gremlin.model.Graph;
import com.tinkerpop.gremlin.statements.Tokens;
import junit.framework.TestCase;
import org.openrdf.sail.memory.MemoryStore;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class AddNamespaceFunctionTest extends TestCase {

    public void testAddNamespaceFunction() {
        Graph graph = new SailGraph(new MemoryStore());
        XPathEvaluator xe = new XPathEvaluator();
        xe.setVariable("$g", graph);
        assertEquals(xe.evaluateList("sail:ns($g, 'tg:marko')").get(0), "tg:marko");
        assertTrue((Boolean) xe.evaluateList("sail:add-ns($g,'tg','http://tinkerpop.com#')").get(0));
        assertEquals(xe.evaluateList("sail:ns($g, 'tg:marko')").get(0), "http://tinkerpop.com#marko");
        graph.shutdown();

    }

    public void testAddNamespaceFunctionGraphVariable() {
        Graph graph = new SailGraph(new MemoryStore());
        XPathEvaluator xe = new XPathEvaluator();
        xe.setVariable(Tokens.GRAPH_VARIABLE, graph);
        assertEquals(xe.evaluateList("sail:ns('tg:marko')").get(0), "tg:marko");
        assertTrue((Boolean) xe.evaluateList("sail:add-ns('tg','http://tinkerpop.com#')").get(0));
        assertEquals(xe.evaluateList("sail:ns('tg:marko')").get(0), "http://tinkerpop.com#marko");
        graph.shutdown();
    }
}
