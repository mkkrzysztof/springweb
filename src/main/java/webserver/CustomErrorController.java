package webserver;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletResponse response) {
        Integer status = response.getStatus();
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        if (httpStatus == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>("Error 404 - not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(String.format("Error %d", status), httpStatus);
    }
}
