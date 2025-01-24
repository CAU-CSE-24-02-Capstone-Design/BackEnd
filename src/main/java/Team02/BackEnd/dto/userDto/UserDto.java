package Team02.BackEnd.dto.userDto;

import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @Builder
    public static class UserAnswerIndexDto {
        private Long id;
        private Long analyzeCompleteAnswerIndex;

        public UserAnswerIndexDto() {
        }

        public UserAnswerIndexDto(final Long id, final Long analyzeCompleteAnswerIndex) {
            this.id = id;
            this.analyzeCompleteAnswerIndex = analyzeCompleteAnswerIndex;
        }
    }

    @Getter
    @Builder
    public static class UserVoiceDto {
        private Long id;
        private String name;
        private String voiceUrl;

        public UserVoiceDto() {

        }

        public UserVoiceDto(final Long id, final String name, final String voiceUrl) {
            this.id = id;
            this.name = name;
            this.voiceUrl = voiceUrl;
        }
    }
}
