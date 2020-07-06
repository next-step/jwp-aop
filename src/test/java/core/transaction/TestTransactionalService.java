package core.transaction;

import core.annotation.Service;
import core.annotation.Transactional;

@Service
public class TestTransactionalService {

    @Transactional
    public String doService(){
        return "doService with @Transactional";
    }
}