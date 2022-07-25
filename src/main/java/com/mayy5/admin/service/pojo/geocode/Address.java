package com.mayy5.admin.service.pojo.geocode;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "roadAddress",
        "jibunAddress",
        "englishAddress",
        "addressElements",
        "x",
        "y",
        "distance"
})
@Generated("jsonschema2pojo")
public class Address implements Serializable {

    @JsonProperty("roadAddress")
    private String roadAddress;
    @JsonProperty("jibunAddress")
    private String jibunAddress;
    @JsonProperty("englishAddress")
    private String englishAddress;
    @JsonProperty("addressElements")
    private List<AddressElement> addressElements = null;
    @JsonProperty("x")
    private String x;
    @JsonProperty("y")
    private String y;
    @JsonProperty("distance")
    private String distance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("roadAddress")
    public String getRoadAddress() {
        return roadAddress;
    }

    @JsonProperty("roadAddress")
    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    @JsonProperty("jibunAddress")
    public String getJibunAddress() {
        return jibunAddress;
    }

    @JsonProperty("jibunAddress")
    public void setJibunAddress(String jibunAddress) {
        this.jibunAddress = jibunAddress;
    }

    @JsonProperty("englishAddress")
    public String getEnglishAddress() {
        return englishAddress;
    }

    @JsonProperty("englishAddress")
    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }

    @JsonProperty("addressElements")
    public List<AddressElement> getAddressElements() {
        return addressElements;
    }

    @JsonProperty("addressElements")
    public void setAddressElements(List<AddressElement> addressElements) {
        this.addressElements = addressElements;
    }

    @JsonProperty("x")
    public String getX() {
        return x;
    }

    @JsonProperty("x")
    public void setX(String x) {
        this.x = x;
    }

    @JsonProperty("y")
    public String getY() {
        return y;
    }

    @JsonProperty("y")
    public void setY(String y) {
        this.y = y;
    }

    @JsonProperty("distance")
    public String getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(String distance) {
        this.distance = distance;
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