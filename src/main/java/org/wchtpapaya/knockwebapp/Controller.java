package org.wchtpapaya.knockwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    private static final String PORT = ":55565";
    private static final String HTTP = "http://";
    private static final String APP_PATH = "iot/knock";

    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private boolean isKnocked = false;
    private final  DataLogger dataLogger;

    public Controller(DataLogger dataLogger) {
        this.dataLogger = dataLogger;
    }

    @RequestMapping(value = "/iot/knocksensor", method = GET)
    public StatusMessage showStatus() {
        return new StatusMessage("OK", 200);
    }


    @RequestMapping(value = "/iot/knocksensor", method = POST)
    public StatusMessage receiveSensorValue(@RequestBody String sensorValue) {
        String[] tokens = sensorValue.split("=");

        String receivedValue = tokens[tokens.length - 1];
        logger.info("received value from sensor: " + receivedValue);
        isKnocked = true;
        dataLogger.log(receivedValue);

        return new StatusMessage("Accepted", 202);
    }

    @RequestMapping(value = "/iot/knocklistener", method = GET)
    public StatusMessage checkKnock() {
        if (isKnocked) {
            isKnocked = false;
            return new StatusMessage("Accepted", 202);
        } else {
            return new StatusMessage("No Content", 204);
        }
    }

}
