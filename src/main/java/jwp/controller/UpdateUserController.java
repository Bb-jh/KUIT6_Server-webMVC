package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateUserController implements Controller {
    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            return "redirect:/";
        }

        User loginedUser = (User) session.getAttribute("user");
        String userId = req.getParameter("userId");
        if (!loginedUser.isSameUser(userId)) {
            return "redirect:/";
        }

        String newPassword = req.getParameter("password");
        String newName = req.getParameter("name");
        String newEmail = req.getParameter("email");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        if (user != null) {
            User updateUser = new User(userId, newPassword, newName, newEmail);
            user.update(updateUser);
        }

        System.out.println("user 정보 수정 완료");
        return "redirect:/user/list";
    }
/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            resp.sendRedirect("/");
            return;
        }

        User loginedUser = (User) session.getAttribute("user");
        String userId = req.getParameter("userId");
        if (!loginedUser.isSameUser(userId)) {
            resp.sendRedirect("/");
            return;
        }

        String newPassword = req.getParameter("password");
        String newName = req.getParameter("name");
        String newEmail = req.getParameter("email");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        if (user != null) {
            User updateUser = new User(userId, newPassword, newName, newEmail);
            user.update(updateUser);
        }

        System.out.println("user 정보 수정 완료");
        resp.sendRedirect("/user/list");
//        super.doPost(req, resp);
    }*/

}