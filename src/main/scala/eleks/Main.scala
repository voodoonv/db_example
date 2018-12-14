package eleks

import eleks.config.ConfigManager
import eleks.util.DatabaseUtil
import org.apache.commons.cli.{DefaultParser, Option, Options}

object Main extends App {
  val options = new Options
  val configFileOption = Option.builder("p").hasArg.required(true).longOpt("dsProperties").build
  options.addOption(configFileOption)
  val parser = new DefaultParser
  val cmd = parser.parse(options, args)
  val propertiesFileLocation: String = cmd.getOptionValue("p")

  ConfigManager.configure(propertiesFileLocation)

  forDemo()

  def forDemo():Unit={
    val needUpdate= DatabaseUtil.createDev("Ivan Ivanov", "Software Developer")
    val needDelete = DatabaseUtil.createDev("Petro Petrov", "DevOps Engineer")

    DatabaseUtil.getAllDev.foreach(println)

    needDelete match {
      case Some(id) =>
        if(DatabaseUtil.deleteDev(id)) println("Successfully deleted")
        else println("Something wrong")
      case None => println("404")
    }

    needUpdate match {
      case Some(id) =>
        if(DatabaseUtil.updateDevTitle(id, "Software Development Expert")) println("Successfully updated")
        else println("Something wrong")
      case None => println("404")
    }

  }
}
