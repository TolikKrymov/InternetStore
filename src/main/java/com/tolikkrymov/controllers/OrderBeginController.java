package com.tolikkrymov.controllers;

import com.tolikkrymov.Helper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderBeginController {

    @GetMapping("/order/begin")
    public String orderBegin(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model) throws IOException {

        List<Integer> productIds = Helper.getProductList(request);
        List<Product> products = new ArrayList<>();

        List<Integer> removedItems = new ArrayList<>();

        for (Integer productId : productIds) {
            Product product = Resources.productJdbcRepository.findById(productId);
            if (product == null || product.getCount() == 0) {
                removedItems.add(productId);
                continue;
            }
            products.add(product);
        }

        if (!removedItems.isEmpty()) {
            for (Integer removedItem : removedItems) {
                productIds.remove(removedItem);
            }
            Helper.setProductList(productIds, response);
        }

        model.addAttribute("products", products);
        model.addAttribute("has_error", !removedItems.isEmpty());

        return "order-begin";
    }

    @GetMapping("/order/counts")
    public String orderBeginEnd(@RequestParam(name="counts") String counts,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                Model model) throws IOException {

        List<Integer> productIds = Helper.getProductList(request);
        List<Product> products = new ArrayList<>();

        for (Integer productId : productIds) {
            Product product = Resources.productJdbcRepository.findById(productId);
            if (product == null || product.getCount() == 0) {
                return "redirect:/order/begin";
            }
            products.add(product);
        }

        String[] perCounts = counts.split(" ");
        List<Integer> iCounts = new ArrayList<>();
        for (String perCount : perCounts) {
            iCounts.add(Integer.valueOf(perCount));
        }

        if (products.size() != iCounts.size()) {
            return "redirect:/order/begin";
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCount() < iCounts.get(i)) {
                return "redirect:/order/begin";
            }
        }

        Helper.setListToCookie(iCounts, response, "counts");

        return "redirect:/order/info";
    }
}
