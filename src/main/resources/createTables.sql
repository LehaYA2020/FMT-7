CREATE TABLE IF NOT EXISTS groups
(
  id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(30) NOT NULL UNIQUE CHECK (name != ''),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS students
(
  id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
  first_name VARCHAR(30) NOT NULL CHECK (first_name != ''),
  last_name VARCHAR(30) NOT NULL CHECK (last_name != ''),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS courses
(
  id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(30) NOT NULL CHECK (name != ''),
  description VARCHAR(200) NOT NULL CHECK (description != ''),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS groups_courses
(
  group_id bigint NOT NULL,
  course_id bigint NOT NULL,
  UNIQUE (group_id , course_id),
  FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS students_groups
(
  student_id bigint NOT NULL UNIQUE,
  group_id bigint NOT NULL,
  FOREIGN KEY (student_id) REFERENCES students(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teachers
(
  id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
  first_name VARCHAR(30) NOT NULL CHECK (first_name != ''),
  last_name VARCHAR(30) NOT NULL CHECK (last_name != ''),
  course_id INTEGER,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS lessons
(
  id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
  course_id INTEGER,
  teacher_id INTEGER,
  classroom INTEGER,
  day VARCHAR(30) NOT NULL CHECK (day != ''),
  time TIME NOT NULL,
  type VARCHAR(30) NOT NULL CHECK (type != ''),
  FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS lessons_groups
(
  group_id bigint NOT NULL,
  lesson_id bigint NOT NULL,
  UNIQUE (group_id , lesson_id),
  FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON UPDATE CASCADE ON DELETE CASCADE
);