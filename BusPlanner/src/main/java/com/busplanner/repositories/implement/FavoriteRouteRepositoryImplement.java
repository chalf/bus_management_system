/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Favoriteroutes;
import com.busplanner.repositories.FavoriteRouteRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
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
public class FavoriteRouteRepositoryImplement implements FavoriteRouteRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional
    public List<Favoriteroutes> getFavoriteRoutesByUserId(int userId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = s.getCriteriaBuilder();
        CriteriaQuery<Favoriteroutes> query = criteria.createQuery(Favoriteroutes.class);
        Root<Favoriteroutes> root = query.from(Favoriteroutes.class);
        
        Predicate predicate = criteria.equal(root.get("userId").get("userId"), userId);
        query.where(predicate);
        return s.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public Favoriteroutes addOrUpdateFavoriteRoute(Favoriteroutes favoriteroute) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (favoriteroute.getFavoriteId() != null) {
        s.update(favoriteroute);
    } else {
        s.save(favoriteroute);
    }
    return favoriteroute;
    }

    @Override
    @Transactional
    public Favoriteroutes getFavoriteRouteById(int favoriteId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Favoriteroutes.class, favoriteId);
    }

    @Override
    @Transactional
    public void deleteFavoriteRoute(int favoriteId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Favoriteroutes favoriteroute = getFavoriteRouteById(favoriteId);
        s.delete(favoriteroute);
    }

    @Override
    @Transactional
    public List<Favoriteroutes> getAllFavoriteRoutes() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<Favoriteroutes> criteriaQuery = criteriaBuilder.createQuery(Favoriteroutes.class);
        Root<Favoriteroutes> root = criteriaQuery.from(Favoriteroutes.class);
        criteriaQuery.select(root);
        return s.createQuery(criteriaQuery).getResultList();
    }

}
