package main.backend.Repositories;

import main.backend.Pojo.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Page<Event> findDistinctByArtists_Id(Long artistId, Pageable pageable);
}
