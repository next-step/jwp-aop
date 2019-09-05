package next.service;

import core.annotation.Component;
import core.annotation.Inject;
import next.dao.UserDao;
import next.model.User;

@Component
public class UserService {

    private final UserDao userDao;
    private final UserLogService userLogService;

    @Inject
    public UserService(UserDao userDao, UserLogService userLogService) {
        this.userLogService = userLogService;
        this.userDao = userDao;
    }

    public User findByUserId(String userId) {
        userLogService.log(userId);
        return userDao.findByUserId(userId);
    }

}
