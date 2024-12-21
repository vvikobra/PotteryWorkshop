package com.example.potteryworkshop.repositories.Impl;

import com.example.potteryworkshop.models.entities.Status;
import com.example.potteryworkshop.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StatusRepositoryImpl extends BaseRepository<Status, UUID> {

    protected StatusRepositoryImpl() {
        super(Status.class);
    }
}
