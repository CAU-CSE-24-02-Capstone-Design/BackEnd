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
public class SelfFeedback extends BaseEntity {

    @Id
    @Column(name = "self_feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String good;
    private String bad;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
