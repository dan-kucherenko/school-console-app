package ua.foxminded.kucherenko.task2.queries.find_students_num;

import org.postgresql.PGResultSetMetaData;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindGroupsStudentsNum implements IResultingQuery<List<GroupStudentsInfo>, FindGroupsStudentsNumData> {
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH = "src/main/resources/sql_queries/business_queries/find_groups_students_num.sql";
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER = QueryParser.parseQuery(FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH);

    @Override
    public List<GroupStudentsInfo> executeQueryWithRes(FindGroupsStudentsNumData data) {
        List<GroupStudentsInfo> res = null;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(FIND_GROUPS_BY_STUDENTS_NUMBER)) {
            statement.setInt(1, data.getStudentsQuantity());
            resultSet = statement.executeQuery();
            res = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<GroupStudentsInfo> parseResultSet(ResultSet resultSet) {
        List<GroupStudentsInfo> res = new ArrayList<>();
        GroupStudentsInfo group = null;
        try {
            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String groupName = resultSet.getString("group_name");
                int studentsQuantity = resultSet.getInt("num_of_students");
                group = new GroupStudentsInfo(groupId, groupName, studentsQuantity);
                res.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
