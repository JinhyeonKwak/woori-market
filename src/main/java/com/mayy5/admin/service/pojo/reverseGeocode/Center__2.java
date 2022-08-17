
package com.mayy5.admin.service.pojo.reverseGeocode;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crs",
    "x",
    "y"
})
@Generated("jsonschema2pojo")
public class Center__2 {

    @JsonProperty("crs")
    private String crs;
    @JsonProperty("x")
    private Double x;
    @JsonProperty("y")
    private Double y;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("crs")
    public String getCrs() {
        return crs;
    }

    @JsonProperty("crs")
    public void setCrs(String crs) {
        this.crs = crs;
    }

    @JsonProperty("x")
    public Double getX() {
        return x;
    }

    @JsonProperty("x")
    public void setX(Double x) {
        this.x = x;
    }

    @JsonProperty("y")
    public Double getY() {
        return y;
    }

    @JsonProperty("y")
    public void setY(Double y) {
        this.y = y;
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
