package sevlet;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

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

                    PreparedStatement pst = connection.prepareStatement("select * FROM customer WHERE id=?");
                    pst.setString(1,searchId);
                    ResultSet resultSet = pst.executeQuery();

                    if (resultSet.next()) {
                        String cstId = resultSet.getString(1);
                        String cstName = resultSet.getString(2);
                        String cstAddress = resultSet.getString(3);
                        double cstSalary = resultSet.getDouble(4);

                        JsonObjectBuilder customerData = Json.createObjectBuilder();
                        customerData.add("id",cstId);
                        customerData.add("name",cstName);
                        customerData.add("address",cstAddress);
                        customerData.add("salary",cstSalary);

                        JsonObjectBuilder searchResponse = Json.createObjectBuilder();
                        searchResponse.add("status",200);
                        searchResponse.add("message","Customer found");
                        searchResponse.add("data",customerData.build());

                        writer.print(searchResponse.build());
                    }else {
                        JsonObjectBuilder searchResponse = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        searchResponse.add("status",404);
                        searchResponse.add("message","Customer is not found");
                        searchResponse.add("data","");
                        writer.print(searchResponse.build());
                    }

                    break;
                case "GETALL":

                    ResultSet rst = connection.prepareStatement("SELECT  * FROM customer").executeQuery();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()){
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        double salary = rst.getDouble(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id",id);
                        objectBuilder.add("name",name);
                        objectBuilder.add("address",address);
                        objectBuilder.add("salary",salary);
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
        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",400);
            response.add("message","Error");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerId = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        double customerSalary = Double.parseDouble(jsonObject.getString("salary"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pst = connection.prepareStatement("INSERT  into customer values(?,?,?,?)");
            pst.setString(1, customerId);
            pst.setString(2, customerName);
            pst.setString(3, customerAddress);
            pst.setDouble(4, customerSalary);

            if (pst.executeUpdate()>0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectBuilder.add("status",200);
                objectBuilder.add("message","Successfully Add...!");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status",400);
            objectBuilder.add("message","Error");
            objectBuilder.add("data",throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cstId = req.getParameter("cstId");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pst = connection.prepareStatement("Delete from Customer where id=?");
            pst.setObject(1, cstId);

            if (pst.executeUpdate()>0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",200);
                response.add("message","Customer Deleted..!x!");
                response.add("data","");
                writer.print(response.build());
            }else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                response.add("status",400);
                response.add("message","Wrong Id Insert");
                response.add("data","");
                writer.print(response.build());
            }
            connection.close();
        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",500);
            response.add("message","Error");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerId = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        double customerSalary = Double.parseDouble(jsonObject.getString("salary"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? where id=?");
            stm.setString(1,customerName);
            stm.setString(2,customerAddress);
            stm.setDouble(3,customerSalary);
            stm.setString(4,customerId);
            if (stm.executeUpdate()>0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Exception Error");
            objectBuilder.add("data", e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }
    }
}
