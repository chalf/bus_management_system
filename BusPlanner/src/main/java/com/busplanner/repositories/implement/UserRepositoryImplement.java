/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.busplanner.resources.CriteriaComponents;
import com.busplanner.busplanner.resources.CriteriaUtil;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
@Transactional
public class UserRepositoryImplement implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public Users retrieveUserByUsername(String username) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaComponents<Users> components = CriteriaUtil.createCriteriaComponents(session, Users.class);

        CriteriaBuilder criteria = components.getCriteriaBuilder();
        CriteriaQuery<Users> query = components.getCriteriaQuery();
        Root<Users> userRoot = components.getRoot();

        Predicate p = criteria.equal(userRoot.get("username"), username);
        query.where(p);
        Query<Users> execution = session.createQuery(query);
        return execution.getSingleResult();
    }

    @Override
    public boolean existsByUsername(String username) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaComponents<Users> components = CriteriaUtil.createCriteriaComponents(session, Users.class);

        CriteriaBuilder criteria = components.getCriteriaBuilder();
        CriteriaQuery<Users> query = components.getCriteriaQuery();
        Root<Users> userRoot = components.getRoot();
        
        query.where(criteria.equal(userRoot.get("username"), username));
        Query<Users> execution = session.createQuery(query);
        return execution != null;
    }

    @Override
    public Users addUser(Users user) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    public boolean authUser(String username, String password) {
        Users user = this.getUserByUsername(username);
        
        return this.passEncoder.matches(password, user.getPassword());
    }

    @Override
    public Users getUserByUsername(String username) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);

        return (Users) query.getSingleResult();
    }
    

}
