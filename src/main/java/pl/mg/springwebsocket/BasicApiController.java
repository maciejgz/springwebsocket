package pl.mg.springwebsocket;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BasicApiController {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> sendMessage(Authentication authentication) {
        System.out.println("basic auth fired");
        return ResponseEntity.ok("OK");
    }


}
