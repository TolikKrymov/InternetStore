package com.tolikkrymov.controllers;

import com.tolikkrymov.Helper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import com.tolikkrymov.entities.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class EditProductController {

    @GetMapping(value = "/edit/product")
    String index(@RequestParam(name = "id") Integer id, Model model) throws IOException {

        Product product = Resources.productJdbcRepository.findById(id);
        Helper.changeInformationToUserView(product);

        List<ProductType> productTypes = Resources.productTypeJdbcRepository.findAll();

        model.addAttribute("product", product);
        model.addAttribute("types", productTypes);

        return "edit-product";
    }

    @PostMapping(value = "/edit/product")
    String index(@ModelAttribute Product product) throws IOException {

        if (Resources.productJdbcRepository.findById(product.getId()) == null) {
            return "error";
        }

        Helper.changeInformationToJSON(product);

        Resources.productJdbcRepository.update(product);

        return "redirect:/product?id=" + product.getId();
    }
}
