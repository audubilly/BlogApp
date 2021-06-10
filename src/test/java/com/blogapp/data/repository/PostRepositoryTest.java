package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void savePostToDBTest() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem ipsium is simply dummy test for");

        log.info("created a blog post --> {}", blogPost);
        postRepository.save(blogPost);


        assertThat(blogPost.getId()).isNotNull();


    }

    @Test
    void throwExceptionWhenSavingPostWithoutDuplicateTitle() {
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem ipsium is simply dummy test for printing and typing");
        log.info("created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech");
        blogPost2.setContent("Lorem ipsium is simply dummy text for of the printing and typing");
        log.info("created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(blogPost2));


    }

    @Test
    void whenPostIsSaved_thenSaveAuthor() {

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

        Post savedPost = postRepository.findByPostTitle("What is Fintech?");
        assertThat(savedPost.getTitle()).isEqualTo("What is Fintech?");


    }


    @Test
    void findAllPostInTheDbTest() {
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);


    }

    @Test

    @Rollback(value = false)
    void deletePostTest() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();

        log.info("Post fetched from the database --> {}", savedPost);
        //delete post
        postRepository.deleteById(savedPost.getId());

        Post deletedPost = postRepository.findById(41).orElse(null);
        assertThat(deletedPost).isNull();

    }

    @Test
    void updateSavedPostTitleTest() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("title post 1");

        savedPost.setTitle("Pentax Post Title");
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("Pentax Post Title");

    }

    @Test
    @Rollback(value = false)
    void updatePostAuthorTest() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNull();

        log.info("Saved post object --> {}", savedPost);

        Author author = new Author();
        author.setFirstName("james");
        author.setLastName("Brown");
        author.setPhoneNumber("090123456");
        author.setEmail("jondoe@mail.com");
        author.setProfession("musician");

        savedPost.setAuthor(author);
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getLastName()).isEqualTo("Brown");

        log.info("Updated Post --> {}", updatedPost);
    }

    @Test
    @Rollback(value = false)
    void addCommentToPostTest() {
        //Fetch post from Db
        Post savedPost = postRepository.findById(43).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getComment()).hasSize(0);


        //create a comment object
        Comment comment1 = new Comment("job", "Really insightfull post");
        Comment comment2 = new Comment("Billy", "Nice One");


        //map the post and comments
        savedPost.addComment(comment1, comment2);

        //save comment
        postRepository.save(savedPost);

        Post commentedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(commentedPost).isNotNull();
        assertThat(commentedPost.getComment()).hasSize(2);
        log.info("commented post --> {}", commentedPost);

    }

    @Test
    @Rollback(value = false)
    void findAllPostInDescendingOrderTest(){

        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();
        assertThat(allPosts).isNotNull();
        log.info("All posts --> {}", allPosts);

        assertTrue(allPosts.get(0).getDateCreated().isAfter(allPosts.get(1).getDateCreated()));
//        assertFalse(allPosts.get(1).getDateCreated().isBefore(allPosts.get(0).getDateCreated()));

        allPosts.forEach(post -> {
            log.info("Post Date {}", post.getAuthor() );
        });
    }


    }




