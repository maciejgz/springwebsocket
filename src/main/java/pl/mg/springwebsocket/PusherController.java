package pl.mg.springwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/push")
public class PusherController {

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> sendMessage(@RequestBody String message, Authentication authentication) {
        System.out.println("SendMessage fired: " + message);
        this.template.convertAndSend("/topic/greetings", new Greeting("pushMessage: " + message));
        return ResponseEntity.ok("OK");
    }
}
