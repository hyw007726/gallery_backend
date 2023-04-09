package cn.batenglish.gallery.controller;

import cn.batenglish.gallery.entity.Comment;
import cn.batenglish.gallery.entity.Image;
import cn.batenglish.gallery.entity.User;
import cn.batenglish.gallery.repository.CommentRepository;
import cn.batenglish.gallery.repository.ImageRepository;
import cn.batenglish.gallery.repository.UserRepository;
import cn.batenglish.gallery.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CommentController {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    @Autowired
    public CommentController(ImageRepository imageRepository,UserService userService,CommentRepository commentRepository, UserRepository userRepository) {
        this.userService = userService;
        this.commentRepository=commentRepository;
        this.userRepository = userRepository;
        this.imageRepository=imageRepository;
    }
    @GetMapping("/getComments")
    @ResponseBody
    public List<Comment> getComments (@RequestParam("image") String imageId){
        List<Comment> comments = commentRepository.findAllByTargetImageIdEquals(Integer.parseInt(imageId));
        return comments;
    }
    @GetMapping("/postComment")
    @ResponseBody
    public Comment postComment(@RequestParam("isLike") String isLike, @RequestParam("image") String imageId,@RequestParam("commenter") String user_id, @RequestParam("content")String content){
//        System.out.println(isLike);
//        System.out.println(imageId);
//        System.out.println(user_id);
//        System.out.println(content);
        Comment newComment= new Comment();
        Optional<User> user = userRepository.findById(Integer.parseInt(user_id));
        Optional<Image> image = imageRepository.findById(Integer.parseInt(imageId));
        if(user.isPresent()){
           User sender=user.get();
            newComment.setSender(sender);
            newComment.setContent(content);
            newComment.setIsLike(Boolean.parseBoolean(isLike));
            if(image.isPresent()){
                newComment.setTargetImage(image.get());
            }

            commentRepository.save(newComment);
            return newComment;
        }else{
            return null;
        }

    }
    @DeleteMapping("/deleteComment")
    @ResponseBody
    public String deleteComment(@NotBlank @RequestHeader("Authorization") String user_id, @RequestParam("id") String comment_id){

        commentRepository.deleteById(Integer.parseInt(comment_id));
        return "Done";
    }
    @PutMapping("/updateComment")
    @ResponseBody
    public Comment updateComment(@NotBlank @RequestHeader("Authorization") String user_id, @RequestParam("id") String comment_id,@RequestParam("content") String content){
        Optional<Comment> optionalComment = commentRepository.findById(Integer.parseInt(comment_id));
        if (optionalComment.isPresent()) {
            Comment currentComment = optionalComment.get();
            currentComment.setContent(content);
            commentRepository.save(currentComment);
            return currentComment;
        }else{
            return null;
        }
    }
}
