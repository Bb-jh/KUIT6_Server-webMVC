package jwp;

import core.jdbc.ConnectionManager;
import jwp.dao.UserDao;
import jwp.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private UserDao userDao;
    private User testUser;

    @BeforeAll
    static void initSchema() throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS USERS");
            stmt.execute("CREATE TABLE USERS (" +
                    "userId VARCHAR(50) PRIMARY KEY," +
                    "password VARCHAR(100)," +
                    "name VARCHAR(100)," +
                    "email VARCHAR(100))");
        }
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        testUser = new User("asdf1234", "pass", "initname", "asdf1234@test.com");
    }

    @Test
    @DisplayName("insert 후 findByUserId로 조회 가능해야 함")
    void testInsertAndFindByUserId() throws SQLException {
        userDao.insert(testUser);
        User found = userDao.findByUserId("asdf1234");

        assertNotNull(found);
        assertEquals("asdf1234", found.getUserId());
        assertEquals("initname", found.getName());
    }

    @Test
    @DisplayName("update로 사용자 정보가 수정되어야 함")
    void testUpdate() throws SQLException {
        userDao.insert(testUser);

        User updated = new User("asdf1234", "newpass", "newname", "new@test.com");
        userDao.update(updated);

        User found = userDao.findByUserId("asdf1234");
        assertEquals("newname", found.getName());
        assertEquals("new@test.com", found.getEmail());
        assertEquals("newpass", found.getPassword());
    }

    @Test
    @DisplayName("findAll로 전체 사용자 목록을 가져올 수 있어야 함")
    void testFindAll() throws SQLException {
        userDao.insert(testUser);
        List<User> users = userDao.findAll();

        assertTrue(!users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getUserId().equals("asdf1234")));
    }

    @Test
    @DisplayName("delete로 사용자 삭제 후 조회 시 null이어야 함")
    void testDelete() throws SQLException {
        userDao.insert(testUser);
        userDao.delete(testUser);

        User found = userDao.findByUserId("asdf1234");
        assertNull(found, "삭제된 사용자는 조회되지 않아야 함");
    }

    @AfterEach
    void tearDown() throws SQLException {
        try {
            userDao.delete(testUser);
        } catch (Exception ignored) {
        }
    }
}
