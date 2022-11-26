package com.messenger.mess.model;

import javax.persistence.*;

@Entity
@Table(name = "friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    private FriendRequestStatus status;

    public FriendRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public FriendRequest setFromUser(User fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public FriendRequest setToUser(User toUser) {
        this.toUser = toUser;
        return this;
    }

    public FriendRequest setStatus(FriendRequestStatus status) {
        this.status = status;
        return this;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }
}
