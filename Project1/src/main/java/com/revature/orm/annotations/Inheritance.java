package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// DO THIS LATER
@Target()
@Retention(RetentionPolicy.RUNTIME)
public @interface Inheritance {
}

// Hibernate supports three basic inheritance mapping strategies
    // Table per class hierarchy
    // Table per subclass
    // Table per concrete class

// Hibernate also supports a fourth, slightly different kind of polymorphism:
    // implicit polymorphism


/*
*
* See Hibernate Inheritance Mapping Annotations section for details.
1
2

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
*
*
*
*
*
*
*
*
* To understand Inheritance Mapping annotations, you must first understand Inheritance Mapping in Hiberate in detail. Once you understand Inheritance mapping concepts, please review below for annotations to be used.

    table per class hierarchy - single table per Class Hierarchy Strategy: the <subclass> element in Hibernate
    1
    2
    3
    4
    5
    6
    7
    8
    9
    10

    @Entity
    @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name="planetype", discriminatorType=DiscriminatorType.STRING )

    @DiscriminatorValue("Plane")
    public class Plane { ... }

    @Entity
    @DiscriminatorValue("A320")
    public class A320 extends Plane { ... }
    table per class/subclass - joined subclass Strategy: the <joined-subclass> element in Hibernate
    1
    2
    3
    4
    5
    6
    7

    @Entity
    @Inheritance(strategy=InheritanceType.JOINED)
    public class Boat implements Serializable { ... }

    @Entity
    @PrimaryKeyJoinColumn
    public class Ferry extends Boat { ... }
    table per concrete class - table per Class Strategy: the <union-class> element in Hibernate
    1
    2
    3

    @Entity
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    public class Flight implements Serializable { ... }
    Hibernate Annotation Tip 	Note: This strategy does not support the IDENTITY generator strategy: the id has to be shared across several tables. Consequently, when using this strategy, you should not use AUTO nor IDENTITY.
* */


