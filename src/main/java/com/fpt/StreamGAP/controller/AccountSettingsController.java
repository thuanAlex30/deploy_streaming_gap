package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.AccountSettings;
import com.fpt.StreamGAP.service.AccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-settings")
public class AccountSettingsController {

    @Autowired
    private AccountSettingsService accountSettingsService;

    @GetMapping
    public List<AccountSettings> getAllAccountSettings() {
        return accountSettingsService.getAllAccountSettings();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<AccountSettings> getAccountSettingsById(@PathVariable Integer user_id) {
        AccountSettings accountSettings = accountSettingsService.getAccountSettingsById(user_id);
        return ResponseEntity.ok(accountSettings);
    }

    @PostMapping
    public ResponseEntity<AccountSettings> createAccountSettings(@RequestBody AccountSettings accountSettings) {
        AccountSettings newAccountSettings = accountSettingsService.createAccountSettings(accountSettings);
        return ResponseEntity.ok(newAccountSettings);
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<AccountSettings> updateAccountSettings(@PathVariable Integer user_id, @RequestBody AccountSettings accountSettings) {
        AccountSettings updatedAccountSettings = accountSettingsService.updateAccountSettings(user_id, accountSettings);
        return ResponseEntity.ok(updatedAccountSettings);
    }
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteAccountSettings(@PathVariable Integer user_id) {
        accountSettingsService.deleteAccountSettings(user_id);
        return ResponseEntity.noContent().build();
    }
}
