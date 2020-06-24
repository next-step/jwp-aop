package next.controller;

import core.annotation.Inject;
import core.annotation.web.Controller;
import core.annotation.web.PathVariable;
import core.annotation.web.RequestMapping;
import core.annotation.web.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dto.QuestionDto;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.security.LoginUser;
import next.service.QnaService;

import java.util.List;

@Controller
public class QnaController extends AbstractNewController {
    private QnaService qnaService;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public QnaController(QnaService qnaService, QuestionDao questionDao, AnswerDao answerDao) {
        this.qnaService = qnaService;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ModelAndView createForm(@LoginUser User loginUser) throws Exception {
        return jspView("/qna/form.jsp");
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public ModelAndView create(@LoginUser User loginUser, QuestionDto questionDto) throws Exception {
        Question question = new Question(
                loginUser.getUserId(),
                questionDto.getTitle(),
                questionDto.getContents());
        questionDao.insert(question);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable String id) throws Exception {
        Question question = questionDao.findById(Long.parseLong(id));
        List<Answer> answers = answerDao.findAllByQuestionId(Long.parseLong(id));

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }

    @RequestMapping(value = "/questions/{id}/form", method = RequestMethod.GET)
    public ModelAndView updateForm(@LoginUser User loginUser, @PathVariable String id) throws Exception {
        Question question = questionDao.findById(Long.parseLong(id));
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }

    @RequestMapping(value = "/questions/{id}", method = RequestMethod.POST)
    public ModelAndView update(@LoginUser User loginUser,
                               @PathVariable String id,
                               QuestionDto questionDto) throws Exception {
        Question question = questionDao.findById(Long.parseLong(id));
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question newQuestion = new Question(
                question.getWriter(),
                questionDto.getTitle(),
                questionDto.getContents());
        question.update(newQuestion);
        questionDao.update(question);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/questions/{id}/delete", method = RequestMethod.POST)
    public ModelAndView delete(@LoginUser User loginUser,
                               @PathVariable String id) throws Exception {
        long questionId = Long.parseLong(id);
        try {
            qnaService.deleteQuestion(questionId, loginUser);
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                    .addObject("answers", qnaService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
