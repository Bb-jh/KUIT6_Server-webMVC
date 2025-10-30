package jwp.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.Question;
import jwp.support.KeyHolder;

import java.util.List;

public class QuestionDao {
    public final JdbcTemplate<Question> jdbcTemplate = new JdbcTemplate<>();
    KeyHolder keyHolder = new KeyHolder();

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?)";

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setInt(4, question.getCountOfAnswer());
        };

        jdbcTemplate.update(sql, pss, keyHolder);
        return findByQuestionId((long) keyHolder.getId());
    }


    public Question findByQuestionId(Long questionId) {
        String sql = "SELECT * FROM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setLong(1, questionId);
        };
        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer")
        );
        return jdbcTemplate.queryForObject(sql, pss, rowMapper);
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";

        RowMapper rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer"));
        return jdbcTemplate.query(sql, rowMapper);
    }
}
