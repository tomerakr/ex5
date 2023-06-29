package hac.beans;

import hac.beans.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SettingsRepo extends JpaRepository<Settings, Long> {
    Settings findFirstByOrderByIdDesc();
}
