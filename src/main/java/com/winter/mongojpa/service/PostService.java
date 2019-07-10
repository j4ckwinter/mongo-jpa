package com.winter.mongojpa.service;

import com.winter.mongojpa.model.Post;
import com.winter.mongojpa.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(ObjectId id) {
        return postRepository.findBy_id(id);
    }

    public Post createPost(Post post) {
        requireNonNull(post.getPostId());
        requireNonNull(post.getUserId());
        post.set_id(ObjectId.get());
        postRepository.save(post);
        return post;
    }

    public void updatePost(ObjectId id, Post post) {
        post.set_id(id);
        postRepository.save(post);
    }

    public void deletePost(ObjectId id) {
        postRepository.delete(postRepository.findBy_id(id));
    }
}
