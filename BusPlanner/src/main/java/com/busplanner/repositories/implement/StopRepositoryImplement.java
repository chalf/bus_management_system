/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.configs.PaginationConfigs;
import com.busplanner.pojo.Stops;
import com.busplanner.repositories.StopRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Repository
public class StopRepositoryImplement implements StopRepository{

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    @Transactional
    public List<Stops> retrieveStop(Map<String, String> params) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = session.getCriteriaBuilder();
        CriteriaQuery<Stops> query = criteria.createQuery(Stops.class);
        Root<Stops> stopRoot = query.from(Stops.class);
        query.select(stopRoot);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String address = params.get("q");
            if (address != null && !address.isEmpty()) {
                Predicate p1 = criteria.like(stopRoot.get("address"), String.format("%%%s%%", address));
                predicates.add(p1);
            }

            String stopName = params.get("name");
            if (stopName != null && !stopName.isEmpty()) {
                Predicate p2 = criteria.like(stopRoot.get("stopName"), String.format("%%%s%%", stopName));
                predicates.add(p2);
            }

            query.where(predicates.toArray(new Predicate[0]));
        }

        Query<Stops> q = session.createQuery(query);

        // Xử lý phân trang
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PaginationConfigs.PAGE_SIZE;

                q.setFirstResult(start);
                q.setMaxResults(PaginationConfigs.PAGE_SIZE);
            }
        }

        return q.getResultList();
    }

    @Override
    @Transactional
    public void addOrUpdateStop(Stops stop) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (stop.getStopId() != null)
            s.update(stop);
        else
            s.save(stop);
    }

    @Override
    @Transactional
    public Stops getStopById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Stops.class, id);
    }

    @Override
    @Transactional
    public void deleteStop(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Stops stop = this.getStopById(id);
        s.delete(stop);
    }
}
