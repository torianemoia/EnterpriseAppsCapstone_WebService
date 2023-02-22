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
public class LocationController {
  
  String connectionURL = "jdbc:sqlserver://breadcrumbsserv.database.windows.net:1433;database=breadcrumbs;user=tbcadmin@breadcrumbsserv;password=BreadCrumbs2023!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
  @RequestMapping(value = "/location", method=RequestMethod.GET)
  public ResponseEntity<List<Location>> locationSearch(@RequestParam("searchString") String searchString)
  {

    List<Location> locations = new ArrayList<>();
      try 
      {
        Connection con = DriverManager.getConnection(connectionURL);
        Statement stmt = con.createStatement();
        String SQL = "select * from location";
        ResultSet rs;

        if (searchString != ""){

          rs = stmt.executeQuery(SQL);

        } else {
          SQL = "select * from location where locationname like " + "'%" + searchString + "%'";
          rs = stmt.executeQuery(SQL);
        }
            
        while(rs.next()){
          Location aLocation = new Location();
          aLocation.setLocationID(rs.getInt("locationID"));
          aLocation.setLocationName(rs.getString("locationName"));
          aLocation.setStreetNumber(rs.getInt("streetNumber"));
          aLocation.setRoadName(rs.getString("roadName"));
          aLocation.setCity(rs.getString("city"));
          aLocation.setState(rs.getString("state"));
          aLocation.setZipCode(rs.getInt("zipCode"));
          aLocation.setLocationTag(rs.getString("locationTag"));
          aLocation.setImgID(rs.getInt("imgID"));
          locations.add(aLocation);
        }

        con.close();
      } 

      
      catch (SQLException e) 
      {
              e.printStackTrace();
      }

      return new ResponseEntity<List<Location>>(locations,HttpStatus.OK);
  }
}