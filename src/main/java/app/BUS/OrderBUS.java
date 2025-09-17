package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.OrderDAO;
import app.DTO.Order;

public class OrderBUS {

    private final OrderDAO dao;

    public OrderBUS() {
        this.dao = new OrderDAO();
    }

    public List<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    public List<Order> getAllActiveOrders() {
        return dao.getAllActiveOrders();
    }

    public int addOrder(Order order) {
        return dao.addOrder(order);
    }

    public int updateOrder(Order order) {
        return dao.updateOrder(order);
    }

    public int deleteOrder(int orderId) {
        return dao.deleteOrder(orderId);
    }

    public int softDeleteOrder(int orderId) {
        return dao.softDeleteOrder(orderId);
    }

    public Order getOrderById(int orderId) {
        return dao.getOrderById(orderId);
    }

    public List<Order> getOrdersByAccountId(int accountId) {
        return dao.getOrdersByAccountId(accountId);
    }

    public List<Order> searchOrders(String keyword) {
        return dao.searchOrders(keyword);
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return dao.getOrdersByCustomerId(customerId);
    }

    public List<Order> getOrdersByDate(String startDate, String endDate) {
        return dao.getOrdersByDate(startDate, endDate);
    }

    public List<Order> getOrderByTotal(BigDecimal minTotal, BigDecimal maxTotal) {
        return dao.getOrderByTotal(minTotal, maxTotal);
    }

    public List<Order> sortOrdersByTotal(boolean ascending) {
        return dao.sortOrdersByTotal(ascending);
    }
}
