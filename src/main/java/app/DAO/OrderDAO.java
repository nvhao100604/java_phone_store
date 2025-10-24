package app.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Order;
import app.DTO.PaymentMethod;
import app.database.DBConnect;

public class OrderDAO {

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("idHD"));
                order.setAccountId(rs.getInt("idTK"));
                order.setCustomerId(rs.getInt("idkh"));
                order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                order.setPurchaseDate(rs.getDate("NGAYMUA"));
                order.setAddress(rs.getString("DIACHI"));
                order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                order.setStatus(rs.getInt("TRANGTHAI"));
                order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getAllActiveOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("idHD"));
                order.setAccountId(rs.getInt("idTK"));
                order.setCustomerId(rs.getInt("idkh"));
                order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                order.setPurchaseDate(rs.getDate("NGAYMUA"));
                order.setAddress(rs.getString("DIACHI"));
                order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                order.setStatus(rs.getInt("TRANGTHAI"));
                order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(d.THANHTIEN) AS TONGDOANHTHU FROM `donhang` d";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            if (rs.next())
                return rs.getBigDecimal(1);
            else
                return BigDecimal.ZERO;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    public List<BigDecimal> getTotalRevenueByMonth(int year) {
        List<BigDecimal> revenues = new ArrayList<>();
        String sql = "WITH months AS (\r\n" +
                "  SELECT 1 AS month\r\n" +
                "  UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" +
                "  UNION SELECT 5 UNION SELECT 6 UNION SELECT 7\r\n" +
                "  UNION SELECT 8 UNION SELECT 9 UNION SELECT 10\r\n" +
                "  UNION SELECT 11 UNION SELECT 12\r\n" +
                ")\r\n" +
                "SELECT \r\n" +
                "    IFNULL(SUM(d.THANHTIEN), 0) AS TongDoanhThu\r\n" +
                "FROM months m\r\n" +
                "LEFT JOIN donhang d \r\n" +
                "    ON MONTH(d.NGAYMUA) = m.month AND YEAR(d.NGAYMUA) = ?\r\n" +
                "GROUP BY m.month\r\n" +
                "ORDER BY m.month;";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                revenues.add(rs.getBigDecimal(1));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return revenues;
    }

    public List<Integer> getAllYear() {
        List<Integer> months = new ArrayList<>();
        String sql = "SELECT YEAR(d.NGAYMUA) AS YEAR FROM `donhang` d GROUP BY YEAR(d.NGAYMUA)";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            while (rs.next())
                months.add(rs.getInt(1));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return months;
    }

    public int addOrder(Order order) {
        String sql = "INSERT INTO donhang (idTK, idkh, THANHTIEN, NGAYMUA, DIACHI, MAKHUYENMAI, PTTHANHTOAN) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getAccountId());
            ps.setInt(2, order.getCustomerId());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setDate(4, new java.sql.Date(order.getPurchaseDate().getTime()));
            ps.setString(5, order.getAddress());
            if (order.getPromotionCode() != null) {
                ps.setInt(6, order.getPromotionCode());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.setString(8, order.getPaymentId().name());

            int rowsAffect = ps.executeUpdate();
            if (rowsAffect == 0) {
                throw new Exception("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new Exception("Creating order failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateOrder(Order order) {
        String sql = "UPDATE donhang SET idTK=?, idkh=?, THANHTIEN=?, NGAYMUA=?, DIACHI=?, MAKHUYENMAI=?, TRANGTHAI=?, PTTHANHTOAN=? WHERE idHD=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, order.getAccountId());
            ps.setInt(2, order.getCustomerId());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setDate(4, new java.sql.Date(order.getPurchaseDate().getTime()));
            ps.setString(5, order.getAddress());
            if (order.getPromotionCode() != null) {
                ps.setInt(6, order.getPromotionCode());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.setInt(7, order.getStatus());
            ps.setString(8, order.getPaymentId().name());
            ps.setInt(9, order.getOrderId());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteOrder(int orderId) {
        String sql = "DELETE FROM donhang WHERE idHD=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int softDeleteOrder(int orderId) {
        String sql = "UPDATE donhang SET TRANGTHAI=0 WHERE idHD=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int restoreOrder(int orderId) {
        String sql = "UPDATE donhang SET TRANGTHAI=1 WHERE idHD=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM donhang WHERE idHD=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    return order;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getOrdersByAccountId(int accountId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE idTK=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> searchOrders(String keyword) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE (idHD LIKE ? OR idTK LIKE ? OR idkh LIKE ? OR THANHTIEN LIKE ? OR NGAYMUA LIKE ? OR DIACHI LIKE ? OR MAKHUYENMAI LIKE ? OR TRANGTHAI LIKE ? OR PTTHANHTOAN LIKE ?) AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 9; i++) {
                ps.setString(i, likeKeyword);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE idkh=? AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByDate(String startDate, String endDate) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE NGAYMUA BETWEEN ? AND ? AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrderByTotal(java.math.BigDecimal minTotal, java.math.BigDecimal maxTotal) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE THANHTIEN BETWEEN ? AND ? AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, minTotal);
            ps.setBigDecimal(2, maxTotal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("idHD"));
                    order.setAccountId(rs.getInt("idTK"));
                    order.setCustomerId(rs.getInt("idkh"));
                    order.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                    order.setPurchaseDate(rs.getDate("NGAYMUA"));
                    order.setAddress(rs.getString("DIACHI"));
                    order.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                    order.setStatus(rs.getInt("TRANGTHAI"));
                    order.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> sortOrdersByTotal(boolean ascending) {
        List<Order> orders = new ArrayList<>();
        String order = ascending ? "ASC" : "DESC";
        String sql = "SELECT * FROM donhang WHERE TRANGTHAI=1 ORDER BY THANHTIEN " + order;
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order orderObj = new Order();
                orderObj.setOrderId(rs.getInt("idHD"));
                orderObj.setAccountId(rs.getInt("idTK"));
                orderObj.setCustomerId(rs.getInt("idkh"));
                orderObj.setTotalAmount(rs.getBigDecimal("THANHTIEN"));
                orderObj.setPurchaseDate(rs.getDate("NGAYMUA"));
                orderObj.setAddress(rs.getString("DIACHI"));
                orderObj.setPromotionCode(rs.getObject("MAKHUYENMAI") == null ? null : rs.getInt("MAKHUYENMAI"));
                orderObj.setStatus(rs.getInt("TRANGTHAI"));
                orderObj.setPaymentId(PaymentMethod.valueOf(rs.getString("PTTHANHTOAN")));
                orders.add(orderObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
