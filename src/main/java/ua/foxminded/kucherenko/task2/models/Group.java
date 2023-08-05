package ua.foxminded.kucherenko.task2.models;

public class Group {
    private final int id;
    private final String name;
    private final int studentsQuantity;

    public Group(int id, String name, int studentQuantity) {
        this.id = id;
        this.name = name;
        this.studentsQuantity = studentQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStudentsQuantity() {
        return studentsQuantity;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + studentsQuantity +
                '}';
    }
}
