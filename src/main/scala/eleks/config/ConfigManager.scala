package eleks.config

import java.io.File
import java.sql.{Connection, DriverManager}
import java.util.Properties

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.builder.fluent.Configurations


object ConfigManager {

  var mysqlConnectionUrl: String = _
  var mysqlUser: String = _
  var mysqlPassword: String = _

  private val dataSinkProperties = new Properties()

  Class.forName("com.mysql.jdbc.Driver")

  def configure(propertiesFileLocation: String): Unit ={
    val configs = new Configurations
    val propertiesFile: File = new File(propertiesFileLocation)
    val config: Configuration = configs.properties(propertiesFile)

    mysqlUser = config.getString("mysql.user")
    mysqlPassword = config.getString("mysql.password")

    val mysqlUrl = config.getString("mysql.server")
    val mysqlPort = config.getString("mysql.port")
    val mysqlDatabase = config.getString("mysql.database")

    mysqlConnectionUrl = s"jdbc:mysql://$mysqlUrl:$mysqlPort/$mysqlDatabase"
  }

  def getMysqlConnection: Connection = {
    dataSinkProperties.put("user", mysqlUser)
    dataSinkProperties.put("password", mysqlPassword)
    dataSinkProperties.put("useSSL", "false")

    DriverManager.getConnection(mysqlConnectionUrl, dataSinkProperties)
  }

}
