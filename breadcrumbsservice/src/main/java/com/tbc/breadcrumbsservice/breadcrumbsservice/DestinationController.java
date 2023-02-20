package com.tbc.breadcrumbsservice.breadcrumbsservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class DestinationController 
{
  
  String connectionURL = "jdbc:sqlserver://breadcrumbsserv.database.windows.net:1433;database=breadcrumbs;user=tbcadmin@breadcrumbsserv;password=BreadCrumbs2023!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

  @RequestMapping(value = "/destination", method=RequestMethod.GET)
  public ResponseEntity<List<Destination>> destinationSearch(@RequestParam("searchString") String searchString)
  {
    List<Destination> destinations = new ArrayList<>();
    String SQL = "";
      try 
      {

        Connection con = DriverManager.getConnection(connectionURL);
        Statement stmt = con.createStatement();

        SQL = "select * from destination where destinationname like " + "'%" + searchString + "%'";
        ResultSet rs = stmt.executeQuery(SQL);
        while(rs.next()){
          Destination aDestination = new Destination();
          aDestination.setDestinationID(rs.getInt("destinationID"));
          aDestination.setDestinationName(rs.getString("destinationName"));
          aDestination.setDestinationTag(rs.getString("destinationTag"));
          aDestination.setDirections(rs.getString("directions"));
          aDestination.setImgID(rs.getInt("imgID"));
          aDestination.setUserID(rs.getInt("userID"));
          destinations.add(aDestination);
        }
        SQL = "select * from destination where destinationTag like " + "'%" + searchString + "%'";
      
        ResultSet rs2 = stmt.executeQuery(SQL);
        
        

        if(rs == rs2){
          while(rs2.next()){
            Destination aDestination = new Destination();
            aDestination.setDestinationID(rs2.getInt("destinationID"));
            aDestination.setDestinationName(rs2.getString("destinationName"));
            aDestination.setDestinationTag(rs2.getString("destinationTag"));
            aDestination.setDirections(rs2.getString("directions"));
            aDestination.setImgID(rs2.getInt("imgID"));
            aDestination.setUserID(rs2.getInt("userID"));
            destinations.add(aDestination);   
          }
        } else {
                 
        }
        con.close();
      } 
      catch (SQLException e) 
      {
              e.printStackTrace();
      }

      return new ResponseEntity<List<Destination>>(destinations,HttpStatus.OK);
  }
}