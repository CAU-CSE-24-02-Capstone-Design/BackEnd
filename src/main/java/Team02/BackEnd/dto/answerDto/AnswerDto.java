package Team02.BackEnd.dto.answerDto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class AnswerDto {

    @Getter
    @Builder
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
    @Builder
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