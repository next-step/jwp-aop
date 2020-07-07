package core.aop.interceptors;

import core.di.factory.example.QuestionRepository;
import core.di.factory.example.UserRepository;

public interface MyTestService {
    String getUppercasedName(String name);
}
