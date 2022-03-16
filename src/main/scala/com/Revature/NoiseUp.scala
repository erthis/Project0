package com.Revature

import processing.core

import scala.math
import java.lang.Object
import java.sql.{Connection, DriverManager}
import processing.core.{PApplet, PConstants}
import processing.core.PMatrix2D
import java.io

import scala.io
import scala.io.Source
import scala.math.{cos, random, sin, sqrt}


object NoiseUp extends PApplet{

  def main(args: Array[String]) {
    //val src = Source.fromFile("C:/Users/enthi/OneDrive/Desktop/CSVFile.csv")
   // println(src)
   // val iter = src.getLines().drop(1).map(_.split(","))
    //println(iter)
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test"
    val username = "root"
    val password = "passwordhere"
    var squareSize = 0
    var seed = 0
    val src = Source.fromFile("C:/Users/enthi/OneDrive/Desktop/CSVFile.csv").getLines
    for(l <- src) {
      // split line by comma and process them
      l.split(",").map { c =>
        if(squareSize == 0){
          squareSize = c.toInt
        }
        else
          {
            seed = c.toInt
          }
      }
    }
    noiseSeed(seed)

    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    //val testForString:String = for(i <- 0 until 10)
    //Create noise tables here
    //.map(_.toInt)).toArray
    //var stringSeed = iter.next()
    //val squareSize:Int = stringSeed(0).toInt //stringSeed.toInt;
    //val xrange = Range(0, squareSize, 1).map(_.toString)
    val mymultiarr= Array.ofDim[Float](squareSize, squareSize)
    var insertSQLReference:String = " "
     var table1Values = new Array[String](squareSize)
   // var fakeNoiseScale1 = sqrt(7)/100
   // var fakeNoiseScale12 = f"$fakeNoiseScale1%.1f"
   // val noiseScale1 = fakeNoiseScale12.toFloat

    /*
        var fakehalfpi = 3.145/2
    var fakehalfpi2 = f"$fakehalfpi%.1f"
    val halfpi = fakehalfpi2.toFloat


    * def draw(): Unit =  { background(0)
var x: Int = 0
while ( { x < width})  { val noiseVal: Float = noise((mouseX + x) * noiseScale, mouseY * noiseScale)
stroke(noiseVal * 255)
line(x, mouseY + noiseVal * 80, x, height)

x += 1}
}
    *
    * */

    for(i <- 0 until mymultiarr.length)
      {
        for(j <- 0 until mymultiarr.length)
        {
          //Noise input here
          mymultiarr(i)(j) = noise(i, j, 7)

          if(j > 0)
          {
            val inputString: String = mymultiarr(i)(j).toString
            table1Values(i) = table1Values(i) + s" '$inputString'"
            if (j < squareSize - 1)
              table1Values(i) = table1Values(i) + ","
          }
          else
          {
            val inputString: String = mymultiarr(i)(j).toString
            table1Values(i) = s" '$inputString'"
            if (j < squareSize - 1)
              table1Values(i) = table1Values(i) + ","
          }
        }
      }
    var table2Values = new Array[String](squareSize)
    for(i <- 0 until mymultiarr.length)
    {
      for(j <- 0 until mymultiarr.length)
      {
        //Noise input here
        mymultiarr(i)(j) = noise(i/3,  j/4, 57)
        if(j > 0)
        {
          val inputString: String = mymultiarr(i)(j).toString
          table2Values(i) = table2Values(i) + s" '$inputString'"
          if (j < squareSize - 1)
            table2Values(i) = table2Values(i) + ","
        }
        else
        {
          val inputString: String = mymultiarr(i)(j).toString
          table2Values(i) = s" '$inputString'"
          if (j < squareSize - 1)
            table2Values(i) = table2Values(i) + ","
        }
      }
    }
    var table3Values = new Array[String](squareSize)
    for(i <- 0 until mymultiarr.length)
    {
      for(j <- 0 until mymultiarr.length)
      {
        //Noise input here
        mymultiarr(i)(j) = noise(i/6, j/5)
        if(j > 0)
        {
          val inputString: String = mymultiarr(i)(j).toString
          table3Values(i) = table3Values(i) + s" '$inputString'"
          if (j < squareSize - 1)
            table3Values(i) = table3Values(i) + ","
        }
        else
        {
          val inputString: String = mymultiarr(i)(j).toString
          table3Values(i) = s" '$inputString'"
          if (j < squareSize - 1)
            table3Values(i) = table3Values(i) + ","
        }
      }
    }
    var tableColumns:String = "zROW int, ";
    for(i <- 0 until squareSize){
      tableColumns = tableColumns + s" xColumn$i float"
      if(i < squareSize - 1)
        tableColumns = tableColumns + ","

      insertSQLReference = insertSQLReference + s" xColumn$i"
      if(i < squareSize - 1)
        insertSQLReference = insertSQLReference + ","
    }
    statement.execute(s"DROP TABLE IF EXISTS noise1;")
    statement.execute(s"DROP TABLE IF EXISTS noise2;")
    statement.execute(s"DROP TABLE IF EXISTS noise3;")

    statement.execute(s"CREATE TABLE noise1 ( $tableColumns );")
    statement.execute(s"CREATE TABLE noise2 ( $tableColumns );")
    statement.execute(s"CREATE TABLE noise3 ( $tableColumns );")

    //#INSERT INTO cancellationrates (user_ID,testaction,testdate)
   // #VALUES (1,'start','1-1-20');
    //println(insertSQLReference)
    for(i <- 0 until squareSize) {
      val linestring:String = i.toString + ", " + table1Values(i)
      statement.execute(s"INSERT INTO noise1 (zROW, $insertSQLReference) \n VALUES ($linestring);")
      println(linestring)
    }
    for(i <- 0 until squareSize) {
      val linestring:String = i.toString + ", " + table2Values(i)
      statement.execute(s"INSERT INTO noise2 (zROW, $insertSQLReference) \n VALUES ($linestring);")
      println(linestring)
    }
    for(i <- 0 until squareSize) {
      val linestring:String = i.toString + ", " + table3Values(i)
      statement.execute(s"INSERT INTO noise3 (zROW, $insertSQLReference) \n VALUES ($linestring);")
      println(linestring)
    }

    //Upload them
    //Fetch them elsewhere,
    //display them.
    statement
    //val noise1 = statement.executeQuery("SELECT * FROM testData1;")
    //val noise2 = statement.executeQuery("SELECT * FROM testData2;")
   // val noise3 = statement.executeQuery("SELECT * FROM testData3;")
   // val noise-final = noise1 + noise2 + noise3
    //while ( resultSet.next() ) {
   //   println(resultSet.getString(1)+", " +resultSet.getString(2) +", " +resultSet.getString(3))
    //}

    PApplet.main("com.Revature.DrawCubes")


    connection.close()


  }

}