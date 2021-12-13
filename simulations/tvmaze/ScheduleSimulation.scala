package tvmaze

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ScheduleSimulation extends Simulation {

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

  private val headers_1 = Map("Accept" -> "*/*")

  private val scn = scenario("ScheduleSimulation")
    .exec(
      http("request_0")
        .get("/schedule?country=US&date=2014-12-01")
        .resources(
          http("request_1")
            .get("/favicon.ico")
            .headers(headers_1)
            .check(status.is(404))
        )
    )
    .pause(6)
    .exec(
      http("request_2")
        .get("/schedule")
    )
    .pause(7)
    .exec(
      http("request_3")
        .get("/schedule/web?date=2020-05-29")
    )
    .pause(4)
    .exec(
      http("request_4")
        .get("/schedule/web?date=2020-05-29&country=")
    )
    .pause(3)
    .exec(
      http("request_5")
        .get("/schedule/web?date=2020-05-29&country=US")
    )
    .pause(6)
    .exec(
      http("request_6")
        .get("/schedule/full")
    )

  setUp(scn.inject(rampUsers(50).during(20.seconds))).protocols(httpProtocol)
}
