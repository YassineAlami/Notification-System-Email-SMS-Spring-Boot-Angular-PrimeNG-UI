package org.sid.notificationservice.service;
import org.sid.notificationservice.model.Sector;

import java.util.List;

public interface SectorService {
    List<Sector> getAllSectors();
    Sector createSector(Sector sector);

    Sector updateSector(Sector sector);

    void deleteSector(Long id);

}
