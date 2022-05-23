package sevlet;

import entity.orderDetail;

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
import java.sql.*;

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
                case "GetOrderId":
                    System.out.println("Start");
                    ResultSet rst = connection.prepareStatement("Select count(*) from `order`").executeQuery();
                    if (rst.next()){
                        if (rst.getInt(1)==0) {
                            JsonObjectBuilder response = Json.createObjectBuilder();
                            resp.setStatus(HttpServletResponse.SC_CREATED);
                            response.add("status", 404);
                            response.add("message", "Generate first orderId");
                            response.add("data", "0-001");
                            writer.print(response.build());
                        }else {
                            ResultSet rstOrderCount = connection.prepareStatement("SELECT `orderId` FROM `order` ORDER BY `orderId` DESC LIMIT 1").executeQuery();
                            if (rstOrderCount.next()) {
                                String lastId = rstOrderCount.getString(1);
                                int value = Integer.parseInt(lastId.split("-")[1]);
                                value++;
                                String nextOrderId;
                                if (value <= 9) {
                                    nextOrderId = "0-00" + value;
                                } else if (value <= 99) {
                                    nextOrderId = "0-0" + value;
                                } else {
                                    nextOrderId = "0-" + value;
                                }

                                JsonObjectBuilder response = Json.createObjectBuilder();
                                resp.setStatus(HttpServletResponse.SC_CREATED);
                                response.add("status", 200);
                                response.add("message", "Generate next orderId");
                                response.add("data", nextOrderId);
                                writer.print(response.build());
                            }
                        }
                    }
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String oId = jsonObject.getString("oId");
        Date date =Date.valueOf(jsonObject.getString("date")) ;
        int subTotal = jsonObject.getInt("subTotal");
        String cstId = jsonObject.getString("cstId");
        String currentTime = jsonObject.getString("currentTime");
        JsonArray saveOrderDeatiales = jsonObject.getJsonArray("saveOrderDeatiales");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println("request Eka Awa danata ok");

        try {
            Connection connection = ds.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pst = connection.prepareStatement("INSERT INTO `order` values(?,?,?,?,?)");
            pst.setString(1,oId);
            pst.setString(2,cstId);
            pst.setDate(3,date);
            pst.setString(4,currentTime);
            pst.setDouble(5,subTotal);

            if (pst.executeUpdate()>0) {
                System.out.println("order eketa data damma");
                for (JsonValue dataArray:saveOrderDeatiales
                ) {
                    orderDetail orderDetail = new orderDetail(dataArray.asJsonObject().getString("orderId"),cstId,dataArray.asJsonObject().getString("itemCode"),dataArray.asJsonObject().getInt("qty"),dataArray.asJsonObject().getInt("price"));
                    PreparedStatement stm = connection.prepareStatement("INSERT INTO `order detail` values (?,?,?,?)");
                    stm.setString(1,orderDetail.getiId());
                    stm.setString(2,orderDetail.getoId());
                    stm.setInt(3,orderDetail.getQty());
                    stm.setDouble(4,orderDetail.getPrice());
                    if (stm.executeUpdate()>0) {
                        System.out.println("Badu Wada ");
                        connection.commit();
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("status",200);
                        objectBuilder.add("message","OrderDetail and order data add Successfully");
                        objectBuilder.add("data","");
                        writer.print(objectBuilder.build());
                    }else {
                        connection.rollback();
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        objectBuilder.add("status",500);
                        objectBuilder.add("message","OrderDetail and order data add unSuccessfully");
                        objectBuilder.add("data","");
                        writer.print(objectBuilder.build());
                    }
                }
               connection.commit();
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status",400);
                objectBuilder.add("message","order data add unSuccessfully");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status",500);
            objectBuilder.add("message","Exception Error");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }

    }

}
