package com.richy2509.templator

import java.io.FileInputStream

import com.typesafe.scalalogging.Logger
import org.scalatest._
import play.api.libs.json.{Json, __, _}

class JsonParserTest extends FlatSpec with Matchers {

  private val logger = Logger.apply("JsonParserTest")

  "A json" should "retrieve value" in {
    val stream = getClass.getResourceAsStream("/test.json")

    val j = Json.parse(
      """{
 "key1" : "value1",
 "key2" : {
   "key21" : 123,
   "key22" : true,
   "key23" : [ "alpha", "beta", "gamma"],
   "key24" : {
      "key241" : 234.123,
      "key242" : "/data.json"
    }
 },
 "key3" : 234
 }""".stripMargin)

    import play.api.libs.json._

    val path = __ \ 'key2 \ 'key24

    val filepath = (j \ "key2" \ "key24" \ "key242").as[String]

    val jsonTransformer = path.json.update(
      __.read[JsObject].map{ o => o ++ Json.obj( "key242" -> Json.parse(getClass.getResourceAsStream(filepath)) ) }
    )

    println(Json.prettyPrint(j.transform(jsonTransformer).get))

    /*

    val content = "bonjour"

    val list = List("n", "j", "r")

    val newC = list.foldLeft(content) {
      (newContent, item) => {
        logger.debug(newContent)
        s"$newContent;${content.indexOf(item)}"
      }
    }

    println(s"[finalContent] $newC")

    val newRoot = fields.foldLeft(root){
      (acc, r) => acc
    }

    println(Json.prettyPrint(newRoot))

    root shouldNot be(null)*/

  }

}