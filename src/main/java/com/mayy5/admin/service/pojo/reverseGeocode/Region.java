
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
    "area0",
    "area1",
    "area2",
    "area3",
    "area4"
})
@Generated("jsonschema2pojo")
public class Region {

    @JsonProperty("area0")
    private Area0 area0;
    @JsonProperty("area1")
    private Area1 area1;
    @JsonProperty("area2")
    private Area2 area2;
    @JsonProperty("area3")
    private Area3 area3;
    @JsonProperty("area4")
    private Area4 area4;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("area0")
    public Area0 getArea0() {
        return area0;
    }

    @JsonProperty("area0")
    public void setArea0(Area0 area0) {
        this.area0 = area0;
    }

    @JsonProperty("area1")
    public Area1 getArea1() {
        return area1;
    }

    @JsonProperty("area1")
    public void setArea1(Area1 area1) {
        this.area1 = area1;
    }

    @JsonProperty("area2")
    public Area2 getArea2() {
        return area2;
    }

    @JsonProperty("area2")
    public void setArea2(Area2 area2) {
        this.area2 = area2;
    }

    @JsonProperty("area3")
    public Area3 getArea3() {
        return area3;
    }

    @JsonProperty("area3")
    public void setArea3(Area3 area3) {
        this.area3 = area3;
    }

    @JsonProperty("area4")
    public Area4 getArea4() {
        return area4;
    }

    @JsonProperty("area4")
    public void setArea4(Area4 area4) {
        this.area4 = area4;
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
