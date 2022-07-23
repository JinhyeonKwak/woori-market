package com.mayy5.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketMapService {

    public static String getRegionCode(String roadAddress) throws IOException, ParseException {

        JSONArray addresses = geocode(roadAddress);
        JSONObject address = (JSONObject) addresses.get(0);
        String jibunAddress = (String) address.get("jibunAddress");

        String[] locationArr = jibunAddress.split("\\s");
        String[] tmp;
        try {
            tmp = Arrays.copyOfRange(locationArr, 0, 4);
            int check = Integer.parseInt(String.valueOf(tmp[3].charAt(0)));
            tmp = Arrays.copyOfRange(locationArr, 0, 3);
        } catch (NumberFormatException e) {
            tmp = Arrays.copyOfRange(locationArr, 0, 4);
        }

        String sb = Arrays.stream(tmp)
                .map(s -> s + " ")
                .collect(Collectors.joining());
        String region = sb;

        String openApiUrl = "http://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList?ServiceKey=eRcBsQ1bX6mccg1jgFvvIl1dS4uvs455RS2zMyRdmmrsE%2FmcLfYdQVR5zih6A%2FGbf08iMsz8cODLVGy6HwscLg%3D%3D&type=json&pageNo=1&numOfRows=3&flag=Y&locatadd_nm=";
        URL url = new URL(openApiUrl + URLEncoder.encode(region, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String result = br.readLine();
        br.close();

        System.out.println("result = " + result);

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(result);
        JSONArray stanReginCd = (JSONArray) response.get("StanReginCd");
        JSONObject row = (JSONObject) stanReginCd.get(1);
        JSONArray rowArr = (JSONArray) row.get("row");
        JSONObject body = (JSONObject) rowArr.get(0);
        String regionCode = (String) body.get("region_cd");
        return regionCode;
    }

    public static Map<String, String> getLatLng(String roadAddress) throws IOException, ParseException {
        JSONArray addresses = geocode(roadAddress);
        JSONObject address = (JSONObject) addresses.get(0);
        String lng = (String) address.get("x");
        String lat = (String) address.get("y");

        HashMap<String, String> latLng = new HashMap<>();
        latLng.put("latitude", lat);
        latLng.put("longitude", lng);
        return latLng;
    }

    /**
     * Naver geocode api
     * @param location (도로명 주소)
     * @return addresses [도로명 주소, 지번 주소, 영어 주소, 경도, 위도, 검색 중심 좌표로부터의 거리, 주소를 이루는 요소들]
     * @throws IOException
     * @throws ParseException
     */
    private static JSONArray geocode(String location) throws IOException, ParseException {
        String geocoderApiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        URL url = new URL( geocoderApiUrl + "?query=" + URLEncoder.encode(location, "UTF-8"));

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "n8buzwmo5q");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", "uoSPNzUnT64eTcbSZT65yWDVWt5sNpLAE5agij2A");
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String result = br.readLine();
        br.close();

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(result);
        JSONArray addresses = (JSONArray) response.get("addresses");
        return addresses;
    }
}
