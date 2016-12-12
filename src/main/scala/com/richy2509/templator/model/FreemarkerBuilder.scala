package com.richy2509.templator.model

import java.io._
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.richy2509.templator.data.{TemplatorEngineConfig, TemplatorFreemarkerConfig}
import com.richy2509.templator.exception.Exception.MyTemplateExceptionHandler
import com.richy2509.templator.utils.StringUtils
import com.typesafe.scalalogging.Logger
import freemarker.cache.TemplateLoader
import freemarker.template.{Configuration, Template}

/**
  * Created by richardkoehl on 10/12/2016.
  */
object FreemarkerBuilder {

  case class FreemarkerConfig(configuration: Configuration) {

    def withDirectory(path: String): FreemarkerConfig = {

      if (StringUtils.isBlank(path)) {
        configuration.setTemplateLoader(new TemplateAbsolutePathLoader)
      } else {
        val dirPath = Paths.get(path)
        if (!Files.exists(dirPath)) {
          Files.createDirectory(dirPath)
        }
        configuration.setDirectoryForTemplateLoading(new File(path))
      }
      this
    }

    def getTemplate(path: String): FreemarkerTemplate = {
      FreemarkerTemplate(configuration, path)
    }

  }

  case class FreemarkerTemplate(configuration: Configuration, path: String) {

    var out = new StringWriter()

    private def getTemplate: Template = {
      configuration.getTemplate(path)
    }

    def process(dataModel: Any): FreemarkerTemplate = {
      getTemplate.process(dataModel, out)
      this
    }

    def saveTo(dirPath: String): FreemarkerTemplate = {
      Logger.apply(FreemarkerTemplate.getClass.getSimpleName).debug(s"Output saved to $dirPath")
      val path = Paths.get(dirPath)
      if (!Files.exists(path)) {
        Files.createDirectories(path.getParent)
      }
      Files.write(path, out.toString.getBytes(StandardCharsets.UTF_8))
      this
    }

  }

  class TemplateAbsolutePathLoader extends TemplateLoader {

    override def getLastModified(templateSource: scala.Any): Long = {
      templateSource.asInstanceOf[File].lastModified()
    }

    override def findTemplateSource(name: String): AnyRef = {
      val source = new File(name)
      if (source.isFile) {
        return source
      }
      null
    }

    override def getReader(templateSource: scala.Any, encoding: String): Reader = {
      if (!templateSource.isInstanceOf[File]) {
        throw new IllegalArgumentException("templateSource is a: " + templateSource.getClass.getName)
      }
      new InputStreamReader(new FileInputStream(templateSource.asInstanceOf[File]), encoding)
    }

    override def closeTemplateSource(templateSource: scala.Any): Unit = {
      // do nothing
    }

  }

  def getConfig(config: TemplatorFreemarkerConfig): FreemarkerConfig = {

    val configuration = new Configuration(Configuration.VERSION_2_3_25)
    configuration.setLogTemplateExceptions(config.enableLogging)
    if (config.enableRewrite){
      configuration.setTemplateExceptionHandler(new MyTemplateExceptionHandler)
    }
    FreemarkerConfig(configuration)
  }

}


