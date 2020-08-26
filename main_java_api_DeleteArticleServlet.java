package api;

import model.Article;
import model.ArticleDao;
import model.User;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessagePage("您尚未登录", "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");

        String articleIdStr = req.getParameter("articleId");
        if (articleIdStr == null|| "".equals(articleIdStr)) {
            String html = HtmlGenerator.getMessagePage("您要删除的文章id有误", "article");
            resp.getWriter().write(html);
            return;
        }
        ArticleDao articleDao = new ArticleDao();
        Article article = articleDao.selectById(Integer.parseInt(articleIdStr));
        if (article.getUserId() != user.getUserId()) {
            String html = HtmlGenerator.getMessagePage("您只能删除自己的文章", "article");
            resp.getWriter().write(html);
            return;
        }

        articleDao.delete(Integer.parseInt(articleIdStr));

        String html = HtmlGenerator.getMessagePage("删除成功", "article");
        resp.getWriter().write(html);

    }
}
