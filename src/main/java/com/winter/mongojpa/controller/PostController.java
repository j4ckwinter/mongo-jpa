package com.winter.mongojpa.controller;

import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.repository.PostRepository;
import com.winter.mongojpa.service.PostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") ObjectId id) {
        return postService.getPostById(id);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Post createPost(@Valid @RequestBody Post post) {
        return postService.createPost(post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public void updatePost(@PathVariable("id") ObjectId id, @Valid @RequestBody Post post) {
        postService.updatePost(id, post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable ObjectId id) {
        postService.deletePost(id);
    }

}
