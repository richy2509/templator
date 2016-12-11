package com.richy2509.templator.exception

/**
  * Created by richardkoehl on 11/12/2016.
  */
object Exception {
  case class InvalidConfigException(msg: String) extends Exception(msg)
  case class NoArgsException() extends Exception("No args ")
}
