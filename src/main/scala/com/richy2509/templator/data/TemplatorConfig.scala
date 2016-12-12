package com.richy2509.templator.data

import com.richy2509.templator.model.FreemarkerBuilder.FreemarkerConfig

import scala.beans.BeanProperty

class TemplatorConfig() {

  @BeanProperty var config: Object = _
  @BeanProperty var params: Object = _
  @BeanProperty var data: java.util.List[TemplatorConfig] = _
  @BeanProperty var outputfile: String = _
  @BeanProperty var modelfile: String = _
  private var templateDir: String = _

  def setTemplateDir(path: String): Unit = {
    this.templateDir = path
  }

  def getTemplateDir: String = {
    templateDir
  }

}