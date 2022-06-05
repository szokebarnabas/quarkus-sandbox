import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class TestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http // 4
            .baseUrl("http://localhost:8080/api/profile/1") // 5
            .acceptHeader("application/json") // 6
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

    ScenarioBuilder scn = scenario("BasicSimulation") // 7
            .exec(http("request_1") // 8
                    .get("/"))
            .pause(5);

    {
        setUp(
                scn.injectOpen(
                        rampUsers(50).during(10),
                        constantUsersPerSec(50).during(10)
                )
        ).protocols(httpProtocol);
    }
}
