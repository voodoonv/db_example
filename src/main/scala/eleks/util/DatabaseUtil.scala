package eleks.util

import scala.collection.mutable.ArrayBuffer
import java.sql.{PreparedStatement, ResultSet, SQLException, Statement}

import eleks.config.ConfigManager
import eleks.entity.Developer

object DatabaseUtil {

  private val selectAllDev = "SELECT id, fullName, displayTitle FROM `Developers`"
  private val insertDev = "INSERT INTO `Developers`(fullName, displayTitle) VALUE (?, ?)"
  private val deleteDev = "DELETE FROM `Developers` WHERE id = ?"
  private val updateDevDesc = "UPDATE `Developers` SET displayTitle = ? WHERE id = ?"

  def getAllDev: ArrayBuffer[Developer] = {
    val connection = ConfigManager.getMysqlConnection
    val statement = connection.createStatement()

    val resultSet = statement.executeQuery(selectAllDev)

    val devList = ArrayBuffer[Developer]()

    while (resultSet.next()) {
      val id = resultSet.getLong("id")
      val devName = resultSet.getString("fullName")
      val title = resultSet.getString("displayTitle")

      devList.append(Developer(id, devName, title))
    }

    statement.close()
    connection.close()

    devList
  }

  def deleteDev(id: Long): Boolean = {
    try {
      val connection = ConfigManager.getMysqlConnection
      val statement = connection.prepareStatement(deleteDev)

      statement.setLong(1, id)

      statement.execute()

      statement.close()
      connection.close()

      true
    } catch {
      case exception: Throwable =>
        throw new SQLException(exception)
    }
  }

  def createDev(fullName: String, displayTitle: String): Option[Long] = {
    try {
      val connection = ConfigManager.getMysqlConnection
      val statement = connection.prepareStatement(insertDev, Statement.RETURN_GENERATED_KEYS)

      statement.setString(1, fullName)
      statement.setString(2, displayTitle)

      if (statement.executeUpdate() == 0) {
        return null
      }

      val rs = statement.getGeneratedKeys
      if (!rs.next()) {
        return null
      }

      val id = rs.getLong(1)

      statement.close()
      connection.close()

      Some(id)
    } catch {
      case exception: Throwable =>
        throw new SQLException(exception)
    }
  }

  def updateDevTitle(id: Long, displayTitle: String): Boolean = {
    try {
      val connection = ConfigManager.getMysqlConnection
      val statement = connection.prepareStatement(updateDevDesc)

      statement.setString(1, displayTitle)
      statement.setLong(2, id)

      statement.execute()

      statement.close()
      connection.close()

      true
    } catch {
      case exception: Throwable =>
        throw new SQLException(exception)
    }
  }



}
