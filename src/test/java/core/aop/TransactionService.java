package core.aop;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import next.dao.AnswerDao;
import next.dao.UserDao;
import next.model.User;

@Service
public class TransactionService {

    private final UserDao userDao;

    @Inject
    public TransactionService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUserThenThrowException(User user){
        userDao.insert(user);
        throw new RuntimeException();
    }
}
