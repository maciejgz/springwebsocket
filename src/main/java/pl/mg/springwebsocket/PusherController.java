package pl.mg.springwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public void sendMessage(@RequestBody String message) {
        System.out.println("SendMessage fired: " + message);
        this.template.convertAndSend("/topic/greetings", new Greeting("pushMessage: " + message));
    }

}
