package com.tolikkrymov.controllers;

import com.tolikkrymov.Helper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrdersController {

    private final int ordersPerPage = 2;

    @GetMapping("/orders")
    public String index(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                        Model model) {

        List<Order> orders = Resources.orderJdbcRepository.findOrdersByPage(page, ordersPerPage);

        List<Delivery> deliveries = Resources.orderJdbcRepository.findAllDeliveries();
        List<Payment> payments = Resources.orderJdbcRepository.findAllPayments();

        for (Order order : orders) {
            for (Delivery delivery : deliveries) {
                if (delivery.getId() == order.getIdDeliveryType()) {
                    order.setDelivery(delivery);
                    break;
                }
            }

            for (Payment payment : payments) {
                if (payment.getId() == order.getIdPaymentType()) {
                    order.setPayment(payment);
                    break;
                }
            }
        }

        List<Product> products = Resources.productJdbcRepository.findAllProducts();

        for (Order order : orders) {
            List<ProductOrder> productOrders = Resources.orderJdbcRepository.findAllProductsByOrderId(order.getId());

            for (ProductOrder productOrder : productOrders) {
                for (Product product : products) {
                    if (product.getId() == productOrder.getIdProduct()) {
                        productOrder.setProduct(product);
                        break;
                    }
                }
            }

            order.setProductsOrders(productOrders);
        }

        int[] pages = Helper.getPages(page, Resources.orderJdbcRepository.getPageCount(ordersPerPage));

        model.addAttribute("orders", orders);
        model.addAttribute("pages", pages);

        return "orders";
    }
}
