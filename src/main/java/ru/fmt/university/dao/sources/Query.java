package ru.fmt.university.dao.sources;

public enum Query {
    GET_ALL_STUDENTS("SELECT id, first_name,last_name, group_id FROM students;"),
    GET_STUDENT_BY_ID("SELECT students.id, students.first_name, students.last_name, group_id FROM students WHERE students.id = ?;"),
    DELETE_STUDENT("DELETE FROM students WHERE id = ?;"),
    GET_STUDENT_BY_GROUP("SELECT * FROM students WHERE group_id=?;"),
    INSERT_STUDENT("INSERT INTO students(first_name, last_name, group_id) VALUES(?, ?, ?);"),
    ASSIGN_STUDENT_TO_GROUP("UPDATE students set group_id=? where id=?;"),
    UPDATE_STUDENT("UPDATE students set first_name=?, last_name=? WHERE id=?;"),
    DELETE_STUDENT_FROM_GROUP("UPDATE students set group_id=null WHERE id=? and group_id=?;"),
    UPDATE_STUDENT_ASSIGNMENTS("UPDATE students set group_id=? where id=?;"),

    GET_ALL_GROUPS("SELECT * FROM groups;"),
    INSERT_GROUP("INSERT INTO groups(name) VALUES (?);"),
    DELETE_GROUP("DELETE FROM groups WHERE id=?;"),
    GET_GROUP_BY_ID("SELECT * FROM groups WHERE id=?;"),
    ASSIGN_GROUP_TO_COURSE("INSERT INTO groups_courses(group_id, course_id) values (?, ?);"),
    DELETE_GROUP_FROM_COURSE("DELETE FROM groups_courses WHERE group_id=? AND course_id=?;"),
    GET_GROUPS_BY_LESSON("SELECT * FROM groups, lessons_groups where lesson_id=? and groups.id=lessons_groups.group_id;"),
    GET_GROUPS_BY_COURSE("SELECT * FROM groups, groups_courses where course_id=? and groups.id=groups_courses.group_id;"),
    GET_GROUPS_BY_STUDENT("SELECT * FROM groups, students where students.id=? and groups.id=students.group_id;"),
    UPDATE_GROUP("UPDATE groups set name=? WHERE id=?;"),

    INSERT_TEACHER("INSERT INTO teachers(first_name, last_name, course_id) VALUES(?, ?, ?);"),
    GET_ALL_TEACHERS("SELECT * FROM teachers;"),
    GET_TEACHER_BY_ID("SELECT * FROM teachers WHERE id=?;"),
    UPDATE_TEACHER_BY_ID("UPDATE teachers set first_name=?, last_name=?, course_id=? WHERE id=?;"),
    DELETE_TEACHER("DELETE FROM teachers WHERE id=?;"),
    GET_TEACHERS_BY_COURSE("SELECT * FROM teachers WHERE course_id=?;"),
    GET_TEACHERS_BY_LESSON("SELECT teachers.* FROM teachers, lessons WHERE lessons.id=? AND teacher_id=teachers.id;"),

    GET_ALL_COURSES("SELECT * FROM courses;"),
    GET_COURSE_BY_ID("SELECT courses.id, courses.name, courses.description FROM courses WHERE id = ?;"),
    INSERT_COURSE("INSERT INTO courses(name, description) VALUES(?, ?);"),
    UPDATE_COURSE("UPDATE courses SET name=?, description=? WHERE id=?;"),
    DELETE_COURSE("DELETE FROM courses WHERE id=?;"),
    GET_COURSES_BY_GROUP_ID("SELECT * FROM courses, groups_courses WHERE courses.id=groups_courses.course_id AND group_id=?;"),

    INSERT_LESSON("INSERT INTO lessons(course_id, teacher_id, classroom, day, time, type) VALUES (?, ?, ?, ?, ?, ?);"),
    GET_ALL_LESSONS("SELECT * FROM lessons;"),
    GET_LESSON_BY_ID("SELECT * FROM lessons WHERE id=?;"),
    DELETE_LESSON("DELETE FROM lessons WHERE id=?;"),
    UPDATE_LESSON("UPDATE lessons set course_id=?, teacher_id=?, classroom=?, day=?, time=?, type=? WHERE id=?;"),
    GET_LESSON_BY_STUDENT("SELECT lessons.* FROM lessons, lessons_groups, students WHERE students.id=? " +
            "AND lessons_groups.group_id=students.group_id AND lessons.id=lessons_groups.lesson_id;"),
    GET_LESSON_BY_TEACHER("SELECT * FROM lessons WHERE teacher_id=?;"),
    GET_LESSON_BY_COURSE("SELECT * FROM lessons WHERE course_id=?;"),
    GET_LESSON_BY_GROUP("SELECT lessons.* FROM lessons, lessons_groups WHERE lessons_groups.lesson_id=lessons.id AND group_id=?;"),
    ASSIGN_GROUP_TO_LESSON("INSERT INTO lessons_groups(lesson_id, group_id) VALUES (?, ?);"),
    DELETE_GROUP_FROM_LESSON("DELETE FROM lessons_groups WHERE lesson_id=? AND group_id=?;");

    private final String text;

    Query(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
