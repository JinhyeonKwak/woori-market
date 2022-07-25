package com.mayy5.admin.security;

public class OpenApiConfig {

    public static final String geocoderApiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    public static final String geocoderApiKeyId = "n8buzwmo5q";
    public static final String geocoderApiKey = "uoSPNzUnT64eTcbSZT65yWDVWt5sNpLAE5agij2A";

    public static final String regionCodeApiUrl = "http://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList";
    public static final String regionCodeApiKey = "eRcBsQ1bX6mccg1jgFvvIl1dS4uvs455RS2zMyRdmmrsE%2FmcLfYdQVR5zih6A%2FGbf08iMsz8cODLVGy6HwscLg%3D%3D"; // encoded

    public static final String errorResult = "<OpenAPI_ServiceResponse>\n" +
            "\t<cmmMsgHeader>\n" +
            "\t\t<errMsg>SERVICE ERROR</errMsg>\n" +
            "\t\t<returnAuthMsg>HTTP_ERROR</returnAuthMsg>\n" +
            "\t\t<returnReasonCode>04</returnReasonCode>\n" +
            "\t</cmmMsgHeader>\n" +
            "</OpenAPI_ServiceResponse>";
}