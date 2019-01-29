package com.tolikkrymov.controllers;

import com.tolikkrymov.Helper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderInfoController {

    @GetMapping("/order/info")
    public String orderInfo(Model model) {

        List<Delivery> deliveries = Resources.orderJdbcRepository.findAllDeliveries();
        List<Payment> payments = Resources.orderJdbcRepository.findAllPayments();

        model.addAttribute("order", new Order());
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("payments", payments);

        return "order-info";
    }

    @PostMapping("/order/complete")
    public String orderInfo(Order order,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {

        List<Integer> productIds = Helper.getProductList(request);
        List<Integer> counts = Helper.getListFromCookie(request, "counts");

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Product product = Resources.productJdbcRepository.findById(productIds.get(i));
            if (product == null || product.getCount() < counts.get(i)) {
                return "redirect:/order/begin";
            }
            products.add(product);
        }

        Delivery delivery = Resources.orderJdbcRepository.findDeliveryById(order.getIdDeliveryType());
        if (delivery == null) {
            return "redirect:/order/begin";
        }

        Payment payment = Resources.orderJdbcRepository.findPaymentById(order.getIdPaymentType());
        if (payment == null) {
            return "redirect:/order/begin";
        }

        order.setId(Resources.orderJdbcRepository.getNewId());

        Long price = 0l;
        for (int i = 0; i < products.size(); i++) {
            price += products.get(i).getPrice() * counts.get(i);
            products.get(i).setCount(products.get(i).getCount() - counts.get(i));
            Resources.productJdbcRepository.update(products.get(i));
        }

        order.setPrice(price);

        order.setApplied(true);

        Resources.orderJdbcRepository.insert(order);

        for (int i = 0; i < products.size(); i++) {
            ProductOrder productOrder = new ProductOrder(order.getId(), products.get(i).getId(), (long)(counts.get(i)));
            Resources.orderJdbcRepository.insert(productOrder);
        }

        Helper.deleteCookie(response, "basket");
        Helper.deleteCookie(response, "counts");

        return "redirect:/orders";
    }
}
