package ua.foxminded.kucherenko.task2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.kucherenko.task2.models.Group;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("""
            SELECT NEW GroupStudentsInfo (g.groupId, g.groupName, COUNT(s))
            FROM Student s
                     INNER JOIN Group g ON s.groupId = g.groupId
            WHERE s.groupId IS NOT NULL
            GROUP BY g.groupId, g.groupName
            HAVING COUNT(g) <= :studentsNum""")
    List<GroupStudentsInfo> getGroupByStudentNum(int studentsNum);
    @Query("SELECT g.groupId FROM Group g")
    List<Integer> getAllGroupIds();
    @Query("""
            SELECT COUNT (*) AS num_of_students
            FROM Student s
            WHERE s.groupId = :groupId
            """)
    int getGroupQuantity(int groupId);
}
