SET @course_1_description = "Цей курс має на меті поділитися з вами силою та красою Kotlin. Ми матимемо базовий огляд мови, а також обговорення багатьох наріжних випадків, особливо щодо взаємодії Java.";
SET @course_2_description = "Ця спеціалізація являє собою поєднання теорії та практики: ви вивчите алгоритмічні прийоми для вирішення різних обчислювальних задач та впровадите близько 100 задач алгоритмічного кодування мовою програмування на ваш вибір.";
SET @course_3_description = "Знання з методами структурованого зберігання даних, основи SQL, принципи використання баз даних у додатках, огляд нереляційних способів зберігання даних.";
SET @course_4_description = "Під час курсу Ви ознайомитеся з історією персональних комп'ютерів, засвоїте поняття алгоритму та навчитеся створювати нескладні комп'ютерні програми сучасною мовою програмування Kotlin.";
SET @course_5_description = "Вивчення навчальної дисципліни `Чисельні методи` дозволяє студентам оволодіти знаннями в галузі практичних методів рішення математичних проблем, що виникають у процесі інженерної діяльності та моделювання фізичних систем";

DROP TABLE IF EXISTS lessons_students;
DROP TABLE IF EXISTS lessons_student_groups;
DROP TABLE IF EXISTS max_attendances;
DROP TABLE IF EXISTS student_groups_courses;
DROP TABLE IF EXISTS student_groups;
DROP TABLE IF EXISTS user_coordinates;
DROP TABLE IF EXISTS user_attendances;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS university;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS university_employees;
DROP TABLE IF EXISTS users_settings;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS settings;
DROP TABLE IF EXISTS specialities;

CREATE TABLE users (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    UNIQUE KEY `full_name` (`first_name`, `middle_name`, `last_name`)
) ENGINE=InnoDB;

CREATE TABLE students (
    id INT UNSIGNED PRIMARY KEY,
    group_id INT UNSIGNED NOT NULL
) ENGINE=InnoDB;

CREATE TABLE university_employees (
    id INT UNSIGNED PRIMARY KEY,
    academic_position VARCHAR(255) NOT NULL,
    administrative_posts VARCHAR(255) NOT NULL,
    academic_degree VARCHAR(255) NOT NULL,
    academic_title VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE student_groups (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    group_leader_id INT UNSIGNED,
    group_curator_id INT UNSIGNED,
    department_id INT UNSIGNED,
    course_number INT UNSIGNED NOT NULL,
    specialty_id INT UNSIGNED NOT NULL
) ENGINE=InnoDB;

CREATE TABLE university (
    id INT UNSIGNED PRIMARY KEY,
    city_id INT UNSIGNED,
    name VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE department (
    id INT UNSIGNED PRIMARY KEY,
    university_id INT UNSIGNED,
    name VARCHAR(255),
    description VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE `courses` (
  `id` int(11) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_date` timestamp NOT NULL,
  `end_date` timestamp NOT NULL,
  `lecturer_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `student_groups_courses`(
    `group_id` int(11) UNSIGNED,
    `course_id` int(11) UNSIGNED,
    PRIMARY KEY (`group_id`, `course_id`)
) ENGINE=InnoDB;

CREATE TABLE `user_attendances` (
  `id` int(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL,
  `course_id` int(10) UNSIGNED NOT NULL,
  `registered_by` int(10) UNSIGNED NOT NULL,
  `registered_timestamp` timestamp NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `settings`(
    `code` varchar(255) COLLATE utf8mb4_unicode_ci PRIMARY KEY,
    `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `default_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `users_settings`(
    `code` varchar(255) COLLATE utf8mb4_unicode_ci PRIMARY KEY,
    `user_id` int(10) UNSIGNED NOT NULL,
    `value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `max_attendances`(
    `student_group_id` INT UNSIGNED,
    `course_id` INT UNSIGNED,
    `value` INT UNSIGNED,
    PRIMARY KEY (`student_group_id`, `course_id`)
) ENGINE=InnoDB;

CREATE TABLE `specialities`(
    `id` INT UNSIGNED PRIMARY KEY,
    `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB;

ALTER TABLE users_settings
ADD FOREIGN KEY (code) REFERENCES settings(code)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE `max_attendances`
ADD FOREIGN KEY (student_group_id) REFERENCES student_groups(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE `max_attendances`
ADD FOREIGN KEY (course_id) REFERENCES courses(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE users_settings
ADD FOREIGN KEY (user_id) REFERENCES users(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE department
ADD FOREIGN KEY (university_id) REFERENCES university(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups
ADD FOREIGN KEY (department_id) REFERENCES department(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups
ADD FOREIGN KEY (group_leader_id) REFERENCES students(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups
ADD FOREIGN KEY (group_curator_id) REFERENCES university_employees(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE students
ADD FOREIGN KEY (id) REFERENCES users(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE university_employees
ADD FOREIGN KEY (id) REFERENCES users(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_attendances
ADD FOREIGN KEY (user_id) REFERENCES users(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_attendances
ADD FOREIGN KEY (course_id) REFERENCES courses(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_attendances
ADD FOREIGN KEY (registered_by) REFERENCES university_employees(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE courses
ADD FOREIGN KEY (lecturer_id) REFERENCES university_employees(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups
ADD FOREIGN KEY (specialty_id) REFERENCES specialities(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups_courses
ADD FOREIGN KEY (group_id) REFERENCES student_groups(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE student_groups_courses
ADD FOREIGN KEY (course_id) REFERENCES courses(id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO university VALUES
(1, 1, 'KhPI');

INSERT INTO specialities VALUES(
    1, '121', 'Інженерія програмного забезпечення'
), (
    2, '122', 'Комп\'ютерні науки'
), (
    3, '126', 'Інформаційні системи та технології'
)
;

INSERT INTO department VALUES
(1, 1, 'KN', 'KN description'),
(2, 1, 'IT', 'IT description');

INSERT INTO student_groups VALUES
(1, 'КН-218а', NULL, NULL, 1, 3, 1),
(2, 'КН-218б', NULL, NULL, 1, 5, 1),
(3, 'КН-218в', NULL, NULL, 1, 5, 1),
(4, 'КН-218ів', NULL, NULL, 1, 2, 1),
(5, 'КН-218г.е', NULL, NULL, 1, 2, 1),
(6, 'КН-218іа.е', NULL, NULL, 1, 2, 1),
(7, 'КН-218іб.е', NULL, NULL, 1, 2, 1),
(8, 'КН-418', NULL, NULL, 1, 2, 2),
(9, 'КН-718', NULL, NULL, 1, 2, 3)
;

INSERT INTO users VALUES(
   20,
   'mail15',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Андрійович',
   'Андоньєв'
),(
   21,
   'mail16',
   'STUDENT',
   'ACTIVE',
   'Олександр',
   'Андрійович',
   'Аніпко'
),(
   22,
   'mail17',
   'STUDENT',
   'ACTIVE',
   'Нікіта',
   'Сергійович',
   'Багацький'
),(
   23,
   'mail18',
   'STUDENT',
   'ACTIVE',
   'Максим',
   'Володимирович',
   'Боровий'
),(
   24,
   'mail19',
   'STUDENT',
   'ACTIVE',
   'Андрій',
   'Володимирович',
   'Василенко'
),(
   25,
   'mail20',
   'STUDENT',
   'ACTIVE',
   'Ярослав',
   'Віталійович',
   'Вовненко'
),(
   26,
   'mail21',
   'STUDENT',
   'ACTIVE',
   'Олександр',
   'Олександрович',
   'Войналович'
),(
   27,
   'mail22',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Сергійович',
   'Гуляєв'
),(
   28,
   'mail23',
   'STUDENT',
   'ACTIVE',
   'Давид',
   'Федорович',
   'Дахіна'
),(
   29,
   'mail24',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Олексійович',
   'Дьомінов'
),(
   30,
   'mail25',
   'STUDENT',
   'ACTIVE',
   'Світлана',
   'Романівна',
   'Жернова'
),(
   31,
   'mail26',
   'STUDENT',
   'ACTIVE',
   'Максим',
   'Русланович',
   'Іюльський'
),(
   32,
   'mail27',
   'STUDENT',
   'ACTIVE',
   'Сергій',
   'Андрійович',
   'Лавренко'
),(
   33,
   'mail28',
   'STUDENT',
   'ACTIVE',
   'Євгенія',
   'Дмитрівна',
   'Папаценко'
),(
   34,
   'mail29',
   'STUDENT',
   'ACTIVE',
   'Владислав',
   'Сергійович',
   'Шевченко'
),(
   35,
   'mail30',
   'STUDENT',
   'ACTIVE',
   'Владислав',
   'Сергійович',
   'Ярмошенко'
),(
   36,
   'mail31',
   'STUDENT',
   'ACTIVE',
   'Богдан',
   'Биба',
   'Сергійович'
),(
   37,
   'mail32',
   'STUDENT',
   'ACTIVE',
   'Мирослав',
   'Грищенко',
   'Дмитрович'
),(
   38,
   'mail33',
   'STUDENT',
   'ACTIVE',
   'Юлія',
   'Миколаївна',
   'Доленко'
),(
   39,
   'mail34',
   'STUDENT',
   'ACTIVE',
   'Юрій',
   'Сергійович',
   'Івахнов'
),(
   40,
   'mail35',
   'STUDENT',
   'ACTIVE',
   'Яна',
   'Вячеславівна',
   'Кабак'
),(
   41,
   'mail36',
   'STUDENT',
   'ACTIVE',
   'Анастасія',
   'Олександрівна',
   'Лужна'
),(
   42,
   'mail37',
   'STUDENT',
   'ACTIVE',
   'Олександра',
   'Миколаївна',
   'Мальцева'
),(
   43,
   'mail38',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Олександрович',
   'Розсоха'
),(
   44,
   'mail39',
   'STUDENT',
   'ACTIVE',
   'Кирило',
   'Дмитрович',
   'Рудковський'
),(
   45,
   'mail40',
   'STUDENT',
   'ACTIVE',
   'Денис',
   'Вікторович',
   'Ткачук'
),(
   46,
   'mail41',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Вікторович',
   'Чернега'
),(
   47,
   'mail42',
   'STUDENT',
   'ACTIVE',
   'Андрій',
   'Валерійович',
   'Шаталов'
),(
   48,
   'mail43',
   'STUDENT',
   'ACTIVE',
   'Катерина',
   'Вячеславівна',
   'Яковлева'
),(
   49,
   'mail44',
   'STUDENT',
   'ACTIVE',
   'Микита',
   'Віталійович',
   'Атаманичев'
),(
   50,
   'mail45',
   'STUDENT',
   'ACTIVE',
   'Михайло',
   'Віталійович',
   'Бессарабенко'
),(
   51,
   'mail46',
   'STUDENT',
   'ACTIVE',
   'Роман',
   'Юрійович',
   'Бовкун'
),(
   52,
   'mail47',
   'STUDENT',
   'ACTIVE',
   'Юрій',
   'Йосипович',
   'Бойчук'
),(
   53,
   'mail48',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Вадимович',
   'Галаган'
),(
   54,
   'mail49',
   'STUDENT',
   'ACTIVE',
   'Сергій',
   'Володимирович',
   'Долгов'
),(
   55,
   'mail50',
   'STUDENT',
   'ACTIVE',
   'Владислав',
   'Георгійович',
   'Ленартович'
),(
   56,
   'mail51',
   'STUDENT',
   'ACTIVE',
   'Іван',
   'Анатолійович',
   'Личковаха'
),(
   57,
   'mail52',
   'STUDENT',
   'ACTIVE',
   'Артем',
   'Валерійович',
   'Лушин'
),(
   58,
   'mail53',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Ігорович',
   'Мірошниченко'
),(
   59,
   'mail54',
   'STUDENT',
   'ACTIVE',
   'Олександр',
   'Андрійович',
   'Музика'
),(
   60,
   'mail55',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Андрійович',
   'Нікітенко'
),(
   61,
   'mail56',
   'STUDENT',
   'ACTIVE',
   'Владислав',
   'Вікторович',
   'Нікітін'
),(
   62,
   'mail57',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Романович',
   'Павленко'
),(
   63,
   'mail58',
   'STUDENT',
   'ACTIVE',
   'Едуард',
   'Васильович',
   'Рудя'
),(
   64,
   'mail59',
   'STUDENT',
   'ACTIVE',
   'Денис',
   'Васильович',
   'Савін'
),(
   65,
   'mail60',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Олексійович',
   'Степанов'
),(
   66,
   'mail61',
   'STUDENT',
   'ACTIVE',
   'Олександр',
   'Олександрович',
   'Хоруженко'
),(
   67,
   'mail62',
   'STUDENT',
   'ACTIVE',
   'Артем',
   'Сергійович',
   'Шадчнєв'
),(
   68,
   'mail63',
   'STUDENT',
   'ACTIVE',
   'Владислав',
   'Віталійович',
   'Юрченко'
),(
   69,
   'mail64',
   'STUDENT',
   'ACTIVE',
   'Гайрат',
   NULL,
   'Бегімкулов'
),(
   70,
   'mail65',
   'STUDENT',
   'ACTIVE',
   'Рабіі',
   NULL,
   'Бумселлек'
),(
   71,
   'mail66',
   'STUDENT',
   'ACTIVE',
   'Атамират',
   NULL,
   'Махмасахатов'
),(
   72,
   'mail67',
   'STUDENT',
   'ACTIVE',
   'Аюб',
   NULL,
   'Мхарреш'
),(
   73,
   'mail68',
   'STUDENT',
   'ACTIVE',
   'Бобур',
   NULL,
   'Хаджиєв'
),(
   74,
   'mail69',
   'STUDENT',
   'ACTIVE',
   'Річард',
   'Річардович',
   'Білоус'
),(
   75,
   'mail70',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Борисович',
   'Бондаренко'
),(
   76,
   'mail71',
   'STUDENT',
   'ACTIVE',
   'Єсенія',
   'Сергіївна',
   'Борзова'
),(
   77,
   'mail72',
   'STUDENT',
   'ACTIVE',
   'Ілля',
   'Костянтинович',
   'Деретюк'
),(
   78,
   'mail73',
   'STUDENT',
   'ACTIVE',
   'Назар',
   'Романович',
   'Кравченко'
),(
   79,
   'mail74',
   'STUDENT',
   'ACTIVE',
   'Віктор',
   'Олександрович',
   'Лобанов'
),(
   80,
   'mail75',
   'STUDENT',
   'ACTIVE',
   'Максим',
   'Сергійович',
   'Макєєв'
),(
   81,
   'mail76',
   'STUDENT',
   'ACTIVE',
   'Олександр',
   'Сергійович',
   'Пасічний'
),(
   82,
   'mail77',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Володимирович',
   'Полторацький'
),(
   83,
   'mail78',
   'STUDENT',
   'ACTIVE',
   'Максим',
   'Ігорович',
   'Савеня'
),(
   84,
   'mail79',
   'STUDENT',
   'ACTIVE',
   'Євгеній',
   'Олексійович',
   'Слоневський'
),(
   85,
   'mail80',
   'STUDENT',
   'ACTIVE',
   'Вадим',
   'Дмитрович',
   'Шевелєв'
),(
   86,
   'mail81',
   'STUDENT',
   'ACTIVE',
   'Олексій',
   'Сергійович',
   'Шишима'
),(
   87,
   'mail82',
   'STUDENT',
   'ACTIVE',
   'Албаз Алі',
   NULL,
   'Унсал'
),(
   88,
   'mail83',
   'STUDENT',
   'ACTIVE',
   'Уссама',
   '',
   'Баркан'
),(
   89,
   'mail84',
   'STUDENT',
   'ACTIVE',
   'Андрю Ейо-Обонг',
   NULL,
   'Ейо'
),(
   90,
   'mail85',
   'STUDENT',
   'ACTIVE',
   'Арбауті Ілясс',
   NULL,
   'Ель'
),(
   91,
   'mail86',
   'STUDENT',
   'ACTIVE',
   'Ісмаіл',
   NULL,
   'Іхлеф'
),(
   92,
   'mail87',
   'STUDENT',
   'ACTIVE',
   'Омар',
   NULL,
   'Лахмар'
),(
   93,
   'mail88',
   'STUDENT',
   'ACTIVE',
   'Мохамед Рамзи',
   NULL,
   'Ріа'
),(
   94,
   'mail89',
   'STUDENT',
   'ACTIVE',
   'Уссама',
   NULL,
   'Тухран'
),(
   95,
   'mail90',
   'STUDENT',
   'ACTIVE',
   'Іреле Оміке',
   NULL,
   'Чукву'
),(
   96,
   'mail91',
   'STUDENT',
   'ACTIVE',
   'Вурал Джан',
   NULL,
   'Джанболат'
),(
   97,
   'mail92',
   'STUDENT',
   'ACTIVE',
   'Алімшах',
   NULL,
   'Йилдирим '
),(
   98,
   'mail93',
   'STUDENT',
   'ACTIVE',
   'Мохамед Хані Халіл',
   NULL,
   'Набхан'
),(
   99,
   'mail95',
   'STUDENT',
   'ACTIVE',
   'Сервет',
   NULL,
   'Озкан'
),(
   100,
   'mail96',
   'STUDENT',
   'ACTIVE',
   'Тара Огюлджан',
   NULL,
   'Озогюл'
),(
   101,
   'mail97',
   'STUDENT',
   'ACTIVE',
   'Дженк',
   NULL,
   'Фіртіна'
),(
   102,
   'mail98',
   'STUDENT',
   'ACTIVE',
   'Таха',
   NULL,
   'Халлукі'
),(
   103,
   'mail99',
   'STUDENT',
   'ACTIVE',
   'Анастасія',
   'Вячеславівна',
   'Амарант'
),(
   104,
   'mail100',
   'STUDENT',
   'ACTIVE',
   'Сергій',
   'Олександрович',
   'Зоря'
),(
   105,
   'mail101',
   'STUDENT',
   'ACTIVE',
   'Святослав',
   'Євгенійович',
   'Коршунов'
),(
   106,
   'mail102',
   'STUDENT',
   'ACTIVE',
   'Євгеній',
   'Олександрович',
   'Пустогар'
),(
   107,
   'mail103',
   'STUDENT',
   'ACTIVE',
   'Данило',
   'Олексійович',
   'Сисоєв'
),(
   108,
   'mail104',
   'STUDENT',
   'ACTIVE',
   'Юлія',
   'Володимирівна',
   'Стрілець'
),(
   109,
   'mail105',
   'STUDENT',
   'ACTIVE',
   'Катерина',
   'Олегівна',
   'Суяніна'
),(
   110,
   'mail106',
   'STUDENT',
   'ACTIVE',
   'Михайло',
   'Юрійович',
   'Валовенко'
),(
   111,
   'mail107',
   'STUDENT',
   'ACTIVE',
   'Максим',
   'Юрійович',
   'Короткий'
),(
   112,
   'mail108',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Вікторович',
   'Ланін'
),(
   113,
   'mail109',
   'STUDENT',
   'ACTIVE',
   'Віталій',
   'Вадимович',
   'Шматько'
),(
   114,
   'mail110',
   'STUDENT',
   'ACTIVE',
   'Дмитро',
   'Юрійович',
   'Ярош'
),(
    333,
    'Edharezenva.Avuzi@cs.khpi.edu.ua',
     'LECTURER',
#     'STUDENT',
#    'TRAINING_REPRESENTATIVE',
    'ACTIVE',
    'Edhar Ezenva',
    NULL,
    'Avuzi'
),(
    444,
    'mail1',
    'LECTURER',
    'ACTIVE',
    'Дмитро',
    'Едуардович',
    'Двухглавов'
    )
;

INSERT INTO university_employees VALUES
(333, 'lecturer', 'lecturer', 'Professor track 3', 'Professor');

INSERT INTO courses VALUES
(1, 'Ext Java', @course_1_description,  date("2020-09-04 12:12:12"), date("2021-06-04 12:12:12"), 333),
(2, 'Spring', @course_2_description,  date("2020-09-04 12:12:12"), date("2021-06-04 12:12:12"), 333),
(3, 'Безпека програм та даних', @course_3_description,  date("2020-09-04 12:12:12"), date("2021-06-04 12:12:12"), 333);

INSERT INTO students VALUES(20, 1);
INSERT INTO students VALUES(21, 1);
INSERT INTO students VALUES(22, 1);
INSERT INTO students VALUES(23, 1);
INSERT INTO students VALUES(24, 1);

INSERT INTO students VALUES(25, 1);
INSERT INTO students VALUES(26, 1);
INSERT INTO students VALUES(27, 1);
INSERT INTO students VALUES(28, 1);
INSERT INTO students VALUES(29, 1);

INSERT INTO students VALUES(30, 1);
INSERT INTO students VALUES(31, 1);
INSERT INTO students VALUES(32, 1);
INSERT INTO students VALUES(33, 1);
INSERT INTO students VALUES(34, 1);

INSERT INTO students VALUES(35, 1);



INSERT INTO students VALUES(36, 2);
INSERT INTO students VALUES(37, 2);
INSERT INTO students VALUES(38, 2);
INSERT INTO students VALUES(39, 2);
INSERT INTO students VALUES(40, 2);

INSERT INTO students VALUES(41, 2);
INSERT INTO students VALUES(42, 2);
INSERT INTO students VALUES(43, 2);
INSERT INTO students VALUES(44, 2);
INSERT INTO students VALUES(45, 2);

INSERT INTO students VALUES(46, 2);
INSERT INTO students VALUES(47, 2);
INSERT INTO students VALUES(48, 2);



INSERT INTO students VALUES(49, 3);
INSERT INTO students VALUES(50, 3);
INSERT INTO students VALUES(51, 3);
INSERT INTO students VALUES(52, 3);
INSERT INTO students VALUES(53, 3);

INSERT INTO students VALUES(54, 3);
INSERT INTO students VALUES(55, 3);
INSERT INTO students VALUES(56, 3);
INSERT INTO students VALUES(57, 3);
INSERT INTO students VALUES(58, 3);

INSERT INTO students VALUES(59, 3);
INSERT INTO students VALUES(60, 3);
INSERT INTO students VALUES(61, 3);
INSERT INTO students VALUES(62, 3);
INSERT INTO students VALUES(63, 3);

INSERT INTO students VALUES(64, 3);
INSERT INTO students VALUES(65, 3);
INSERT INTO students VALUES(66, 3);
INSERT INTO students VALUES(67, 3);
INSERT INTO students VALUES(68, 3);



INSERT INTO students VALUES(69, 4);
INSERT INTO students VALUES(70, 4);
INSERT INTO students VALUES(71, 4);
INSERT INTO students VALUES(72, 4);
INSERT INTO students VALUES(73, 4);



INSERT INTO students VALUES(74, 5);
INSERT INTO students VALUES(75, 5);
INSERT INTO students VALUES(76, 5);
INSERT INTO students VALUES(77, 5);
INSERT INTO students VALUES(78, 5);

INSERT INTO students VALUES(79, 5);
INSERT INTO students VALUES(80, 5);
INSERT INTO students VALUES(81, 5);
INSERT INTO students VALUES(82, 5);
INSERT INTO students VALUES(83, 5);

INSERT INTO students VALUES(84, 5);
INSERT INTO students VALUES(85, 5);
INSERT INTO students VALUES(86, 5);



INSERT INTO students VALUES(87, 6);
INSERT INTO students VALUES(88, 6);
INSERT INTO students VALUES(89, 6);
INSERT INTO students VALUES(90, 6);
INSERT INTO students VALUES(91, 6);

INSERT INTO students VALUES(92, 6);
INSERT INTO students VALUES(93, 6);
INSERT INTO students VALUES(94, 6);
INSERT INTO students VALUES(95, 6);



INSERT INTO students VALUES(96, 7);
INSERT INTO students VALUES(97, 7);
INSERT INTO students VALUES(98, 7);
INSERT INTO students VALUES(99, 7);
INSERT INTO students VALUES(100, 7);

INSERT INTO students VALUES(101, 7);
INSERT INTO students VALUES(102, 7);



INSERT INTO students VALUES(103, 8);
INSERT INTO students VALUES(104, 8);
INSERT INTO students VALUES(105, 8);
INSERT INTO students VALUES(106, 8);
INSERT INTO students VALUES(107, 8);

INSERT INTO students VALUES(108, 8);
INSERT INTO students VALUES(109, 8);



INSERT INTO students VALUES(110, 9);
INSERT INTO students VALUES(111, 9);
INSERT INTO students VALUES(112, 9);
INSERT INTO students VALUES(113, 9);
INSERT INTO students VALUES(114, 9);
;

INSERT INTO `settings` VALUES
('MIN_STUDENT_ATTENDANCE_FILE_UPLOAD_INTERVAL', 'Встановлює мінімальний проміжок часу в секундах між завантаженням файлу для користувача', '3600');
INSERT INTO `users_settings` VALUES
('MIN_STUDENT_ATTENDANCE_FILE_UPLOAD_INTERVAL', 333, '3600');

INSERT INTO `max_attendances` VALUES
    (1, 1, 100),
    (2, 2, 150),
    (3, 2, 200)
;
