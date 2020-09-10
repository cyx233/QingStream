package com.java.tanghao;

public class Covid{
    private CovidProperties properties;
    private CovidRelation[] relations;

    public void setProperties(CovidProperties properties) {
        this.properties = properties;
    }

    public void setRelations(CovidRelation[] relations) {
        this.relations = relations;
    }

    public CovidProperties getProperties() {
        return properties;
    }

    public CovidRelation[] getRelations() {
        return relations;
    }
}
