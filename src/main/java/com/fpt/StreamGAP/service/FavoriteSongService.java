package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.FavoriteSongRepository;
import com.fpt.StreamGAP.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteSongService {

    @Autowired
    private FavoriteSongRepository favoriteSongRepository;
    @Autowired
    private UserRepo userRe;

    public List<FavoriteSong> getAllFavoriteSongsForCurrentUser() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();  // This will give you the username of the current user

        // Assuming the `User` is mapped in the `FavoriteSong` entity and you have a method to find by user
        User currentUser = userRe.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Query to get the favorite songs for the current user
        return favoriteSongRepository.findByUser(currentUser);
    }


    public Optional<FavoriteSong> getFavoriteSongById(FavoriteSongId id) {
        return favoriteSongRepository.findById(id);
    }

    public FavoriteSong saveFavoriteSong(FavoriteSong favoriteSong) {
        return favoriteSongRepository.save(favoriteSong);
    }



    public void deleteFavoriteSong(int userId, int songId) {
        FavoriteSongId favoriteSongId = new FavoriteSongId(userId, songId);

        // Check if the favorite song exists before deleting
        if (favoriteSongRepository.existsById(favoriteSongId)) {
            favoriteSongRepository.deleteById(favoriteSongId);
        } else {
            throw new RuntimeException("Favorite song not found for userId: " + userId + " and songId: " + songId);
        }
    }
}
