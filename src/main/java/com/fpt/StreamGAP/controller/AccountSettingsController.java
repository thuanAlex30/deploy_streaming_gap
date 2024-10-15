package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.AccountSettingsDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.AccountSettings;
import com.fpt.StreamGAP.service.AccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account-settings")
public class AccountSettingsController {

    @Autowired
    private AccountSettingsService accountSettingsService;

    @GetMapping
    public ReqRes getAllAccountSettings() {
        List<AccountSettings> accountSettingsList = accountSettingsService.getAllAccountSettings();

        List<AccountSettingsDTO> accountSettingsDTOs = accountSettingsList.stream()
                .map(accountSettings -> {
                    AccountSettingsDTO dto = new AccountSettingsDTO();
                    dto.setAccount_settings_id(accountSettings.getAccount_settings_id());
                    dto.setUser_id(accountSettings.getUser().getUser_id());
                    dto.setPrivacy(accountSettings.getPrivacy());
                    dto.setEmail_notifications(accountSettings.getEmail_notifications());
                    dto.setVolume_level(accountSettings.getVolume_level());
                    dto.setSleep_timer(accountSettings.getSleep_timer());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Account settings retrieved successfully");
        response.setAccountSettingsList(accountSettingsDTOs);
        return response;
    }

    @GetMapping("/{account_settings_id}")
    public ResponseEntity<ReqRes> getAccountSettingsByUserId(@PathVariable Integer account_settings_id) {
        return accountSettingsService.getAccountSettingsByUserId(account_settings_id)
                .map(accountSettings -> {
                    AccountSettingsDTO dto = new AccountSettingsDTO();
                    dto.setAccount_settings_id(accountSettings.getAccount_settings_id());
                    dto.setUser_id(accountSettings.getUser().getUser_id());
                    dto.setPrivacy(accountSettings.getPrivacy());
                    dto.setEmail_notifications(accountSettings.getEmail_notifications());
                    dto.setVolume_level(accountSettings.getVolume_level());
                    dto.setSleep_timer(accountSettings.getSleep_timer());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Account settings retrieved successfully");
                    response.setAccountSettingsList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Account settings not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<ReqRes> createAccountSettings(@RequestBody AccountSettings accountSettings) {
        if (accountSettings.getUser() == null) {
            ReqRes response = new ReqRes();
            response.setStatusCode(400);
            response.setMessage("User cannot be null");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<AccountSettings> existingSettings = accountSettingsService.getAccountSettingsByUserId(accountSettings.getUser().getUser_id());
        if (existingSettings.isPresent()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(409);
            response.setMessage("Account settings already exist for this user");
            return ResponseEntity.status(409).body(response);
        }

        AccountSettings savedAccountSettings = accountSettingsService.createAccountSettings(accountSettings);

        AccountSettingsDTO dto = new AccountSettingsDTO();
        dto.setAccount_settings_id(savedAccountSettings.getAccount_settings_id());
        dto.setUser_id(savedAccountSettings.getUser().getUser_id());
        dto.setPrivacy(savedAccountSettings.getPrivacy());
        dto.setEmail_notifications(savedAccountSettings.getEmail_notifications());
        dto.setVolume_level(savedAccountSettings.getVolume_level());
        dto.setSleep_timer(savedAccountSettings.getSleep_timer());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Account settings created successfully");
        response.setAccountSettingsList(List.of(dto));
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{account_settings_id}")
    public ResponseEntity<ReqRes> updateAccountSettings(@PathVariable Integer account_settings_id, @RequestBody AccountSettings accountSettings) {
        return accountSettingsService.updateAccountSettings(account_settings_id, accountSettings)
                .map(updatedSettings -> {
                    AccountSettingsDTO dto = new AccountSettingsDTO();
                    dto.setAccount_settings_id(updatedSettings.getAccount_settings_id());
                    dto.setUser_id(updatedSettings.getUser().getUser_id());
                    dto.setPrivacy(updatedSettings.getPrivacy());
                    dto.setEmail_notifications(updatedSettings.getEmail_notifications());
                    dto.setVolume_level(updatedSettings.getVolume_level());
                    dto.setSleep_timer(updatedSettings.getSleep_timer());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Account settings updated successfully");
                    response.setAccountSettingsList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Account settings not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @DeleteMapping("/{account_settings_id}")
    public ResponseEntity<ReqRes> deleteAccountSettings(@PathVariable Integer account_settings_id) {
        Optional<AccountSettings> existingSettings = accountSettingsService.getAccountSettingsByUserId(account_settings_id);
        if (existingSettings.isPresent()) {
            accountSettingsService.deleteAccountSettings(account_settings_id);
            ReqRes response = new ReqRes();
            response.setStatusCode(200);
            response.setMessage("Account settings deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Account settings not found");
            return ResponseEntity.status(404).body(response);
        }
    }

}