
package com.mayy5.admin.service.pojo.regionCode;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "region_cd",
    "sido_cd",
    "sgg_cd",
    "umd_cd",
    "ri_cd",
    "locatjumin_cd",
    "locatjijuk_cd",
    "locatadd_nm",
    "locat_order",
    "locat_rm",
    "locathigh_cd",
    "locallow_nm",
    "adpt_de"
})
public class Row implements Serializable {

    @JsonProperty("region_cd")
    private String regionCd;
    @JsonProperty("sido_cd")
    private String sidoCd;
    @JsonProperty("sgg_cd")
    private String sggCd;
    @JsonProperty("umd_cd")
    private String umdCd;
    @JsonProperty("ri_cd")
    private String riCd;
    @JsonProperty("locatjumin_cd")
    private String locatjuminCd;
    @JsonProperty("locatjijuk_cd")
    private String locatjijukCd;
    @JsonProperty("locatadd_nm")
    private String locataddNm;
    @JsonProperty("locat_order")
    private String locatOrder;
    @JsonProperty("locat_rm")
    private String locatRm;
    @JsonProperty("locathigh_cd")
    private String locathighCd;
    @JsonProperty("locallow_nm")
    private String locallowNm;
    @JsonProperty("adpt_de")
    private String adptDe;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public String getRegionCd() {
        return regionCd;
    }

    @JsonAnySetter
    public void setRegionCd(String regionCd) {
        this.regionCd = regionCd;
    }

    @JsonAnyGetter
    public String getSidoCd() {
        return sidoCd;
    }

    @JsonAnySetter
    public void setSidoCd(String sidoCd) {
        this.sidoCd = sidoCd;
    }

    @JsonAnyGetter
    public String getSggCd() {
        return sggCd;
    }

    @JsonAnySetter
    public void setSggCd(String sggCd) {
        this.sggCd = sggCd;
    }

    @JsonAnyGetter
    public String getUmdCd() {
        return umdCd;
    }

    @JsonAnySetter
    public void setUmdCd(String umdCd) {
        this.umdCd = umdCd;
    }

    @JsonAnyGetter
    public String getRiCd() {
        return riCd;
    }

    @JsonAnySetter
    public void setRiCd(String riCd) {
        this.riCd = riCd;
    }

    @JsonAnyGetter
    public String getLocatjuminCd() {
        return locatjuminCd;
    }

    @JsonAnySetter
    public void setLocatjuminCd(String locatjuminCd) {
        this.locatjuminCd = locatjuminCd;
    }

    @JsonAnyGetter
    public String getLocatjijukCd() {
        return locatjijukCd;
    }

    @JsonAnySetter
    public void setLocatjijukCd(String locatjijukCd) {
        this.locatjijukCd = locatjijukCd;
    }

    @JsonAnyGetter
    public String getLocataddNm() {
        return locataddNm;
    }

    @JsonAnySetter
    public void setLocataddNm(String locataddNm) {
        this.locataddNm = locataddNm;
    }

    @JsonAnyGetter
    public String getLocatOrder() {
        return locatOrder;
    }

    @JsonAnySetter
    public void setLocatOrder(String locatOrder) {
        this.locatOrder = locatOrder;
    }

    @JsonAnyGetter
    public String getLocatRm() {
        return locatRm;
    }

    @JsonAnySetter
    public void setLocatRm(String locatRm) {
        this.locatRm = locatRm;
    }

    @JsonAnyGetter
    public String getLocathighCd() {
        return locathighCd;
    }

    @JsonAnySetter
    public void setLocathighCd(String locathighCd) {
        this.locathighCd = locathighCd;
    }

    @JsonAnyGetter
    public String getLocallowNm() {
        return locallowNm;
    }

    @JsonAnySetter
    public void setLocallowNm(String locallowNm) {
        this.locallowNm = locallowNm;
    }

    @JsonAnyGetter
    public String getAdptDe() {
        return adptDe;
    }

    @JsonAnySetter
    public void setAdptDe(String adptDe) {
        this.adptDe = adptDe;
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
