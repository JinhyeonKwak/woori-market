
package com.mayy5.admin.service.pojo.regionCode;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "StanReginCd"
})
public class RegionCodeResponse implements Serializable {

    @JsonProperty("StanReginCd")
    private List<StanReginCd> stanReginCd = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public List<StanReginCd> getStanReginCd() {
        return stanReginCd;
    }

    @JsonAnySetter
    public void setStanReginCd(List<StanReginCd> stanReginCd) {
        this.stanReginCd = stanReginCd;
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
