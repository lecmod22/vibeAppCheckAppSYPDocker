package main.backend.Repositories;

import main.backend.Pojo.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByEvent_IdOrderByCreatedAtDesc(Long eventId);

    @Query("select coalesce(avg(r.stars), 0) from Rating r where r.event.id = :eventId")
    double avgStarsByEventId(@Param("eventId") Long eventId);

    @Query("select count(r) from Rating r where r.event.id = :eventId")
    long countByEventId(@Param("eventId") Long eventId);
}
