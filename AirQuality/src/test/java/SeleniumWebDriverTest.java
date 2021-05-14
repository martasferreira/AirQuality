import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import io.github.bonigarcia.seljup.SeleniumJupiter;




@ExtendWith(SeleniumJupiter.class)
public class SeleniumWebDriverTest {


    @Test
    public void testAirQualityPage(FirefoxDriver driver) {

        driver.get("http://localhost:8080/airQuality");

        assertEquals("Spring Boot - Air Quality App", driver.getTitle());

        WebElement titulo = driver.findElement(By.id("titulo"));

        assertEquals("Air Quality App",titulo.getText().toString() );

        driver.findElement(By.id("input")).click();
        driver.findElement(By.id("input")).clear();
        driver.findElement(By.id("input")).sendKeys("Aveiro");
        driver.findElement(By.id("input")).sendKeys(Keys.ENTER);

        // as info may take time to be processed ; may depend on internet speed
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableBody")));

        int numOfRow = table.findElements(By.tagName("tr")).size();

        assertThat(numOfRow).isGreaterThan(0);


        // when city doesn't exist, there are no results


        driver.findElement(By.id("input")).click();
        driver.findElement(By.id("input")).clear();
        driver.findElement(By.id("input")).sendKeys("CidadeQueNãoExiste");
        driver.findElement(By.id("input")).sendKeys(Keys.ENTER);

        WebElement table2=driver.findElement(By.id("tableBody"));
        int numOfRow2 = table2.findElements(By.tagName("tr")).size();

        assertThat(numOfRow2).isEqualTo(0);

        driver.quit();

    }



    @Test
    public void testCacheStatsPage(FirefoxDriver driver) {

        driver.get("http://localhost:8080/cacheStats");

        assertEquals("Spring Boot - CacheStatistics", driver.getTitle());

        WebElement titulo =driver.findElement(By.id("titulo"));

        assertEquals("Cache Statistics:",titulo.getText().toString() );

        WebElement requests=driver.findElement(By.id("requests"));
        WebElement hits=driver.findElement(By.id("hits"));
        WebElement misses=driver.findElement(By.id("misses"));

        assertThat(Integer.parseInt(requests.getText())).isGreaterThanOrEqualTo(0);

        assertThat(Integer.parseInt(hits.getText())).isGreaterThanOrEqualTo(0);

        assertThat(Integer.parseInt(misses.getText())).isGreaterThanOrEqualTo(0);


        driver.quit();


    }


}



