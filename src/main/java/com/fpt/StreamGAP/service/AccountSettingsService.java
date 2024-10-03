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

    public Optional<AccountSettings> getAccountSettingsById(Integer user_id) {
        return accountSettingsRepository.findById(user_id);
    }

    public AccountSettings saveAccountSettings(AccountSettings accountSettings) {
        return accountSettingsRepository.save(accountSettings);
    }

    public void deleteAccountSettings(Integer user_id) {
        accountSettingsRepository.deleteById(user_id);
    }
}
