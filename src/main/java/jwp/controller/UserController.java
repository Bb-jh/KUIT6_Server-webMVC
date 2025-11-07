package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) throws Exception {
/*        System.out.println("user = " + user);
        System.out.println("userId = " + user.getUserId());
        System.out.println("password = " + user.getPassword());
        System.out.println("name = " + user.getName());*/
        userDao.insert(user);
        System.out.println("user 회원가입 완료");
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) throws Exception {
        if (UserSessionUtils.isLogined(session)) {
            model.addAttribute("users", userDao.findAll());
            return "user/list";
            // properties로 prefix=/와  suffix=.jsp 정의해서 뒤에 빼도 됨
        }
        return "redirect:/user/loginForm";
    }


    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) throws Exception {
        User loginUser = new User(userId, password);
        User user = userDao.findByUserId(userId);

        if (user != null && user.isSameUser(loginUser)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/login")
    public String login(HttpSession session) throws Exception {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.removeAttribute("user");
        return "redirect:/";
    }

    // update, 회원가입 중복 튕김, question 수정,삭제,등록안됨
    @PostMapping("/update")
    public String update(@ModelAttribute User modifiedUser) throws Exception {
/*        System.out.println(modifiedUser.getUserId());
        System.out.println(modifiedUser.getName());
        System.out.println(modifiedUser.getPassword());
        System.out.println(modifiedUser.getEmail());*/
        userDao.update(modifiedUser);
        return "redirect:/user/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(@ModelAttribute User updateUser, HttpSession session) throws Exception {
        User user = userDao.findByUserId(updateUser.getUserId());
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.equals(value)) {            // 수정되는 user와 수정하는 user가 동일한 경우
                return "user/updateForm";
            }
        }
        return "redirect:/";
    }


    @GetMapping("/form")
    public String formForwarding() {
        return "user/form";
    }
    @GetMapping("/loginForm")
    public String loginFormForwarding() {
        return "user/login";
    }
    @GetMapping("/loginFailed")
    public String loginFailForwarding() {
        return "user/loginFailed";
    }
}
