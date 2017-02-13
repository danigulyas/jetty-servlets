package blog;

import blog.post.PostDTO;
import blog.post.PostRepository;
import blog.post.PostService;
import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author dani
 */
public class HelloServlet extends HttpServlet {
    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getServletPath();

        printHeader(res);
        switch (path) {
            case "/new":
                displayNewPostForm(req, res);
                break;
            default:
                printHomePage(req, res);
        }
        printFooter(res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getServletPath();

        printHeader(res);
        switch (path) {
            case "/new":
                handleNewPost(req, res);
                break;
            default:
                res.sendRedirect("/blog/");
                break;
        }
        printFooter(res);
    }

    private void printHomePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setStatus(200);

        PrintWriter w = res.getWriter();
        w.println("<h1> Hi ^^ </h1>");
        w.println("<p> The amount of posts is: " + postService.count() + ".</p>");

        w.println("<hr><ul>");
        List<PostDTO> posts = postService.getAll();
        for(PostDTO post : posts) {
            String content = post.getContent();
            content = content.length() > 20 ? content.substring(0, 20) + "..." : content;

            w.println("<li>" + content + "</li>");
        }
        w.println("</ul>");
    }

    private void displayNewPostForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter w = res.getWriter();
        w.println("Hello, this is the form for new posts.");
        w.println("<form method=\"post\" url=\"/blog/new\">");
        w.println("<input type=\"string\" name=\"content\" placeholder=\"Write your content here.\"/><br>");
        w.println("<input type=\"submit\"/>");
        w.println("</form>");
        res.setStatus(200);
    }

    private void handleNewPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();

        try {
            postService.createNewPost(params);
        } catch(InvalidArgumentException e) {

        }

        res.sendRedirect("/blog/");
    }

    private void printHeader(HttpServletResponse res) throws IOException {
        PrintWriter w = res.getWriter();
        w.println("<html><body>");
    }

    private void printFooter(HttpServletResponse res) throws IOException {
        PrintWriter w = res.getWriter();
        w.println("</body></html>");
    }
}
