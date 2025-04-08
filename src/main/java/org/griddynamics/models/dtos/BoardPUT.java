package org.griddynamics.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "BoardPUT{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        BoardPUT boardPUT = (BoardPUT) object;
        return Objects.equals(name, boardPUT.name) && Objects.equals(desc, boardPUT.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc);
    }
}
