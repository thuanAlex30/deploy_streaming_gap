package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.CommentDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<ReqRes> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Comments retrieved successfully.");
        response.setCommentList(comments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ReqRes> createComment(@RequestBody CommentDTO commentDTO) {
        Optional<User> optionalUser = Optional.ofNullable(commentService.getUserById(commentDTO.getUserId()));
        if (optionalUser.isEmpty()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Song> optionalSong = Optional.ofNullable(commentService.getSongById(commentDTO.getSongId()));
        if (optionalSong.isEmpty()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Song not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        CommentDTO createdComment = commentService.createComment(commentDTO, optionalUser.get(), optionalSong.get());

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Comment created successfully.");
        response.setCommentList(List.of(createdComment));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ReqRes> updateComment(@PathVariable Integer commentId, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO);

        ReqRes response = new ReqRes();
        if (updatedComment != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Comment updated successfully.");
            response.setCommentList(List.of(updatedComment));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Comment not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ReqRes> deleteComment(@PathVariable Integer commentId) {
        Optional<CommentDTO> commentOptional = commentService.getCommentById(commentId);

        ReqRes response = new ReqRes();
        if (commentOptional.isPresent()) {
            commentService.deleteComment(commentId);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Comment deleted successfully.");
            response.setCommentList(List.of(commentOptional.get()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Comment not found.");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ReqRes> getCommentById(@PathVariable Integer commentId) {
        Optional<CommentDTO> comment = commentService.getCommentById(commentId);

        ReqRes response = new ReqRes();
        if (comment.isPresent()) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Comment retrieved successfully.");
            response.setCommentList(List.of(comment.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Comment not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

