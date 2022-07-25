
package com.mayy5.admin.service.pojo.regionCode;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalCount",
    "numOfRows",
    "pageNo",
    "type",
    "RESULT"
})
public class Head implements Serializable {

    @JsonProperty("totalCount")
    private String totalCount;
    @JsonProperty("numOfRows")
    private String numOfRows;
    @JsonProperty("pageNo")
    private String pageNo;
    @JsonProperty("type")
    private String type;
    @JsonProperty("RESULT")
    private Result result;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public String getTotalCount() {
        return totalCount;
    }

    @JsonAnySetter
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @JsonAnyGetter
    public String getNumOfRows() {
        return numOfRows;
    }

    @JsonAnySetter
    public void setNumOfRows(String numOfRows) {
        this.numOfRows = numOfRows;
    }

    @JsonAnyGetter
    public String getPageNo() {
        return pageNo;
    }

    @JsonAnySetter
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    @JsonAnyGetter
    public String getType() {
        return type;
    }

    @JsonAnySetter
    public void setType(String type) {
        this.type = type;
    }

    @JsonAnyGetter
    public Result getResult() {
        return result;
    }

    @JsonAnySetter
    public void setResult(Result result) {
        this.result = result;
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
