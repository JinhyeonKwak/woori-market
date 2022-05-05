package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Retailer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RetailerRepository {

    private final EntityManager em;

    public void save(Retailer retailer) {
        em.persist(retailer);
    }

    public Retailer findOne(Long id) {
        return em.find(Retailer.class, id);
    }

    public List<Retailer> findAll() {
        return em.createQuery("select r from Retailer r", Retailer.class)
                .getResultList();
    }
}
