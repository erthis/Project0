import java.sql.{Connection, DriverManager}
import java.util.Properties



object NoiseUp {

  def main(args: Array[String]) {
    // connect to the database named "test" on the localhost

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test"
    val username = "root"
    val password = "passwordhere"

    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    //val testForString:String = for(i <- 0 until 10)
    //Create noise tables here
    //.map(_.toInt)).toArray
    val squareSize:Int = 10;
    //val xrange = Range(0, squareSize, 1).map(_.toString)
    var xTable1String:String = ""
    var xTable2String:String = ""
    var xTable3String:String = ""
    val mymultiarr= Array.ofDim[Int](squareSize, squareSize)
    var insertSQLReference:String = ""
    // val table1Values = Array.ofDim[String](squareSize, squareSize)
    var table1Values = new Array[String](squareSize)
    for(i <- 0 until mymultiarr.length)
    {
      for(j <- 0 until mymultiarr.length)
      {
        //Noise input here
        mymultiarr(i)(j) = 1
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
    var tableColumns:String = " ";
    for(i <- 0 until squareSize){
      tableColumns = tableColumns + s" xColumn$i double"
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

    //#INSERT INTO cancellationrates (user_ID,testaction,testdate)
    // #VALUES (1,'start','1-1-20');
    //println(insertSQLReference)
    for(i <- 0 until squareSize) {
      val linestring:String = table1Values(i)
      statement.execute(s"INSERT INTO noise1 ($insertSQLReference) \n VALUES ($linestring);")
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
    connection.close()
  }

}