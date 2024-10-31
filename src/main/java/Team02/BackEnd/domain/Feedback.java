package Team02.BackEnd.domain;

import Team02.BackEnd.domain.oauth.User;
import jakarta.persistence.*;
import lombok.*;

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
    private String beforeScript;
    private String afterAudioLink;
    private String afterScript;
    private String feedbackText;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String beforeScript, String afterAudioLink, String afterScript, String feedbackText) {
        this.beforeScript = beforeScript;
        this.afterAudioLink = afterAudioLink;
        this.afterScript = afterScript;
        this.feedbackText = feedbackText;
    }
}
