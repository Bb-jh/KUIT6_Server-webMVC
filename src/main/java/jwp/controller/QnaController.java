package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final QuestionDao questionDao;

    @GetMapping("/form")
    public String createForm(HttpSession session) throws Exception {
        if (UserSessionUtils.isLogined(session)) {          // 회원만 질문 등록 가능
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam int questionId, HttpSession session, Model model) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }
        Question question = questionDao.findByQuestionId(questionId);
        model.addAttribute("question", question);
        User user = UserSessionUtils.getUserFromSession(session);

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        questionDao.update(question);
        return "qna/updateForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int questionId, HttpSession session) throws Exception {
        questionDao.delete(questionId);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Question reqQuestion, HttpSession session) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/users/loginForm";
        }

        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionDao.findByQuestionId(reqQuestion.getQuestionId());

        System.out.println(question.getQuestionId());
        System.out.println(question.getWriter());
        System.out.println(question.getTitle());
        System.out.println(question.getContents());

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(reqQuestion.getTitle(), reqQuestion.getContents());
        questionDao.update(question);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Question question) throws Exception {
        Question savedQuestion = questionDao.insert(question);
        System.out.println("saved question id= " + savedQuestion.getQuestionId());
        return "redirect:/";
    }

    @GetMapping("/show")
    public String show(@RequestParam int questionId, Model model) throws Exception {
        Question question = questionDao.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }

}
