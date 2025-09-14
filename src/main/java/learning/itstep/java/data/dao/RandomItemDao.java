package learning.itstep.java.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import learning.itstep.java.data.dto.RandomItem;

public class RandomItemDao {
    private final Connection connection;
    public RandomItemDao(Connection connection) {
        this.connection = connection;
    }

    public void add(RandomItem randomItem) {
        String sql = "INSERT INTO `random_items`(`id`, `int_val`, `float_val`, `str_val`)"
                + "VALUES(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, randomItem.getId());
            statement.setInt(2, randomItem.getIntVal());
            statement.setDouble(3, randomItem.getFloatVal());
            statement.setString(4, randomItem.getStrVal());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("RandomItemDao::add " + ex.getMessage());
        }
    }

    public List<RandomItem> getAll() {
        List<RandomItem> res = new ArrayList<>();
        String sql = "SELECT r.`id`, r.`int_val`, r.`float_val`, r.`str_val` "
                + "FROM `random_items` r";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                res.add(RandomItem.fromResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("RandomItemDao::getAll " + ex.getMessage());
        }
        return res;
    }

    public void delete(String id) {
        String sql = "DELETE FROM `random_items` WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("RandomItemDao::delete " + ex.getMessage());
        }
    }
}
