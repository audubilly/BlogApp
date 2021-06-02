package com.blogapp.service.post;

import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;


    @InjectMocks
    PostServiceImpl postServiceImpl = new PostServiceImpl();
    Post testPost;

    @BeforeEach
    void setUp() {
        testPost= new Post();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenTheSaveMethodIsCalled_thenRepositoryIsCalledOnceTest() throws PostObjectIsNullException {
        when(postServiceImpl.savePost(new PostDto())).thenReturn(testPost);
        postServiceImpl.savePost(new PostDto());

        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    void whenTheFindAllMethodIsCalledThenReturnListsOfPosts(){
        List<Post> postList = new ArrayList<>();
        when(postServiceImpl.findAllPosts()).thenReturn(postList);
        postServiceImpl.findAllPosts();

        verify(postRepository,times(1)).findAll();
    }
}