package Team02.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedbackRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserFeedbackDto{
        String beforeAudioLink;
        String beforeScript;
        String afterAudioLink;
        String afterScript;
        String feedbackText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetStatisticsFeedbackDto{
        Long gantour; // 간투어 횟수
        Long silentTime; // 침묵 시간 초
        Long wrongWord; // 잘못된 단어 사용 횟수
        Long context; // 잘못된 문맥 횟수

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFeedbackDto {
        GetUserFeedbackDto getUserFeedbackDto;
        GetStatisticsFeedbackDto getStatisticsFeedbackDto;
    }
}
