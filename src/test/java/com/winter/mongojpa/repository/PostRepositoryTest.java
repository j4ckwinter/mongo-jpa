package com.winter.mongojpa.repository;

import com.winter.mongojpa.model.Post;
import org.apache.maven.surefire.shade.org.apache.commons.lang.RandomStringUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void createAPostThenGoAndGetIt() {
        //given
        Post post = new Post();
        ObjectId objectId = new ObjectId();
        post.set_id(objectId);
        post.setPostId(33L);
        post.setUserId(44L);
        post.setTitle("Extraction");
        post.setBody("It is what it is");
        postRepository.save(post);

        //when
        Post foundPost = postRepository.findBy_id(objectId);

        //then
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void createANumberOfPosts() {
        //given
        int inserts = 15;

        //when
        for (int i = 0; i < inserts; i++) {
            double id = Math.random() * 1000;
            String generatedString = RandomStringUtils.randomAlphabetic(10);
            Post post = new Post();
            post.setPostId((long) id);
            post.setUserId((long) id);
            post.setTitle(generatedString);
            post.setBody(generatedString);
            postRepository.save(post);
        }

        //then
        List<Post> allPosts = postRepository.findAll();
        assertThat(allPosts.size()).isGreaterThan(15);
    }

    @Test
    public void editAPostAndPersistIt() {
        //given
        Post post = new Post();
        ObjectId objectId = new ObjectId();
        post.set_id(objectId);
        post.setPostId(999L);
        post.setUserId(888L);
        post.setTitle("I would love to be edited");
        post.setBody("Please edit me Jack");
        postRepository.save(post);

        //when
        Post foundPost = postRepository.findBy_id(objectId);
        foundPost.setBody("Thanks for editing me jack you legend");
        postRepository.save(foundPost);

        //then
        Post editedPost = postRepository.findBy_id(objectId);
        assertThat(post.getTitle()).isEqualTo(editedPost.getTitle());
        assertThat(post.getPostId()).isEqualTo(editedPost.getPostId());
        assertThat(post.getBody()).isNotEqualTo(editedPost.getBody());
    }

    @Test
    public void canNotGetADeletedPost() {
        //given
        Post post = new Post();
        ObjectId objectId = new ObjectId();
        post.set_id(objectId);
        post.setPostId(999L);
        post.setUserId(888L);
        post.setTitle("I would love to be deleted");
        post.setBody("Please delete me Jack");
        postRepository.save(post);

        //when
        postRepository.delete(postRepository.findBy_id(objectId));

        //then
        assertThat(postRepository.findBy_id(objectId)).isNull();
    }
}
