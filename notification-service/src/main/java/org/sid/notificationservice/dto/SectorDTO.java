package org.sid.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.User;

import javax.persistence.OneToMany;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SectorDTO {

    private Long id;
    private String name;
    private String description;
    private List<User> users;

}
