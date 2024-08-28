package com.busplanner.repositories.implement;

import com.busplanner.busplanner.resources.CriteriaComponents;
import com.busplanner.busplanner.resources.CriteriaUtil;
import com.busplanner.configs.PaginationConfigs;
import com.busplanner.pojo.Users;
import com.busplanner.repositories.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class UserRepositoryImplement implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    @Transactional
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
    @Transactional
    // true -> đã tồn tại username
    public boolean existsByUsername(String username) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaComponents<Users> components = CriteriaUtil.createCriteriaComponents(session, Users.class);

        CriteriaBuilder criteria = components.getCriteriaBuilder();
        CriteriaQuery<Users> query = components.getCriteriaQuery();
        Root<Users> userRoot = components.getRoot();

        query.where(criteria.equal(userRoot.get("username"), username));
        Query<Users> executeQuery = session.createQuery(query);
        try {
            Users user = executeQuery.getSingleResult(); 
    /* vì username là unique nên ta sài getSingleResult() không có lỗi, 
    nếu lỗi sẽ xuất hiện ngoại lệ dưới*/
            return true; // User tồn tại
        } catch (NoResultException e) {
            return false; // Không có user nào có username này
        }
    }

    @Override
    @Transactional
    public Users addUser(Users user) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try {
            // nếu vi phạm trùng username hoặc email thì gán id = -1 hoặc 0 như một flag để controller biết
            if(!this.existsByUsername(user.getUsername()) == false){
                user.setUserId(Users.duplicateUsername());
                return user;
            }
            if(!this.existsByEmail(user.getEmail()) == false){
                user.setUserId(Users.duplicateEmail());
                return user;
            }
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
    @Transactional
    public boolean authUser(String username, String password) {
        try {
            Users user = this.getUserByUsername(username);
            return this.passEncoder.matches(password, user.getPassword());
        } catch (NoResultException e) {
            return false;
        }

    }

    @Override
    @Transactional
    public Users getUserByUsername(String username) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("Users.findByUsername");
        query.setParameter("username", username);
        return (Users) query.getSingleResult();

    }
    
    @Override
    @Transactional
    // true -> đã tồn tại email
    public boolean existsByEmail(String email){
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaComponents<Users> components = CriteriaUtil.createCriteriaComponents(session, Users.class);

        CriteriaBuilder criteria = components.getCriteriaBuilder();
        CriteriaQuery<Users> query = components.getCriteriaQuery();
        Root<Users> userRoot = components.getRoot();
        Predicate p = criteria.equal(userRoot.get("email"), email);
        query.where(p);
        Query executeQuery = session.createQuery(query);
        try {
            Users u = (Users) executeQuery.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public List<Users> getListUser(Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root<Users> userRoot = q.from(Users.class);
        q.select(userRoot);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String userName = params.get("q");
            if (userName != null && !userName.isEmpty()) {
                Predicate p1 = b.like(userRoot.get("username"), String.format("%%%s%%", userName));
                predicates.add(p1);
            }

            String fullName = params.get("fullname");
            if (fullName != null && !fullName.isEmpty()) {
                Predicate p2 = b.like(userRoot.get("fullName"), String.format("%%%s%%", fullName));
                predicates.add(p2);
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query<Users> query = s.createQuery(q);

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
    public boolean updateUser(Users updatedUser) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        try {
            s.update(updatedUser);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


}
