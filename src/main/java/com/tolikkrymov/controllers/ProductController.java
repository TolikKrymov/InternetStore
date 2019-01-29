package com.tolikkrymov.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolikkrymov.ProductHelper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import com.tolikkrymov.entities.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/product")
    String getProduct(@RequestParam(name = "id") Integer id,
                      @RequestParam(name="action", required = false, defaultValue = "none") String action,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      Model model) throws IOException {

        List<Integer> productIds = ProductHelper.getProductList(request);

        Boolean hasInBasket = productIds.contains(id);

        if (!action.equals("none")) {
            if (action.equals("add") && !hasInBasket) {
                productIds.add(id);
            } else if (action.equals("remove") && hasInBasket) {
                productIds.remove(id);
            }
            try {
                ProductHelper.setProductList(productIds, response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return "redirect:/product?id=" + id;
        }

        Product product = Resources.productJdbcRepository.findById(id);
        LinkedHashMap<String, String> map;

        map = new ObjectMapper().readValue(
                product.getInformation(),
                new TypeReference<LinkedHashMap<String, String>>() { });

        long count = product.getCount();
        String s_count = (count != 0) ? "На складе " + count + " шт." : "Нет на складе";

        ProductType productType = Resources.productTypeJdbcRepository.findById(product.getIdType());

        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("price", product.getPrice());
        model.addAttribute("parameters", map);
        model.addAttribute("count", s_count);
        model.addAttribute("id_type", productType.getId());
        model.addAttribute("type", productType.getName());
        model.addAttribute("added", count == 0 ? "disabled" : hasInBasket ? "yes" : "no");

        return "product";
    }
}
