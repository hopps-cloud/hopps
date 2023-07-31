package cloud.hopps;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(HttpBinTestResouce.class)
public class ProxyTests
{
	@Test
	@DisplayName("Should GET from proxy")
	void shouldGetFromProxy()
	{
		RestAssured.given()
			.when()
			.get("/")
			.then()
			.statusCode(200);
	}
}
