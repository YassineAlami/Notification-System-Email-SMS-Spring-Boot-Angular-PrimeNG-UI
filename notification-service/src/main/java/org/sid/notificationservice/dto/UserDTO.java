package org.sid.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.Sector;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private SectorDTO sector;
}
