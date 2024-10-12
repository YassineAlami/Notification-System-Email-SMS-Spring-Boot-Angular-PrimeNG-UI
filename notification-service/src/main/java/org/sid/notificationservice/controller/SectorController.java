package org.sid.notificationservice.controller;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.Sector;
import org.sid.notificationservice.service.SectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/sectors")
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<Sector>> getAllSectors() {
        List<Sector> sectors = sectorService.getAllSectors();
        return new ResponseEntity<>(sectors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sector> createSector(@RequestBody Sector sector) {
        Sector createdSector = sectorService.createSector(sector);
        return new ResponseEntity<>(createdSector, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // Update mapping for specific ID
    public ResponseEntity<Sector> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        sector.setId(id); // Ensure ID matches path variable
        Sector updatedSector = sectorService.updateSector(sector);
        return ResponseEntity.ok(updatedSector); // Return updated sector in response
    }


    @DeleteMapping("/{id}") // Delete mapping for specific ID
    public ResponseEntity<?> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return ResponseEntity.noContent().build(); // Return empty 204 No Content on success
    }

}