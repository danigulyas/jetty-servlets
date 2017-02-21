package com.danigu.blog;

import com.danigu.blog.web.BlogServlet;
import com.danigu.blog.web.CommentServlet;
import com.danigu.blog.web.PostServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author dani
 */
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        ServiceLocator locator = new ServiceLocator();

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/v1/");
        configureServlets(locator, context);

        server.setHandler(context);
        server.start();
        server.join();
    }

    public static void configureServlets(ServiceLocator locator, ServletContextHandler context) {
        CommentServlet commentServlet = new CommentServlet(locator.getCommentService(), locator.getPostService());
        context.addServlet(new ServletHolder(commentServlet), "/comment/*");

        PostServlet postServlet = new PostServlet(locator.getPostService());
        context.addServlet(new ServletHolder(postServlet), "/post/*");

        BlogServlet blogServlet = new BlogServlet(locator.getPostService(), locator.getCommentService());
        context.addServlet(new ServletHolder(blogServlet), "/blog/*");
    }
}
