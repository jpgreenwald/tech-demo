package io.swsb.entity;

import io.swsb.rest.Views;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by swsb
 */
@Entity
@Table
public class User
{
    @JsonView(Views.Private.class)
    @Id
    private Long id;

    @Column
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
