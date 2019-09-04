package core.di.factory.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactoryTestService {

    private static final Logger logger = LoggerFactory.getLogger(FactoryTestService.class);

    public String log() {
        logger.info("log method invoked");
        return "ok";
    }

}
