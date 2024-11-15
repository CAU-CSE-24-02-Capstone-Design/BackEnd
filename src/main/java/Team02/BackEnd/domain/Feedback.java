package Team02.BackEnd.domain;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beforeAudioLink;

    @Column(columnDefinition = "TEXT")
    private String beforeScript;

    private String afterAudioLink;

    @Column(columnDefinition = "TEXT")
    private String afterScript;

    @Column(columnDefinition = "TEXT")
    private String feedbackText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateFeedbackData(final GetFeedbackToFastApiDto updateFeedback) {
        this.beforeScript = updateFeedback.getBeforeScript();
        this.afterAudioLink = updateFeedback.getAfterAudioLink();
        this.afterScript = updateFeedback.getAfterScript();
        this.feedbackText = updateFeedback.getFeedbackText();
    }
}
