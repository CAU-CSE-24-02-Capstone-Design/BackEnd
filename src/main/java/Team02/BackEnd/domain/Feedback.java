package Team02.BackEnd.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private String beforeScript;
    private String afterAudioLink;
    private String afterScript;
    private String feedbackText;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public void updateBeforeAudioLink(String beforeAudioLink) {
        this.beforeAudioLink = beforeAudioLink;
    }
}
