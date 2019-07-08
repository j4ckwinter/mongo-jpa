package com.winter.mongojpa.controller;

import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.repository.PostRepository;
import javafx.geometry.Pos;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @RequestMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") ObjectId id) {
        return postRepository.findBy_id(id);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Post createPost(@Valid @RequestBody Post post) {
        post.set_id(ObjectId.get());
        postRepository.save(post);
        return post;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public void updatePost(@PathVariable("id") ObjectId id, @Valid @RequestBody Post post) {
        post.set_id(id);
        postRepository.save(post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable ObjectId id) {
        postRepository.delete(postRepository.findBy_id(id));
    }

}
