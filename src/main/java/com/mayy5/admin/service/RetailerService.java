package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.domain.RetailerSchedule;
import com.mayy5.admin.model.domain.Schedule;
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

        validateDuplicateRetailer(retailer); //중복 장원 검증
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


    /**
     * 출석 관리 스케줄 추가
     * @param retailerId
     * @param marketId
     */
    @Transactional
    public void addSchedule(Long retailerId, Long marketId) {
        // 장원 조회
        Retailer retailer = retailerRepository.findOne(retailerId);

        // 장 조회 & 출석 관리 스케줄 조회
        Market market = marketRepository.findOne(marketId);
        Schedule schedule = market.getSchedule();

        // RetailerSchedule 관계 엔티티 생성
        RetailerSchedule retailerSchedule = new RetailerSchedule();

        // 스케줄 추가
        retailer.addRetailerSchedule(retailerSchedule);
        schedule.addRetailerSchedule(retailerSchedule);
    }


    //==기타 메서드==//
    private void validateDuplicateRetailer(Retailer retailer) {
        List<Retailer> findRetailers = retailerRepository.findByName(retailer.getUserName());
        if (!findRetailers.isEmpty()) {
            throw new IllegalStateException("이미 등록되었습니다.");
        }
    }
}
