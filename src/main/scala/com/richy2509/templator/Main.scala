package com.richy2509.templator

import com.richy2509.templator.config.YamlConfigBuilder
import com.richy2509.templator.data.TemplatorTemplateConfig
import com.richy2509.templator.model.FreemarkerBuilder
import com.richy2509.templator.parser.ArgParser
import com.richy2509.templator.validation.ConfigFields
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
      val childrenConfig = it.next()
      freemarkerConfig
        .withDirectory(childrenConfig.getTemplateDir)
        .getTemplate(childrenConfig.modelfile)
        .process(childrenConfig.params)
        .saveTo(childrenConfig.outputfile)
    }
  } else {
    freemarkerConfig
      .withDirectory(templateConfig.getTemplateDir)
      .getTemplate(templateConfig.modelfile)
      .process(templateConfig.params)
      .saveTo(templateConfig.outputfile)
  }

}







