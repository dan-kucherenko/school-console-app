package ua.foxminded.kucherenko.task2.models;

public class StudentCourse {
    private int studentId;
    private int courseId;

    public StudentCourse() {
    }

    public StudentCourse(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse that = (StudentCourse) o;

        if (getStudentId() != that.getStudentId()) return false;
        return getCourseId() == that.getCourseId();
    }

    @Override
    public int hashCode() {
        int result = getStudentId();
        result = 31 * result + getCourseId();
        return result;
    }
}
