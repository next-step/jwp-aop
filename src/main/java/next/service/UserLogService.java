package next.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLogService {

    private static final Logger logger = LoggerFactory.getLogger(UserLogService.class);

    public void log(String id) {
        logger.info("## {} entrance", id);
    }

}
