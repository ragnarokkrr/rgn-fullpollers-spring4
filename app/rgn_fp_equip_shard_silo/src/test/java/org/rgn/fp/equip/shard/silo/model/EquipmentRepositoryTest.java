package org.rgn.fp.equip.shard.silo.model;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
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

import static com.jayway.restassured.RestAssured.*;

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
    static final String APPLICATION_JSON = "application/json";
    static Header acceptJson = new Header("Accept", APPLICATION_JSON);

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

        Equipment equipment = givenEquipment(20);

        given().
                contentType(ContentType.JSON).
                header(acceptJson).
                pathParam("id", equipment.getId()).
                body(equipment).
                when().
                put("/rest/equipments/{id}").
                then().
                log().everything().
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void canPutEquipWithTemplate() {

        Equipment equipment = givenEquipment(30);

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = baseURI + ":" + port;
        restTemplate.put(baseUrl + "/rest/equipments/{id}", equipment, equipment.getId());

        when().
                get("/rest/equipments/" + 30).
                then().
                log().everything().
                statusCode(HttpStatus.SC_OK);


    }

    private Equipment givenEquipment(int id) {
        City city = new City();
        city.setId(1);
        city.setName("Porto Alegre");
        city.setState(StateEnum.RS);

        Equipment equipment = new Equipment();
        equipment.setCity(city);

        equipment.setId(id);
        equipment.setName("Equip" + id);
        return equipment;
    }

}
