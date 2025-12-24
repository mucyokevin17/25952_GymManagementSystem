package fitness.tracker.rw.config;

import fitness.tracker.rw.model.Location;
import fitness.tracker.rw.model.LocationType;
import fitness.tracker.rw.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationSeeder implements CommandLineRunner {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Always try to seed. createLocation handles duplicates.
        seedLocations();
    }

    private void seedLocations() {
        // Provinces
        Location kigali = createLocation("Kigali City", LocationType.PROVINCE, null);
        Location northern = createLocation("Northern Province", LocationType.PROVINCE, null);
        Location southern = createLocation("Southern Province", LocationType.PROVINCE, null);
        Location eastern = createLocation("Eastern Province", LocationType.PROVINCE, null);
        Location western = createLocation("Western Province", LocationType.PROVINCE, null);

        // Districts for Kigali
        Location gasabo = createLocation("Gasabo", LocationType.DISTRICT, kigali);
        Location kicukiro = createLocation("Kicukiro", LocationType.DISTRICT, kigali);
        Location nyarugenge = createLocation("Nyarugenge", LocationType.DISTRICT, kigali);

        // Sectors for Gasabo
        Location kinyinya = createLocation("Kinyinya", LocationType.SECTOR, gasabo);
        Location remera = createLocation("Remera", LocationType.SECTOR, gasabo);
        Location kimironko = createLocation("Kimironko", LocationType.SECTOR, gasabo);

        // Cells for Kinyinya
        Location kagugu = createLocation("Kagugu", LocationType.CELL, kinyinya);
        Location gacuriro = createLocation("Gacuriro", LocationType.CELL, kinyinya);

        // Villages for Kagugu
        createLocation("Kagugu I", LocationType.VILLAGE, kagugu);
        createLocation("Kagugu II", LocationType.VILLAGE, kagugu);
        createLocation("Gatare", LocationType.VILLAGE, kagugu);

        // Villages for Gacuriro
        createLocation("Gacuriro I", LocationType.VILLAGE, gacuriro);
        createLocation("Vision 2020", LocationType.VILLAGE, gacuriro);

        // Districts for Northern
        createLocation("Musanze", LocationType.DISTRICT, northern);
        createLocation("Gicumbi", LocationType.DISTRICT, northern);

        // Districts for Southern
        createLocation("Huye", LocationType.DISTRICT, southern);
        createLocation("Nyanza", LocationType.DISTRICT, southern);

        // Districts for Eastern
        createLocation("Rwamagana", LocationType.DISTRICT, eastern);
        createLocation("Bugesera", LocationType.DISTRICT, eastern);

        // Districts for Western
        createLocation("Rubavu", LocationType.DISTRICT, western);
        createLocation("Rusizi", LocationType.DISTRICT, western);

        System.out.println("Locations seeded successfully!");
    }

    private Location createLocation(String name, LocationType type, Location parent) {
        return locationRepository.findByNameAndParent(name, parent)
                .orElseGet(() -> {
                    Location location = Location.builder()
                            .name(name)
                            .type(type)
                            .parent(parent)
                            .build();
                    return locationRepository.save(location);
                });
    }
}
