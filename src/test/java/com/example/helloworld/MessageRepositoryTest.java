package com.example.helloworld;

import com.example.helloworld.messages.Message;
import com.example.helloworld.messages.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MessageRepositoryTest {
    @Autowired private MessageRepository repo;

    @Test
    public void testAddNew(){
        Message message = new Message();
        message.setText("Hello         World");

        Message savedMessage = repo.save(message);

        Assertions.assertNotNull(savedMessage);
        Assertions.assertTrue(savedMessage.getId() > 0);
    }

    @Test
    public void testListAll(){
        Iterable<Message> messages = repo.findAll();

        org.assertj.core.api.Assertions.assertThat(messages).hasSizeGreaterThan(0);

        for(Message message : messages){
            System.out.println(message);
        }
    }

    @Test
    public void testUpdate(){
        Integer messageId = 1;
        Optional<Message> optionalMessage = repo.findById(messageId);
        Message message = optionalMessage.get();
        message.setText("bye bye ");
        repo.save(message);

        Message updatedMessage = repo.findById(messageId).get();
        org.assertj.core.api.Assertions.assertThat(updatedMessage.getText()).isEqualTo("bye bye ");
    }

    @Test
    public void testGet(){
        Integer messageId = 2;
        Optional<Message> optionalMessage = repo.findById(messageId);
        Message message = optionalMessage.get();

        org.assertj.core.api.Assertions.assertThat(optionalMessage).isPresent();

        System.out.println(optionalMessage.get());
    }
    @Test
    public void testDelete(){
        Integer messageId = 2;
        repo.deleteById(messageId);

        Optional<Message> optionalMessage = repo.findById(messageId);
        org.assertj.core.api.Assertions.assertThat(optionalMessage).isNotPresent();

    }
}
