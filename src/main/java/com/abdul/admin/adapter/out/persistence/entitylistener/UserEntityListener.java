package com.abdul.admin.adapter.out.persistence.entitylistener;

import com.abdul.admin.adapter.out.persistence.entity.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Objects;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEntityListener {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PrePersist
    @PreUpdate
    public void prePersist(User user) {
        if (Objects.nonNull(user.getPassword())) {
            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }
    }

    /*
        private static final Integer PADDING_SIZE = 8;

        public static String prepareDisplayableId(String prefix, Long id) {
            String format = "%0" + PADDING_SIZE + "d";
            var formattedNumber = String.format(format, id);
            return prefix + formattedNumber;
        }

        @PostPersist
        @PostUpdate
        public void setVersionNumber(User sample) {
            if (smartContract.getId() != null) {
                String displayableId = prepareDisplayableId("V", sample.getId());
                sample.setVersion(displayableId);
            }
        }
    */
}
