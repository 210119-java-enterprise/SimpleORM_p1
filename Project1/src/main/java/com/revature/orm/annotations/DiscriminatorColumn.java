package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// The DiscriminatorColumn annotation is used to customize the discriminator column. The discriminator column is used so that the ORM can differentiate the records that are going to be stored in the same table
// The default column is called DTYPE and it has the name of the entity as a value
// The ElementType.Type is used for Class, interface (including annotation type), or enum declaration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscriminatorColumn {

    public String name() default "Make Sure to Default In the Business Logic To The Name Of The Entity";
    // Hibernate's second value is an ENUM. Make this entire annotation after you finish the basics and if you have time
}

/*


See Hibernate Inheritance Mapping Annotations section for details.
1
2
3

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="planetype", discriminatorType=DiscriminatorType.STRING )







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
*
*
* */
