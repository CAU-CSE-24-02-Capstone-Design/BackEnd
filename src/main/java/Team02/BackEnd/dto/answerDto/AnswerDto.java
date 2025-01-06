package Team02.BackEnd.dto.answerDto;

import java.time.LocalDateTime;
import lombok.Getter;

public class AnswerDto {

    @Getter
    public static class AnswerIdDto {
        private Long id;
        private LocalDateTime createdAt;

        public AnswerIdDto() {

        }

        public AnswerIdDto(Long id, LocalDateTime createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    @Getter
    public static class AnswerLevelDto {
        private Long id;
        private LocalDateTime createdAt;
        private Long level;

        public AnswerLevelDto() {

        }

        public AnswerLevelDto(Long id, LocalDateTime createdAt, Long level) {
            this.id = id;
            this.createdAt = createdAt;
            this.level = level;
        }
    }

    @Getter
    public static class AnswerQuestionDto {
        private Long id;
        private String description;

        public AnswerQuestionDto() {

        }

        public AnswerQuestionDto(Long id, String description) {
            this.id = id;
            this.description = description;
        }
    }
}