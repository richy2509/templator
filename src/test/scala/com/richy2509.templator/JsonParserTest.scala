package com.richy2509.templator

import com.typesafe.scalalogging.Logger
import org.scalatest._
import play.api.libs.json._

class JsonParserTest extends FlatSpec with Matchers {

  private val logger = Logger.apply("JsonParserTest")

  "A json" should "retrieve value" in {
    val stream = getClass.getResourceAsStream("/test.json")

    val includeControllerJ =
      """
        |{
        | "adminController": {}
        |,"bookingController": {}
        |,"homeController": {}
        |}
      """.stripMargin

    val includeDataJ =
      """
        |{
        | "adminController": {}
        |,"bookingController": {}
        |,"homeController": {}
        |}
      """.stripMargin

    val mainJ = Json.parse(
      """
      |{
      |  "include": ["controller.json", "data.json"]
      |}
      """.stripMargin)


    val path = __

    val filepath = (mainJ \ "include").as[Array[String]]

    val jsonTransformer = path.json.update(
      __.read[JsObject].map { o => o ++ Json.obj("include" ->
        Json.parse(filepath(0))
      ) }
    )

    println(Json.prettyPrint(mainJ.transform(jsonTransformer).get))

  }

}