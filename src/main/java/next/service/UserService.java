package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import next.dao.AspectUserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final AspectUserDao userDao;
    private final UserLogService userLogService;

    @Inject
    public UserService(AspectUserDao userDao, UserLogService userLogService) {
        this.userLogService = userLogService;
        this.userDao = userDao;
    }

    public User findByUserId(String userId) {
        userLogService.log(userId);
        return userDao.findByUserId(userId);
    }

    @Transactional
    public void insertUserWithAdmin(User user) {
        userDao.insert(user);

        if ("admin@test.com".equalsIgnoreCase(user.getEmail())) {
            logger.info("## updated admin user: {}", user.getUserId());
            user.updateRole("admin");
            userDao.update(user);

            if ("jun".equalsIgnoreCase(user.getUserId())) {
                throw new RuntimeException("jun ignored");
            }
        }
    }
}
