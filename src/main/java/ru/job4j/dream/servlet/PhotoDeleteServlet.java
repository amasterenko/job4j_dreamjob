package ru.job4j.dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
/**
 * The class deletes candidates' photos by serving GET-requests.
 * @author AndrewMs
 * @version 1.0
 */
public class PhotoDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        for (File file : new File("C:\\images\\").listFiles()) {
            String fileName = file.getName();
            if (id.equals(fileName.substring(0, fileName.indexOf(".")))) {
                file.delete();
                break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}