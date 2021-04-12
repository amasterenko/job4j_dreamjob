package ru.job4j.dream.servlet;

import org.json.JSONObject;
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
import java.io.PrintWriter;

/**
 * The class saves a candidate to the store by serving POST-requests
 * and returns a candidate as a JSON object by serving GET-requests.
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
        store.save(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("cname"),
                        city
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("candidates", store.findAllCandidates());
            req.getRequestDispatcher("candidates.jsp").forward(req, resp);
        } else {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            try {
                Candidate candidate = store.findCandidateById(Integer.parseInt(id));
                JSONObject jsonResp = new JSONObject(candidate);
                jsonResp.put("city", new JSONObject(candidate.getCity()));
                PrintWriter writer = resp.getWriter();
                writer.print(jsonResp);
                writer.flush();
            } catch (Exception e) {
                LOG.error("Exception occurred: ", e);
            }
        }

    }
}
