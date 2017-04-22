package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;
import java.sql.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@EnableAutoConfiguration
public class MainController {

  @RequestMapping("/home")
  String home() {
      return "home";
  }

  @RequestMapping(value="/submit/", method = RequestMethod.POST)
  @ResponseBody
  String submit(@RequestBody String body) {
    System.out.println("body => " + body);
    Map<String,String> records = parseParams(body);
    
    String startDate = records.get("startDate");
    String endDate = records.get("endDate");
    
    try {
        Connection connection = getConnection();

        Statement stmt = connection.createStatement();
        createTables(connection);
        
        createRequest(connection, startDate, endDate);

      } catch (Exception e) {
        e.printStackTrace();
        return "There was an error: " + e.getMessage();
      }
    
    return "ok";
  }

  private Map<String,String> parseParams(String body) {
    String[] args = body.split("&");

    Map<String,String> records = new HashMap<String,String>();

    for (String arg : args) {
      String[] parts = arg.split("=");
      String key = parts[0];
      String val = parts.length > 1 ? URLDecoder.decode(parts[1]) : null;
      records.put(key, val);
    }

    return records;
  }

  
  private void createRequest(Connection connection, String startDate, String endDate) throws SQLException {
    PreparedStatement pstmt = connection.prepareStatement(
        "INSERT INTO requests (start_Date, end_Date) VALUES (?,?)");
    pstmt.setString(1, startDate);
    pstmt.setString(2, endDate);
    pstmt.executeUpdate();
  }
 
  private void createTables(Connection connection) throws SQLException {
    Statement stmt = connection.createStatement();
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS requests (" +
      "start_date  varchar(250)," +
      "end_date    varchar(250),"+
    ")");
  }

  private Connection getConnection() throws URISyntaxException, SQLException {
    URI dbUri = new URI(System.getenv("DATABASE_URL"));

    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

    if (null != dbUri.getUserInfo()) {
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      return DriverManager.getConnection(dbUrl, username, password);
    } else {
      return DriverManager.getConnection(dbUrl);
    }

  }

}
