package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class saves a candidate to the store by serving POST-requests
 * and returns candidates by serving GET-requests.
 * @author AndrewMs
 * @version 1.0
 */
public class CandidateServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Store store = PsqlStore.instOf();
        req.setCharacterEncoding("UTF-8");
        City city = store.findCityById(Integer.parseInt(req.getParameter("cityId")));
        String name = req.getParameter("cname");
        String id = req.getParameter("id");
        if (id.length() == 0) {
            id = "0";
        }
        store.save(
                new Candidate(
                        Integer.parseInt(id),
                        name,
                        city
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("candidates", store.findAllCandidates());
            req.getRequestDispatcher("candidates.jsp").forward(req, resp);
        } else {
            Candidate candidate = store.findCandidateById(Integer.parseInt(id));
            req.setAttribute("candidate", candidate);
            req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
        }

    }
}
