package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.CommentDTO;
import com.fpt.StreamGAP.entity.Comment;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.CommentRepository;
import com.fpt.StreamGAP.repository.SongRepository;
import com.fpt.StreamGAP.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CommentRepository commentRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Song getSongById(Integer songId) {
        return songRepository.findById(songId).orElse(null);
    }

    public CommentDTO createComment(CommentDTO commentDTO, User user, Song song) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setSong(song);
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(new Date(System.currentTimeMillis()));
        comment = commentRepository.save(comment);
        return convertToDTO(comment);
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        System.out.println("Retrieved comments: " + comments);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public  CommentDTO updateComment(Integer commentId, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentDTO.getContent());
            comment.setCreatedAt(new Date(System.currentTimeMillis()));

            comment = commentRepository.save(comment);
            return convertToDTO(comment);
        }
        return null;
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getComment_id());
        dto.setUserId(comment.getUser().getUser_id());
        dto.setSongId(comment.getSong().getSongId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    public Optional<CommentDTO> getCommentById(Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.map(this::convertToDTO);
    }
}

