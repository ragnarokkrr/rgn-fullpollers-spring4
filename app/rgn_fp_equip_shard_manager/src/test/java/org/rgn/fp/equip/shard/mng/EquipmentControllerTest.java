package org.rgn.fp.equip.shard.mng;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;

/**
 * Test for {@link EquipmentController}.
 *
 * @author ragnarokkrr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class EquipmentControllerTest {
    static final String APPLICATION_JSON = "application/json";
    static Header acceptJson = new Header("Accept", APPLICATION_JSON);

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {

        RestAssured.port = port;
    }

    @Test
    public void canPostEquip() {
        EquipmentVO equipmentVO = givenEquipmentVO(1);

        given().
                contentType(ContentType.JSON).
                header(acceptJson).
                body(equipmentVO).
                when().
                post("/shard/manager/equipment").
                then().
                log().
                everything().
                statusCode(HttpStatus.SC_OK);

        EquipmentVO equipmentVORio = givenEquipmentVORio(100);

        given().
                contentType(ContentType.JSON).
                header(acceptJson).
                body(equipmentVORio).
                when().
                post("/shard/manager/equipment").
                then().
                log().
                everything().
                statusCode(HttpStatus.SC_OK);


        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private EquipmentVO givenEquipmentVO(int id) {
        EquipmentVO equipmentVO = new EquipmentVO();

        equipmentVO.setCityId("1");
        equipmentVO.setCityName("Porto Alegre");
        equipmentVO.setCityState("RS");
        equipmentVO.setName("Equip" + id);

        return equipmentVO;
    }

    private EquipmentVO givenEquipmentVORio(int id) {
        EquipmentVO equipmentVO = new EquipmentVO();

        equipmentVO.setCityId("2");
        equipmentVO.setCityName("Rio de Janeiro");
        equipmentVO.setCityState("RJ");
        equipmentVO.setName("Equip" + id);

        return equipmentVO;
    }

}
