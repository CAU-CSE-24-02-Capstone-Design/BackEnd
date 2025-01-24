package Team02.BackEnd.service.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class CalendarManagerTest {

    @Mock
    private CalendarCheckService calendarCheckService;

    @InjectMocks
    private CalendarManager calendarManager;

    private String accessToken;
    private String year;
    private String month;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        year = "2025";
        month = "1";
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getDatesWhenUserDid() {
        // given

        // when
        given(calendarCheckService.getDatesWhenUserDid(accessToken, year, month)).willReturn(new Long[31]);

        Long[] result = calendarManager.getDatesWhenUserDid(accessToken, year, month);

        // then
        assertThat(result.length).isEqualTo(31);
    }
}