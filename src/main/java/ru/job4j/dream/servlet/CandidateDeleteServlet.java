package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class deletes a candidate with specified id.
 * @author AndrewMs
 * @version 1.0
 */
public class CandidateDeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PsqlStore.instOf().deleteCandidateById(Integer.parseInt(req.getParameter("id")));
        req.getRequestDispatcher("/delete_photo.do").forward(req, resp);
    }
}
