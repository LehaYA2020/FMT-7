insert into courses (name, description) values ('Course-1', 'forTest'), ('Course-2', 'forTest'), ('Course-3', 'forTest');

insert into groups (name) values ('Group-1'), ('Group-2'), ('Group-3');

insert into students (first_name, last_name, group_id) values ('S-01', 'Student', 1), ('S-02', 'Student', 1), ('S-03', 'Student', 2);
 insert into students (first_name, last_name) values('S-04', 'Student');

insert into teachers (first_name, last_name, course_id) values ('T-1', 'Teacher', 1), ('T-2', 'Teacher', 1), ('T-3', 'Teacher', 2);

insert into lessons (course_id, teacher_id, classroom, day, time, type) values (1, 1, 10, 'MONDAY', '09:30:00', 'LECTURE'), (2, 1, 10, 'TUESDAY', '09:30:00', 'LECTURE'), (2, 2, 20, 'FRIDAY', '09:30:00', 'LECTURE');


insert into groups_courses (group_id, course_id) values (1, 1), (1, 2), (2, 2);

insert into lessons_groups (lesson_id, group_id) values (1, 1), (2, 1), (2, 2), (3, 2);