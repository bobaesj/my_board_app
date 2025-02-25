package com.bit.myboardapp.exception;

import com.bit.myboardapp.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<Void>> handleRuntimeException(RuntimeException e){
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDto.setStatusMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        responseDto.setStatusMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ResponseDto<Void>> handleSecurityException(SecurityException ex) {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setStatusCode(HttpStatus.FORBIDDEN.value());
        responseDto.setStatusMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }

}
