package io.meys.oauthtesting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping("/sec")
    public String sec() {
        return "Sec";
    }

    @GetMapping("/verysec")
    public String verySec() {
        return "Very sec";
    }

    @GetMapping("/open")
    public String open() {
        return "Open";
    }
}
