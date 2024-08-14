/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Trafficreports;
import com.busplanner.repositories.TrafficReportRepository;
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
public class TrafficReportRepositoryImplement implements TrafficReportRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional
    public List<Trafficreports> getAllTrafficReports() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Trafficreports> criteria = criteriaBuilder.createQuery(Trafficreports.class);
        Root<Trafficreports> root = criteria.from(Trafficreports.class);
        criteria.select(root);
        return s.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional
    public void addOrUpdateTrafficReport(Trafficreports report) {
            Session s = this.sessionFactory.getObject().getCurrentSession();
        if (report.getReportId()!= null) {
            s.update(report);
        } else {
            s.save(report);
        }
    }

    @Override
    @Transactional
    public Trafficreports getTrafficReportById(int reportId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Trafficreports.class, reportId);
    }

    @Override
    @Transactional
    public void deleteTrafficReportById(int reportId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Trafficreports report = getTrafficReportById(reportId);
        if (report != null) {
            s.delete(report);
        }
    }

    @Override
    @Transactional
    public List<Trafficreports> getTrafficReportsByUserId(int userId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Trafficreports> criteria = criteriaBuilder.createQuery(Trafficreports.class);
        Root<Trafficreports> root = criteria.from(Trafficreports.class);
        
        Predicate userPredicate = criteriaBuilder.equal(root.get("userId").get("userId"), userId);
        criteria.where(userPredicate);
        
        return s.createQuery(criteria).getResultList();
    }
    
}
