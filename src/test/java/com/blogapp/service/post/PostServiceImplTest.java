package com.blogapp.service.post;

import com.blogapp.data.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postServiceImpl = new PostServiceImpl();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

}