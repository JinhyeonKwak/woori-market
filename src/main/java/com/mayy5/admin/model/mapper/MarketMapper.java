package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketSchedule;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

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

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketMapper {

    MarketMapper marketMapper = Mappers.getMapper(MarketMapper.class);

    default Market toEntity(MarketCreateRequestDto dto) throws IOException, ParseException {

        String regionCode = getRegionCode(dto.getLocationAddress());
        Map<String, String> latLng = getLatLng(dto.getLocationAddress());

        Market market = Market.builder()
                .locationAddress(dto.getLocationAddress())
                .detailAddress(dto.getDetailAddress())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .marketDay(dto.getMarketDay())
                .regionCode(regionCode)
                .latitude(latLng.get("latitude"))
                .longitude(latLng.get("longitude"))
                .build();

        return market;
    }

    @Mapping(source = "id", target = "marketId")
    MarketResponseDto toMarketResponse(Market market);

    void update(MarketUpdateRequestDto marketRequest, @MappingTarget Market market);

    @Mapping(source = "marketRetailer.retailer.id", target = "retailerId")
    @Mapping(source = "marketRetailer.market.id", target = "marketId")
    ScheduleResponseDto toScheduleResponse(MarketSchedule marketSchedule);


    //TODO 서비스 전환 지도서비스
    private Map<String, String> getLatLng(String location) throws IOException, ParseException {
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
        JSONObject address = (JSONObject) addresses.get(0);
        String lng = (String) address.get("x");
        String lat = (String) address.get("y");

        HashMap<String, String> latLng = new HashMap<>();
        latLng.put("latitude", lat);
        latLng.put("longitude", lng);
        return latLng;
    }

    private String getRegionCode(String location) throws IOException, ParseException {

        String[] locationArr = location.split("\\s");
        String[] tmp = Arrays.copyOfRange(locationArr, 0, locationArr.length - 1);
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

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(result);
        JSONArray stanReginCd = (JSONArray) response.get("StanReginCd");
        JSONObject row = (JSONObject) stanReginCd.get(1);
        JSONArray rowArr = (JSONArray) row.get("row");
        JSONObject body = (JSONObject) rowArr.get(0);
        String regionCode = (String) body.get("region_cd");
        return regionCode;
    }
}
