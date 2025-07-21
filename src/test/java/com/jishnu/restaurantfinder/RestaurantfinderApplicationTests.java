package com.jishnu.restaurantfinder;

import com.jishnu.restaurantfinder.loader.DataLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@ImportAutoConfiguration(exclude = DataLoader.class)
public class RestaurantfinderApplicationTests {
    @Test
    void contextLoads() {
    }
}
