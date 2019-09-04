package next.service;

import core.annotation.Inject;
import next.dao.UserDao;
import next.model.User;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

}
