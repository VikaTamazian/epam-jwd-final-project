package com.tamazian.dao;

import com.tamazian.entity.Flight;
import com.tamazian.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface FlightDao extends CommonDao<Integer, Flight> {

    Optional<Flight> findFlightByName(String name) throws DaoException;

    List<Flight> findFlightByUserName(Integer id) throws DaoException;

    void changeAircraftByName(String name, Integer aircraftId) throws DaoException;

    int getFlightCount() throws DaoException;

}
