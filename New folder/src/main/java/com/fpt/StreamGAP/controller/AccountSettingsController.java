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

    @GetMapping("/{id}")
    public ResponseEntity<AccountSettings> getAccountSettingsById(@PathVariable Integer id) {
        return ResponseEntity.ok(accountSettingsService.getAccountSettingsById(id));
    }

}
