package org.neo4j.tushare;

import com.tushare.core.api.TushareStockDataService;
import com.tushare.core.impl.DefaultTushareStockDataService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Procedure;

public class UpdateStockNode {

    // This field declares that we need a GraphDatabaseService
    // as context when any procedure in this class is invoked
    @Context
    public GraphDatabaseService db;

    // This gives us a log instance that outputs messages to the
    // standard log, normally found under `data/log/console.log`
    @Context
    public Log log;



    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Procedure
    public void createAndUpdateStockNodes(){
        TushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);

    }

}
