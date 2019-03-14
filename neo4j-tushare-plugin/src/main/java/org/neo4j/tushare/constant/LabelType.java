package org.neo4j.tushare.constant;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.Label;

public enum LabelType implements Label {
    STOCK("STOCK"),
    INDUSTRY("INDUSTRY"),
    CONCEPT("CONCEPT");

    private String name;
    private Label label;

    LabelType(String name){
        this.name = name;
        this.label = Label.label(this.name);
    }

    public String getName(){
        return name;
    }

    public Label getLabel(){
        return this.label;
    }



    public LabelType getLabelTypeByName(String name){
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for(LabelType labelType : LabelType.values()){
            if(labelType.getName().equals((name))){
                return labelType;
            }
        }

        return null;

    }

}
