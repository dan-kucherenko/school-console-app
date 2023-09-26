package ua.foxminded.kucherenko.task2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GroupStudentsInfo {
    @Id
    private int groupId;
    private String groupName;
    private long numOfStudents;

    public GroupStudentsInfo() {
    }

    public GroupStudentsInfo(int groupId, String groupName, long numOfStudents) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.numOfStudents = numOfStudents;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public long getNumOfStudents() {
        return numOfStudents;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setNumOfStudents(int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + groupId +
                ", name='" + groupName + '\'' +
                ", quantity=" + numOfStudents +
                '}';
    }
}
