package core.di.aspect.example;

import core.annotation.Repository;
import next.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class AspectUserDao {

    private final Map<String, User> registry = new HashMap<>();

    public void addUser(User user) {
        Objects.requireNonNull(user, "user not null");
        registry.put(user.getUserId(), user);
    }

    public User getUser(String userId) {
        return registry.get(userId);
    }
}
