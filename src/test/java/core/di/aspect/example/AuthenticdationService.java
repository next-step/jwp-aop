package core.di.aspect.example;

import core.annotation.Service;

@Service
public class AuthenticdationService {

    public static boolean IS_LOGIN = true;

    public boolean login() {
        return IS_LOGIN;
    }

}
