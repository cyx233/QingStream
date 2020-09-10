package com.java.tanghao;

import java.util.HashMap;

public class Covid{
    private HashMap<String, String> properties;
    private CovidRelation[] relations;

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public void setRelations(CovidRelation[] relations) {
        this.relations = relations;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public CovidRelation[] getRelations() {
        return relations;
    }
}
