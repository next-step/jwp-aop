package core.aop.example.transactional;

import core.annotation.Service;
import core.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionalMethodService {

    @Transactional
    public String doServiceWithTransactional(){
        String msg = "doService with @Transactional";
        log.debug(msg);
        return msg;
    }

    public String doServiceWithoutTransactional(){
        String msg = "doService without @Transactional";
        log.debug(msg);
        return msg;
    }

    @Transactional
    public String doExceptionalServiceWithTransactional(){
        String msg = "doExceptionalService with @Transactional";
        log.debug(msg);
        throw new RuntimeException();
    }
}