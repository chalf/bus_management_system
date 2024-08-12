/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.pojo.Users;
import com.busplanner.repositories.UserRepository;
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
 * @author Admin
 */
@Repository
public class UserRepositoryImplement implements UserRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    @Transactional
    public Users retrieveUser(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteria = session.getCriteriaBuilder();
        CriteriaQuery query = criteria.createQuery(Users.class);
        Root<Users> userRoot = query.from(Users.class);
        
        Predicate p = criteria.equal(userRoot.get("userId"), id);
        query.where(p);
        Query<Users> q = session.createQuery(query);
        return q.getSingleResult();
    }
    
}
