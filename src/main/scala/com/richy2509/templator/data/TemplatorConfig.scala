package com.richy2509.templator.data

import scala.beans.BeanProperty

class TemplatorConfig {

  @BeanProperty var params: Object = _
  @BeanProperty var data: java.util.List[TemplatorConfig] = _
  @BeanProperty var outputfile: String = _
  @BeanProperty var modelfile: String = _
  private var templateDir: String = _

  def setTemplateDir(path: String): Unit = {
    this.templateDir = path
  }

  def getTemplateDir: String = {
    if (templateDir == null || templateDir.isEmpty){
      return "/"
    }
    templateDir
  }

}