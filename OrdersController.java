package com.legacy.orders;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Legacy-style JAX-RS API
@Path("/orders")
public class OrderController {

    private static List<Order> orders = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getAllOrders() {
        return orders;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("id") int id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        throw new NotFoundException("Order not found with id: " + id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        orders.add(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("id") int id, Order updatedOrder) {
        for (Order o : orders) {
            if (o.getId() == id) {
                o.setCustomerName(updatedOrder.getCustomerName());
                o.setAmount(updatedOrder.getAmount());
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        orders.removeIf(o -> o.getId() == id);
        return Response.ok().build();
    }
}

class Order {
    private int id;
    private String customerName;
    private double amount;

    public Order() {}

    public Order(int id, String customerName, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }

    public void setId(int id) { this.id = id; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAmount(double amount) { this.amount = amount; }
}
