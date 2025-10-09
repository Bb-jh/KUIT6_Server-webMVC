package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginUserController implements Controller {
    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> { return getLoginForm(req, resp); }
            case "POST" -> { return doLogin(req, resp); }
            default -> { return ""; }
        }
    }

    private String getLoginForm(HttpServletRequest req, HttpServletResponse resp) {
        return "/user/login.jsp";
    }

    private String doLogin(HttpServletRequest req, HttpServletResponse resp) {
        User user = MemoryUserRepository.getInstance().findUserById(req.getParameter("userId"));

        if (user == null || !user.matchPassword(req.getParameter("password"))) {
            System.out.println("user 로그인 실패");
            return "redirect:/user/loginFailed.jsp";
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        System.out.println("user 로그인 성공");
        return "redirect:/";
    }



/*    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = MemoryUserRepository.getInstance().findUserById(req.getParameter("userId"));

        if (user == null || !user.matchPassword(req.getParameter("password"))) {
            System.out.println("user 로그인 실패");
            resp.sendRedirect("/user/loginFailed.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        System.out.println("user 로그인 성공");
        resp.sendRedirect("/");
//        super.doPost(req, resp);
    }*/
}
