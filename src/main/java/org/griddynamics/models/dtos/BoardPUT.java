package org.griddynamics.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardPUT {
    @JsonProperty("name")
    public String name;
    @JsonProperty("desc")
    public String desc;

    public BoardPUT(String name, String desc) {
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
