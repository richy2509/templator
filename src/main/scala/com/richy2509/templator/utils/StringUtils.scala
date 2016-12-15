package com.richy2509.templator.utils

/**
  * Created by richardkoehl on 11/12/2016.
  */
object StringUtils {

  def isBlank(input: String): Boolean = {
    input == null || input.isEmpty
  }


}
