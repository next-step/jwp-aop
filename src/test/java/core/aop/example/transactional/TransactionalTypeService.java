package core.aop.example.transactional;

import core.annotation.Service;
import core.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TransactionalTypeService {

    public String doServiceWithTransactional(){
        String msg = "doService with @Transactional";
        log.debug(msg);
        return msg;
    }

    public String doExceptionalServiceWithTransactional(){
        String msg = "doExceptionalService with @Transactional";
        log.debug(msg);
        throw new RuntimeException();
    }
}