package app.BUS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import app.DAO.OrderDAO;
import app.DTO.Order;
import app.DTO.OrderDetail;
import app.DTO.PaymentMethod;

public class OrderBUS {

    private final OrderDAO dao;
    private final OrderDetailBUS orderDetailBUS;

    public OrderBUS() {
        this.orderDetailBUS = new OrderDetailBUS();
        this.dao = new OrderDAO();
    }

    public int addOrderDetail(OrderDetail orderDetail) {
        return orderDetailBUS.addOrderDetail(orderDetail);
    }

    public List<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    public List<Order> getAllActiveOrders() {
        return dao.getAllActiveOrders();
    }

    public BigDecimal getTotalRevenue() {
        return dao.getTotalRevenue();
    }

    public BigDecimal getAverageOrderValue() {
        return dao.getAverageOrderValue();
    }

    public List<BigDecimal> getTotalRevenueByMonth(int year) {
        return dao.getTotalRevenueByMonth(year);
    }

    public Map<String, BigDecimal> getRevenueByCategory(int categoryId) {
        return dao.getRevenueByCategory(categoryId);
    }

    public List<Integer> getAllYear() {
        return dao.getAllYear();
    }

    public int addOrder(Order order) {
        return dao.addOrder(order);
    }

    public int updateOrder(Order order) {
        if (order.getOrderId() == 0) {
            return -1;
        }
        return dao.updateOrder(order);
    }

    public int deleteOrder(int orderId) {
        return dao.deleteOrder(orderId);
    }

    public int softDeleteOrder(int orderId) {
        return dao.softDeleteOrder(orderId);
    }

    public int restoreOrder(int orderId) {
        return dao.restoreOrder(orderId);
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

    public List<Order> filterOrder(String keyword,
            String emString,
            Date fromDate,
            Date toDate,
            BigDecimal fromPrice,
            BigDecimal toPrice,
            PaymentMethod payment,
            boolean sortByPriceAscending) {
        return dao.filterOrder(keyword,
                emString,
                fromDate,
                toDate,
                fromPrice,
                toPrice,
                payment,
                sortByPriceAscending);
    }
}
