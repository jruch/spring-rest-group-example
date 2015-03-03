package com.westbrain.sandbox.jaxrs;

/**
 * Created by jruch on 3/3/2015.
 */

import com.westbrain.sandbox.jaxrs.user.User;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@AuthenticationPrincipal User user) {
        return new Greeting(counter.incrementAndGet(), String.format(template, user.getLogin()));
    }

}