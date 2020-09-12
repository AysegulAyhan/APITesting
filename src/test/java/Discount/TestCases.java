package Discount;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestCases {

    Cookies cookies;
    String id;

    @BeforeClass
    public void init(){
        baseURI="https://test.basqar.techno.study";
        Map<String,String> body=new HashMap<>();
        body.put("username","daulet2030@gmail.com");
        body.put("password","TechnoStudy123@");

         cookies = given()
                .body(body)
                .contentType(ContentType.JSON)
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().detailedCookies();
    }

    @Test
    public void createDiscount(){
    Pojo body=new Pojo();
    body.setActive(true);
    body.setCode("274");
    body.setDescription("Politics2");
    body.setPriority("1");
        id = given()
                .body(body)
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/discounts")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");
    }
    @Test
    public void createDiscountNeg(){
        Pojo body=new Pojo();
        body.setActive(true);
        body.setCode("274");
        body.setDescription("Politics2");
        body.setPriority("1");
        given()
                .body(body)
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/discounts")
                .then()
                .statusCode(400);

    }
    @Test(dependsOnMethods = "createDiscount")
    public void updateDiscount(){
        Pojo pojo=new Pojo();
        pojo.setPriority("2");
        pojo.setActive(true);
        pojo.setDescription("Economics");
        pojo.setCode("112");
        pojo.setId(id);

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(pojo)
                .when()
                .put("/school-service/api/discounts")
                .then().statusCode(200);

    }
    @Test(dependsOnMethods = "updateDiscount")
    public void deleteDiscount(){

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .delete("/school-service/api/discounts/"+id)
                .then().statusCode(200);
    }
    @Test(dependsOnMethods = "deleteDiscount")
    public void deleteDiscountNeg(){
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .delete("/school-service/api/discounts/"+id)
                .then().statusCode(404);

    }



}
