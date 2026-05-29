package com.weatherApp;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.weatherApp.config.TestOpenWeatherConfig;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestOpenWeatherConfig.class})
@TestPropertySource(properties = {
        "openweather.api.apiUrl=http://localhost:8089/data/2.5/weather",
        "openweather.api.key=testKey",
        "openweather.api.lang=en"
})
public abstract class BaseOpenWeatherTest {
    protected static WireMockServer wireMockServer;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

}
