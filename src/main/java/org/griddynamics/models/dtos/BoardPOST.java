package org.griddynamics.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardPOST {

    @JsonProperty("name")
    private String name;
    @JsonProperty("desc")
    private String desc;

    public BoardPOST(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
