package com.winter.mongojpa.repository;

import com.winter.mongojpa.model.Post;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
