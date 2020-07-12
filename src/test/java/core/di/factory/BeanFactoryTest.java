package core.di.factory;

import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.scanner.ClassBeanScanner;
import core.di.beans.factory.scanner.MethodBeanScanner;
import core.di.factory.example.BoardService;
import core.di.factory.example.MockBoardRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
