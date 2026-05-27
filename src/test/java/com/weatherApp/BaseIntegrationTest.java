package com.weatherApp;

import com.weatherApp.config.TestDatabaseConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
@Transactional
public abstract class BaseIntegrationTest {
}
