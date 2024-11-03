package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DemoTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token="BQC2-dYKlWeVOZv52QrlrJrkPc-zdyJ6VwMcjvRWZzkyTUduDblRgAQYqm1ZJftli9nt2AuS43I9tITT6YNljYqWWkcddyfk7plVjCeIaUEGmlW8-GcnZJB1UGfCuPLqC_1FMlRTNXEjBddGli1eT_d5555jGpJUY6hM0MiNpLQt23wiyt5wOj5Ie9t66F_dHHSeVlf4H_mbfLB9HTUfmSrXI-smoqI_0kJnKu5mplMEF9JOQSCWbovcoFa_Uq1B5uvseebFjiaki92eOp5mrTL7";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization","Bearer "+access_token)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification=requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

    }

    @Test
    public void ShouldBeAbleToCreatePlaylist(){
        String payload="{\n" +
                "    \"name\": \"New Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";

        given(requestSpecification)
                .body(payload)
                .when().post("/users/31zghzhjvfg3ao3x6px4wogw6sc4/playlists")
                .then().spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .body("name",equalTo("New Playlist"),
                        "description",equalTo("New playlist description"),
                        "public",equalTo(false));
    }
}
