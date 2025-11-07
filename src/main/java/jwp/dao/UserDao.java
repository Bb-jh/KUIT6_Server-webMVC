package jwp.dao;

import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor // lombok -> final 키워드 생성자들을 자동으로 만들어 줌.
public class UserDao {

    private final EntityManager em;


    @Transactional
    public void insert(User user) {
        em.persist(user);
    }

    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u From User u", User.class).getResultList();
    }

    public User findByUserId(String userId) {
        return em.find(User.class, userId);
    }
}
