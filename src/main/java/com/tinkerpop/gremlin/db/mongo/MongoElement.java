package com.tinkerpop.gremlin.db.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tinkerpop.gremlin.model.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class MongoElement implements Element {

    DBObject dbObject;
    MongoGraph graph;

    public MongoElement(DBObject dbObject, MongoGraph graph) {
        this.dbObject = dbObject;
        this.graph = graph;
    }


    public void saveDbObject() {
        if (this instanceof MongoVertex)
            this.graph.getVertexCollection().save(this.dbObject);
        else
            this.graph.getEdgeCollection().save(this.dbObject);
    }

    public void refreshDbObject() {
        DBObject queryObject = new BasicDBObject();
        queryObject.put(MongoGraph.ID, this.getId());
        if (this instanceof MongoVertex)
            this.dbObject = this.graph.getVertexCollection().findOne(queryObject);
        else
            this.dbObject = this.graph.getEdgeCollection().findOne(queryObject);
    }

    public Object getId() {
        return this.dbObject.get(MongoGraph.ID).toString();
    }

    public Set<String> getPropertyKeys() {
        this.refreshDbObject();
        Set<String> keys = new HashSet<String>();
        DBObject properties = (DBObject) this.dbObject.get(MongoGraph.PROPERTIES);
        keys.addAll(properties.keySet());
        return keys;
    }

    public Object getProperty(final String key) {
        this.refreshDbObject();
        return ((DBObject) this.dbObject.get(MongoGraph.PROPERTIES)).get(key);
    }

    public Object removeProperty(final String key) {
        Object retObject = ((DBObject) this.dbObject.get(MongoGraph.PROPERTIES)).removeField(key);
        this.saveDbObject();
        return retObject;
    }

    public void setProperty(final String key, final Object value) {
        DBObject properties = (DBObject) this.dbObject.get(MongoGraph.PROPERTIES);
        properties.put(key, value);
        this.dbObject.put(MongoGraph.PROPERTIES, properties);
        this.saveDbObject();
        this.graph.getIndex().put(key, value, this);
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    public DBObject getRawObject() {
        return this.dbObject;
    }
}
