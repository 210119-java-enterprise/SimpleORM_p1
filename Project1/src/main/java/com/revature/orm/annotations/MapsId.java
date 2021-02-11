package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MapsId {
}

/*
*
* Persist two entities with shared key (when one entity holds a foreign key to the other) using @MapsId annotation. See OneToOne section for details.
1
2
3
4

@OneToOne
@MapsId
@JoinColumn(name = "contactId")
private Contact contact;
* */
