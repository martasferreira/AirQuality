package tqs.AirQuality.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.AirQuality.models.Results;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ResultsRepository extends JpaRepository<Results,Long> {

    Results findByCity(String city);

    Results findByCityAndParameter(String city, String parameter);

    List<Results> findAll();



}
