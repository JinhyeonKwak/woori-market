package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketMemberApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import com.mayy5.admin.service.MarketAgentService;
import com.mayy5.admin.service.RetailerService;
import com.mayy5.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketMemberController implements MarketMemberApi {

	private final MarketAgentService marketAgentService;
	private final RetailerService retailerService;
	private final UserService userService;

	private final RetailerMapper retailerMapper;
	private final UserMapper userMapper;
	private final MarketAgentMapper marketAgentMapper;

	@Override
	public ResponseEntity<MarketAgentResponseDto> createMarketAgent(MarketAgentRequestDto marketAgentRequestDto) {

		try {
			MarketAgent input = marketAgentMapper.toEntity(marketAgentRequestDto);
			String loginUserId = userService.getLoginUserId();
			MarketAgent marketAgent = marketAgentService.createMarketAgent(loginUserId, input);
			return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "createMarketAgent");
		}
	}

	@Override
	public ResponseEntity<List<MarketAgentResponseDto>> getMarketAgents() {

		try {
			String loginUserId = userService.getLoginUserId();
			List<MarketAgent> marketAgentList = marketAgentService.getMarketAgentsByUserId(loginUserId);
			List<MarketAgentResponseDto> marketAgentResponseDtoList = marketAgentMapper.toDtoList(marketAgentList);
			return new ResponseEntity<>(marketAgentResponseDtoList, HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "getMarketAgents");
		}
	}

	@Override
	public ResponseEntity<MarketAgentResponseDto> getMarketAgent(Long marketAgentId) {

		try {
			MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
			return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "getMarketAgent");
		}
	}

	@Override
	public ResponseEntity<MarketAgentResponseDto> updateMarketAgent(MarketAgentRequestDto marketAgentRequestDto,
		Long marketAgentId) {

		try {
			MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
			marketAgentMapper.update(marketAgentRequestDto, marketAgent);
			MarketAgent updateMarketAgent = marketAgentService.updateMarketAgent(marketAgent);

			return new ResponseEntity<>(marketAgentMapper.toDto(updateMarketAgent), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "updateMarketAgent");
		}
	}

	@Override
	public ResponseEntity<RetailerResponseDto> createRetailer(RetailerRequestDto retailerRequest) {

		try {
			Retailer input = retailerMapper.toEntity(retailerRequest);
			Retailer retailer = retailerService.createRetailer(input);
			return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "createRetailer");
		}
	}

//	@Override
//	public ResponseEntity<List<RetailerResponseDto>> getRetailers() {
//
//		try {
//			String loginUserId = userService.getLoginUserId();
//			List<Retailer> retailerList = retailerService.getRetailersByUserId(loginUserId);
//			List<RetailerResponseDto> retailerResponseDtoList = retailerMapper.toDtoList(retailerList);
//			return new ResponseEntity<>(retailerResponseDtoList, HttpStatus.OK);
//		} catch (CommonException e) {
//			throw e;
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			throw new CommonException(BError.FAIL, "getRetailers");
//		}
//	}

	@Override
	public ResponseEntity<RetailerResponseDto> getRetailer(Long retailerId) {

		try {
			Retailer retailer = retailerService.getRetailer(retailerId);
			return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "getRetailer");
		}
	}

	@Override
	public ResponseEntity<RetailerResponseDto> updateRetailer(Long retailerId, RetailerRequestDto retailerRequest) {

		try {
			Retailer retailer = retailerService.getRetailer(retailerId);
			retailerMapper.update(retailerRequest, retailer);
			Retailer updateRetailer = retailerService.updateRetailer(retailer);
			return new ResponseEntity<>(retailerMapper.toDto(updateRetailer), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "updateRetailer");
		}
	}
}
