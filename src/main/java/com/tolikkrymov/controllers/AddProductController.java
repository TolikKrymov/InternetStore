package com.tolikkrymov.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolikkrymov.ProductHelper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import com.tolikkrymov.entities.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class AddProductController {

    @GetMapping("/add/product")
    String index(Model model) {

        List<ProductType> productTypes = Resources.productTypeJdbcRepository.findAll();

        model.addAttribute("types", productTypes);
        model.addAttribute("product", new Product());

        return "add-product";
    }

    @PostMapping("/add/product")
    String index(Product product, Model model) throws IOException {

        int id = Resources.productJdbcRepository.getNewId();

        product.setId(id);

        ProductType productType = Resources.productTypeJdbcRepository.findById(product.getIdType());

        if (productType == null) {
            return "error";
        }

        ProductHelper.changeInformationToJSON(product);

        Resources.productJdbcRepository.insert(product);

        return "redirect:/product?id=" + product.getId();
    }
}
