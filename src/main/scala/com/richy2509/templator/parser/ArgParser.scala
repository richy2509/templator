package com.richy2509.templator.parser

import com.richy2509.templator.exception.Exception.NoArgsException
import com.typesafe.scalalogging.Logger

/**
  * Created by richardkoehl on 11/12/2016.
  */
object ArgParser {

  class ArgConfig(args: Array[String]) {

    def toMap: Map[String, String] = {

      var argMap: Map[String, String] = Map.empty[String, String]

      args
        .filter(_.contains("="))
        .foreach((input) => {
          val split = input.split("=")
          val code = split(0)
          val value = split(1)
          argMap += (code -> value)

        })
      argMap
    }

    def getConfig: String = {
      val parsedArgs = toMap
      Logger.apply(ArgParser.getClass.getSimpleName).debug(s"retrieve arg config from $parsedArgs")
      if (parsedArgs == null || parsedArgs.isEmpty) {
        throw NoArgsException()
      }
      parsedArgs("config")
    }
  }

  def build(args: Array[String]): ArgConfig = {
    new ArgConfig(args)
  }

}
