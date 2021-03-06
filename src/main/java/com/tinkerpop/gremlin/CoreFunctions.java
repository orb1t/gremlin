package com.tinkerpop.gremlin;

import com.tinkerpop.gremlin.statements.EvaluationException;
import org.apache.commons.jxpath.Function;
import org.apache.commons.jxpath.Functions;
import org.apache.commons.jxpath.JXPathContext;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class CoreFunctions implements Functions {

    private static Functions coreFunctions = JXPathContext.newContext(null).getFunctions();

    public Function getFunction(final String namespace, final String name, final Object[] parameters) {
        // TODO -- this doesn't seem to be getting called?!   ID IS BEING TREATED SEPERATELY
        // TODO -- Implement an identity and key manager and throw the EvaluationException 
        if (name.equals("id") || name.equals("key")) {
            throw EvaluationException.createException(FunctionHelper.makeFunctionName(namespace, name), EvaluationException.EvaluationErrorType.USE_OTHER);
        }
        return coreFunctions.getFunction(namespace, name, parameters);
    }

    public Set getUsedNamespaces() {
        return coreFunctions.getUsedNamespaces();
    }
}
