package toy_project.mentor_pairing.domain;

public enum ApplicationStatus {
    APPROVED,   //관리자가 승인한 상태
    PENDING,    //승인 대기 상태
    CANCELED,   //신청자가 취소한 상태
    DISAPPROVAL //관리자가 거절한 상태
}
