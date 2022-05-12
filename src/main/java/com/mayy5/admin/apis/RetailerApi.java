package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.RetailerRequest;
import com.mayy5.admin.model.res.RetailerResponse;
import com.mayy5.admin.model.res.UserRTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Retailer", tags = SwaggerApiTag.RETAILER)
@Validated
public interface RetailerApi {

    @ApiOperation(value = "장원 등록 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PostMapping(path = "/v1/retailer", consumes = "application/json")
    ResponseEntity<RetailerResponse> createRetailer(@RequestBody @Valid RetailerRequest retailerRequest);

    @ApiOperation(value = "장원 리스트 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/retailers/{userId}")
    List<ResponseEntity<RetailerResponse>> getRetailers(@PathVariable String userId);

    @ApiOperation(value = "장원 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/retailer/{retailerId}")
    ResponseEntity<RetailerResponse> getRetailer(@PathVariable Long retailerId);

    @ApiOperation(value = "장원 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PatchMapping(path = "/v1/retailer/{retailerId}", consumes = "application/json")
    ResponseEntity<RetailerResponse> updateRetailer(@PathVariable Long retailerId,
                                                    @RequestBody @Valid RetailerRequest retailerRequest);

    @ApiOperation(value = "등록 장원 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserRTO.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @DeleteMapping(path = "/v1/retailer/{retailerId}")
    ResponseEntity<UserRTO> deleteRetailer(@PathVariable Long retailerId);
}
