package jwp;

import jwp.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final RequestMapper requestMapper;

    public DispatcherServlet() {
        this.requestMapper = new RequestMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String method = req.getMethod();
        Controller controller = requestMapper.getMapping(requestURI, method);
        if (controller == null) {
            String path = req.getRequestURI().substring(req.getContextPath().length());
            System.out.println("[Dispatcher] No handler for " + req.getMethod() + " " + path);
            return;
        }
        String view = controller.process(req, resp);
        renderView(req, resp, view);
    }

    private void renderView(HttpServletRequest req, HttpServletResponse resp, String view) throws IOException, ServletException {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("[Dispatcher] handler for " + req.getMethod() + " " + path);

        if (view.startsWith("redirect")) {
            resp.sendRedirect(view.substring("redirect:".length()));
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher(view);
        rd.forward(req, resp);
    }
}
