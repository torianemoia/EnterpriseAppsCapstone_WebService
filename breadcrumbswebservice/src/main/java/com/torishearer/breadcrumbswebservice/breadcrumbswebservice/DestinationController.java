package com.torishearer.breadcrumbswebservice.breadcrumbswebservice;

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

      // No arguments returns all destinations
      if (searchString.equals("default")) {
        SQL = "select * from destination";
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
          Destination aDestination = new Destination();
          aDestination.setDestinationID(rs.getInt("destinationID"));
          aDestination.setDestinationName(rs.getString("destinationName"));
          aDestination.setDestinationTag(rs.getString("destinationTag"));
          aDestination.setDirections(rs.getString("directions"));
          aDestination.setLocationID(rs.getInt("locationID"));
          aDestination.setImgID(rs.getInt("imgID"));
          aDestination.setUserID(rs.getInt("userID"));
          destinations.add(aDestination);
        }

        // Else searches DestinationName and DestinationTag
      } else {

        SQL = "select * from destination where destinationname like " + "'%" + searchString + "%'"
            + "or destinationTag like '%" + searchString + "%'";
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
          Destination aDestination = new Destination();
          aDestination.setDestinationID(rs.getInt("destinationID"));
          aDestination.setDestinationName(rs.getString("destinationName"));
          aDestination.setDestinationTag(rs.getString("destinationTag"));
          aDestination.setDirections(rs.getString("directions"));
          aDestination.setLocationID(rs.getInt("locationID"));
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

  @RequestMapping(value = "/destination", method = RequestMethod.POST)
  public ResponseEntity<String> destinationCreate(
      @RequestParam(value = "destinationName", defaultValue = "defaultDestinationName") String destinationName,
      @RequestParam(value = "destinationTag", defaultValue = "default,tag") String destinationTag,
      @RequestParam(value = "directions", defaultValue = "direction*description;direction*description") String directions,
      @RequestParam(value = "locationID", defaultValue = "0000") int locationID,
      @RequestParam(value = "imgID", defaultValue = "0000") int imgID,
      @RequestParam(value = "userID", defaultValue = "0000") int userID) {

    String response = "Insert Entry Fired";

    try {
      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL = "select * from destination";
      ResultSet rs;
      int destinationID = 0;

      // get highest ID and increment
      rs = stmt.executeQuery(SQL);
      while (rs.next()) {
        if (rs.getInt("destinationID") > destinationID) {
          destinationID = rs.getInt("destinationID");
        }
      }
      destinationID++;

      SQL = "insert into destination values (" + destinationID + "," + "'" + destinationName + "',"
          + "'" + destinationTag + "'," + "'" + directions + "'," + locationID + "," + imgID + "," + userID + ")";

      stmt.executeQuery(SQL);

    } catch (SQLException e) {
      // e.printStackTrace();
    }

    return new ResponseEntity<String>(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/destination", method = RequestMethod.DELETE)
  public ResponseEntity<String> destinationDelete(
      @RequestParam(value = "destinationID", defaultValue = "-1") int destinationID) {

    String response = "Deleted Entry";
    try {

      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL;
      ResultSet rs;


      // no destination requested for deletion
      if (destinationID == -1) {
        response = "Could not find entry";
        return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
      }

      else {

        SQL = "delete from destination where destinationID = " + destinationID;
        rs = stmt.executeQuery(SQL);

      }
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<String>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/destination", method = RequestMethod.PUT)
  public ResponseEntity<String> destinationUpdate(
    @RequestParam(value = "destinationID", defaultValue = "0000")int destinationID,
    @RequestParam(value = "destinationName", defaultValue = "defaultDestinationName") String destinationName,
    @RequestParam(value = "destinationTag", defaultValue = "default,tag") String destinationTag,
    @RequestParam(value = "directions", defaultValue = "direction*description;direction*description") String directions,
    @RequestParam(value = "locationID", defaultValue = "0000") int locationID,
    @RequestParam(value = "imgID", defaultValue = "0000") int imgID,
    @RequestParam(value = "userID", defaultValue = "0000") int userID){

    String response = "Updated Entry";

    try {
      Connection con = DriverManager.getConnection(connectionURL);
      Statement stmt = con.createStatement();
      String SQL = "";
      
      SQL = "update destination set destinationName ='"+ destinationName + "',"
          + "destinationTag = '" + destinationTag + "'," + "directions = '" + directions + "'," + "locationID = " + locationID + "," + "imgID = " + imgID + ","
          + "userID = " +userID + "where destinationID =" + destinationID ;

      
      stmt.executeQuery(SQL);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<String>(response, HttpStatus.OK);

  }
}