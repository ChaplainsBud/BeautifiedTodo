package com.example.beautifiedtodo;

import com.cloudinary.utils.ObjectUtils;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listCourses(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        System.out.println("I am here!");
        return "list";
    }


    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("course", new Task());

        // model.addAttribute("course", new Task());
        return "taskform";
    }

    // below
    @PostMapping("/process")
    public String processForm(@ModelAttribute @Valid Task task, BindingResult result,
                              @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "taskform";
        }
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            task.setImg(uploadResult.get("url").toString());
            taskRepository.save(task);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
            return "redirect:/";
        }

        @RequestMapping("/detail/{id}")
        public String showCourse ( @PathVariable("id") long id, Model model)
        {
            model.addAttribute("course", taskRepository.findById(id).get());
            return "show";
        }

        @RequestMapping("/update/{id}")
        public String updateCourse ( @PathVariable("id") long id,
        Model model){
            model.addAttribute("course", taskRepository.findById(id).get());
            return "taskform";
        }

        @RequestMapping("/delete/{id}")
        public String delCourse ( @PathVariable("id") long id){
            taskRepository.deleteById(id);
            return "redirect:/";
        }
    }
