package ru.fmt.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fmt.university.dao.interfaces.*;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.dto.*;
import ru.fmt.university.service.implementation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;

@SpringBootTest
public abstract class ServiceTest {

    protected static Course expectedCourse = new Course(1, "Test", "Course");
    protected static Group expectedGroup = new Group(1, "Test");
    protected static Student expectedStudent = new Student(1, "fName", "lName");
    protected static Teacher expectedTeacher = new Teacher(1, "TestT", "lName", expectedCourse.getId());
    protected static Lesson expectedLesson = new Lesson(1, expectedCourse.getId(), expectedTeacher.getId(), 10, DayOfWeek.MONDAY,
            LocalTime.of(9, 30, 0), LessonType.LECTURE);
    protected static List<Lesson> expectedLessons = singletonList(expectedLesson);
    protected static List<Teacher> expectedTeachers = singletonList(expectedTeacher);
    protected static List<Course> expectedCourses = singletonList(expectedCourse);
    protected static List<Group> expectedGroups = singletonList(expectedGroup);
    protected static List<Student> expectedStudents = singletonList(expectedStudent);

    @Autowired
    protected CourseService courseService;
    @Autowired
    protected GroupService groupService;
    @Autowired
    protected LessonService lessonService;
    @Autowired
    protected StudentService studentService;
    @Autowired
    protected TeacherService teacherService;

    @MockBean
    protected ICourseRepository courseRepository;
    @MockBean
    protected IGroupRepository groupRepository;
    @MockBean
    protected ILessonRepository lessonRepository;
    @MockBean
    protected IStudentRepository studentRepository;
    @MockBean
    protected ITeacherRepository teacherRepository;
}
