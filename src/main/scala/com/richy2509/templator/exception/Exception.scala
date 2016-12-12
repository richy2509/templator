package com.richy2509.templator.exception

import java.io.{IOException, Writer}

import com.typesafe.scalalogging.Logger
import freemarker.core.Environment
import freemarker.template.{TemplateException, TemplateExceptionHandler}

/**
  * Created by richardkoehl on 11/12/2016.
  */
object Exception {
  case class InvalidConfigException(msg: String) extends Exception(msg)
  case class NoArgsException() extends Exception("No args ")
  class MyTemplateExceptionHandler extends TemplateExceptionHandler {
    override def handleTemplateException(te: TemplateException, env: Environment, out: Writer): Unit = {
      try {
        out.write("${" + te.getBlamedExpressionString + "}")
      } catch{
        case ioe: IOException => Logger.apply("MyTemplateExceptionHandler").error("Failed to render message")
      }
    }
  }
}
