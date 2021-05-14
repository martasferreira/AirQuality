package tqs.AirQuality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.AirQuality.models.CacheStats;
import tqs.AirQuality.models.Results;
import tqs.AirQuality.external.AirQualityException;
import tqs.AirQuality.services.AirQualityService;
import java.util.List;


@Controller
@RequestMapping("/")
public class AirQualityController {

    @Autowired
    private AirQualityService service;



    // REST API FOR WEB PAGES



    @GetMapping(value="/get/{city}")
    @ResponseBody
    public List<Results> getAirQualityCityInfo(@PathVariable("city") String city) throws AirQualityException {

        service.getInfoForCityAndSave(city);

        return service.findAllResults();

    }


    @GetMapping(value="/airQuality")
    public String getAirQualityPage()
    {

        return "airQuality";

    }


    @GetMapping(value="/cacheStats")
    public String getCacheStatsPage(Model model) {

        CacheStats cacheStats= service.cacheStatsSave();

        model.addAttribute("requests", cacheStats.getNumberOfRequests());
        model.addAttribute("hits", cacheStats.getNumberOfHits());
        model.addAttribute("misses", cacheStats.getNumberOfMisses());

        return "cacheStats";

    }




    // REST API FOR TESTS, DOES THE SAME THAT getCacheStatsPage DO, but without model


    @GetMapping(value="/get/cacheStats")
    @ResponseBody
    public CacheStats getCacheStats() {

        return service.cacheStatsSave();

    }
}
