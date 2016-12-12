package com.richy2509.templator

import com.richy2509.templator.config.YamlConfigBuilder
import com.richy2509.templator.data.TemplatorConfig
import com.richy2509.templator.model.FreemarkerBuilder
import com.richy2509.templator.parser.ArgParser
import com.richy2509.templator.validation.ConfigFields
import com.typesafe.scalalogging.Logger

/**
  * Created by richardkoehl on 08/12/2016.
  */
object Main extends App {

  Logger.apply(Main.getClass.getSimpleName).debug("Templator initialization")

  val config = YamlConfigBuilder
    .build
    .from(ArgParser.build(args).getConfig)
    .withClass("com.richy2509.templator.data.TemplatorConfig")
    .get()
    .asInstanceOf[TemplatorConfig]

  if (config.data != null && !config.data.isEmpty) {

    val it = config.data.iterator()
    while (it.hasNext) {
      val childrenConfig = it.next()
      FreemarkerBuilder
        .config
        .withDirectory(childrenConfig.getTemplateDir)
        .getTemplate(childrenConfig.modelfile)
        .process(childrenConfig.params)
        .saveTo(childrenConfig.outputfile)
    }
  } else {
    FreemarkerBuilder
      .config
      .withDirectory(config.getTemplateDir)
      .getTemplate(config.modelfile)
      .process(config.params)
      .saveTo(config.outputfile)
  }

}







