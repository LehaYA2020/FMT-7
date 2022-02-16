package ru.fmt.university.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import ru.fmt.university.dao.implementation.hibernate.*;
import ru.fmt.university.dao.implementation.jpa.*;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.entity.*;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DataJpaTest
@Sql(scripts = {"/createTables.sql", "/create-data.sql"})
@Sql(scripts = "/cleanup-data.sql", executionPhase = AFTER_TEST_METHOD)
public abstract class RepositoryTest {
    protected static final List<CourseEntity> testCourseList = new LinkedList<>();
    protected static final List<GroupEntity> testGroupList = new LinkedList<>();
    protected static final List<StudentEntity> testStudentList = new LinkedList<>();
    protected static final List<TeacherEntity> testTeacherList = new LinkedList<>();
    protected static final List<LessonEntity> testLessonList = new LinkedList<>();

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

    @Autowired(required = false)
    protected CourseJpa courseJpa;
    @Autowired(required = false)
    protected GroupJpa groupJpa;
    @Autowired(required = false)
    protected LessonJpa lessonJpa;
    @Autowired(required = false)
    protected StudentJpa studentJpa;
    @Autowired(required = false)
    protected TeacherJpa teacherJpa;

    @Autowired
    protected DataSource dataSource;
    @Autowired
    ApplicationContext context;

    @BeforeAll
    protected static void prepareLists() {

        for (int i = 1; i <= 3; i++) {
            testCourseList.add(new CourseEntity(i, "Course-" + i, "forTest"));
            testGroupList.add(new GroupEntity(i, "Group-" + i));
        }
        for (int i = 1; i <= 4; i++) {
            testStudentList.add(new StudentEntity(i, "S-0" + i, "Student", testGroupList.get(0)));
        }

        testStudentList.get(2).setGroup(testGroupList.get(1));
        testStudentList.get(3).setGroup(null);

        testTeacherList.add(new TeacherEntity(1, "T-" + 1, "Teacher", testCourseList.get(0)));
        testTeacherList.add(new TeacherEntity(2, "T-" + 2, "Teacher", testCourseList.get(0)));
        testTeacherList.add(new TeacherEntity(3, "T-" + 3, "Teacher", testCourseList.get(1)));

        testLessonList.add(new LessonEntity(1, testCourseList.get(0), testTeacherList.get(0), 10,
                DayOfWeek.MONDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE));
        testLessonList.add(new LessonEntity(2, testCourseList.get(1), testTeacherList.get(0), 10,
                DayOfWeek.TUESDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE));
        testLessonList.add(new LessonEntity(3, testCourseList.get(1), testTeacherList.get(1), 20,
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

    /*@BeforeEach
    protected void fillDb() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        Reader createDatabaseReader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("createTables.sql").getFile()));
        scriptRunner.runScript(createDatabaseReader);

        Reader fillDatabaseReader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("create-data.sql").getFile()));
        scriptRunner.runScript(fillDatabaseReader);
        scriptRunner.closeConnection();
    }*/

    /*@AfterEach
    public void clearDatabase() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        Reader reader = new BufferedReader(
                new FileReader(context.getClassLoader().getResource("cleanup-create-data.sql").getFile()));
        scriptRunner.runScript(reader);
        scriptRunner.closeConnection();
    }*/
}
