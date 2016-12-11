package com.richy2509.templator.model

import java.io.{File, StringWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.typesafe.scalalogging.Logger
import freemarker.template.{Configuration, Template}

/**
  * Created by richardkoehl on 10/12/2016.
  */
object FreemarkerBuilder {

  case class FreemarkerConfig(version: freemarker.template.Version) {

    var configuration = new Configuration(version)

    def withDirectory(path: String): FreemarkerConfig = {
      val dirPath = Paths.get(path)
      if (!Files.exists(dirPath)) {
        Files.createDirectory(dirPath)
      }
      configuration.setDirectoryForTemplateLoading(new File(path))
      this
    }

    def getTemplate(path: String): FreemarkerTemplate = {
      FreemarkerTemplate(configuration.getTemplate(path))
    }

  }

  case class FreemarkerTemplate(template: Template) {

    var out = new StringWriter()

    def process(dataModel: Any): FreemarkerTemplate = {
      template.process(dataModel, out)
      this
    }

    def saveTo(path: String): FreemarkerTemplate = {
      Logger.apply(FreemarkerTemplate.getClass.getSimpleName).debug(s"Output saved to $path")
      Files.write(Paths.get(path), out.toString.getBytes(StandardCharsets.UTF_8))
      this
    }

  }

  def config: FreemarkerConfig = {
    FreemarkerConfig(Configuration.VERSION_2_3_25)
  }

}
