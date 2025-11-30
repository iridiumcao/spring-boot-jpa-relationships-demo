package io.github.iridiumcao.jpademo;

import io.github.iridiumcao.jpademo.entity.Detail;
import io.github.iridiumcao.jpademo.entity.Post;
import io.github.iridiumcao.jpademo.entity.Tag;
import io.github.iridiumcao.jpademo.entity.User;
import io.github.iridiumcao.jpademo.repository.PostRepository;
import io.github.iridiumcao.jpademo.repository.TagRepository;
import io.github.iridiumcao.jpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class DemoRunner implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private TagRepository tagRepository;

    @Override
    @Transactional // 开启事务，保证懒加载 (Lazy Loading) 能够正常获取数据
    public void run(String... args) throws Exception {
        System.out.println("====== 开始 JPA 关联测试 ======");

        // 1. 测试 1-1 (User + Detail)
        // 创建 User
        User user = new User("JohnDoe");

        // 创建 Detail
        Detail detail = new Detail("123 Baker Street");

        // 建立双向关联 (虽然数据库只需要一边有外键，但 Java 对象内存中最好双向设置)
        user.setDetail(detail);
        detail.setUser(user);

        // 保存 User (因为配置了 CascadeType.ALL，Detail 也会被保存)
        userRepository.save(user);
        System.out.println(">>> 用户及详情已保存，User ID: " + user.getId());

        // 2. 测试 1-n (User + Post)
        Post post1 = new Post("Hibernate Tips");
        Post post2 = new Post("Spring Boot Guide");

        // 建立关联
        post1.setUser(user);
        post2.setUser(user);
        user.setPosts(Arrays.asList(post1, post2));

        // 这里可以直接 save post，或者再次 save user (因为 cascade)
        postRepository.saveAll(Arrays.asList(post1, post2));
        System.out.println(">>> 两篇文章已保存");

        // 3. 测试 n-m (Post + Tag)
        Tag tagJava = new Tag("Java");
        Tag tagDb = new Tag("Database");

        // 保存标签
        tagRepository.saveAll(Arrays.asList(tagJava, tagDb));

        // 给 post1 打两个标签
        post1.getTags().add(tagJava);
        post1.getTags().add(tagDb);

        // 给 post2 打一个标签
        post2.getTags().add(tagJava);

        // 更新 Post (会更新中间表 t_post_tag)
        postRepository.save(post1);
        postRepository.save(post2);
        System.out.println(">>> 标签关联已建立");

        printData(user.getId());
    }

    private void printData(Long userId) {
        System.out.println("\n====== 查询验证 ======");
        User foundUser = userRepository.findById(userId).orElseThrow();

        System.out.println("1. [1-1] 用户名: " + foundUser.getUsername());
        System.out.println("   用户地址: " + foundUser.getDetail().getAddress());

        System.out.println("2. [1-n] 该用户发布的文章数量: " + foundUser.getPosts().size());

        foundUser.getPosts().forEach(post -> {
            System.out.println("   - 文章标题: " + post.getTitle());
            // 3. [n-m]
            System.out.print("     [n-m] 包含标签: ");
            post.getTags().forEach(tag -> System.out.print(tag.getName() + " "));
            System.out.println();
        });
    }
}