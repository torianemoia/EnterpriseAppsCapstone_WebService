package com.torishearer.breadcrumbswebservice.breadcrumbswebservice;
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

  //READ

  @RequestMapping(value = "/location", method = RequestMethod.GET)
  public ResponseEntity<List<Location>> locationSearch(
      @RequestParam(value = "searchString", defaultValue = "default") String searchString) {
    List<Location> locations = new ArrayList<>();

    try {
      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL = "select * from location";
      ResultSet rs;

      if (searchString.equals("default")) {

        SQL = "select * from location";
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
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
      } else {
        SQL = "select * from location where locationname like " + "'%" + searchString + "%' or locationtag like " + "'%"
            + searchString + "%'";
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
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

      }
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
  }

  //CREATE

  @RequestMapping(value = "/location", method = RequestMethod.POST)
  public ResponseEntity<String> locationCreate(
      @RequestParam(value = "locationName", defaultValue = "defaultLocationName") String locationName,
      @RequestParam(value = "streetNumber", defaultValue = "0000") int streetNumber,
      @RequestParam(value = "roadName", defaultValue = "defaultRoadName") String roadName,
      @RequestParam(value = "city", defaultValue = "defaultCity") String city,
      @RequestParam(value = "state", defaultValue = "XX") String state,
      @RequestParam(value = "zipCode", defaultValue = "00000") int zipCode,
      @RequestParam(value = "locationTag", defaultValue = "default,tag") String locationTag,
      @RequestParam(value = "imgID", defaultValue = "0000") int imgID) {

    String response = "Inserted Entry";

    try {
      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL = "select * from location";
      ResultSet rs;
      int locationID = 0;

      // get highest ID and increment
      rs = stmt.executeQuery(SQL);
      while (rs.next()) {
        if (rs.getInt("locationID") > locationID) {
          locationID = rs.getInt("locationID");

        }
      }
      locationID++;

      SQL = "insert into Location values (" + locationID + "," + "'" + locationName + "',"
          + streetNumber + "," + "'" + roadName + "'," + "'" + city + "'," + "'" + state + "',"
          + zipCode + "," + "'" + locationTag + "'," + imgID + ")";

      
      stmt.executeQuery(SQL);

    } catch (SQLException e) {
      //e.printStackTrace();
    }

    return new ResponseEntity<String>(response, HttpStatus.OK);

  }

  //DELETE

  @RequestMapping(value = "/location", method = RequestMethod.DELETE)
  public ResponseEntity<String> locationDelete(
      @RequestParam(value = "locationID", defaultValue = "-1") int locationID) {

    String response = "Deleted Entry";
    try {

      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL;
      ResultSet rs;


      // no location requested for deletion
      if (locationID == -1) {
        response = "Could not find entry";
        return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
      }

      else {

        SQL = "delete from location where locationID = " + locationID;
        rs = stmt.executeQuery(SQL);

      }
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<String>(response, HttpStatus.OK);
  }

  //UPDATE

  @RequestMapping(value = "/location", method = RequestMethod.PUT)
  public ResponseEntity<String> locationUpdate(
      @RequestParam(value = "locationID", defaultValue = "-1") int locationID,
      @RequestParam(value = "locationName", defaultValue = "defaultLocationName") String locationName,
      @RequestParam(value = "streetNumber", defaultValue = "0000") int streetNumber,
      @RequestParam(value = "roadName", defaultValue = "defaultRoadName") String roadName,
      @RequestParam(value = "city", defaultValue = "defaultCity") String city,
      @RequestParam(value = "state", defaultValue = "XX") String state,
      @RequestParam(value = "zipCode", defaultValue = "00000") int zipCode,
      @RequestParam(value = "locationTag", defaultValue = "default,tag") String locationTag,
      @RequestParam(value = "imgID", defaultValue = "0000") int imgID) {

    String response = "Updated Entry";

    try {
      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL = "";
      
      SQL = "update location set locationName ='"+ locationName + "',"
          + "streetNumber = " + streetNumber + "," + "roadName = '" + roadName + "'," + "city = '" + city + "'," + "state = '" + state + "',"
          + "zipCode = " + zipCode + "," + "locationTag = '" + locationTag + "'," + "imgID =" + imgID + "where locationID =" + locationID ;

      
      stmt.executeQuery(SQL);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<String>(response, HttpStatus.OK);

  }
}