/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Schedules;
import com.busplanner.repositories.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ScheduleRepositoryImplement implements ScheduleRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional
    public List<Schedules> getAllSchedules(Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Schedules> q = b.createQuery(Schedules.class);
        Root<Schedules> root = q.from(Schedules.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String dayOfWeek = params.get("q");
            if (dayOfWeek != null && !dayOfWeek.isEmpty()) {
                Predicate p1 = b.equal(root.get("dayOfWeek"), dayOfWeek);
                predicates.add(p1);
            }

            String start = params.get("start");
            if (start != null && !start.isEmpty()) {
                Predicate p2 = b.greaterThanOrEqualTo(root.get("departureTime"), java.sql.Time.valueOf(start));
                predicates.add(p2);
            }

            String end = params.get("end");
            if (end != null && !end.isEmpty()) {
                Predicate p3 = b.lessThanOrEqualTo(root.get("arrivalTime"), java.sql.Time.valueOf(end));
                predicates.add(p3);
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        return s.createQuery(q).getResultList();
    }

    @Override
    @Transactional
    public Schedules getScheduleById(int scheduleId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Schedules.class, scheduleId);
    }

    @Override
    @Transactional
    public Schedules addOrUpdateSchedule(Schedules schedule) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (schedule.getScheduleId() != null) {
            s.update(schedule);
        } else {
            s.save(schedule);
        }
        return schedule;
    }

    @Override
    @Transactional
    public void deleteSchedule(int scheduleId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Schedules schedule = getScheduleById(scheduleId);
        if (schedule != null) {
            s.delete(schedule);
        }
    }

    @Override
    @Transactional
    public List<Schedules> getSchedulesByBusId(int busId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Schedules> criteriaQuery = criteriaBuilder.createQuery(Schedules.class);
        Root<Schedules> root = criteriaQuery.from(Schedules.class);
        
        Predicate predicate = criteriaBuilder.equal(root.get("busId").get("busId"), busId);
        criteriaQuery.where(predicate);
        return s.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public List<Schedules> getSchedulesByRouteId(int routeId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Schedules> criteriaQuery = criteriaBuilder.createQuery(Schedules.class);
        Root<Schedules> root = criteriaQuery.from(Schedules.class);
        
        Predicate predicate = criteriaBuilder.equal(root.get("routeId").get("routeId"), routeId);
        criteriaQuery.where(predicate);
        return s.createQuery(criteriaQuery).getResultList();
    }
    
}
