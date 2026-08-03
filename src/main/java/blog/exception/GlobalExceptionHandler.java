package blog.exception;

import blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String,String> resp=new HashMap<>();
          ex.getBindingResult().getAllErrors().forEach((error)->{
              String fielderror=((FieldError)error).getField();
              String getmessage=error.getDefaultMessage();
              resp.put(fielderror,getmessage);
          });
//                .getFieldError()
//                .getDefaultMessage();
//
//        ApiResponse apiResponse = new ApiResponse(message, false);
//
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }
}