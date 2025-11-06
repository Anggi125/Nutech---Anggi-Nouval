package com.example.simsppob.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simsppob.models.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}