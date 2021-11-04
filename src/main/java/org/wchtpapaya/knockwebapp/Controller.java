package org.wchtpapaya.knockwebapp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iot/knocksensor")
public class Controller {
    @GetMapping
    public StatusMessage showStatus() {
        return new StatusMessage("OK", 200);
    }
    @PostMapping
    public StatusMessage receiveSensorValue(@RequestBody String sensorValue) {
        System.out.println(sensorValue);
        return  new StatusMessage("OK", 202);
    }

}
