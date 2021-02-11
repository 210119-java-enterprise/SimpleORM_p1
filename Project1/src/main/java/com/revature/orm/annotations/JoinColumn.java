package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
}

/*
* Use @JoinColumn annotation for one-to-one or many-to-one associations when foreign key is held by one of the entities. We can use @OneToOne or @ManyToOne mappedBy attribute for bi-directional relations.
* Also see OneToOne and ManyToOne sections for more details.
1
2
3

@ManyToOne
@JoinColumn(name = "statusId")
private CompanyStatus status;
*
* */
