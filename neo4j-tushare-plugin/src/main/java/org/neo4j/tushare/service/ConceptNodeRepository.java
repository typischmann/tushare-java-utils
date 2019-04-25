package org.neo4j.tushare.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.helpers.collection.Iterators;

public class ConceptNodeRepository {

    private GraphDatabaseService graphDatabaseService;

    public ConceptNodeRepository(GraphDatabaseService graphDatabaseService){
        this.graphDatabaseService = graphDatabaseService;
    }

}
