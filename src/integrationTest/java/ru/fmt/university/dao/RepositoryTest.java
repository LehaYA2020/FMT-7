package ru.fmt.university.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.fmt.university.dao.implementation.hibernate.*;
import ru.fmt.university.dao.implementation.jdbcTemplate.*;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.dto.*;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public abstract class RepositoryTest {
    protected static final List<Course> testCourseList = new LinkedList<>();
    protected static final List<Group> testGroupList = new LinkedList<>();
    protected static final List<Student> testStudentList = new LinkedList<>();
    protected static final List<Teacher> testTeacherList = new LinkedList<>();
    protected static final List<Lesson> testLessonList = new LinkedList<>();

    @Autowired(required = false)
    protected CourseRepositoryJdbcTemplateImpl courseRepository;
    @Autowired(required = false)
    protected GroupRepositoryJdbcTemplateImpl groupRepository;
    @Autowired(required = false)
    protected LessonRepositoryJdbcTemplateImpl lessonRepository;
    @Autowired(required = false)
    protected StudentRepositoryJdbcTemplateImpl studentRepository;
    @Autowired(required = false)
    protected TeacherRepositoryJdbcTemplateImpl teacherRepository;

    @Autowired(required = false)
    protected CourseRepositoryHibernateImpl courseRepositoryHibernate;
    @Autowired(required = false)
    protected GroupRepositoryHibernateImpl groupRepositoryHibernate;
    @Autowired(required = false)
    protected LessonRepositoryHibernateImpl lessonRepositoryHibernate;
    @Autowired(required = false)
    protected StudentRepositoryHibernateImpl studentRepositoryHibernate;
    @Autowired(required = false)
    protected TeacherRepositoryHibernateImpl teacherRepositoryHibernate;

    @Autowired
    protected DataSource dataSource;
    protected ScriptRunner scriptRunner;
    @Autowired
    ApplicationContext context;

    @BeforeAll
    protected static void prepareLists() {

        for (int i = 1; i <= 3; i++) {
            testCourseList.add(new Course(i, "Course-" + i, "forTest"));
            testGroupList.add(new Group(i, "Group-" + i));
        }
        for (int i = 1; i <= 4; i++) {
            testStudentList.add(new Student(i, "S-0" + i, "Student", 1));
        }

        testStudentList.get(2).setGroupId(2);
        testStudentList.get(3).setGroupId(0);

        testTeacherList.add(new Teacher(1, "T-" + 1, "Teacher", testCourseList.get(0).getId()));
        testTeacherList.add(new Teacher(2, "T-" + 2, "Teacher", testCourseList.get(0).getId()));
        testTeacherList.add(new Teacher(3, "T-" + 3, "Teacher", testCourseList.get(1).getId()));

        testLessonList.add(new Lesson(1, testCourseList.get(0).getId(), new Teacher(1).getId(), 10,
                DayOfWeek.MONDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE));
        testLessonList.add(new Lesson(2, testCourseList.get(1).getId(), new Teacher(1).getId(), 10,
                DayOfWeek.TUESDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE));
        testLessonList.add(new Lesson(3, testCourseList.get(1).getId(), 2, 20,
                DayOfWeek.FRIDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE));
    }

    @AfterAll
    public static void clearLists() {
        testCourseList.clear();
        testLessonList.clear();
        testTeacherList.clear();
        testGroupList.clear();
        testStudentList.clear();
    }

    @BeforeEach
    protected void fillDb() throws Exception {
        scriptRunner = new ScriptRunner(dataSource.getConnection());
        Reader createDatabaseReader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("createTables.sql").getFile()));
        scriptRunner.runScript(createDatabaseReader);

        Reader fillDatabaseReader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("fillDb.sql").getFile()));
        scriptRunner.runScript(fillDatabaseReader);
        scriptRunner.closeConnection();
    }

    @AfterEach
    public void clearDatabase() throws Exception {
        scriptRunner = new ScriptRunner(dataSource.getConnection());
        Reader reader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("clearDatabase.sql").getFile()));
        scriptRunner.runScript(reader);
        scriptRunner.closeConnection();
    }
}
