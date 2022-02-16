package ru.fmt.university.dao.jpa;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class TeacherJpaTest {
}
