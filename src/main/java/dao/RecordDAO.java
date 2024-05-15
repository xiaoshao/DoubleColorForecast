package dao;

import model.RecordModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RecordDAO {
    Connection dbConnection;

    public RecordDAO() throws SQLException {
        this.dbConnection = MysqlConnectionFactory.getMysqlConnection();
    }

    public void addRecord(RecordModel model) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement("insert into history_record(no, first, second, third, forth, fifth, sixth, blue) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setInt(0, model.getNo());
        preparedStatement.setInt(1, model.getFirst());
        preparedStatement.setInt(2, model.getSecond());
        preparedStatement.setInt(3, model.getThird());
        preparedStatement.setInt(4, model.getForth());
        preparedStatement.setInt(5, model.getFifth());
        preparedStatement.setInt(6, model.getSix());
        preparedStatement.setInt(7, model.getBlue());

        preparedStatement.execute();
    }

    public void addRecords(List<RecordModel> models) throws SQLException {
        try {
            for (RecordModel model : models) {
                addRecord(model);
            }
            this.dbConnection.commit();
        } catch (SQLException e) {
            this.dbConnection.rollback();
        }
    }
}
