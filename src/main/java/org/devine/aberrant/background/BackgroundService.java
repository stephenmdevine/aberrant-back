package org.devine.aberrant.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackgroundService {

    @Autowired
    private BackgroundRepository backgroundRepository;

    public List<Background> findAll() {
        return backgroundRepository.findAll();
    }

    public Background findById(Long backgroundId) {
        return backgroundRepository.findById(backgroundId)
                .orElseThrow(() -> new IllegalArgumentException("Background not found"));
    }

}
