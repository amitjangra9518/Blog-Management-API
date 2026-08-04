package blog.exception;

import blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
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
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fielderror = ((FieldError) error).getField();
            String getmessage = error.getDefaultMessage();
            resp.put(fielderror, getmessage);
        });

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    // thrown when the request isn't valid multipart/form-data
    // (e.g. wrong Body type or a manually-set Content-Type header in Postman)
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiResponse> handleMultipartError(MultipartException ex) {
        String message = "Invalid file upload request. In Postman, set Body to "
                + "form-data, add a key matching the expected name as type File, "
                + "and remove any manually-added Content-Type header.";
        return new ResponseEntity<>(new ApiResponse(message, false), HttpStatus.BAD_REQUEST);
    }

    // thrown by our own FileService validation (missing file, bad extension, etc.)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> handleIOException(IOException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}