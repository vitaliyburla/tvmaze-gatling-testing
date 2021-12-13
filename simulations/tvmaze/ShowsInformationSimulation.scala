package tvmaze

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ShowsInformationSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://api.tvmaze.com")
    .inferHtmlResources(AllowList(), DenyList())
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
    )
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ru")
    .userAgentHeader(
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.1 Safari/605.1.15"
    )

  private val scn = scenario("ShowsInformationSimulation")
    .exec(
      http("request_0")
        .get("/shows/1")
    )
    .pause(2)
    .exec(
      http("request_1")
        .get("/shows/1?embed=cast")
    )
    .pause(2)
    .exec(
      http("request_2")
        .get("/shows/1/episodes")
    )
    .pause(1)
    .exec(
      http("request_3")
        .get("/shows/1/episodes?specials=1")
    )
    .pause(3)
    .exec(
      http("request_4")
        .get("/shows/180/alternatelists")
    )

  setUp(scn.inject(rampUsers(50).during(20.seconds))).protocols(httpProtocol)
}
