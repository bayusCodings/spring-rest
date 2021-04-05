package com.app.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Response {
    private int statusCode;
    private String message;
}
