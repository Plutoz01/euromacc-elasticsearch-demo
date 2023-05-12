package com.plutoz.demo.euromacc.elasticsearch.controller;

import com.plutoz.demo.euromacc.elasticsearch.converter.UserConverter;
import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.helper.UserTestHelper;
import com.plutoz.demo.euromacc.elasticsearch.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private UserConverter mockUserConverter;

    User testUser1;
    User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = UserTestHelper.getTestUser()
                .firstName("user-1")
                .build();
        testUser2 = UserTestHelper.getTestUser()
                .firstName("user-2")
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockUserService);
    }

    @Nested
    class CreateUser {
        @Test
        void shouldCreateAndReturnNewUser_whenUserCreationWasSuccess() throws Exception {
            mockRequestDtoToEntityConversion(testUser1);
            mockEntityToDtoConversion(testUser2);
            doReturn(testUser2).when(mockUserService).create(testUser1);

            invokeEndpoint(createRequestBody(testUser1))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(testUser2.getId().toString())))
                    .andExpect(jsonPath("$.firstName", is(testUser2.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(testUser2.getLastName())))
                    .andExpect(jsonPath("$.email", is(testUser2.getEmail())));
            verify(mockUserService, times(1)).create(testUser1);
        }

        @Test
        void shouldReturn400BadRequest_whenRequestBodyIsInvalid() throws Exception {
            invokeEndpoint("{ \"firstName\": \"John\" }")
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors", Matchers.contains("lastName: 'must not be blank'")));
        }

        private ResultActions invokeEndpoint(String postBody) throws Exception {
            return mockMvc.perform(post("/elasticsearch/create")
                    .content(postBody)
                    .contentType(MediaType.APPLICATION_JSON));
        }

        private String createRequestBody(User r) {
            return """
                    {
                        "firstName": "%s",
                        "lastName": "%s",
                        "email": "%s"
                    }
                    """.formatted(r.getFirstName(), r.getLastName(), r.getEmail());
        }

        private void mockRequestDtoToEntityConversion(User expected) {
            doReturn(expected).when(mockUserConverter).toModel(any());
        }
    }

    @Nested
    class SearchUser {
        private final String firstNameSearchWord = "first";
        private final String lastNameSearchWord = "last";

        @Test
        public void shouldReturnEmptyArray_whenNoUserMatchWithRequestedCriteria()  throws Exception {
            mockSearchUser(Collections.emptyList());

            invokeEndpoint(createRequestBody(firstNameSearchWord, lastNameSearchWord))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userList").isArray())
                    .andExpect(jsonPath("$.userList", hasSize(0)))
                    .andExpect(jsonPath("$.total", is(0)));
            verify(mockUserService, times(1)).findByName(firstNameSearchWord, lastNameSearchWord);
        }

        @Test
        public void shouldReturnExpectedResponse_whenUsersMatchWithRequestedCriteria()  throws Exception {
            mockSearchUser(List.of(testUser1, testUser2));
            mockEntityToDtoConversion(testUser1);
            mockEntityToDtoConversion(testUser2);

            invokeEndpoint(createRequestBody(firstNameSearchWord, lastNameSearchWord))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userList").isArray())
                    .andExpect(jsonPath("$.userList", hasSize(2)))
                    .andExpect(jsonPath("$.userList.[0].id", is(testUser1.getId().toString())))
                    .andExpect(jsonPath("$.userList.[1].id", is(testUser2.getId().toString())))
                    .andExpect(jsonPath("$.total", is(2)));
            verify(mockUserService, times(1)).findByName(firstNameSearchWord, lastNameSearchWord);
        }

        @Test
        public void shouldReturn400BadRequest_whenRequestBodyIsInvalid() throws Exception {
            invokeEndpoint("{}")
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors", Matchers.contains("lastName: 'must not be blank'", "firstName: 'must not be blank'")));
        }

        private ResultActions invokeEndpoint(String postBody) throws Exception {
            return mockMvc.perform(post("/elasticsearch/search")
                    .content(postBody)
                    .contentType(MediaType.APPLICATION_JSON));
        }

        private String createRequestBody(String firstName, String lastName) {
            return """
                    {
                        "firstName": "%s",
                        "lastName": "%s"
                    }
                    """.formatted(firstName, lastName);
        }

        private void mockSearchUser(List<User> returned) {
            doReturn(returned).when(mockUserService).findByName(firstNameSearchWord, lastNameSearchWord);
        }
    }

    private void mockEntityToDtoConversion(User user) {
        doReturn(UserTestHelper.getTestUserResponse(user).build())
                .when(mockUserConverter).toResponse(user);
    }
}