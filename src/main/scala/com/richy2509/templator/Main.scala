package com.richy2509.templator

import com.richy2509.templator.config.YamlConfigBuilder
import com.richy2509.templator.data.TemplatorTemplateConfig
import com.richy2509.templator.model.FreemarkerBuilder
import com.richy2509.templator.model.FreemarkerBuilder.{FreemarkerConfig, FreemarkerTemplate}
import com.richy2509.templator.parser.ArgParser
import com.typesafe.scalalogging.Logger

/**
  * Created by richardkoehl on 08/12/2016.
  */
object Main extends App {

  Logger.apply(Main.getClass.getSimpleName).debug("Templator initialization")

  val templateConfig = YamlConfigBuilder
    .build
    .from(ArgParser.build(args).getConfig)
    .withClass("com.richy2509.templator.data.TemplatorTemplateConfig")
    .get()
    .asInstanceOf[TemplatorTemplateConfig]

  val freemarkerConfig = FreemarkerBuilder.getConfig(templateConfig.config.freemarker)

  if (templateConfig.data != null && !templateConfig.data.isEmpty) {
    val it = templateConfig.data.iterator()
    while (it.hasNext) {
      process(freemarkerConfig, it.next())
    }
  } else {
    process(freemarkerConfig, templateConfig)
  }

  def process(freemarkerConfig: FreemarkerConfig, template: TemplatorTemplateConfig): FreemarkerTemplate = {
    freemarkerConfig
      .withDirectory(template.getTemplateDir)
      .getTemplate(template.modelfile)
      .process(template.params)
      .saveTo(template.outputfile)
  }

}







