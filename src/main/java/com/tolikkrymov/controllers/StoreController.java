package com.tolikkrymov.controllers;

import com.tolikkrymov.ProductHelper;
import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import com.tolikkrymov.entities.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StoreController {

    private final int defaultProductsPerPage = 2;

    @GetMapping("/store")
    String index(@RequestParam Map<String, String> params,
                 HttpServletRequest request,
                 Model model) throws IOException {

        String s_page = params.get("page");
        int page = (s_page != null) ? Integer.valueOf(s_page) : 1;

        String s_id_type = params.get("id_type");
        List<Product> productsOnPage;
        int lastPage;
        if (s_id_type == null) {
            productsOnPage = Resources.productJdbcRepository.findByPage(page, defaultProductsPerPage);
            lastPage = Resources.productJdbcRepository.getPageCount(defaultProductsPerPage);
        }
        else {
            long idType = Long.valueOf(s_id_type);
            productsOnPage = Resources.productJdbcRepository.findByPageAndIdType(page, defaultProductsPerPage, idType);
            lastPage = Resources.productJdbcRepository.getPageCountByIdType(defaultProductsPerPage, idType);
        }

        List<ProductType> productTypes = Resources.productTypeJdbcRepository.findAll();

        List<Integer> productIds = ProductHelper.getProductList(request);
        List<Product> productsInBasket = new ArrayList<>();
        for (int productId : productIds) {
            productsInBasket.add(Resources.productJdbcRepository.findById(productId));
        }

        model.addAttribute("products", productsOnPage);
        model.addAttribute("pages", getPages(page, lastPage));
        model.addAttribute("id_type", s_id_type == null ? "" : ",id_type = " + s_id_type);
        model.addAttribute("product_types", productTypes);
        model.addAttribute("product_basket", productsInBasket);

        return "store";
    }

    private int[] getPages(int currentPage, int lastPage) {

        int left = Math.max(1, currentPage - 5);
        int right = Math.min(lastPage, currentPage + 5);

        int[] pages;
        if (lastPage == right) {
            pages = new int[right - left + 1];
        }
        else {
            pages = new int[right - left + 2];
            pages[pages.length - 1] = lastPage;
        }

        for (int i = 0; i < right - left + 1; i++) {
            pages[i] = left + i;
        }

        return pages;
    }
}
