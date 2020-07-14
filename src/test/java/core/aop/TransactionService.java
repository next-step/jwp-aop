package core.aop;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import core.jdbc.JdbcTemplate;
import next.dao.UserDao;
import next.dto.UserUpdatedDto;
import next.model.User;

/**
 * @author KingCjy
 */
@Service
public class TransactionService {
    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public TransactionService(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void update(UserUpdatedDto userUpdatedDto) {

        User user = findUserById(userUpdatedDto.getUserId());
        user.update(userUpdatedDto);
        updateUser(user);
//        error
        String sql = "SELECT * FROM USERS WHERE id = ?";
        jdbcTemplate.query(sql, rs -> null, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Transactional
    public void addUser(User user) {
        userDao.insert(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public User findUserById(String userId) {
        return userDao.findByUserId(userId);
    }
}
