package com.schoolmanagement.poc;

import com.schoolmanagement.poc.enums.Roles;
import com.schoolmanagement.poc.repository.ActivityRepository;
import com.schoolmanagement.poc.repository.GradeRepository;
import com.schoolmanagement.poc.repository.StudentRepository;
import com.schoolmanagement.poc.repository.UserRepository;
import com.schoolmanagement.poc.repository.entities.ActivityEntity;
import com.schoolmanagement.poc.repository.entities.GradeEntity;
import com.schoolmanagement.poc.repository.entities.StudentEntity;
import com.schoolmanagement.poc.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SchoolManagementApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ActivityRepository activityRepository;
    private final GradeRepository gradeRepository;

    static {
        System.setProperty("mail.mime.charset", "utf8");
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }


    public void run(String... args) throws Exception {
        log.info("Reset default users");
        userRepository.deleteAll();
        studentRepository.deleteAll();
        activityRepository.deleteAll();
        gradeRepository.deleteAll();


        log.info("Creating admin user");
        userRepository.save(
                UserEntity.builder()
                        .email("admin@admin.com")
                        .password(new BCryptPasswordEncoder().encode("admin"))
                        .role(Roles.ADMIN)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());
        log.info("Admin user created");


        log.info("Creating manager user");
        userRepository.save(
                UserEntity.builder()
                        .email("manager@manager.com")
                        .password(new BCryptPasswordEncoder().encode("manager"))
                        .role(Roles.MANAGER)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());
        log.info("Manager user created");

        log.info("Creating student 1");

        StudentEntity studentEntity = studentRepository.save(
                StudentEntity.builder()
                        .documentNumber("02223344033")
                        .name("Joao da Silva")
                        .email("joao@gmail.com")
                        .phone("11999999999")
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());

        log.info("Creating student 2");
        StudentEntity studentEntity1 = studentRepository.save(
                StudentEntity.builder()
                        .documentNumber("02223344034")
                        .name("Maria da Silva")
                        .email("maria@gmail.com)")
                        .phone("11999999998")
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());

        log.info("Creating activity 1");
        ActivityEntity activityEntity = activityRepository.save(
                ActivityEntity.builder()
                        .title("Redação primeiro semestre")
                        .description("Redação sobre o primeiro semestre do ano")
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());

        log.info("Creating activity 2");
        ActivityEntity activityEntity1 = activityRepository.save(
                ActivityEntity.builder()
                        .title("Redação segundo semestre")
                        .description("Redação sobre o segundo semestre do ano")
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .active(true)
                        .build());

        log.info("Creating grades");
        gradeRepository.save(
                GradeEntity.builder()
                        .studentId(studentEntity.getId())
                        .activityId(activityEntity.getId())
                        .grade(8.5)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .build());

        gradeRepository.save(
                GradeEntity.builder()
                        .studentId(studentEntity.getId())
                        .activityId(activityEntity1.getId())
                        .grade(9.5)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .build());

        gradeRepository.save(
                GradeEntity.builder()
                        .studentId(studentEntity1.getId())
                        .activityId(activityEntity.getId())
                        .grade(7.5)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .build());

        gradeRepository.save(
                GradeEntity.builder()
                        .studentId(studentEntity1.getId())
                        .activityId(activityEntity1.getId())
                        .grade(6.5)
                        .createdAt(String.valueOf(ZonedDateTime.now()))
                        .updatedAt(String.valueOf(ZonedDateTime.now()))
                        .build());

        log.info("DONE.");
    }

}