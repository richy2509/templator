package com.richy2509.templator.validation

import java.io.{File, FileNotFoundException}

import com.richy2509.templator.data.TemplatorConfig
import com.richy2509.templator.exception.Exception.InvalidConfigException
import com.richy2509.templator.utils.StringUtils

/**
  * Created by richardkoehl on 11/12/2016.
  */
object ConfigFields extends Enumeration {
  type TemplatorFields = Value
  val modelfile, outputfile, templateDir, params = Value

  def validateField(gc: TemplatorConfig, field: TemplatorFields): Unit = {
    field.toString match {
      case "templateDir" => validationFilepath.apply(gc.templateDir)
      case "modelfile" => fieldCorrect.apply(gc.modelfile)
      case "outputfile" => fieldCorrect.apply(gc.outputfile)
      case "params" => if (params == null) {
        throw InvalidConfigException(field.toString)
      }
    }
  }

  def validationFilepath: (String) => String = Function.chain(Seq(
    fieldCorrect, fileCorrect
  ))

  val fieldCorrect: (String) => String = (s: String) =>
    if (StringUtils.isBlank(s)) {
      throw InvalidConfigException("value field not correct")
    }
    else s


  val fileCorrect: (String) => String = (s: String) =>
    if (!new File(s).exists()) {
      throw new FileNotFoundException(s)
    }
    else s
}

