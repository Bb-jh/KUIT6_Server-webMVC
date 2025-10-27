package jwp.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    // TODO insert, update, delete
    // sql이랑 param 주입하는 함수형 인터페이스 구현시킨담에 jdbc 템플릿에 실행관련 작업들 위임함.
    public final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate();


    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };
        jdbcTemplate.update(sql, pss);
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET password =?, name =?, email =? WHERE userId =?";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };
        jdbcTemplate.update(sql, pstmtSetter);
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM USERS WHERE userId =?";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getUserId());
        };
        jdbcTemplate.update(sql, pss);
    }

    // TODO findAll, findByUserId (select 조회)

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        RowMapper rowMapper = rs -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE userId=?";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, userId);
        };
        RowMapper rowMapper = rs -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }
}

