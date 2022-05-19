package sevlet;

import javax.annotation.Resource;
import javax.json.*;
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

                    PreparedStatement pst = connection.prepareStatement("select * FROM item WHERE code=?");
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
                case "GetCustomerID":
                    ResultSet result = connection.prepareStatement("SELECT  code FROM item").executeQuery();
                    JsonArrayBuilder cstIdArray = Json.createArrayBuilder();
                    while (result.next()){
                        String cstId = result.getString(1);

                        JsonObjectBuilder cstIdObject = Json.createObjectBuilder();
                        cstIdObject.add("id",cstId);
                        cstIdArray.add(cstIdObject.build());
                    }

                    JsonObjectBuilder responseGetCstId = Json.createObjectBuilder();
                    responseGetCstId.add("status",200);
                    responseGetCstId.add("message","Done");
                    responseGetCstId.add("data",cstIdArray.build());
                    writer.print(responseGetCstId.build());
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader itemReader = Json.createReader(req.getReader());
        JsonObject jsonObject = itemReader.readObject();
        String itemId = jsonObject.getString("itemId");
        String description = jsonObject.getString("description");
        int qty = Integer.parseInt(jsonObject.getString("qty"));
        double unitePrice = Double.parseDouble(jsonObject.getString("unitePrice"));


        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pst = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pst.setString(1,itemId);
            pst.setString(2,description);
            pst.setInt(3,qty);
            pst.setDouble(4,unitePrice);

            if (pst.executeUpdate()>0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectBuilder.add("status",200);
                objectBuilder.add("message","Successfully Add....!");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status",501);
                objectBuilder.add("message","Error");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status",400);
            objectBuilder.add("message","Error Exception");
            objectBuilder.add("data",throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("itemId");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pst = connection.prepareStatement("Delete from item where code=?");
            pst.setString(1, itemId);

            if (pst.executeUpdate()>0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",200);
                response.add("message","Item Deleted..!x!");
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
        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status",500);
            response.add("message","Error");
            response.add("data",throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            String option = req.getParameter("option");
            Connection connection = ds.getConnection();
            switch (option){
                case "updateItem":
                    JsonReader itemReader = Json.createReader(req.getReader());
                    JsonObject jsonObject = itemReader.readObject();
                    String itemId = jsonObject.getString("itemId");
                    String description = jsonObject.getString("description");
                    int qty = Integer.parseInt(jsonObject.getString("qty"));
                    double unitePrice = Double.parseDouble(jsonObject.getString("unitePrice"));


                    PreparedStatement stm = connection.prepareStatement("UPDATE item SET description=?,qtyOnHand=?,unitPrice=? where code=?");
                    stm.setString(1,description);
                    stm.setInt(2,qty);
                    stm.setDouble(3,unitePrice);
                    stm.setString(4,itemId);
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
                 break;

                case "updateQTY":
                    JsonReader itemQtyReader = Json.createReader(req.getReader());
                    JsonObject jsonItemObject = itemQtyReader.readObject();
                    String updateItemId = jsonItemObject.getString("itemId");
                    int updateQty = jsonItemObject.getInt("updateQty");

                    PreparedStatement pst = connection.prepareStatement("update item set qtyOnHand=? where code=?");
                    pst.setInt(1,updateQty);
                    pst.setString(2,updateItemId);
                    if (pst.executeUpdate()>0) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("status", 200);
                        objectBuilder.add("message", "Successfully Updated Qty");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    }else {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        objectBuilder.add("status", 400);
                        objectBuilder.add("message", "Qty Update Failed");
                        objectBuilder.add("data", "");
                        writer.print(objectBuilder.build());
                    }
                    break;
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
