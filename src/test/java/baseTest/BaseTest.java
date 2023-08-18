package baseTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    public static RequestSpecification spec;
    private RequestSpecBuilder builder;

    public BaseTest() {
        String baseURI = System.getProperty("appURL");
        builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURI);
        builder.setBasePath("api");
        builder.setContentType(ContentType.JSON);
        builder.log(LogDetail.URI);
        builder.log(LogDetail.METHOD);
        builder.log(LogDetail.BODY);
        builder.addFilter(new ResponseLoggingFilter(LogDetail.STATUS));
        builder.addFilter(new ResponseLoggingFilter(LogDetail.BODY));

        spec = builder.build();
    }
}
