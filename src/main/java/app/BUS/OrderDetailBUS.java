package app.BUS;

import java.util.List;

import app.DAO.OrderDetailDAO;
import app.DTO.OrderDetail;

public class OrderDetailBUS {

    private final OrderDetailDAO dao;

    public OrderDetailBUS() {
        this.dao = new OrderDetailDAO();
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        return dao.getOrderDetailsByOrderId(orderId);
    }

    public int addOrderDetail(OrderDetail orderDetail) {
        return dao.addOrderDetail(orderDetail);
    }

    // public int updateOrderDetail(OrderDetail orderDetail) {
    // return dao.updateOrderDetail(orderDetail);
    // }
}
