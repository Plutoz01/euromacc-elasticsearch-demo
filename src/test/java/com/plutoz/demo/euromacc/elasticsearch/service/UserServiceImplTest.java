package com.plutoz.demo.euromacc.elasticsearch.service;

import com.plutoz.demo.euromacc.elasticsearch.domain.User;
import com.plutoz.demo.euromacc.elasticsearch.helper.UserTestHelper;
import com.plutoz.demo.euromacc.elasticsearch.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private UserServiceImpl service;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockRepository);
    }

    @Nested
    class Create {
        User newUser;
        User persistedUser;

        @BeforeEach
        void setUp() {
            var builder = UserTestHelper.getTestUser();
            newUser = builder
                    .id(null)
                    .firstName("NEW")
                    .build();
            persistedUser = builder.firstName("PERSISTED").build();
        }

        @Test
        void shouldPersistPassedUserAndReturnSavedEntity_whenSaveWasSuccess() {
            doReturn(persistedUser).when(mockRepository).save(newUser);

            final var returned = service.create(newUser);

            assertEquals(persistedUser, returned);
            verify(mockRepository, times(1)).save(newUser);
        }

        @Test
        void shouldGenerateRandomIdBeforeSave() {
            assertNull(newUser.getId());
            final ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

            service.create(newUser);

            verify(mockRepository, times(1)).save(argumentCaptor.capture());
            assertNotNull(argumentCaptor.getValue().getId());
        }
    }

    @Nested
    class FindByName {
        @Test
        void shouldReturnAllUsersProvidedByRepository() {
            final List<User> testUsers = List.of(
                    UserTestHelper.getTestUser().id(UUID.randomUUID()).build(),
                    UserTestHelper.getTestUser().id(UUID.randomUUID()).build()
            );
            String firstNameSearchWord = "first";
            String lastNameSearchWord = "last";
            doReturn(testUsers).when(mockRepository).findByFirstNameLikeAndLastNameLike(firstNameSearchWord, lastNameSearchWord);

            final var actual = service.findByName(firstNameSearchWord, lastNameSearchWord);

            assertEquals(testUsers, actual);
            verify(mockRepository, times(1)).findByFirstNameLikeAndLastNameLike(firstNameSearchWord, lastNameSearchWord);
        }
    }
}