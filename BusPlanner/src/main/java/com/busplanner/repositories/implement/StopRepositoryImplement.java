/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Stops;
import com.busplanner.repositories.StopRepository;
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
public class StopRepositoryImplement implements StopRepository{

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    @Transactional
    public List<Stops> retrieveStop() {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = session.getCriteriaBuilder();
        CriteriaQuery<Stops> query = criteria.createQuery(Stops.class);
        Root<Stops> stopRoot = query.from(Stops.class);
        
        Query<Stops> q = session.createQuery(query);
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
