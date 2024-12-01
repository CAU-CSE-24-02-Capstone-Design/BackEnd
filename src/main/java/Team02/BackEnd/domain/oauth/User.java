package Team02.BackEnd.domain.oauth;


import Team02.BackEnd.domain.BaseEntity;
import Team02.BackEnd.domain.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {
                                "oauth_server_id",
                                "oauth_server"
                        }
                ),
        }
)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private OauthId oauthId;

    @Column
    private String voiceUrl;

    @Column(name = "level1_question_number")
    private Long level1QuestionNumber;

    @Column(name = "level2_question_number")
    private Long level2QuestionNumber;

    @Column(name = "level3_question_number")
    private Long level3QuestionNumber;

    @Column
    private Long analyzeCompleteAnswerIndex;

    public void updateVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public void updateRole() {
        this.role = Role.USER;
    }

    public void updateAnalyzeCompleteAnswerIndex(final Long analyzeCompleteAnswerIndex) {
        this.analyzeCompleteAnswerIndex = analyzeCompleteAnswerIndex;
    }

    public Long getQuestionNumber(final Long level) {
        if (level == 1) {
            return this.level1QuestionNumber;
        } else if (level == 2) {
            return this.level2QuestionNumber;
        } else if (level == 3) {
            return this.level3QuestionNumber;
        }
        return null;
    }

    public void addQuestionNumber(final Long level) {
        if (level == 1) {
            this.level1QuestionNumber++;
        } else if (level == 2) {
            this.level2QuestionNumber++;
        } else if (level == 3) {
            this.level3QuestionNumber++;
        }
    }

    public void minusQuestionNumber(final Long level) {
        if (level == 1) {
            this.level1QuestionNumber = Math.max(1, this.level1QuestionNumber - 1);
        } else if (level == 2) {
            this.level2QuestionNumber = Math.max(1, this.level2QuestionNumber - 1);
        } else if (level == 3) {
            this.level3QuestionNumber = Math.max(1, this.level3QuestionNumber - 1);
        }
    }
}
