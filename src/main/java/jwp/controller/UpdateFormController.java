package jwp.controller;

import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/updateForm")
public class UpdateFormController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            System.out.println("사용자 정보 수정 폼 요청 실패");
            resp.sendRedirect("/");
            return;
        }
        String requestId = req.getParameter("userId");
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || !sessionUser.isSameUser(requestId)) {
            System.out.println("사용자 정보 수정 폼 요청 실패");
            resp.sendRedirect("/");
            return;
        }
        System.out.println("사용자 정보 수정 폼 요청 성공");

        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
//        super.doGet(req, resp);
    }
}
