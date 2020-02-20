//package com.example.beautifiedtodo;
//
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Controller
//public class HomeController2 {
//    @Autowired
//    ImageRepository imageRepository;
//
//    @Autowired
//    CloudinaryConfig cloudc;
//
//    @RequestMapping("/")
//    public String listImages(Model model){
//        model.addAttribute("images", imageRepository.findAll());
//        return "list";
//    }
//    @GetMapping("/add")
//    public String newImage(Model model){
//        model.addAttribute("image", new Image());
//        return "taskform"; // same html?
//    }
//    @PostMapping("/process")
//    public String processActor(@ModelAttribute Image image,
//                               @RequestParam("file")MultipartFile file){
//        if (file.isEmpty()){
//            return "redirect:/add";
//        }
//        try {
//            Map uploadResult = cloudc.upload(file.getBytes(),
//            ObjectUtils.asMap("resourcetype", "auto"));
//            image.setImg(uploadResult.get("url").toString());
//            imageRepository.save(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "redirect:/add";
//        }
//            return "redirect:/";
//    }
//}
