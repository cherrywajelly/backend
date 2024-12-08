package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class JamIntegrationTest extends TestContainerSupport {
    private final JamService jamService;

    @Autowired
    public JamIntegrationTest(final JamService jamService) {
        this.jamService = jamService;
    }

    @Test
    @DisplayName("")
    public void jamIntegrationTest() {

    }
}
