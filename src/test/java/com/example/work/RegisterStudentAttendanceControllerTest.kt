package com.example.work

import com.example.work.service.StudentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

class RegisterStudentAttendanceControllerTest {

    @Test fun shouldWork() {
        assertThat(1 + 2).isEqualTo(4)
    }
}