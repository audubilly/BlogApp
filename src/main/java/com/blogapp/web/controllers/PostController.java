package com.blogapp.web.controllers;


import com.blogapp.data.models.Post;
import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostDoesNotExistEXception;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class PostController {

    @Autowired
    PostService postServiceImpl;

    @GetMapping("")
    public String getIndex(Model model){
        List<Post> postList = postServiceImpl.findPostsInDescendingOrder();
        model.addAttribute("postList",postList);
        return "index";
    }

    @GetMapping("posts/create")
    public String getPostForm(Model model){
        model.addAttribute("error",false);
        return "create";
    }

    @PostMapping("posts/save")
    public String savePost(@ModelAttribute @Valid PostDto postDto, BindingResult result,  Model model){
        log.info("Post Dto received --> {}", postDto);
        if(result.hasErrors())
            return "create";

        try{
            postServiceImpl.savePost(postDto);
        }catch (PostObjectIsNullException e){
            log.info("exception occurred --> {}", e.getMessage());
        }catch (DataIntegrityViolationException dx){
            log.info("Constraint exception occurred --> {}", dx.getMessage());
            model.addAttribute("error",true);
            model.addAttribute("errorMessage","Title not accepted, Already exists!");
            return "create";
        }
        return  "redirect:/posts";
    }

    @ModelAttribute
    public void createPostModel(Model model){
        model.addAttribute("postDto", new PostDto());
    }

    @GetMapping("posts/info/{postId}")
    public  String getPostDetails(@PathVariable("postId") Integer postId, Model model){
        log.info("request for a post path --> {}", postId);

        try{
        Post savedPost = postServiceImpl.findById(postId);
        model.addAttribute("postInfo", savedPost);
        log.info("Post info  --> {}", savedPost);
    }catch (PostDoesNotExistEXception  px){
            log.info("exception occured");
        }
       return "post";
    }
}
