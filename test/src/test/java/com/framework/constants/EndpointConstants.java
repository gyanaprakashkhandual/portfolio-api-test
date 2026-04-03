package com.framework.constants;

public class EndpointConstants {

    private EndpointConstants() {
    }

    // User Endpoints
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{userId}";
    public static final String USER_POSTS = "/users/{userId}/posts";

    // Auth Endpoints
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_LOGOUT = "/auth/logout";
    public static final String AUTH_REFRESH = "/auth/refresh";

    // Product Endpoints
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT_BY_ID = "/products/{productId}";

    // Health Check
    public static final String HEALTH = "/health";
}