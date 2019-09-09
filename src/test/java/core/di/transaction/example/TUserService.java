package core.di.transaction.example;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import next.dto.UserUpdatedDto;
import next.model.User;

@Service
public class TUserService {

    private final TUserDao TUserDao;

    @Inject
    public TUserService(TUserDao TUserDao) {
        this.TUserDao = TUserDao;
    }

    @Transactional
    public void insertAndModify(User user, UserUpdatedDto updatedUser) {
        process(user, updatedUser);
    }

    public void insertAndModifyNotTransaction(User user, UserUpdatedDto updatedUser) {
        process(user, updatedUser);
    }

    private void process(User user, UserUpdatedDto updatedUser) {
        TUserDao.insert(user);
        user.update(updatedUser);
        TUserDao.update(user);
    }

    public User getUser(String userId) {
        return TUserDao.findByUserId(userId);
    }

}
