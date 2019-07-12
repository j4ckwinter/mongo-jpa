package com.winter.mongojpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.service.PostService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PostControllerTest {

    @MockBean
    PostService mockPostService; //mock service as we don't need it's implementation details

    @Autowired
    private MockMvc mockMvc; //mock mvc to test controller

    ObjectMapper objectMapper = new ObjectMapper(); //object mapper for serialization

    @Test
    public void itShouldReturnCreatedPost() throws Exception {
        //given
        Post dummyPost = new Post();
        dummyPost.set_id(new ObjectId());
        dummyPost.setTitle("I am the title");

        //when
        when(mockPostService.createPost(any(Post.class))).thenReturn(dummyPost);

        //then
        mockMvc.perform(post("/api/posts/")
                .content(objectMapper.writeValueAsString(dummyPost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(dummyPost.getTitle()));
    }

    @Test
    public void itShouldReturnAllPosts() throws Exception {
        //given
        Post dummyPost = new Post();
        dummyPost.set_id(new ObjectId());
        dummyPost.setTitle("I am title");
        List<Post> allPosts = Arrays.asList(dummyPost);

        //when
        when(mockPostService.getAllPosts()).thenReturn(allPosts);

        //then
        mockMvc.perform(get("/api/posts/")
                .content(objectMapper.writeValueAsString(dummyPost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value(dummyPost.getTitle()));
    }

    @Test
    public void itShouldReturnAnOkStatusAfterAnUpdatedPost() throws Exception {
        //given
        Post dummyPost = new Post();
        ObjectId objectId = new ObjectId();
        dummyPost.set_id(objectId);
        dummyPost.setTitle("I am the title");

        //when
        when(mockPostService.createPost(any(Post.class))).thenReturn(dummyPost);
        dummyPost.setTitle("new title");
        mockPostService.updatePost(objectId, dummyPost);

        //then
        mockMvc.perform(put("/api/posts/" + objectId)
                .content(objectMapper.writeValueAsString(dummyPost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnAnOkStatusAfterADeletedPost() throws Exception {
        //given
        Post dummyPost = new Post();
        ObjectId objectId = new ObjectId();
        dummyPost.set_id(objectId);
        dummyPost.setTitle("I am the title");

        //when
        when(mockPostService.createPost(any(Post.class))).thenReturn(dummyPost);
        dummyPost.setTitle("new title");
        mockPostService.deletePost(objectId);

        //then
        mockMvc.perform(delete("/api/posts/" + objectId)
                .content(objectMapper.writeValueAsString(dummyPost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}
