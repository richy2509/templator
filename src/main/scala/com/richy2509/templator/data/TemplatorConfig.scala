package com.richy2509.templator.data

import scala.beans.BeanProperty

class TemplatorConfig {
  @BeanProperty var params: Object = _
  @BeanProperty var modelfile: String = _
  @BeanProperty var outputfile: String = _
  @BeanProperty var templateDir: String = _
}