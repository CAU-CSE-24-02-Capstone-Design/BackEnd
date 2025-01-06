package Team02.BackEnd.dto.userDto;

import lombok.Getter;

public class UserDto {

    @Getter
    public static class UserAnswerIndexDto {
        private Long id;
        private Long analyzeCompleteAnswerIndex;

        public UserAnswerIndexDto() {
        }

        public UserAnswerIndexDto(Long id, Long analyzeCompleteAnswerIndex) {
            this.id = id;
            this.analyzeCompleteAnswerIndex = analyzeCompleteAnswerIndex;
        }
    }

    @Getter
    public static class UserVoiceDto {
        private Long id;
        private String name;
        private String voiceUrl;

        public UserVoiceDto() {

        }

        public UserVoiceDto(Long id, String name, String voiceUrl) {
            this.id = id;
            this.name = name;
            this.voiceUrl = voiceUrl;
        }
    }
}
