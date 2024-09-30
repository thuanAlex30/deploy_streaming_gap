package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.AccountSettings;
import com.fpt.StreamGAP.repository.AccountSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountSettingsService {

    @Autowired
    private AccountSettingsRepository accountSettingsRepository;

    public List<AccountSettings> getAllAccountSettings() {
        return accountSettingsRepository.findAll();
    }

    public AccountSettings getAccountSettingsById(Integer id) {
        Optional<AccountSettings> accountSettings = accountSettingsRepository.findById(id);
        if (accountSettings.isPresent()) {
            return accountSettings.get();
        } else {
            throw new RuntimeException("Không tìm thấy cài đặt tài khoản với ID: " + id);
        }
    }
}
