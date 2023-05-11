package com.example.jscode.entity;

import javax.persistence.*;

@Entity
@Table (name ="board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="boardTitle")
    private String boardTitle;

    @Column(name="boardContents",length = 200, nullable = false)
    private String boardContents;


}
