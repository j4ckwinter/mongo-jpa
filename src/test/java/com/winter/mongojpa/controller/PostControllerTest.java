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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/api/posts/").
                content(objectMapper.writeValueAsString(dummyPost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(dummyPost.getTitle()));
    }
}
