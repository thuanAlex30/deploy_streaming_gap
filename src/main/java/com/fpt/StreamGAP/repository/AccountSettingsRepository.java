package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingsRepository extends JpaRepository<AccountSettings, Integer> {
}
