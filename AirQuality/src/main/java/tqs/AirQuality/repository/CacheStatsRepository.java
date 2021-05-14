package tqs.AirQuality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.AirQuality.models.CacheStats;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface CacheStatsRepository extends JpaRepository<CacheStats,Long> {

    CacheStats findById(long id);

}
