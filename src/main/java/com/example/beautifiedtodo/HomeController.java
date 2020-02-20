package com.example.beautifiedtodo;

import com.cloudinary.utils.ObjectUtils;
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
    ImageRepository imageRepository;

    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/")
    public String listCourses(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("images", imageRepository.findAll());
        return "list";
    }
    //
    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("course", new Task());
        model.addAttribute("image", new Image());

        // model.addAttribute("course", new Task());
        return "taskform";
    }

    //
    @PostMapping("/process")
    public String processForm(@Valid Task task,
                              BindingResult result, @ModelAttribute Image image,
                              @RequestParam("file")MultipartFile file){
        if (result.hasErrors()){
            return "taskform";
        }
        taskRepository.save(task);
        return "redirect:/";
    }


    @PostMapping("/process")
    public String processForm(@ModelAttribute Image image,
                               @RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            image.setImg(uploadResult.get("url").toString());
            imageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }



    //


    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("course", taskRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               Model model){
        model.addAttribute("course", taskRepository.findById(id).get());
        return "taskform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        taskRepository.deleteById(id);
        return "redirect:/";
    }

    // below









    // above

}
