package core.aop.example.transactional;

import core.annotation.Service;
import core.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NonTransactionalService {

    public String doService(){
        String msg = "doService";
        log.debug(msg);
        return msg;
    }

    public String doExceptionalService(){
        String msg = "doExceptionalService";
        log.debug(msg);
        throw new RuntimeException();
    }
}