package com.mayy5.admin.controller;

import com.mayy5.admin.apis.RetailerApi;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.RetailerRequest;
import com.mayy5.admin.model.res.RetailerResponse;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.service.RetailerService;
import com.mayy5.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RetailerController implements RetailerApi {

    private final RetailerService retailerService;
    private final UserService userService;
    private final RetailerMapper retailerMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<RetailerResponse> createRetailer(@RequestBody @Valid RetailerRequest retailerRequest) {
        Retailer input = retailerMapper.toEntity(retailerRequest);
        User user = userService.getUser(retailerRequest.getUserId());
        input.setUser(user);
        Retailer retailer = retailerService.createRetailer(input);
        return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<RetailerResponse>> getRetailers(@PathVariable String userId) {
        User user = userService.getUser(userId);
        List<Retailer> retailerList = user.getRetailerList();
        List<ResponseEntity<RetailerResponse>> responseEntities = retailerList.stream()
                .map(retailer -> new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public ResponseEntity<RetailerResponse> getRetailer(@PathVariable Long retailerId) {
        Retailer retailer = retailerService.getRetailer(retailerId);
        return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RetailerResponse> updateRetailer(@PathVariable Long retailerId,
                                                           @RequestBody @Valid RetailerRequest retailerRequest) {
        Retailer retailer = retailerService.getRetailer(retailerId);
        retailerMapper.update(retailerRequest, retailer);
        Retailer updateRetailer = retailerService.updateRetailer(retailer);
        return new ResponseEntity<>(retailerMapper.toDto(updateRetailer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRTO> deleteRetailer(@PathVariable Long retailerId) {
        User user = retailerService.getRetailer(retailerId).getUser();
        retailerService.deleteRetailer(retailerId);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
}
