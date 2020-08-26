package api;

import model.User;
import model.UserDao;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if (name == null || "".equals(name) || password == null || "".equals(password)) {
            String html = HtmlGenerator.getMessagePage("用户名或密码为空", "login.html");
            resp.getWriter().write(html);
            return;
        }

        UserDao userDao = new UserDao();
        User user = userDao.selectByName(name);
        if (user == null || !password.equals(user.getPassword())) {
            String html = HtmlGenerator.getMessagePage("用户民或密码错误", "login.html");
            resp.getWriter().write(html);
            return;
        }

        HttpSession httpSession = req.getSession(true);
        httpSession.setAttribute("user", user);
        String html = HtmlGenerator.getMessagePage("登录成功", "article");
        resp.getWriter().write(html);
    }
}
