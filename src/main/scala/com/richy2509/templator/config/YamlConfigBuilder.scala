package com.richy2509.templator.config

import java.io.FileInputStream

import com.richy2509.templator.utils.StringUtils
import com.typesafe.scalalogging.Logger
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

/**
  * Created by richardkoehl on 10/12/2016.
  */
object YamlConfigBuilder {

  case class YamlConfig(
                         var className: String = null,
                         var path: String = null,
                         var value: AnyRef = null
                       ) {

    val logger: Logger = Logger.apply(YamlConfig.getClass)

    def from(p: String): YamlConfig = {
      path = p
      this
    }

    def withClass(cName: String): YamlConfig = {
      className = cName
      this
    }

    def get(): AnyRef = {
      Logger.apply(YamlConfig.getClass.getSimpleName).debug(s"Retrieve yaml from : $path")
      if (StringUtils.isBlank(className)) {
        return new Yaml().load(new FileInputStream(path))
      }
      new Yaml(new Constructor(className)).load(new FileInputStream(path))
    }

  }

  def build: YamlConfig = { YamlConfig() }

}
