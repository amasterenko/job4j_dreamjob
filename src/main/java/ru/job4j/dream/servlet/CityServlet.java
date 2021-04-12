package ru.job4j.dream.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * The class returns all cities as a JSON object by serving GET-requests.
 * @author AndrewMs
 * @version 1.0
 */
public class CityServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CityServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Store store = PsqlStore.instOf();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            JSONArray jsonArray = new JSONArray(store.findAllCities());
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("cities", jsonArray);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonObj);
            writer.flush();
        } catch (Exception e) {
            LOG.error("Exception occurred: ", e);
        }
    }
}
