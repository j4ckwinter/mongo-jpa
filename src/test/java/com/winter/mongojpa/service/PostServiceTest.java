package com.winter.mongojpa.service;

import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        dummyPost.setTitle("When cruises go wrong");
        dummyPost.setUserId(999L);
        dummyPost.setPostId(989L);

        //when
        when(mockPostRepository.save(any(Post.class))).thenReturn(dummyPost);
        Post createdPost = postService.createPost(dummyPost);

        //then
        assertThat(createdPost.getTitle()).isSameAs(dummyPost.getTitle());
    }

    @Test(expected = NullPointerException.class)
    public void whenSavingPostWithoutPostIdAndUserIdShouldThrowException() {
        Post post = new Post();
        post.setTitle("I shouldn't");
        post.setBody("WORK");

        postService.createPost(post);
    }
}
