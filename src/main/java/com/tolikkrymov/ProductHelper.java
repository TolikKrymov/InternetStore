package com.tolikkrymov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolikkrymov.entities.Product;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ProductHelper {

    public static void changeInformationToUserView(Product product) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        LinkedHashMap<String, String> map;
        map = objectMapper.readValue(
                product.getInformation(),
                new TypeReference<LinkedHashMap<String, String>>() { });

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        product.setInformation((stringBuilder.toString()));
    }

    public static void changeInformationToJSON(Product product) throws IOException {

        String[] lines = product.getInformation().split("\\n");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String line : lines) {
            if (!StringUtils.isEmptyOrWhitespace(line)) {
                String[] elements = line.split(":");
                if (elements.length != 2){
                    throw new IOException("Должны быть ровно две части, разделённых двоеточием в каждой строке");
                }
                map.put(elements[0].trim(), elements[1].trim());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        product.setInformation(mapper.writeValueAsString(map));
    }

    public static List<Integer> getListFromCookie(HttpServletRequest request,
                                                  String cookieName) throws IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new ArrayList<>();
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return new ObjectMapper().readValue(
                        fromBase64(cookie.getValue()),
                        new TypeReference<ArrayList<Integer>>() {});
            }
        }

        return new ArrayList<>();
    }

    public static void setListToCookie(List<Integer> list,
                                       HttpServletResponse response,
                                       String cookieName) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        Cookie cookie = new Cookie(cookieName, toBase64(mapper.writeValueAsString(list)));
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static List<Integer> getProductList(HttpServletRequest request) throws IOException {

        return getListFromCookie(request,"basket");
    }

    public static void setProductList(List<Integer> productIds,
                                      HttpServletResponse response) throws JsonProcessingException {

        setListToCookie(productIds, response, "basket");
    }

    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    private static String toBase64(String source) {
        return new String(Base64.getEncoder().encode(source.getBytes()));
    }

    private static String fromBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64.getBytes()));
    }
}
