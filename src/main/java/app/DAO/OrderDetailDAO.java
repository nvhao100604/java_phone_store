package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DTO.OrderDetail;
import app.database.DBConnect;

public class OrderDetailDAO {

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM chitiethoadon WHERE idHD = " + orderId;
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(rs.getInt("idHD"));
                orderDetail.setProductId(rs.getInt("idSP"));
                orderDetail.setQuantity(rs.getInt("SOLUONG"));
                orderDetail.setPrice(rs.getBigDecimal("GIABAN"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetails;
    }

    public int addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO chitiethoadon (idHD, idSP, SOLUONG, GIABAN) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setBigDecimal(4, orderDetail.getPrice());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
