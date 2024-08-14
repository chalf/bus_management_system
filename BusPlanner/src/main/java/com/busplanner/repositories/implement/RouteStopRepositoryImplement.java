/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Routestops;
import com.busplanner.repositories.RouteStopRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Repository
public class RouteStopRepositoryImplement implements RouteStopRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional
    public List<Routestops> getAllRouteStops() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Routestops> criteriaQuery = criteriaBuilder.createQuery(Routestops.class);
        Root<Routestops> root = criteriaQuery.from(Routestops.class);
        criteriaQuery.select(root);
        return s.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public Routestops getRouteStopById(int routeStopId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Routestops.class, routeStopId);
    }

    @Override
    @Transactional
    public Routestops addOrUpdateRouteStop(Routestops routeStop) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (routeStop.getRouteStopId() != null) {
            s.update(routeStop);
        } else {
            s.save(routeStop);
        }
        return routeStop;
    }

    @Override
    @Transactional
    public void deleteRouteStop(int routeStopId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Routestops routeStop = getRouteStopById(routeStopId);
        if (routeStop != null) {
            s.delete(routeStop);
        }
    }

    @Override
    @Transactional
    public List<Routestops> getRouteStopsByRouteId(int routeId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Routestops> criteriaQuery = criteriaBuilder.createQuery(Routestops.class);
        Root<Routestops> root = criteriaQuery.from(Routestops.class);
        
        Predicate predicate = criteriaBuilder.equal(root.get("routeId").get("routeId"), routeId);
        criteriaQuery.where(predicate);
        return s.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public List<Routestops> getRouteStopsByStopId(int stopId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Routestops> criteriaQuery = criteriaBuilder.createQuery(Routestops.class);
        Root<Routestops> root = criteriaQuery.from(Routestops.class);
        
        Predicate predicate = criteriaBuilder.equal(root.get("stopId").get("stopId"), stopId);
        criteriaQuery.where(predicate);
        return s.createQuery(criteriaQuery).getResultList();
    }
    
}
