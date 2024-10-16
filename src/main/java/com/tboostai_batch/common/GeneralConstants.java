package com.tboostai_batch.common;

public class GeneralConstants {


    public static final int CONNECT_TIMEOUT = 10000;   // 5 秒
    public static final int RESPONSE_TIMEOUT = 30000; // 30 秒
    public static final int IO_TIMEOUT = 60000;       // 10 秒
    public static final long ONE_YEAR_IN_SECONDS = 31_536_000L;
    public static final String HTTPS = "https";
    public static final String CATEGORY_IDS = "category_ids";
    public static final String EN_CA = "en-CA";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String ACCEPT_LANG = "Accept-Language";
    public static final String ACCEPT_LANGUAGE_EN = "en";
    public static final String STAR = "*";

    // Ebay
    public static final String EBAY_CANADA = "EBAY_CA";
    public static final String EBAY_PLATFORM= "EBAY";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String EBAY_HEADER_MARKET_PLACE = "X-EBAY-C-MARKETPLACE-ID";
    public static final String EBAY_SEARCH_API_LIMIT_CALL_MAX = "200";
    public static final int EBAY_CHUNK_SIZE = 20;

    // Webclient
    public static final int WEBCLIENT_BUFFER_SIZE = 1024 * 1024; //1MB

    public static final String GOOGLE_MAP_API_ADDR = "address";
    public static final String GOOGLE_MAP_API_KEY = "key";
}