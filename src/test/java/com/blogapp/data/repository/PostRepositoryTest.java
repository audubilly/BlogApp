package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void savePostToDBTest(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem ipsium is simply dummy test for");

        log.info("created a blog post --> {}", blogPost);
        postRepository.save(blogPost);


        assertThat(blogPost.getId()).isNotNull();


    }
    @Test
    void throwExceptionWhenSavingPostWithoutDuplicateTitle(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem ipsium is simply dummy test for");
        log.info("created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech");
        blogPost2.setContent("Lorem ipsium is simply dummy text for of the printing and typing");
        log.info("created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));



    }

    @Test
    void whenPostIsSaved_thenSaveAuthor(){

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem ipsium is simply dummy test for");

        log.info("created a blog post --> {}", blogPost);


    Author author = new Author();
    author.setFirstName("john");
    author.setLastName("Wick");
    author.setEmail("john@mail.com");
    author.setPhoneNumber("09056790444");

        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post after saving --> {}", blogPost);

    }


    @Test
    void findAllPostInTheDbTest(){
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }



}