package com.example.jwtmember.entity;


import com.example.jwtmember.util.CommonUtils;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedBy
    @Column(name = "CRT_ID")
            @Comment("등록자")
            private String crtId;

            @CreatedDate
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Column(name = "CRT_DT")
            @Comment("등록일")
            private LocalDateTime crtDt;

            @Column(name = "CRT_IP_ADDR")
            @Comment("등록IP")
            private String crtIpAddr;

            @LastModifiedBy
            @Column(name = "UPD_ID")
            @Comment("수정자")
            private String updId;

            @LastModifiedDate
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Column(name = "UPD_DT")
            @Comment("수정일")
            private LocalDateTime updDt;

            @Column(name = "UPD_IP_ADDR")
            @Comment("수정IP")
            private String updIpAddr;

            @PrePersist
            public void prePersist() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (servletRequestAttributes != null) {
                            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
                            this.crtIpAddr = CommonUtils.getRemoteAddr(httpServletRequest);
                    }
            }

            @PreUpdate
            public void preUpdate() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (servletRequestAttributes != null) {
                          HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
                         this.updIpAddr = CommonUtils.getRemoteAddr(httpServletRequest);
                    }
            }
}
