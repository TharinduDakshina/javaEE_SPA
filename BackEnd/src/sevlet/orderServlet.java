package sevlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class orderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/thogakade/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = ds.getConnection();
            String option = req.getParameter("option");

            switch (option){
                case "LENGTH":
                    System.out.println("Start");
                    ResultSet rstOrderCount = connection.prepareStatement("SELECT COUNT(orderId) FROM `order`").executeQuery();
                    if (rstOrderCount.next()) {
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("status",200);
                        response.add("message","count order table length success");
                        response.add("data",rstOrderCount.getInt(1));
                        writer.print(response.build());
                    }
                    System.out.println("length : "+rstOrderCount.getInt(1));
                    System.out.println("End");
                    break;
            }
        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",500);
            response.add("message","Exception Error");
            response.add("status",e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        }

    }
}
