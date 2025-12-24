package fitness.tracker.rw.service;

import fitness.tracker.rw.dto.LocationDTO;
import fitness.tracker.rw.model.Location;
import fitness.tracker.rw.model.LocationType;
import fitness.tracker.rw.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> getDistrictsByProvince(Long provinceId) {
        return locationRepository.findByParentId(provinceId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> getLocationsByParent(Long parentId) {
        return locationRepository.findByParentId(parentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> getProvinces() {
        return locationRepository.findByType(LocationType.PROVINCE).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LocationDTO createLocation(LocationDTO dto) {
        Location location = new Location();
        location.setName(dto.getName());
        location.setType(dto.getType());
        
        if (dto.getParentId() != null) {
            Location parent = locationRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent location not found"));
            location.setParent(parent);
        }

        return mapToDTO(locationRepository.save(location));
    }

    private LocationDTO mapToDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getType(),
                location.getParent() != null ? location.getParent().getId() : null,
                null
        );
    }
}
