package com.danigu.blog.presentation;

import com.danigu.blog.post.service.PostService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dani
 */
@AllArgsConstructor
public class PostServlet extends HttpServlet {
    private final PostService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        switch (path) {
            case "/new":
                renderNewPost(req, resp);
                break;

            case "/delete":
                handleDelete(req, resp);
                break;

            default:
                renderNotFound(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        switch (path) {
            case "/new":
                handleNewPost(req, resp);
                break;

            default:
                renderNotFound(req, resp);
        }
    }

    /**
     * Renders the form to create a new post.
     * @param req
     * @param resp
     */
    public void renderNewPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.OK_200);
        PrintWriter pw = resp.getWriter();

        pw.println("<html><body>");
        pw.printf("<form method=\"post\" url=\"%s\">", getServletUri(req) + "/new");
        pw.println("<input type=\"string\" name=\"name\" placeholder=\"Write the name of your post here\"/>");
        pw.println("<input type=\"string\" name=\"content\" placeholder=\"Write your post here\"/>");
        pw.println("<input type=\"submit\"/>");
        pw.println("</form>");
        pw.println("</body></html>");
    }

    /**
     * Handles delete request for post.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long postId;

        try {
            postId = Long.valueOf(req.getParameter("id"));
        } catch(Exception e) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Bad request, get param id must present with a value type of long.");
            return;
        }

        try {
            service.deleteById(postId);
        } catch(NotFoundException nfe) {
            resp.setStatus(HttpStatus.NOT_FOUND_404);
            resp.getWriter().println("Post not found.");
        }

        redirect(req, resp, "/blog");
    }

    /**
     * Handles new post request.
     * @param req
     * @param resp
     */
    public void handleNewPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String content = req.getParameter("content");

        if(content == null || content.length() == 0 || name == null || name.length() == 0) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter()
                    .println("Bad request, post body must have to fields [name, content] both with at least one characters.");

            return;
        }

        service.add(name, content);

        redirect(req, resp, "/blog");
    }

    /**
     * Helpers
     */

    public void redirect(HttpServletRequest req, HttpServletResponse resp, String uri) throws IOException {
        String contextPath = req.getContextPath() == null ? "" : req.getContextPath();
        resp.sendRedirect(contextPath + uri);
    }

    public String getServletUri(HttpServletRequest req) {
        return (req.getContextPath() == null ? "" : req.getContextPath()) + req.getServletPath();
    }

    public void renderNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.NOT_FOUND_404);
        resp.getWriter().println("Not found.");
    }
}
