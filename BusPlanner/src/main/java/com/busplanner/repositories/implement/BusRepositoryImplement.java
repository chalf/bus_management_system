/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Buses;
import com.busplanner.repositories.BusRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
public class BusRepositoryImplement implements BusRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    @Transactional
    public List<Buses> getListBus() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = s.getCriteriaBuilder();
        CriteriaQuery<Buses> query = criteria.createQuery(Buses.class);
        Root<Buses> busRoot = query.from(Buses.class);
        
        Query<Buses> q = s.createQuery(query);
        return q.getResultList();
        
    }

    @Override
    @Transactional
    public void addOrUpdateBus(Buses bus) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if(bus.getBusId() != null)
            s.update(bus);
        else
            s.save(bus);
    }

    @Override
    @Transactional
    public Buses getBusById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Buses.class, id);
    }

    @Override
    @Transactional
    public void deleteBusbyId(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Buses bus = getBusById(id);
        s.delete(bus);
    }
    
}
