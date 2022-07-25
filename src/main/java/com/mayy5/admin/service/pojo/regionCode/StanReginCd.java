
package com.mayy5.admin.service.pojo.regionCode;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "head",
    "row"
})
public class StanReginCd implements Serializable {

    @JsonProperty("head")
    private List<Head> head = new ArrayList<>();
    @JsonProperty("row")
    private List<Row> row = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public List<Head> getHead() {
        return head;
    }

    @JsonAnySetter
    public void setHead(List<Head> head) {
        this.head = head;
    }

    @JsonAnySetter
    public List<Row> getRow() {
        return row;
    }

    @JsonAnySetter
    public void setRow(List<Row> row) {
        this.row = row;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
