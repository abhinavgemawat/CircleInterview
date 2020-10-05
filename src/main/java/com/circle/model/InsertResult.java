package com.circle.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertResult {
    private String action;
    private int oid;
    private int modified;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getModified() {
        return modified;
    }

    public void setModified(int modified) {
        this.modified = modified;
    }

    public InsertResult (String action, int oid, int mod) {
        this.action = action;
        this.oid = oid;
        this.modified = mod;
    }
    
}
