package fitness.tracker.rw.repository;

import fitness.tracker.rw.model.Location;
import fitness.tracker.rw.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByParentId(Long parentId);

    List<Location> findByType(LocationType type);

    java.util.Optional<Location> findByNameAndParent(String name, Location parent);
}
