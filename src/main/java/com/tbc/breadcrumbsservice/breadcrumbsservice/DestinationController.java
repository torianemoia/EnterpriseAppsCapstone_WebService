package com.tbc.breadcrumbsservice.breadcrumbsservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class DestinationController {

  String connectionURL = "jdbc:sqlserver://breadcrumbsserv.database.windows.net:1433;database=breadcrumbs;user=tbcadmin@breadcrumbsserv;password=BreadCrumbs2023!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

  @RequestMapping(value = "/destination", method = RequestMethod.GET)
  public ResponseEntity<List<Destination>> destinationSearch(
      @RequestParam(value = "searchString", defaultValue = "default") String searchString) {
    List<Destination> destinations = new ArrayList<>();



    try {



      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL;
      ResultSet rs;
      boolean destinationNameFound = false;



      //No arguments returns all destinations
      if (searchString.equals("default")) {
        SQL = "select * from destination";
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
          Destination aDestination = new Destination();
          aDestination.setDestinationID(rs.getInt("destinationID"));
          aDestination.setDestinationName(rs.getString("destinationName"));
          aDestination.setDestinationTag(rs.getString("destinationTag"));
          aDestination.setDirections(rs.getString("directions"));
          aDestination.setImgID(rs.getInt("imgID"));
          aDestination.setUserID(rs.getInt("userID"));
          destinations.add(aDestination);
        }



      //Else searches DestinationName and DestinationTag
      } else {

        SQL = "select * from destination where destinationname like " + "'%" + searchString + "%'" + "or destinationTag like '%" + searchString + "%'";
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
          Destination aDestination = new Destination();
          aDestination.setDestinationID(rs.getInt("destinationID"));
          aDestination.setDestinationName(rs.getString("destinationName"));
          aDestination.setDestinationTag(rs.getString("destinationTag"));
          aDestination.setDirections(rs.getString("directions"));
          aDestination.setImgID(rs.getInt("imgID"));
          aDestination.setUserID(rs.getInt("userID"));        
          destinations.add(aDestination);
        }
        
          
        
      }
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<List<Destination>>(destinations, HttpStatus.OK);
  }
}