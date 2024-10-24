package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.PartyMode;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.PartyModeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Date;
@Service
public class PartyModeService {

    @Autowired
    private PartyModeRepository partyModeRepository;

    @Autowired
    private UserManagementService userManagementService;

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
    private boolean isALL() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            return authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase("USER")||grantedAuthority.getAuthority().equalsIgnoreCase("ADMIN"));
        }
        return false;
    }
    private boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            return authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        }
        return false;
    }

    public List<PartyMode> getAllPartyModesForCurrentUser() {
        try {
            if (isALL()) {
                return partyModeRepository.findAll();
            } else {
                String currentUsername = getCurrentUsername();
                return partyModeRepository.findByHostUsername(currentUsername);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving party modes: " + e.getMessage());
        }
    }

    public Optional<PartyMode> getPartyModeByIdForCurrentUser(Integer id) {
        try {
            if (isALL()) {
                return partyModeRepository.findById(id);
            } else {
                String currentUsername = getCurrentUsername();
                return partyModeRepository.findByPartyIdAndHostUsername(id, currentUsername);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving party mode: " + e.getMessage());
        }
    }

    @Transactional
    public PartyMode updatePartyMode(PartyMode partyMode) {
        try {
            if (isAdmin() || partyMode.getHost().getUsername().equals(getCurrentUsername())) {
                return partyModeRepository.save(partyMode);
            } else {
                throw new RuntimeException("You are not allowed to update this party mode");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating party mode: " + e.getMessage());
        }
    }

    @Transactional
    public PartyMode savePartyMode(PartyMode partyMode) {
        try {
            User currentUser = userManagementService.getUserByUsername(getCurrentUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            partyMode.setHost(currentUser);
            partyMode.setCreated_at(new Date());
            return partyModeRepository.save(partyMode);
        } catch (Exception e) {
            throw new RuntimeException("Error saving party mode: " + e.getMessage());
        }
    }

    public void deletePartyModeForCurrentUser(Integer id) {
        try {
            String currentUsername = getCurrentUsername();
            if (isAdmin()) {
                partyModeRepository.deleteById(id);
            } else {
                Optional<PartyMode> partyMode = partyModeRepository.findByPartyIdAndHostUsername(id, currentUsername);
                if (partyMode.isPresent()) {
                    partyModeRepository.delete(partyMode.get());
                } else {
                    throw new RuntimeException("Bạn không có quyền xóa chế độ party này");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting party mode: " + e.getMessage());
        }
    }

}
