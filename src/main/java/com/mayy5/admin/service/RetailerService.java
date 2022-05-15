package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.repository.RetailerRepository;
import com.mayy5.admin.type.RetailerMetaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetailerService {

    private final RetailerRepository retailerRepository;
    private final UserService userService;

    @Transactional
    public Retailer createRetailer(String id, Map<RetailerMetaType, String> meta) {
        User user = userService.getUser(id);
        return Retailer.createRetailer(user, meta);
    }

    @Transactional(readOnly = true)
    public Retailer getRetailer(Long retailerId) {
        return retailerRepository.findById(retailerId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Retailer"));
    }

    @Transactional(readOnly = true)
    public List<Retailer> getRetailerList() {
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
}
