package com.mayy5.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.security.OpenApiConfig;
import com.mayy5.admin.service.pojo.geocode.GeocodeResponse;
import com.mayy5.admin.service.pojo.regionCode.RegionCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class MarketMapService {

    @SneakyThrows
    public static String getRegionCode(String roadAddress) {

        ResponseEntity<GeocodeResponse> geocodeResponse = geocode(roadAddress);
        String jibunAddress = geocodeResponse.getBody().getAddresses().get(0).getJibunAddress();

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

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(OpenApiConfig.regionCodeApiUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
                .baseUrl(OpenApiConfig.regionCodeApiUrl)
                .uriBuilderFactory(factory)
                .build();

        String response = null;
        Instant start = Instant.now();
        Instant finish;
        Long timeElapsed = null;

        do {
            response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("ServiceKey", OpenApiConfig.regionCodeApiKey)
                            .queryParam("type", "json")
                            .queryParam("pageNo", 1)
                            .queryParam("numOfRows", 3)
                            .queryParam("flag", "Y")
                            .queryParam("locatadd_nm", URLEncoder.encode(region, StandardCharsets.UTF_8))
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toMillis();
            } while (response == OpenApiConfig.errorResult && timeElapsed < 5000);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RegionCodeResponse regionCodeResponse = objectMapper.readValue(response, RegionCodeResponse.class);
            String regionCode = regionCodeResponse.getStanReginCd().get(1).getRow().get(0).getRegionCd();
            return regionCode;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "GET REGION CODE");
        }

    }

    public static Map<String, String> getLatLng(String roadAddress) {
        ResponseEntity<GeocodeResponse> geocodeResponse = geocode(roadAddress);
        String lng = geocodeResponse.getBody().getAddresses().get(0).getX();
        String lat = geocodeResponse.getBody().getAddresses().get(0).getY();

        HashMap<String, String> latLng = new HashMap<>();
        latLng.put("latitude", lat);
        latLng.put("longitude", lng);
        return latLng;
    }

    private static ResponseEntity<GeocodeResponse> geocode(String location) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", OpenApiConfig.geocoderApiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", OpenApiConfig.geocoderApiKey);

        HttpEntity request = new HttpEntity(headers);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(OpenApiConfig.geocoderApiUrl)
                .queryParam("query", location)
                .build();

        ResponseEntity<GeocodeResponse> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                request,
                GeocodeResponse.class);

        return response;
    }

}
