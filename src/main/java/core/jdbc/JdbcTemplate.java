package core.jdbc;

import jwp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// sql 실행하는 책임
public class JdbcTemplate<T> {
    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        // try문 실행전에 괄호 안 실행하고, try문 끝나면 바로 자원해제됨
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmtSetter.setParameters(pstmt);
            pstmt.executeUpdate();
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper) throws SQLException {
        List<T> objects = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
        ) {
            while (rs.next()) {
                T object = rowMapper.mapRow(rs);
                objects.add(object);
            }
        }
        return objects;
    }

    public T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        T object = null;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            // 위의 두개가 먼저 된 후에(param 바인딩된후에) ResultSet 받아야 함.
            rs = pstmt.executeQuery();
            pstmtSetter.setParameters(pstmt);
            if (rs.next()) {
                object = rowMapper.mapRow(rs);
            }
        } finally {
            if (rs != null)
                rs.close();
        }
        return object;
    }
}
