package ca.cal.mockmvctest.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(@RequestParam(required = false) String personName) {
        personName = personName == null ? "world!" : personName;
        return ResponseEntity.ok(String.format("Hello, %s", personName));
    }
}
