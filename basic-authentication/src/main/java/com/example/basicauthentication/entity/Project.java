package com.example.basicauthentication.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "project_table")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String intro;
    private String owner;
    private Long ownerId;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
//    private List<Long> membersList;
    @ManyToMany(targetEntity = User.class,cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "project_member_list",
//            joinColumns = @JoinColumn(name = "project_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id")
//    )
    @JoinColumn(name = "fk_project_meber",referencedColumnName = "id")
    private List<User> members;


    public List<Long> getMembersIdList() {
        List<Long> ids=new ArrayList<>();
        for(User member : members){
            ids.add(member.getId());
        }
        return ids;
    }
}
