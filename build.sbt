name := "JDBCEasyScala"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.1.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"
libraryDependencies ++= Seq(
  "org.processing" % "core" % "3.0b5",
  "org.processing" % "net" % "3.0b5",
  "org.processing" % "video" % "3.0b5",
  "org.processing" % "serial" % "3.0b5",
  "org.processing" % "pde" % "3.0b5",
  "org.processing" % "pdf" % "3.0b5")

