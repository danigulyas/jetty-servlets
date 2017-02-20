package com.danigu.blog.presentation;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.service.CommentService;
import com.danigu.blog.post.Post;
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
 * Responsible for creating and deleting comments.
 * @author dani
 */
@AllArgsConstructor
public class CommentServlet extends HttpServlet {
    private final CommentService service;
    private final PostService postService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("<html><body>"); // header

        switch (req.getPathInfo()) {
            case "/new":
                renderNewForm(req, resp);
                break;

            case "/delete":
                handleDelete(req, resp);
                break;

            default:
                renderNotFound(req, resp);
        }

        resp.getWriter().print("</body></html>"); // footer
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("<html><body>"); // header

        switch (req.getPathInfo()) {
            case "/new":
                handleNew(req, resp);
                break;

            default:
                renderNotFound(req, resp);
        }

        resp.getWriter().print("</body></html>"); // footer
    }

    /**
     * Renders a form for creating a new comment.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void renderNewForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long postId;

        try {
            postId = Long.valueOf(req.getParameter("postId"));
        } catch(Exception e) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter()
                    .println("<p> Bad request, please attach postId get param such as: <code>?postId=1</code> (long).</p>");
            return;
        }

        resp.setStatus(HttpStatus.OK_200);
        PrintWriter pw = resp.getWriter();
        String url = req.getContextPath() + req.getServletPath() + "/new";
        pw.println("<form method=\"post\" url=\"" + url + "\">");
        pw.println("<input type=\"hidden\" name=\"postId\" value=\"" + postId.toString() + "\"/>");
        pw.println("<input type=\"text\" name=\"content\" placeholder=\"Write your comment here\"/>");
        pw.println("<input type=\"submit\"/>");
        pw.println("</form>");
    }

    /**
     * Creates a new comment, displays error if post not found or if params are invalid, redirects to blog.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleNew(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long postId;
        String content;

        try {
            postId = Long.valueOf(req.getParameter("postId"));
            content = req.getParameter("content");

            if(content == null || content.length() == 0) throw new Exception("illegal argument");
        } catch(Exception ex) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Invalid request, please attach postId and content to the body.");
            return;
        }

        Post post = postService.getById(postId);
        if(post == null) {
            resp.setStatus(HttpStatus.NOT_FOUND_404);
            resp.getWriter().println("Post not found.");
            return;
        }

        service.add(post, content);

        redirect(req, resp, "/blog");
    }

    /**
     * Deletes a comment, displays error if not found or invalid param, redirects back to blog.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id;

        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch(Exception e) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Bad request, get parameter id should appear with a long value.");
            return;
        }

        try {
            service.deleteById(id);
        } catch(NotFoundException nfe) {
            resp.setStatus(HttpStatus.NOT_FOUND_404);
            resp.getWriter().println("Comment not found.");
            return;
        }

        redirect(req, resp, "/blog");
    }

    /**
     * Renders not found.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void renderNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.NOT_FOUND_404);
        resp.getWriter().println("Not found.");
    }

    /**
     * Location to redirect to another servlet.
     * @param req
     * @param resp
     * @param uri
     * @throws IOException
     */
    public void redirect(HttpServletRequest req, HttpServletResponse resp, String uri) throws IOException {
        String contextPath = req.getContextPath() == null ? "" : req.getContextPath();

        resp.sendRedirect(contextPath + uri);

    }
}
