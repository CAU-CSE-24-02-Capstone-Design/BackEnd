package Team02.BackEnd.domain.oauth;


import Team02.BackEnd.domain.BaseEntity;
import Team02.BackEnd.domain.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "question_number")
    @ColumnDefault("1") // 디폴트 1 (첫번째 질문)
    private Long questionNumber; // 현재 몇번째 question인지 (질문 중복 방지)

    public void updateVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }
}
