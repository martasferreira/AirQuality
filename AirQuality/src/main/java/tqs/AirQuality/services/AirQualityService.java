package tqs.AirQuality.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.AirQuality.models.CacheStats;
import tqs.AirQuality.models.Results;
import tqs.AirQuality.external.AirQualityException;
import tqs.AirQuality.external.ExternalAPI;
import tqs.AirQuality.models.AirQualityResponse;
import tqs.AirQuality.models.CacheStats;
import tqs.AirQuality.models.Cache;
import tqs.AirQuality.models.Results;
import tqs.AirQuality.repository.ResultsRepository;
import tqs.AirQuality.repository.CacheStatsRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AirQualityService {


    Cache cache = new Cache(20000);

    CacheStats cacheStats=new CacheStats();


    @Autowired
    private ExternalAPI restUtil;

    @Autowired
    private ResultsRepository repository;

    @Autowired
    private CacheStatsRepository repositoryCache;



    // return info from repositories


    public Results findInfoByCity(String city) {
        return repository.findByCity(city);
    }


    public List<Results> findAllResults() {
        return repository.findAll();
    }


    public CacheStats findCacheStats(long id) { return repositoryCache.findById(id); }


    public List<CacheStats> findAllCacheStats() {
        return repositoryCache.findAll();
    }




    // save info into repositories


    @Transactional
    public CacheStats cacheStatsSave() {

        return repositoryCache.save(this.cacheStats);

    }


    @Transactional
    public void getInfoForCityAndSave(String city) throws AirQualityException {


        this.cacheStats.setNumberOfRequests(this.cacheStats.getNumberOfRequests()+1);

        repository.deleteAll();

        AirQualityResponse response;

        // cleans the cache if timeToLive passed
        this.cache.cleanup();

        if (this.cache.get(city)==null) {

            this.cacheStats.setNumberOfMisses(this.cacheStats.getNumberOfMisses()+1);

            response = restUtil.getInfoForCity(city);
            // get from external API

            this.cache.put(city, response);

        } else {

            this.cacheStats.setNumberOfHits(this.cacheStats.getNumberOfHits()+1);
            // get from Cache

            response = cache.get(city);

        }

        for (Results r : response.getResults()) {

            // json have duplicats ( same city and parameter ) , so here they are removed
            if (repository.findByCityAndParameter(r.getCity(), r.getParameter()) == null) {
                repository.save(r);
            }
        }

    }

}
