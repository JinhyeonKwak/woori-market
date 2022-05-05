package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.RetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetailerService {

    private final RetailerRepository retailerRepository;
    private final MarketRepository marketRepository;

    @Transactional
    public Long join(Retailer retailer) {

        retailerRepository.save(retailer);
        return retailer.getId();
    }

    @Transactional(readOnly = true)
    public List<Retailer> findRetailers() {
        return retailerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Retailer findOne(Long retailerId) {
        return retailerRepository.findOne(retailerId);
    }

    //==기타 메서드==//
}
