package org.wchtpapaya.knockwebapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Timer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping(value = "/iot")
public class Controller {

    private final int timerDelay = 120_000;

    private boolean knockState = false;
    private LocalTime knockTime;
    private final DataLogger dataLogger;
    private Timer timer = new Timer(true);

    public Controller(DataLogger dataLogger) {
        this.dataLogger = dataLogger;
    }

    @RequestMapping(value = "/knock/sensor", method = GET)
    public ResponseEntity<String> showStatus() {
        return new ResponseEntity<>("Iot server is online", HttpStatus.OK);
    }

    @RequestMapping(value = "/knock/sensor", method = POST)
    public Message receiveSensorValue(@RequestBody String sensorValue) {
        String[] tokens = sensorValue.split("=");

        String receivedValue = tokens[tokens.length - 1];
        log.info("received value from sensor: " + receivedValue);
        knockState = true;

        timer.cancel();
        timer = new Timer(true);
        timer.schedule(new ResetTimer(this), timerDelay);

        dataLogger.log(receivedValue);

        return new SensorValueMessage(60);
    }

    @RequestMapping(value = "/knock/listener", method = GET)
    public Message checkKnock() {

        if (knockState) {
            return new StatusMessage("Accepted", 202);
        } else {
            return new StatusMessage("No Content", 204);
        }
    }

    protected void resetTimer() {
        log.info("Knock state is reset");
        knockState = false;
    }

}
