package Team02.BackEnd.domain;

import Team02.BackEnd.domain.oauth.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Answer extends BaseEntity {

    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //todo: 나중에 리포트에서 이 앤써부터 이 앤써까지의 분석내용이다~ 하고 해당거 누르면 해당 피드백 페이지로 넘어갈 수 있었음 좋겠다.
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;

    private int evaluation;

    public void updateEvaluation(final int evaluation) {
        this.evaluation = evaluation;
    }
}
