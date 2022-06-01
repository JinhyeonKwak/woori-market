package com.mayy5.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.repository.MarketRetailerRepository;
import com.mayy5.admin.repository.RetailerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetailerService {

	private final RetailerRepository retailerRepository;
	private final MarketRetailerRepository marketRetailerRepository;
	private final UserService userService;

	@Transactional
	public Retailer createRetailer(String userId, Retailer input) {
		User user = userService.getUser(userId);
		Retailer retailer = Retailer.createRetailer(user, input.getName(), input.getType(), input.getMeta());
		return retailerRepository.save(retailer);
	}

	@Transactional(readOnly = true)
	public Retailer getRetailer(Long retailerId) {
		return retailerRepository.findById(retailerId)
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Retailer"));
	}

	@Transactional(readOnly = true)
	public List<Retailer> getAllRetailers() {
		return retailerRepository.findAll();
	}

	@Transactional
	public Retailer updateRetailer(Retailer retailer) throws CommonException {
		retailerRepository.findById(retailer.getId())
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "retailer"));
		return retailerRepository.save(retailer);
	}

	@Transactional
	public void deleteRetailer(Long retailerId) throws CommonException {
		try {
			Optional<Retailer> retailer = retailerRepository.findById(retailerId);
			retailer.ifPresent(resourceExist -> {
				retailerRepository.deleteById(retailerId);
			});
			return;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Retailer Delete");
		}
	}

	@Transactional(readOnly = true)
	public List<Retailer> getRetailersByUserId(String userId) {
		List<Retailer> retailers = retailerRepository.getRetailersByUserId(userId);
		return retailers;
	}

	@Transactional(readOnly = true)
	public List<MarketRetailer> getMarketRetailersOfRetailer(Long retailerId) {
		List<MarketRetailer> marketRetailers = marketRetailerRepository.getMarketRetailersByRetailerId(retailerId);
		return marketRetailers;
	}
}
