package org.neo4j.tushare;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.reference.ConceptFields;
import com.tushare.constant.stock.basic.StockBasicFields;
import com.tushare.core.api.TushareStockDataService;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Procedure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateStockNode {

    // This field declares that we need a GraphDatabaseService
    // as context when any procedure in this class is invoked
    @Context
    public GraphDatabaseService db;

    // This gives us a log instance that outputs messages to the
    // standard log, normally found under `data/log/console.log`
    @Context
    public Log logger;



    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Procedure(value = "tushare.createAndUpdateStockNodes")
    public void createAndUpdateStockNodes(){
        TushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        try (Transaction transaction = db.beginTx()){

            ApiResponse apiResponse = tushareStockDataService.stockCompany();
            Map<String, Integer> fieldMap = new HashMap<>();
            List<String> fields = apiResponse.getFields();
            Integer position = 0;
            for(String field : fields){
                fieldMap.put(field, position);
                position++;
            }

            List<String[]> items = apiResponse.getItems();

            for(String[] item: items){
                String tsCode = item[fieldMap.get(StockBasicFields.TS_CODE)];
            }
            transaction.success();
        } catch (TushareException e) {
            logger.error("Tushare Exception : {}", e);
        }

    }

    @Procedure(value = "tushare.createAndUpdateConceptNodes")
    public void createAndUpdateConceptNodes(){
        TushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        try (Transaction transaction = db.beginTx()){
            ApiResponse apiResponse = tushareStockDataService.concept();
            Map<String, Integer> fieldMap = new HashMap<>();
            List<String> fields = apiResponse.getFields();
            Integer position = 0;
            for(String field : fields){
                fieldMap.put(field, position);
                position++;
            }

            List<String[]> items = apiResponse.getItems();

            for(String[] item: items){
                String code = item[fieldMap.get(ConceptFields.CODE)];
            }
            transaction.success();
        } catch (TushareException e) {
            logger.error("Tushare Exception : {}", e);
        }

    }

}
