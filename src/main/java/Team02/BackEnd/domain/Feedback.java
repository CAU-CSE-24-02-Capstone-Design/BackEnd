package Team02.BackEnd.domain;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "feedback", indexes = {
        @Index(name = "idx_user_createdAt", columnList = "user_id, created_at DESC")
})
public class Feedback extends BaseEntity {

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beforeAudioLink;

    @Column(length = 1000)
    private String beforeScript;

    private String afterAudioLink;

    @Column(length = 1000)
    private String afterScript;

    @Column(length = 1000)
    private String feedbackText;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateFeedbackData(final GetFeedbackToFastApiDto updateFeedback) {
        this.beforeScript = updateFeedback.getBeforeScript();
        this.afterAudioLink = updateFeedback.getAfterAudioLink();
        this.afterScript = updateFeedback.getAfterScript();
        this.feedbackText = updateFeedback.getFeedbackText();
    }
}
