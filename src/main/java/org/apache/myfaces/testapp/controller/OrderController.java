package org.apache.myfaces.testapp.controller;

import org.apache.myfaces.testapp.pojo.Order;
import org.apache.myfaces.tobago.model.SheetState;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class OrderController implements Serializable {

    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private Order order;
    private List<Order> listOrder;
    private SheetState selectedOrder;
    private Timestamp timestamp;

    @Resource(name = "jdbc/testRS")
    private DataSource ds;

    public OrderController() {
        selectedOrder = new SheetState();
        List<Integer> sel = new ArrayList<>();
        sel.add(0);
        selectedOrder.setSelectedRows(sel);
    }

    public List<Order> getListOrder() {
        listOrder = new ArrayList<>();
        try {
            con = ds.getConnection();
            st = con.createStatement();
            ResultSet result = st.executeQuery("SELECT id, customer, address, orderDate, status FROM testTable");
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setCustomer(result.getString("customer"));
                order.setAddress(result.getString("address"));
                order.setOrderDate(result.getTimestamp("orderDate"));
                order.setStatus(result.getString("status"));
                listOrder.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOrder;
    }

    public void newOrder(ActionEvent event) {
        order = new Order();
        order.setOrderDate(new Date());
    }

    public void editOrder(ActionEvent event) {
        List<Integer> selection = selectedOrder.getSelectedRows();
        order = listOrder.get(selection.get(0));
    }

    public void saveChangedOrder(ActionEvent event) {
        timestamp = new Timestamp(order.getOrderDate().getTime());
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("UPDATE testTable SET customer = ?, address = ?, orderDate = ?, status = ? " +
                    "WHERE id = ?");
            ps.setString(1, order.getCustomer());
            ps.setString(2, order.getAddress());
            ps.setTimestamp(3, timestamp);
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveOrder(ActionEvent event) {
        timestamp = new Timestamp(order.getOrderDate().getTime());
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("INSERT INTO testTable (customer, address, orderDate, status)" +
                    " VALUES (?,?,?,?)");
            ps.setString(1, order.getCustomer());
            ps.setString(2, order.getAddress());
            ps.setTimestamp(3, timestamp);
            ps.setString(4, order.getStatus());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SheetState getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(SheetState selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
}
