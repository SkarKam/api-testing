package org.griddynamics.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

    private String id;
    private String name;
    private String desc;
    private Object descData;
    private boolean closed;
    private String idOrganization;
    private String idEnterprise;
    private boolean pinned;
    private String url;
    private String shortUrl;
}
