
package com.mayy5.admin.service.pojo.regionCode;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resultCode",
    "resultMsg"
})
public class Result implements Serializable {

    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("resultMsg")
    private String resultMsg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public String getResultCode() {
        return resultCode;
    }

    @JsonAnySetter
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @JsonAnyGetter
    public String getResultMsg() {
        return resultMsg;
    }

    @JsonAnySetter
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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
