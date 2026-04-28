package com.kesi.tracker.group.domain;

public enum MemberCountChange {
    INCREMENT,
    DECREMENT,
    NONE;

    public static MemberCountChange from(
            GroupMemberStatus before,
            GroupMemberStatus after
    ){
        boolean wasMemberCountable = before.isCountableMember();
        boolean isMemberCountable = after.isCountableMember();

        if(!wasMemberCountable && isMemberCountable) {
            return MemberCountChange.INCREMENT;
        }

        if(wasMemberCountable && !isMemberCountable) {
            return MemberCountChange.DECREMENT;
        }

        return MemberCountChange.NONE;
    }
}
