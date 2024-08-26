/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.configs.PaginationConfigs;
import com.busplanner.pojo.Routes;
import com.busplanner.repositories.RouteRepository;
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

@Repository
public class RouteRepositoryImplement implements RouteRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional
    public List<Routes> getListRoutes(Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Routes> q = b.createQuery(Routes.class);
        Root<Routes> routeRoot = q.from(Routes.class);
        q.select(routeRoot);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String routeName = params.get("q");
            if (routeName != null && !routeName.isEmpty()) {
                Predicate p1 = b.like(routeRoot.get("routeName"), String.format("%%%s%%", routeName));
                predicates.add(p1);
            }

            String startPoint = params.get("start");
            if (startPoint != null && !startPoint.isEmpty()) {
                Predicate p2 = b.like(routeRoot.get("startPoint"), String.format("%%%s%%", startPoint));
                predicates.add(p2);
            }

            String endPoint = params.get("end");
            if (endPoint != null && !endPoint.isEmpty()) {
                Predicate p3 = b.like(routeRoot.get("endPoint"), String.format("%%%s%%", endPoint));
                predicates.add(p3);
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query<Routes> query = s.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PaginationConfigs.PAGE_SIZE;

                query.setFirstResult(start);
                query.setMaxResults(PaginationConfigs.PAGE_SIZE);
            }
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public void addOrUpdateRoute(Routes route) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (route.getRouteId() != null) {
            s.update(route);
        } else {
            s.save(route);
        }
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
        if (route != null) {
            s.delete(route);
        }

    }
}
