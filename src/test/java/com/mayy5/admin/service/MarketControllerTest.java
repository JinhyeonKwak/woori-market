package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Address;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.res.RetailerResponseDto;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.MarketRetailerRepository;
import com.mayy5.admin.repository.RetailerRepository;
import com.mayy5.admin.type.RetailerMetaType;
import com.mayy5.admin.type.UserMetaType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MarketControllerTest {

    @Autowired
    MarketService marketService;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    RetailerService retailerService;
    @Autowired
    UserService userService;
    @Autowired
    MarketRetailerRepository marketRetailerRepository;
    @Autowired
    RetailerMapper retailerMapper;
    @Autowired
    RetailerRepository retailerRepository;

    @Test
    @Transactional
    void registerRetailer() {
        User input = new User();
        input.setId("wlsgus555");
        input.setPassword("k1823511");
        Map<UserMetaType, String> inputMeta = new HashMap<>();
        inputMeta.put(UserMetaType.ROLE, "ROLE_MARKET_AGENT");
        input.setMeta(inputMeta);
        User user = userService.createUser(input);

        Market market = new Market();
        market.setMarketDay(DayOfWeek.FRIDAY);
        market.setAddress(new Address("string", "string", "string"));
        market.setStartDate(LocalDate.now());
        market.setEndDate(LocalDate.now());
        marketRepository.save(market);

        Map<RetailerMetaType, String> meta = new HashMap<>();
        meta.put(RetailerMetaType.BUSINESS_TYPE, "FOOD");
        Retailer retailer = Retailer.createRetailer(user, meta);
        retailerRepository.save(retailer);

        marketService.addRetailer(market.getId(), retailer.getId());

        Market updateMarket = marketService.updateMarket(market);


        List<MarketRetailer> all = marketRetailerRepository.findAll();
        all.stream().map(marketRetailer -> "marketRetailer = " + marketRetailer).forEach(System.out::println);
    }
}
