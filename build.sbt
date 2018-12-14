val dependencies = Seq(
  "mysql" % "mysql-connector-java" % "5.1.44",
  "commons-cli" % "commons-cli" % "1.4",
  "org.apache.commons" % "commons-configuration2" % "2.1.1",
  "org.apache.commons" % "commons-text" % "1.2",
  "commons-beanutils" % "commons-beanutils" % "1.9.3"
)

lazy val root = (project in file(".")).settings(
  name := "db_sample",
  version := "1.0",
  scalaVersion := "2.12.4",
  libraryDependencies ++= dependencies
)