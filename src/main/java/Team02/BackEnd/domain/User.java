package Team02.BackEnd.domain;

import Team02.BackEnd.dto.user.UserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "DTYPE")
@SuperBuilder
public abstract class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String familyCode;
    private boolean isParent;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 가족 정보 update 메소드
    public void update(UserRequest.UserFamilyInfoRequestDTO userFamilyInfoRequestDTO) {
        this.familyCode = userFamilyInfoRequestDTO.getFamilyCode();
        this.isParent = userFamilyInfoRequestDTO.getIsParent().equals("true");
    }
}
