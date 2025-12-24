package fitness.tracker.rw.controller;

import fitness.tracker.rw.dto.LocationDTO;
import fitness.tracker.rw.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<LocationDTO>> getProvinces() {
        return ResponseEntity.ok(locationService.getProvinces());
    }

    @GetMapping("/{parentId}/children")
    public ResponseEntity<List<LocationDTO>> getChildren(@PathVariable Long parentId) {
        return ResponseEntity.ok(locationService.getLocationsByParent(parentId));
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(locationService.createLocation(locationDTO));
    }
}
