package org.example.len.ch14_channel;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class EnumTest {

//    @Test
//    void alwaysTrueTest() {
//        assertThat(ServiceType.SERVICE_A.getDisplayTypes()).contains(DisplayType.DISPLAY_1);
//    }

    @Test
    void circle() {
        assertThat(DisplayType.DISPLAY_1.getServiceType().getDisplayTypes())
                .contains(DisplayType.DISPLAY_1, DisplayType.DISPLAY_2);
    }

    @Test
    void circle2() {
        assertThat(ServiceType.get("SERVICE_A"))
                .contains(DisplayType.DISPLAY_1, DisplayType.DISPLAY_2);
    }

}