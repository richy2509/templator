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

  override def main(args: Array[String]): Unit = {

    Logger.apply(Main.getClass.getSimpleName).debug("Templator initialization")

    val config = YamlConfigBuilder
      .build
      .from(ArgParser.build(args).getConfig)
      .withClass("com.richy2509.templator.data.TemplatorConfig")
      .get()
      .asInstanceOf[TemplatorConfig]

    ConfigFields.values
      .foreach(ConfigFields.validateField(config, _))

    val data = config.params

    FreemarkerBuilder
      .config
      .withDirectory(config.templateDir)
      .getTemplate(config.modelfile)
      .process(data)
      .saveTo(config.outputfile)

  }

}







