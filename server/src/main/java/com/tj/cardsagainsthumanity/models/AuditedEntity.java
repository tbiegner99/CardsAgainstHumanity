package com.tj.cardsagainsthumanity.models;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public abstract class AuditedEntity {
    @Basic
    private Date created;
    @Basic
    private Date updated;
    private Boolean deleted = false;

    public AuditedEntity() {
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void onPrePersist() {
    }

    public void onPreUpdate() {
    }

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        setCreated(now);
        setUpdated(now);
        onPrePersist();
    }

    @PreUpdate
    protected void onUpdate() {
        Date now = new Date();
        setUpdated(now);
        onPreUpdate();
    }
}
