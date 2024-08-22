package com.example.demo.Utils;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {

	private HttpStatus status;

	private String message;

	private T data;


}