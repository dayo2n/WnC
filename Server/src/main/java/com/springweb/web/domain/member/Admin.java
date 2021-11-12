package com.springweb.web.domain.member;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("A")
public class Admin extends Member{

    protected Admin() {
        designateAdmin();//어드민으로 지정
    }



}
