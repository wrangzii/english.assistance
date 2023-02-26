package com.ielts.assistance.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class DataResponse {
    Integer pageNumber;
    List<?> data;
}
