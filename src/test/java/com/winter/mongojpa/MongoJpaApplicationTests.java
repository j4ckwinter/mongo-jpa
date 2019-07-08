package com.winter.mongojpa;

import com.winter.mongojpa.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoJpaApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void contextLoads() {
    }

}
