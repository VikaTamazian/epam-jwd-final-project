package com.tamazian.dao.impl;

import com.tamazian.dao.FlightDao;
import com.tamazian.entity.Flight;
import com.tamazian.entity.enam.Aircraft;
import com.tamazian.entity.enam.Airport;
import com.tamazian.exception.DaoException;
import com.tamazian.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDaoImpl implements FlightDao {

    private final static FlightDaoImpl INSTANCE = new FlightDaoImpl();

    private static final String FIND_ALL_FLIGHTS_SQL = """
            SELECT id, name, date, aircraft_id, to_airport_id, from_airport_id, captain, first_officer, chief_steward, steward_1, steward_2
            FROM flight
            """;

    private static final String FIND_FLIGHT_BY_ID_SQL = FIND_ALL_FLIGHTS_SQL + " WHERE f.id = ?";

    private static final String DELETE_SQL = """
            DELETE FROM flight
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = "UPDATE flight SET name=?, date=?, to_aircraft_id=?, from_aircraft_id=?, captain=?, first_officer=?, chief_steward=?, steward_1=?, steward_2=? WHERE id= ?";

    private static final String SAVE_SQL = """
            INSERT INTO flight (name, date, aircraft_id, to_airport_id, from_airport_id, captain, first_officer, chief_steward, steward_1, steward_2)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String FIND_FLIGHT_BY_NAME_SQL = FIND_ALL_FLIGHTS_SQL + " WHERE f.name = ?";
    private static final String FIND_FLIGHT_BY_USER_NAME_SQL = FIND_ALL_FLIGHTS_SQL + " WHERE captain=? or first_officer=? or chief_steward=? or steward_1=? or steward_2=?";
    private static final String CHANGE_AIRCRAFT_BY_NAME = "UPDATE flight SET aircraft_id = ? WHERE name = ?";


    private FlightDaoImpl() {
    }

    public static FlightDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Flight> findAll() throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_FLIGHTS_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Flight> flights = new ArrayList<>();

            while (resultSet.next()) {
                flights.add(getBuild(resultSet));
            }
            return flights;

        } catch (SQLException e) {
            throw new DaoException("Error with showing the list of flights", e);
        }
    }

    @Override
    public Optional<Flight> findById(Integer id) throws DaoException {
        Flight flight = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_FLIGHT_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flight = getBuild(resultSet);
            }
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Error with find User by id .", e);
            throw new DaoException("Error with find flight by id", e);
        }

        return Optional.ofNullable(flight);
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Impossible to delete the flight", e);
        }
    }

    @Override
    public void update(Flight entity) throws DaoException {

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDate());
            preparedStatement.setObject(3, entity.getAircraftId());
            preparedStatement.setObject(4, entity.getToAirportId());
            preparedStatement.setObject(5, entity.getFromAirportId());
            preparedStatement.setObject(6, entity.getCaptainId());
            preparedStatement.setObject(7, entity.getFirstOfficerId());
            preparedStatement.setObject(8, entity.getChiefStewardId());
            preparedStatement.setObject(9, entity.getFirstStewardId());
            preparedStatement.setObject(10, entity.getSecondStewardId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Impossible to update", e);
        }
    }

    @Override
    public void save(Flight entity) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDate());
            preparedStatement.setObject(3, entity.getAircraftId());
            preparedStatement.setObject(4, entity.getToAirportId());
            preparedStatement.setObject(5, entity.getFromAirportId());
            preparedStatement.setObject(6, entity.getCaptainId());
            preparedStatement.setObject(7, entity.getFirstOfficerId());
            preparedStatement.setObject(8, entity.getChiefStewardId());
            preparedStatement.setObject(9, entity.getFirstStewardId());
            preparedStatement.setObject(10, entity.getSecondStewardId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            throw new DaoException("Error with adding new flight", e);

        }
    }

    @Override
    public Optional<Flight> findFlightByName(String name) throws DaoException {
        Flight flight = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_FLIGHT_BY_NAME_SQL)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flight = getBuild(resultSet);
            }
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Error with find User by email .", e);
            throw new DaoException("Impossible to find User by email .", e);
        }
        return Optional.empty();
    }


    @Override
    public List<Flight> findFlightByUserName(Integer id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_FLIGHT_BY_USER_NAME_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            if (resultSet.next()) {
                flights.add(getBuild(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Error with find User by email .", e);
            throw new DaoException("Impossible to find flights", e);
        }

    }

    @Override
    public void changeAircraftByName(String name, Integer aircraftId) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(CHANGE_AIRCRAFT_BY_NAME)) {
            statement.setInt(1, aircraftId);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            // LOGGER.log(Level.ERROR, "Error with changing git link. ", e);
            throw new DaoException("Error with changing git link. ", e);
        }
    }

    @Override
    public int getFlightCount() throws DaoException {
        return rowCountByQuery(FIND_ALL_FLIGHTS_SQL);
    }


    private Flight getBuild(ResultSet resultSet) throws SQLException {
        return Flight.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .date(resultSet.getObject("date", Timestamp.class).toLocalDateTime())
                .aircraftId(Aircraft.find(resultSet.getObject("aircraft_id", String.class)).orElse(null))
                .toAirportId(Airport.find(resultSet.getObject("to_airport_id", String.class)).orElse(null))
                .fromAirportId(Airport.find(resultSet.getObject("from_airport_id", String.class)).orElse(null))
                .captainId(resultSet.getObject("captain", Integer.class))
                .firstOfficerId(resultSet.getObject("first_officer", Integer.class))
                //.title(Title.find(resultSet.getObject("title", String.class)).orElse(null))
                .chiefStewardId(resultSet.getObject("chief_steward", Integer.class))
                .firstStewardId(resultSet.getObject("steward_1", Integer.class))
                .secondStewardId(resultSet.getObject("steward_2", Integer.class))
                //   .title(Title.valueOf(resultSet.getObject("title", String.class))
                .build();
    }
}
