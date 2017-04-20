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
    
    String startDate = records.get("start_date");
    String endDate = records.get("end_date");
    
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

  

  /*
  private void createAppointment(Connection connection, String inviteeId, String date, Boolean sendSms) throws SQLException {
    PreparedStatement pstmt = connection.prepareStatement(
        "INSERT INTO appointments (invitee_id, date) VALUES (?,?)");
    pstmt.setString(1, inviteeId);
    pstmt.setString(2, date);
    pstmt.executeUpdate();
  }
  */
  
  
  /*
  private void createInvitee(Connection connection, String name, String email, String phone) throws SQLException {
    PreparedStatement pstmt = connection.prepareStatement(
        "SELECT name FROM invitees WHERE email=?");
    pstmt.setString(1, email);
    ResultSet rs = pstmt.executeQuery();

    if (!rs.next()) {
      pstmt = connection.prepareStatement(
          "INSERT INTO invitees (name, email, phone) VALUES (?,?,?)");
      pstmt.setString(1, name);
      pstmt.setString(2, email);
      pstmt.setString(3, phone);
      pstmt.executeUpdate();
    }
  }
  */

  private void createTables(Connection connection) throws SQLException {
    Statement stmt = connection.createStatement();
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (" +
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
