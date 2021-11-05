package org.wchtpapaya.knockwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/iot/knocksensor")
public class Controller {

    final private Logger logger = LoggerFactory.getLogger(Controller.class);

    final private List<String> clients;

    public Controller() {
        clients = new LinkedList<>();
        clients.add("192.168.1.66");
    }

    @GetMapping
    public StatusMessage showStatus() {
        return new StatusMessage("OK", 200);
    }

    @PostMapping
    public StatusMessage receiveSensorValue(@RequestBody String sensorValue) {
        String[] tokens = sensorValue.split("=");

        logger.info("received value from sensor: " + tokens[tokens.length - 1]);
        for (String client : clients) {
            String result = new RestTemplate().getForObject("http://" + client + ":55565", String.class);
        }

        return new StatusMessage("OK", 202);
    }

}
