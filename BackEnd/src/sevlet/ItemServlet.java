package sevlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/thogakade/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            String option = req.getParameter("option");
            String searchId = req.getParameter("searchId");
            Connection connection = ds.getConnection();

            switch (option) {

                case "SEARCH":

                    PreparedStatement pst = connection.prepareStatement("select * FROM item WHERE id=?");
                    pst.setString(1,searchId);
                    ResultSet resultSet = pst.executeQuery();

                    if (resultSet.next()) {
                        String itemId = resultSet.getString(1);
                        String description = resultSet.getString(2);
                        int qtyOnHand = resultSet.getInt(3);
                        double unitePrice = resultSet.getDouble(4);

                        JsonObjectBuilder itemData = Json.createObjectBuilder();
                        itemData.add("itemId",itemId);
                        itemData.add("description",description);
                        itemData.add("qty",qtyOnHand);
                        itemData.add("unitePrice",unitePrice);

                        JsonObjectBuilder searchResponse = Json.createObjectBuilder();
                        searchResponse.add("status",200);
                        searchResponse.add("message","Item found");
                        searchResponse.add("data",itemData.build());

                        writer.print(searchResponse.build());
                    }else {
                        JsonObjectBuilder searchResponse = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        searchResponse.add("status",404);
                        searchResponse.add("message","Item is not found");
                        searchResponse.add("data","");
                        writer.print(searchResponse.build());
                    }
                    break;
                case "GETALL":

                    ResultSet rst = connection.prepareStatement("SELECT  * FROM item").executeQuery();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()){
                        String itemId = rst.getString(1);
                        String description = rst.getString(2);
                        int quantityOnHand = rst.getInt(3);
                        double unitePrice = rst.getDouble(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("itemId",itemId);
                        objectBuilder.add("description",description);
                        objectBuilder.add("qty",quantityOnHand);
                        objectBuilder.add("unitePrice",unitePrice);
                        arrayBuilder.add(objectBuilder.build());
                    }

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status",200);
                    response.add("message","Done");
                    response.add("data",arrayBuilder.build());
                    writer.print(response.build());
                    break;
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",400);
            response.add("message","Error");
            response.add("data",throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }
}
