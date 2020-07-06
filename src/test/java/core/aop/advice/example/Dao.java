package core.aop.advice.example;

import core.annotation.Inject;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
    private JdbcTemplate jdbcTemplate;

    @Inject
    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void multipleQuery() {
        User user = new User("admin", "", "", "");

        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());

        throw new RuntimeException("rollback!!");
    }

    public User getAdmin() {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        RowMapper<User> rm = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, "admin");
    }
}
