import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class SampleTest {

    @Test
    public void sampleLogin(){
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset")
                .when().get("https://api.thecatapi.com/v1/images/search")
                .then().statusCode(200).log().all();


    /*given().contentType("application/x-www-form-urlencoded; charset")
                .formParam("grant_type", "password")
                .formParam("username", "Test User")
                .formParam("password", "Test123&&")
                .when().post("http://demo6116845.mockable.io/login")
                .then().statusCode(200);*/
    }

    @Test
    void test01(){
        //GET TEST
        Response response =
                RestAssured.get("https://reqres.in/api/users?page2");
        System.out.println(response.getBody().asString());
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        //Validar status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        //Valdiar contenido body
        String body = response.getBody().asString();
        boolean containsData = body.contains("george.bluth@reqres.in");
        Assert.assertTrue(containsData);
        //Assert.assertEquals(body.data.id[0].email, "michael.lawson@reqres.in");

    }

    @Test
    void testGET_Gherkin(){
        RestAssured.given()
                .get("https://reqres.in/api/users?page2")
                .then()
                .statusCode(200)
                .body("data.id[0]", Matchers.equalTo(1))/*.log().all()*/;
    }


    @Test
    void testPost(){
        JSONObject request = new JSONObject();
        request.put("name", "Amann");
        request.put("Job", "Teacher");
        RestAssured.given()
                .header("Content-type", "application/json")
                .contentType(ContentType.JSON)
                .body(request.toString())
                .when().
                post("https://reqres.in/api/users")
                .then()
                .statusCode(201).log().all();
    }

    @Test
    void test_PUT(){
        JSONObject request = new JSONObject();
        request.put("name", "Amann");
        request.put("Job", "Lawyer");
        RestAssured.given()
                .header("Content-type", "application/json")
                .contentType(ContentType.JSON)
                .body(request.toString())
                .when().
                put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    void test_DELETE(){
        RestAssured.given()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204).log().all();
    }

    @Test
    void test_JSONSchema(){ //Not Working, every single test is passing by

        RestAssured.get("https://reqres.in/api/users").then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users-schema.json"));
    }
}
