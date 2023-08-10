package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindGroupsStudentsNum implements IResultingQuery<List<GroupStudentsInfo>> {
    private int studentsQuantity;
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER = """
            SELECT students.group_id, group_name, COUNT(*) AS num_of_students
            FROM school.students
                INNER JOIN school.groups ON school.students.group_id = school.groups.group_id
            WHERE students.group_id IS NOT NULL
            GROUP BY students.group_id, group_name
            HAVING COUNT(*) <= ?
            """;

    private void passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the maximum number of students in group you want to find: ");
        studentsQuantity = sc.nextInt();
        if (studentsQuantity < 0) {
            throw new IllegalArgumentException("Number of students can't be less than 0");
        }
    }

    @Override
    public List<GroupStudentsInfo> executeQueryWithRes() {
        List<GroupStudentsInfo> res = null;
        ResultSet resultSet = null;

        passData();
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(FIND_GROUPS_BY_STUDENTS_NUMBER)) {
            statement.setInt(1, studentsQuantity);
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
