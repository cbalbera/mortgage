package com.mortgage.mortgage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/com.mortgage")

public class mortgageController {

    private final mortgageService mortgageService;

    @Autowired
    public mortgageController(mortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    /*
    @GetMapping
    //TODO
    */

    @PostMapping
    //TODO
    public void newMortgage(@RequestBody mortgage mortgage) {
        // add to db
    }

}
