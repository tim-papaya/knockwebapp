package org.wchtpapaya.knockwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    private static final String PORT = ":55565";
    private static final String HTTP = "http://";
    private static final String APP_PATH = "iot/knock";

    final private Logger logger = LoggerFactory.getLogger(Controller.class);
    private boolean isKnocked = false;

    final private List<String> clients;

    public Controller() {
        clients = new LinkedList<>();
        clients.add("192.168.1.66");
    }

    @RequestMapping(value = "/iot/knocksensor", method = GET)
    @GetMapping
    public StatusMessage showStatus() {
        return new StatusMessage("OK", 200);
    }


    @RequestMapping(value = "/iot/knocksensor", method = POST)
    @PostMapping
    public StatusMessage receiveSensorValue(@RequestBody String sensorValue) {
        String[] tokens = sensorValue.split("=");

        logger.info("received value from sensor: " + tokens[tokens.length - 1]);
        for (String client : clients) {
            String result = new RestTemplate().getForObject(HTTP + client + PORT + APP_PATH, String.class);
        }

        return new StatusMessage("Accepted", 202);
    }

    @RequestMapping(value = "/iot/knocklistener", method = GET)
    @GetMapping
    public StatusMessage checkKnock() {
        if (isKnocked) {
            isKnocked = false;
            return new StatusMessage("Accepted", 202);
        } else {
            return new StatusMessage("No Content", 204);
        }
    }

}
