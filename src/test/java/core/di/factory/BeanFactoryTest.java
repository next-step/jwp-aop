package core.di.factory;

import core.di.beans.factory.ClassBeanScanner;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.MethodBeanScanner;
import core.di.factory.example.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanFactoryTest {
    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        new ClassBeanScanner(beanFactory).scan("core.di.factory.example");
        new MethodBeanScanner(beanFactory).scan("core.di.factory.example");

        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    public void qualifierTest() {
        BoardService boardService = beanFactory.getBean(BoardService.class);

        assertThat(boardService.getBoardRepository()).isInstanceOf(MockBoardRepository.class);
    }

}
