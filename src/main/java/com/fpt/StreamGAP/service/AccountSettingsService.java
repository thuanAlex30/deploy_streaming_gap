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

    public AccountSettings getAccountSettingsById(Integer user_id) {
        return accountSettingsRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("AccountSettings not found for id: " + user_id));
    }

    public AccountSettings createAccountSettings(AccountSettings accountSettings) {
        return accountSettingsRepository.save(accountSettings);
    }

    public AccountSettings updateAccountSettings(Integer user_id, AccountSettings accountSettings) {
        AccountSettings existingAccountSettings = getAccountSettingsById(user_id);
        existingAccountSettings.setPrivacy(accountSettings.getPrivacy());
        existingAccountSettings.setEmail_notifications(accountSettings.getEmail_notifications());
        existingAccountSettings.setVolume_level(accountSettings.getVolume_level());
        existingAccountSettings.setSleep_timer(accountSettings.getSleep_timer());
        return accountSettingsRepository.save(existingAccountSettings);
    }
    public void deleteAccountSettings(Integer user_id) {
        accountSettingsRepository.deleteById(user_id);
    }
}
