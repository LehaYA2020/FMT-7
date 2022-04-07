package ru.fmt.university.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan({"ru.fmt.university.service", "ru.fmt.university.dao.interfaces"})
public class ServiceTestConfig {
}
