/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.repositories.implement;

import com.busplanner.busplanner.resources.CriteriaComponents;
import com.busplanner.busplanner.resources.CriteriaUtil;
import com.busplanner.pojo.Users;
import com.busplanner.repositories.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
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
    @Autowired
    private Cloudinary cloudinary;

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
        try {
            Map imageInfo = cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            user.setAvatarUrl((String) imageInfo.get("secure_url"));
            // Gán giá trị cho createdAt và updatedAt
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(new Date());
            }
            user.setUpdatedAt(new Date());
            session.save(user);
        } catch (IOException ex) {
            Logger.getLogger(UserRepositoryImplement.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    @Override
    public boolean authUser(String username, String password) {
        try {
            Users user = this.getUserByUsername(username);
            return this.passEncoder.matches(password, user.getPassword());
        } catch (NoResultException e) {
            return false;
        }

    }

    @Override
    public Users getUserByUsername(String username) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("Users.findByUsername");
        query.setParameter("username", username);
        return (Users) query.getSingleResult();

    }

}
