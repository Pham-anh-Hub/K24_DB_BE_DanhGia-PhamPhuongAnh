package vn.rikkei.cinemabox.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Xử lý lỗi tập trung: chuyển exception thành ApiError.
 * Muốn thêm loại lỗi mới: thêm một phương thức @ExceptionHandler và gọi build(...).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // 1. JSON không hợp lệ / thiếu body / sai kiểu trong JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(HttpMessageNotReadableException ex,  HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST,  "BAD-REQUEST", ex.getMessage(), req, List.of());
    }
    // 2. Sai kiểu ở @RequestParam hoặc @PathVariable (vd: ?page=abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        String msg = "Tham số '" + ex.getName() + "' không đúng định dạng";
        return build(HttpStatus.BAD_REQUEST, msg , ex.getMessage(), req, List.of());

    }
    // 3. Thiếu @RequestParam bắt buộc
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest req) {
        String msg = "Thiếu tham số bắt buộc: " + ex.getParameterName();
        return build(HttpStatus.BAD_REQUEST, msg, ex.getMessage(), req, List.of());

    }
//    // 4. Lỗi @Valid trên DTO (@RequestBody)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
//        String msg = ex.getBindingResult().getFieldErrors().stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .findFirst()
//                .orElse("Dữ liệu đầu vào không hợp lệ");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ErrorResponse(400, msg));
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), req, List.of());
    }

    /** Gọi tới đường dẫn không có endpoint nào. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoEndpoint(NoResourceFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", "Không có endpoint " + req.getRequestURI(), req, List.of());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethod(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", ex.getMessage(), req, List.of());
    }

    /** Lưới an toàn cuối cùng: lỗi chưa được xử lý riêng sẽ thành 500. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        log.error("Lỗi chưa xử lý tại {}", req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Lỗi hệ thống", req, List.of());
    }



    protected ResponseEntity<ApiError> build(HttpStatus status, String error, String message,
                                             HttpServletRequest req, List<ApiError.FieldViolation> details) {
        ApiError body = ApiError.of(status.value(), error, message, req.getRequestURI(), details);
        return ResponseEntity.status(status).body(body);
    }
}
