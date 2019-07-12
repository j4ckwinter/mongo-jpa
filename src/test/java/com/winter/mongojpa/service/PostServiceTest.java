package com.winter.mongojpa.service;

import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.repository.PostRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    PostRepository mockPostRepository;

    @InjectMocks
    PostService postService;

    @Test
    public void whenSavingPostICanReturnPost() {
        //given
        Post dummyPost = new Post();
        ObjectId objectId = new ObjectId();
        dummyPost.set_id(objectId);
        dummyPost.setTitle("Title");
        dummyPost.setBody("Delete me!!");
        dummyPost.setUserId(922L);
        dummyPost.setPostId(911L);

        //when
        when(mockPostRepository.findBy_id(objectId)).thenReturn(dummyPost);

        Post foundPost = postService.getPostById(objectId);

        assertThat(foundPost.getTitle()).isEqualTo(dummyPost.getTitle());
    }

    @Test
    public void whenEditingAPostIStayEdited() {
        //given
        Post dummyPost = new Post();
        ObjectId objectId = new ObjectId();
        String body = "Like a postman I always deliver";
        dummyPost.set_id(objectId);
        dummyPost.setTitle("I'm a postman");
        dummyPost.setBody(body);
        dummyPost.setUserId(999L);
        dummyPost.setPostId(989L);

        //when
        when(mockPostRepository.findBy_id(objectId)).thenReturn(dummyPost);
        Post foundPost = postService.getPostById(objectId);
        foundPost.setBody("I forgotted to deliver");

        when(mockPostRepository.save(foundPost)).thenReturn(null);
        postService.updatePost(objectId, foundPost);

        when(mockPostRepository.findBy_id(objectId)).thenReturn(foundPost);
        Post updatedPost = postService.getPostById(objectId);

        //then
        assertThat(updatedPost.getTitle()).isEqualTo(dummyPost.getTitle());
        assertThat(updatedPost.getBody()).isNotEqualTo(dummyPost.getTitle());
    }

    @Test(expected = NullPointerException.class)
    public void whenDeletingAPostItIsDeleted() {
        //given
        Post dummyPost = new Post();
        ObjectId objectId = new ObjectId();
        String body = "Like a postman I always deliver";
        dummyPost.set_id(objectId);
        dummyPost.setTitle("I'm a postman");
        dummyPost.setBody(body);
        dummyPost.setUserId(999L);
        dummyPost.setPostId(989L);

        //when
        when(mockPostRepository.findBy_id(objectId)).thenReturn(null);

        //then
        assertThat(postService.getPostById(objectId).getBody()).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenSavingPostWithoutPostIdAndUserIdShouldThrowException() {
        Post post = new Post();
        post.setTitle("I shouldn't");
        post.setBody("WORK");

        postService.createPost(post);
    }
}
