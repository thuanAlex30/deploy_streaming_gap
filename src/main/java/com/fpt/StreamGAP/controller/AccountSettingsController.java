//package com.fpt.StreamGAP.controller;
//
//import com.fpt.StreamGAP.dto.AccountSettingsDTO;
//import com.fpt.StreamGAP.dto.ReqRes;
//import com.fpt.StreamGAP.entity.AccountSettings;
//import com.fpt.StreamGAP.service.AccountSettingsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/account-settings")
//public class AccountSettingsController {
//
//    @Autowired
//    private AccountSettingsService accountSettingsService;
//
//    @GetMapping
//    public ReqRes getAllAccountSettings() {
//        List<AccountSettings> accountSettingsList = accountSettingsService.getAllAccountSettings();
//
//        List<AccountSettingsDTO> accountSettingsDTOs = accountSettingsList.stream()
//                .map(accountSettings -> {
//                    AccountSettingsDTO dto = new AccountSettingsDTO();
//                    dto.setUser_id(accountSettings.getUser_id());
//                    dto.setPrivacy(accountSettings.getPrivacy());
//                    dto.setEmail_notifications(accountSettings.getEmail_notifications());
//                    dto.setVolume_level(accountSettings.getVolume_level());
//                    dto.setSleep_timer(accountSettings.getSleep_timer());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//
//        ReqRes response = new ReqRes();
//        response.setStatusCode(200);
//        response.setMessage("Account settings retrieved successfully");
//        response.setAccountSettingsList(accountSettingsDTOs);
//        return response;
//    }
//
//    @GetMapping("/{user_id}")
//    public ResponseEntity<ReqRes> getAccountSettingsById(@PathVariable Integer user_id) {
//        return accountSettingsService.getAccountSettingsById(user_id)
//                .map(accountSettings -> {
//                    AccountSettingsDTO dto = new AccountSettingsDTO();
//                    dto.setUser_id(accountSettings.getUser_id());
//                    dto.setPrivacy(accountSettings.getPrivacy());
//                    dto.setEmail_notifications(accountSettings.getEmail_notifications());
//                    dto.setVolume_level(accountSettings.getVolume_level());
//                    dto.setSleep_timer(accountSettings.getSleep_timer());
//
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(200);
//                    response.setMessage("Account settings retrieved successfully");
//                    response.setAccountSettingsList(List.of(dto));
//                    return ResponseEntity.ok(response);
//                })
//                .orElseGet(() -> {
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(404);
//                    response.setMessage("Account settings not found");
//                    return ResponseEntity.status(404).body(response);
//                });
//    }
//
//    @PostMapping
//    public ResponseEntity<ReqRes> createAccountSettings(@RequestBody AccountSettings accountSettings) {
//        if (accountSettings.getUser() == null) {
//            ReqRes response = new ReqRes();
//            response.setStatusCode(400);
//            response.setMessage("User cannot be null");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        AccountSettings savedAccountSettings = accountSettingsService.saveAccountSettings(accountSettings);
//
//        AccountSettingsDTO dto = new AccountSettingsDTO();
//        dto.setUser_id(savedAccountSettings.getUser_id());
//        dto.setPrivacy(savedAccountSettings.getPrivacy());
//        dto.setEmail_notifications(savedAccountSettings.getEmail_notifications());
//        dto.setVolume_level(savedAccountSettings.getVolume_level());
//        dto.setSleep_timer(savedAccountSettings.getSleep_timer());
//
//        ReqRes response = new ReqRes();
//        response.setStatusCode(201);
//        response.setMessage("Account settings created successfully");
//        response.setAccountSettingsList(List.of(dto));
//        return ResponseEntity.status(201).body(response);
//    }
//
//    @PutMapping("/{user_id}")
//    public ResponseEntity<ReqRes> updateAccountSettings(@PathVariable Integer user_id, @RequestBody AccountSettings accountSettings) {
//        return accountSettingsService.getAccountSettingsById(user_id)
//                .map(existingAccountSettings -> {
//                    accountSettings.setUser_id(user_id);
//                    AccountSettings updatedAccountSettings = accountSettingsService.saveAccountSettings(accountSettings);
//
//                    AccountSettingsDTO dto = new AccountSettingsDTO();
//                    dto.setUser_id(updatedAccountSettings.getUser_id());
//                    dto.setPrivacy(updatedAccountSettings.getPrivacy());
//                    dto.setEmail_notifications(updatedAccountSettings.getEmail_notifications());
//                    dto.setVolume_level(updatedAccountSettings.getVolume_level());
//                    dto.setSleep_timer(updatedAccountSettings.getSleep_timer());
//
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(200);
//                    response.setMessage("Account settings updated successfully");
//                    response.setAccountSettingsList(List.of(dto));
//                    return ResponseEntity.ok(response);
//                })
//                .orElseGet(() -> {
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(404);
//                    response.setMessage("Account settings not found");
//                    return ResponseEntity.status(404).body(response);
//                });
//    }
//
//    @DeleteMapping("/{user_id}")
//    public ResponseEntity<ReqRes> deleteAccountSettings(@PathVariable Integer user_id) {
//        if (accountSettingsService.getAccountSettingsById(user_id).isPresent()) {
//            accountSettingsService.deleteAccountSettings(user_id);
//            ReqRes response = new ReqRes();
//            response.setStatusCode(204);
//            response.setMessage("Account settings deleted successfully");
//            return ResponseEntity.noContent().build();
//        } else {
//            ReqRes response = new ReqRes();
//            response.setStatusCode(404);
//            response.setMessage("Account settings not found");
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//}
