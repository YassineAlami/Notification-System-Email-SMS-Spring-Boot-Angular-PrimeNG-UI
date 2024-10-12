package org.sid.notificationservice.service;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.Sector;
import org.sid.notificationservice.repository.SectorRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    @Override
    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    @Override
    public Sector createSector(Sector sector) {
        return sectorRepository.save(sector);
    }


    @Override
    public Sector updateSector(Sector sector) {
        // Check if sector exists (optional)
        Optional<Sector> existingSector = sectorRepository.findById(sector.getId());
        if (!existingSector.isPresent()) {
            throw new ResourceNotFoundException("Sector with id " + sector.getId() + " not found");
        }
        return sectorRepository.save(sector); // Save the updated sector
    }


    @Override
    public void deleteSector(Long id) {
        Optional<Sector> existingSector = sectorRepository.findById(id);
        if (existingSector.isPresent()) {
            sectorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Sector with id " + id + " not found");
        }
    }
}