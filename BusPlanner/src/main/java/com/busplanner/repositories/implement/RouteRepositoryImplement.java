/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Routes;
import com.busplanner.repositories.RouteRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class RouteRepositoryImplement implements RouteRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    @Transactional
    public List<Routes> getListRoutes() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = s.getCriteriaBuilder();
        CriteriaQuery<Routes> query = criteria.createQuery(Routes.class);
        Root<Routes> routeRoot = query.from(Routes.class);
        
        Query<Routes> q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    @Transactional
    public void addOrUpdateRoute(Routes route) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if(route.getRouteId() != null)
            s.update(route);
        else
            s.save(route);
    }

    @Override
    @Transactional
    public Routes getRouteById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Routes.class, id);
    }

    @Override
    @Transactional
    public void deleteRouteById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Routes route = getRouteById(id);
        s.delete(route);
    }
    
}
