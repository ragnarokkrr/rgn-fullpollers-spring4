package org.rgn.fp.equip.shard.silo.model;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rgn.fp.equip.shard.silo.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.when;

/**
 * Test for {@link EquipmentRepository}.
 *
 * @author ragnarokkrr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class EquipmentRepositoryTest {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {

        RestAssured.port = port;
    }

    @Test
    public void canFetchEquip() {
        when().
                get("/rest/equipments/search/findByName?name={name}", "Equip1").
                then().
                log().everything().
                statusCode(HttpStatus.SC_OK);
    }


    @Test
    public void canPutEquip() {
        RestTemplate restTemplate = new RestTemplate();

        when().
                post("/rest/equipments/search/findByName?name={name}", "Equip1").
                then().
                log().everything().
                statusCode(HttpStatus.SC_OK);
    }


}
