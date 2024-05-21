package org.devine.aberrant.power;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerService {

    @Autowired
    private PowerRepository powerRepository;

    public List<Power> findAll() {
        return powerRepository.findAll();
    }

    public Power findById(Long powerId) {
        return powerRepository.findById(powerId)
                .orElseThrow(() -> new IllegalArgumentException("Power not found"));
    }
}
