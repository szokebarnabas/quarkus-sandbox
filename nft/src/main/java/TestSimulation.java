import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class TestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080/api/profile/1")
            .acceptHeader("application/json")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate");

    ScenarioBuilder scn = scenario("BasicSimulation")
            .exec(http("request_1")
                    .get("/"))
            .pause(5);

    {
        setUp(
                scn.injectOpen(
                        rampUsers(50).during(10),
                        constantUsersPerSec(50000).during(60)
                )
        ).protocols(httpProtocol);
    }
}
