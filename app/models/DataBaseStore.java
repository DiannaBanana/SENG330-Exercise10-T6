package models;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseStore implements ObservationStore {


    public DataBaseStore(){
        var url = "jdbc:h2:tcp://localhost:9092/~/tmp/h2dbs/testdb";
        var user = "sa";
        var passwd = "s$cret";

        var query = "SELECT * FROM cars";

        try (var con = DriverManager.getConnection(url, user, passwd);
             var st = con.createStatement();
             var rs = st.executeQuery(query)) {

            while (rs.next()) {

                System.out.printf("%d %s %d%n", rs.getInt(1),
                        rs.getString(2), rs.getInt(3));
            }

        } catch (SQLException ex) {
            var lgr = Logger.getLogger(DataBaseStore.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Observation> getObservationById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Observation> getObservations() {
        return null;
    }

    @Override
    public void addObservation(Observation toAdd) {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public void removeObservationById(long id) {

    }
}
