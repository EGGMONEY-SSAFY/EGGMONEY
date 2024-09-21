package com.ssafy.eggmoney.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AssetController
 * /asset 경로로 들어오는 요청을 처리하는 컨트롤러.
 */
@RestController
public class AssetController {

    /**
     * /asset 경로로 GET 요청이 들어오면 "Asset Content"라는 문자열을 반환.
     * @return String - "Asset Content"
     */
    @GetMapping("/asset")
    public String getAsset() {
        return "Asset Content";
    }
}
