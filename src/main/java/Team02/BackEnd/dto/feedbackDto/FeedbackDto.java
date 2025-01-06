package Team02.BackEnd.dto.feedbackDto;

import lombok.Getter;

public class FeedbackDto {

    @Getter
    public static class FeedbackAudioLinkDto {
        private String beforeAudioLink;

        public FeedbackAudioLinkDto() {

        }

        public FeedbackAudioLinkDto(String beforeAudioLink) {
            this.beforeAudioLink = beforeAudioLink;
        }
    }
}