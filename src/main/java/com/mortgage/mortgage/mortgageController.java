package com.mortgage.mortgage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/mortgage")

public class mortgageController {

    private final mortgageService mortgageService;

    @Autowired
    public mortgageController(mortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }


    @GetMapping("all")
    public List<mortgage> getMortgage() {
        return mortgageService.getMortgages();
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<double[][]> getMortgageById(@PathVariable("id") Long id) {
        Optional<mortgage> opt_mortgage = mortgageService.findMortgageById(id);
        if (opt_mortgage.isPresent()) {
            mortgage mortgage = opt_mortgage.get();
            return new ResponseEntity<>(mortgage.get_amort_schedule(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<mortgage> newMortgage(@RequestBody mortgage mortgage) {
        mortgage new_mtg = mortgageService.addNewMortgage(mortgage);
        return new ResponseEntity<>(new_mtg,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<mortgage> updateMortgage(@RequestBody mortgage mortgage) {
        mortgage new_mtg = mortgageService.updateMortgage(mortgage);
        return new ResponseEntity<>(new_mtg,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMortgage(@PathVariable("id") Long id) {
        deleteMortgage(id);
        return;
    }
}
