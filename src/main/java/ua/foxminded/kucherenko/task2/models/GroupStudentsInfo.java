package ua.foxminded.kucherenko.task2.models;

public class GroupStudentsInfo {
    private int groupId;
    private String groupName;
    private int numOfStudents;

    public GroupStudentsInfo() {
    }

    public GroupStudentsInfo(int groupId, String groupName, int numOfStudents) {
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

    public int getNumOfStudents() {
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
