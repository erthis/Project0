package com.Revature

import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO.PI
import org.apache.spark.sql.catalyst.expressions.SizeBasedWindowFunction.n

import java.sql.{Connection, DriverManager}
import processing.core.{PApplet, PConstants}

import java.sql.ResultSet
import scala.collection.mutable.ArrayBuffer

class DrawCubes extends PApplet{

  case class specificCube(x:Float, y:Float, z:Float) {

    def draw():Unit = {
      pushMatrix()
      translate(x, y, z)
      box(10)
      popMatrix()
    }
  }


  override def settings():Unit = {
    size(800, 800, PConstants.P3D)
  }
  override  def setup():Unit = {
    frameRate(30)
  }
  override def draw() = {
    background(0)
    var fakehalfpi = 3.145/2
    var fakehalfpi2 = f"$fakehalfpi%.1f"
    val halfpi = fakehalfpi2.toFloat
    rotateX(PApplet.map(mouseY, 0, height, halfpi, -halfpi))
    rotateY(PApplet.map(mouseX, 0, height, -halfpi, halfpi))

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test"
    val username = "root"
    val password = "passwordhere"

    var connection: Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val getLengthResultSet = statement.executeQuery("SELECT * FROM noise1;")
    // val noise3 = statement.executeQuery("SELECT * FROM noise3;")
    // val noisetable1 = noise1.map(_.toInt)

    var length = new Iterator[String] {
      def hasNext = getLengthResultSet.next()

      def next() = getLengthResultSet.getString(1)
    }.toList.length - 1
    var noisearr1 = new ArrayBuffer[List[Float]]
    getLengthResultSet.close()

    // var tempnoise = statement.executeQuery(s"SELECT xColumn0 FROM noise1 WHERE zROW = 0;")
    // tempnoise.next()
    // print(tempnoise.getFloat("xColumn0"))
    /*
  var test = new Iterator[Float] {
    def hasNext = tempnoise.next()
    def next() = tempnoise.getFloat(1)
  }.toList
  */
   // camera(70.0, 35.0, 120.0, 50.0, 50.0, 0.0, 0.0, 1.0, 0.0)
   // translate(50, 50, 0)
   // rotateX(-PI / 6)


    val mymultiarr = Array.ofDim[Float](length, length)
    for (i <- 0 until length) {
      for (j <- 0 until length) {
        var noisevar1:Float = 0
        var noisevar2:Float = 0
        var noisevar3:Float = 0

        var tempnoise = statement.executeQuery(s"SELECT xColumn$j FROM noise1 WHERE zROW = $i;")
        tempnoise.next()
        noisevar1 = tempnoise.getFloat(1)
        tempnoise.close()
        tempnoise = statement.executeQuery(s"SELECT xColumn$j FROM noise2 WHERE zROW = $i;")
        tempnoise.next()
        noisevar2 = tempnoise.getFloat(1)
        tempnoise.close()
        tempnoise = statement.executeQuery(s"SELECT xColumn$j FROM noise3 WHERE zROW = $i;")
        tempnoise.next()
        noisevar3 = tempnoise.getFloat(1)
        tempnoise.close()
        var testCube = specificCube( i*30 + 200, (noisevar1 + noisevar2 + noisevar3)* 60 + 500, j*30)
        testCube.draw()
        //print(tempnoise.getFloat(1))
        // print(" ")
        //tempnoise.close()
      }
      //print(s"\n")
    }
  }

  //val tempnoise = statement.executeQuery(s"SELECT * FROM noise1 WHERE zROW = 0;")
  //println(length)
  //for(i <- 1 to length) {
   // val tempnoise = statement.executeQuery(s"SELECT * FROM noise1;")
   // print(tempnoise.getFloat(i))
  //  print(s"\n")
    // while(tempnoise.next()) {
    //for(j <- 1 to length){
   // print(tempnoise.getFloat(i))
   // print(s" ")
    //noisearr1 += tempnoise.getFloat(i)
    // }
    // }
 // }
/*
    var test = new Iterator[Float] {
      def hasNext = tempnoise.next()
      def next() = tempnoise.getFloat(1)
    }.toList
   // println(test)
   // noisearr1(i) = test
    tempnoise.close()
  }*/
  /*
  for(i <- 0 until length){
    for(j <- 0 until length){
     print(noisearr1(i)(j))
    }
    print(s"\n")
  }
  //noise1.close()
  */

  /*
  val mymultiarr = Array.ofDim[Float](length, length)
 // println(noise1List)
 var table1Values = new Array[String](length)
  for(i <- 0 until length)
  {
    for(j <- 0 until length)
    {
      //Noise input here
      mymultiarr(i)(j) = 1
      if(j > 0)
      {
        val inputString: String = mymultiarr(i)(j).toString
        table1Values(i) = table1Values(i) + s" '$inputString'"
        if (j < length - 1)
          table1Values(i) = table1Values(i) + ","
      }
      else
      {
        val inputString: String = mymultiarr(i)(j).toString
        table1Values(i) = s" '$inputString'"
        if (j < length - 1)
          table1Values(i) = table1Values(i) + ","
      }
    }
  }


*/
/*
  val length = test.length
  //Array[Array[Float]](length)
  var noiseArrayTable = for(i <- 0 until length){
    val tempnoise = statement.executeQuery(s"SELECT * FROM noise$i;")
    var test = new Iterator[Float] {
      def hasNext = tempnoise.next()
      def next() = tempnoise.getFloat(1)
    }.toList
    tempnoise.close()

  }
*/

  /*
  for(i <- 0 until length){
    val tempnoise = statement.executeQuery("SELECT * FROM noise1;")
    var test = new Iterator[String] {
      def hasNext = probenoise.next()
      def next() = probenoise.getString(1)
    }.toArray
    noiseArrayTable(i) = test
  }
  for(elem <- noiseArrayTable){
    val tempnoise = statement.executeQuery("SELECT * FROM noise1;")
    var test = new Iterator[String] {
      def hasNext = probenoise.next()
      def next() = probenoise.getString(1)
    }.toArray
    elem = test


  }*/
 // println(length)
  //val it = results(noise1)(_.getFloat(1))
 // println(it.map())


}
