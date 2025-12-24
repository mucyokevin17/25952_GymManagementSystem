package fitness.tracker.rw.dto;

import fitness.tracker.rw.model.LocationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    private String name;
    private LocationType type;
    private Long parentId;
    private List<LocationDTO> children;
}
