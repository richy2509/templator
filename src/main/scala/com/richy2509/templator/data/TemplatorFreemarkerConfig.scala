package com.richy2509.templator.data

import scala.beans.BeanProperty

/**
  * Created by Richard on 12/12/2016.
  */
class TemplatorFreemarkerConfig {

  @BeanProperty var enableRewrite: Boolean = true
  @BeanProperty var enableLogging: Boolean = false

}
