package com.bit.myboardapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {

    private T item;
    private int statusCode;
    private String statusMessage;

}