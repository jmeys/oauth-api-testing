package io.meys.oauthtesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OauthTestingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("unit")
class OauthTestingApplicationTests {

    @Autowired
    private MockMvc mvc;

    // No user
    @Test
    public void test() throws Exception {
        mvc.perform(get("/sec"))
           .andExpect(status().is(401));
    }

    // Mock User
    @Test
    @WithMockUser
    public void test1() throws Exception {
        mvc.perform(get("/sec"))
           .andExpect(status().is(200))
           .andExpect(content().string("Sec"));
    }

    // Provide Mock User
    @Test
    public void test2() throws Exception {
        mvc.perform(get("/sec")
                .with(user("yannick")))
           .andExpect(status().is(200))
           .andExpect(content().string("Sec"));
    }

    // Mock user but no roles when required
    @Test
    @WithMockUser
    public void test3() throws Exception {
        mvc.perform(get("/verysec"))
           .andExpect(status().is(403));
    }

    // Provide Mock User with required role
    @Test
    public void test4() throws Exception {
        mvc.perform(get("/verysec")
                .with(user("yannick").roles("ADMIN")))
           .andExpect(status().is(200))
           .andExpect(content().string("Very sec"));
    }

    // Provide Mock User with role when not required
    @Test
    public void test5() throws Exception {
        mvc.perform(get("/open")
                .with(user("yannick").roles("ADMIN")))
           .andExpect(status().is(200))
           .andExpect(content().string("Open"));
    }
}
