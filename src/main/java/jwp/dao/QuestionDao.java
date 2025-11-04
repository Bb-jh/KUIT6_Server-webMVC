package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {
    private final EntityManager em;

    @Transactional
    public Question insert(Question question) throws SQLException {
        em.persist(question);
        return question;

    }

    public void update(Question question) throws SQLException {
        em.merge(question);
    }

    public void delete(int questionId) throws SQLException {
        Question question = em.find(Question.class, questionId);
        if (question != null) {
            em.remove(question);
        }
    }

    public List<Question> findAll() throws SQLException {
        return em.createQuery("select q From Question q", Question.class).getResultList();
    }

    public Question findByQuestionId(int questionId) throws SQLException {
        return em.find(Question.class, questionId);
    }
}
