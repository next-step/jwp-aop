package core.di.aspect.example;

import core.annotation.Inject;
import core.annotation.Service;
import next.model.User;

@Service
public class AspectUserService {

    private final AspectUserDao userDao;

    @Inject
    public AspectUserService(AspectUserDao userDao) {
        this.userDao = userDao;
    }

    public User addAndGetUser(User user) {
        userDao.addUser(user);
        return userDao.getUser(user.getUserId());
    }
}
