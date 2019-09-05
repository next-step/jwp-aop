package study.cglibProxy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberServiceImpl implements MemberService {
    private static  final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    public MemberServiceImpl() {
        logger.debug("MemberServiceImpl create");
    }

    @Override
    public void regist(Member member) {
        logger.debug("MemberServiceImpl.regist");
    }

    @Override
    public Member getMember(String id) {
        logger.debug("MemberServiceImpl.getMember");
        return new Member(id,"changjun");
    }
}
