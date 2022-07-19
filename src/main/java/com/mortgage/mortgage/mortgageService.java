package com.mortgage.mortgage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class mortgageService {
    @Autowired
    public mortgageService(mortgageRepository mortgageRepository) {
        this.mortgageRepository = mortgageRepository;
    }

    private final mortgageRepository mortgageRepository;

    public List<mortgage> getMortgages() {
        return mortgageRepository.findAll();
    }

    public Optional<mortgage> findMortgageById(Long id) {
        return mortgageRepository.findById(id);
    }

    public mortgage addNewMortgage(mortgage mortgage) {
        mortgageRepository.save(mortgage);
        return mortgage;
    }

    public void deleteMortgage(Long mortgageId) {
        boolean exists = mortgageRepository.existsById(mortgageId);
        if (!exists) {
            throw new IllegalStateException("com.mortgage with id "+mortgageId+" does not exist");
        }
        mortgageRepository.deleteById(mortgageId);
    }

    public mortgage updateMortgage(mortgage mortgage) {
        //TODO
        return mortgage;
    }
}
