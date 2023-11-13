package com.example.helloworld;

import com.example.helloworld.messages.Message;
import com.example.helloworld.messages.MessageNotFoundException;
import com.example.helloworld.messages.MessageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageService service;

    @GetMapping("/messages")
    public String showMessageList(Model model) {
        List<Message> listMessages = service.listAll();
        model.addAttribute("listMessages", listMessages);

        return "messages";
    }

    @GetMapping("/messages/new")
    public String showNewForm(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("pageTitle", "Add new message");
        return "message_from";
    }

    @PostMapping("/messages/save")
    public String saveMessage(Message message, RedirectAttributes ra) {
        service.save(message);
        ra.addFlashAttribute("msg", "The message has been saved successfully");
        return "redirect:/messages";
    }

    @GetMapping("/messages/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Message message = service.get(id);
            model.addAttribute("message", message);
            model.addAttribute("pageTitle", "Edit message (ID: " + id + ")");
            return "message_from";

        } catch (MessageNotFoundException e) {
            ra.addFlashAttribute("msg", "the message has been saved successfully");
            return "redirect:/messages";
        }
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("msg", "the user ID " + id + "has been deleted.");
        } catch (MessageNotFoundException e) {
            ra.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/messages";
    }
}
