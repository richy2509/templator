package com.richy2509.templator.config

import java.io.FileInputStream

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

/**
  * Created by richardkoehl on 10/12/2016.
  */
object YamlConfigBuilder {

  case class YamlConfig(var className: String, var path: String) {

    def from(p: String): YamlConfig = {
      path = p
      this
    }

    def withClass(cName: String): YamlConfig = {
      className = cName
      this
    }

    def get(): AnyRef = {
      new Yaml(new Constructor(className)).load(new FileInputStream(path))
    }

  }

  def build: YamlConfig = { YamlConfig(null, null) }

}
